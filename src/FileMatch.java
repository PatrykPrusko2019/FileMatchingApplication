import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * matches files
 */
public class FileMatch {

    Accounts accouns;
    TransactionRecord transactionRecord;
    Path mainFile, transactionFile, logFile, resultFile;


    public FileMatch(Accounts accounts, TransactionRecord transactionRecord, Path pathToMainFile, Path pathToTransactionFile) {
        this.accouns = accounts;
        this.transactionRecord = transactionRecord;
        this.mainFile = pathToMainFile;
        this.transactionFile = pathToTransactionFile;
        this.resultFile = Paths.get("src/filesText/result.txt");
        this.logFile = Paths.get("src/filesText/log.txt");
    }

    public Path getMainFile() {
        return mainFile;
    }
    public void setMainFile(Path mainFile) {
        this.mainFile = mainFile;
    }
    public Path getTransactionFile() {
        return transactionFile;
    }
    public void setTransactionFile(Path transactionFile) {
        this.transactionFile = transactionFile;
    }
    public Accounts getAccouns() {
        return accouns;
    }
    public void setAccouns(Accounts accouns) {
        this.accouns = accouns;
    }
    public TransactionRecord getTransactionRecord() {
        return transactionRecord;
    }
    public void setTransactionRecord(TransactionRecord transactionRecord) {
        this.transactionRecord = transactionRecord;
    }
    public Path getLogFile() {
        return logFile;
    }
    public Path getResultFile() {
        return resultFile;
    }
    /**
     * match data records from 2 text files by account number
     */
    public void matchTheGivenFiles() {


        List<Account> recordsOfAccount= getAccouns().getAccountList();
        List<Transaction> recordsOfTransaction = getTransactionRecord().getListOfTransactions();
        boolean[] transactionInstanceCounter = new boolean[recordsOfTransaction.size() + 1];

        Account customerRecord;
        StringBuilder newMainFile = new StringBuilder();
        int counter = 0;

        for(int i = 0; i < recordsOfAccount.size(); i++, counter = 0) {

           customerRecord = recordsOfAccount.get(i);

            //compares by account number
            newMainFile.append(String.format("%d. customer of bank%n%s%n", (i+1), customerRecord));

            for(int j = 0; j < recordsOfTransaction.size(); j++) {

                if( customerRecord.combine(recordsOfTransaction.get(j)) ) { // if true they are the same
                    String str = getResultOfTransaction(recordsOfTransaction.get(j).getTransactionAmount() );
                    newMainFile.append(String.format("account balance: %.2f -> %s%n", customerRecord.getBalance(), str) );
                    counter++;
                }
            }

            if(counter == 0) { // if there were no transactions, rewrite the given record without any changes
                newMainFile.append(String.format("account balance: %.2f, no transactions%n", customerRecord.getBalance() ) );
            }
        }

        transactionInstanceCounter = returnIndexWhereNoTransactions(transactionInstanceCounter, recordsOfAccount, recordsOfTransaction);


        createsResultFileAndLogFile(newMainFile, transactionInstanceCounter); // creates a new result.txt and log.txt file

    }

    /**
     * returns boolean arrays with indexes that are invalid true -> transactions
     * @param transactionInstanceCounter
     * @param recordsOfAccount
     * @param recordsOfTransaction
     * @return boolean result
     */
    private boolean[] returnIndexWhereNoTransactions(boolean[] transactionInstanceCounter, List<Account> recordsOfAccount, List<Transaction> recordsOfTransaction) {
        int transaction, counter = 0;

        for(int i = 0; i < recordsOfTransaction.size(); i++, counter = 0) {
            transaction = recordsOfTransaction.get(i).getAccountNumber();
                    for(int j = 0; j < recordsOfAccount.size(); j++) {

                        if( transaction == recordsOfAccount.get(j).getAccountNumber() ) {
                            counter++;
                        }
                    }

                    if(counter == 0) {
                        transactionInstanceCounter[i+1] = true;
                    }
        }
        return transactionInstanceCounter;
    }


    /**
     * if the transaction amount is positive return "purchases" if the transaction
     * amount is negative return "payment"
     * @param transactionAmount
     * @return
     */
    private String getResultOfTransaction(double transactionAmount) {
        if(transactionAmount == 0) {
            return "zero transaction";
        } else if(transactionAmount > 0) {
            return "shopping transaction";
        } else {
            return "payment transaction";
        }
    }

    /**
     * creates 2 files: new main file and file with transaction logs
     * @param newMainFile
     * @param transactionInstanceCounter
     */
    private void createsResultFileAndLogFile(StringBuilder newMainFile, boolean[] transactionInstanceCounter) {

        //first creates the result file
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(resultFile.toString());

            fileWriter.write(newMainFile.toString());

        } catch (IOException ex) {
            System.err.println("wrong result.txt");
            ex.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //next creates the log file

        try {
            fileWriter = new FileWriter(logFile.toString());

            newMainFile = createTextTologFile(transactionInstanceCounter);

            fileWriter.write(newMainFile.toString());

        } catch (IOException ex) {
            System.err.println("wrong log.txt");
            ex.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * creates text into a log file
     * @param transactionInstanceCounter
     * @return
     */
    private StringBuilder createTextTologFile(boolean[] transactionInstanceCounter) {
        //todo logi gdy false transactionInstanceCounter[1] == recordsOfTransaction[0] to dopisujemy do log.txt
        StringBuilder textToLogFile = new StringBuilder();
        List<Transaction> recordsOfTransaction = getTransactionRecord().getListOfTransactions();

        for(int i = 0; i < transactionInstanceCounter.length-1; i++) {

            if(transactionInstanceCounter[i+1] == true) { //when true -> then the given account number did not appear and saves to log.txt
                textToLogFile.append(recordsOfTransaction.get(i).getAccountNumber() + " " + recordsOfTransaction.get(i).getTransactionAmount() + "\n");
            }
        }
        return textToLogFile;
    }
}
