import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionRecord {

    private List<Transaction> listOfTransactions;

    public TransactionRecord() {
        this.listOfTransactions = new ArrayList<>();
    }

    public List<Transaction> getListOfTransactions() {
        return listOfTransactions;
    }
    public void setListOfTransactions(List<Transaction> listOfTransactions) {
        this.listOfTransactions = listOfTransactions;
    }

    public TransactionRecord getTransactionRecord(Path pathToTransactionFile, TransactionRecord transactionRecord) {

        try {
            List<String> list = Files.readAllLines(pathToTransactionFile);

            for(String str : list) {
                String[] strings = str.split(" ");
                transactionRecord.getListOfTransactions().add(new Transaction(Integer.parseInt(strings[0]), Double.parseDouble(strings[1])));
            }

        } catch (IOException e) {
            System.err.println("problem with the transactionFile.txt");
            e.printStackTrace();
        }

        transactionRecord = sortAscendingByAccountNumber(pathToTransactionFile, transactionRecord);

        return transactionRecord;
    }

    /**
     * sort ascending list of accounts in transaction file
     * @param pathToTransactionFile
     * @param transactionRecord
     */
    private TransactionRecord sortAscendingByAccountNumber(Path pathToTransactionFile, TransactionRecord transactionRecord) {

        List<Transaction> sortedList = transactionRecord.getListOfTransactions();

        sortedList = sortedList.stream().sorted((o1, o2) -> o1.compareTo( o2 ) )
                .collect(Collectors.toList());

        transactionRecord.setListOfTransactions(sortedList); // assigns a new sorted list

        sortFileTransaction(transactionRecord, pathToTransactionFile);

        return transactionRecord;
    }

    /**
     * sorts the transaction file txt in ascending order
     * @param transactionRecord
     * @param pathToTransactionFile
     */
    private void sortFileTransaction(TransactionRecord transactionRecord, Path pathToTransactionFile) {

        FileWriter fileWriter = null;
        StringBuilder stringBuilder = new StringBuilder();
        List<Transaction> transactionRecordList = transactionRecord.getListOfTransactions();

        for(Transaction tr : transactionRecordList) {
            stringBuilder.append(tr.getAccountNumber() + " " + tr.getTransactionAmount() + "\n");
        }

        try {
            fileWriter = new FileWriter(pathToTransactionFile.toString());
            fileWriter.write(stringBuilder.toString()); // adds new text
            fileWriter.close();

        } catch (IOException e) {
            System.err.println("wrong file transactionFile.txt");
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        int counter = 1;
        StringBuilder stringBuilder = new StringBuilder();
        for(Transaction tr : listOfTransactions) {
            stringBuilder.append( String.format("%d %d %.2f%n" , (counter++), tr.getAccountNumber(), tr.getTransactionAmount() ) );
        }

        return String.valueOf(stringBuilder);
    }
}


