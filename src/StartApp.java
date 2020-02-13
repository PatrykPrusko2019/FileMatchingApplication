import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 *
 *
 The program creates 2 text files:
 - main file
 - transactional file
 he later compares these two files, by customer account number:
 - if the account numbers in both files match, it checks what the transaction was.
 - if the numbers do not match, it either adds the wrong account number to the log
 file or the given record without changes.

 As a result, it shows the results on the console in a new text file and log file

 displays 2 new files: result.txt and log.txt:
 - result.txt -> list of customers with their transactions (shopping or payment);
 if the purchase transaction is a positive transaction amount,
 if the payment transaction is a negative transaction amount

 - log.txt -> list of account numbers that are incorrect (from the transaction file)


 assumptions for transactions -> if they are positive (purchases) we add to the
 customer's balance -> 1000 + 25 = 1025 balance (purchases),
 if they are negative (payments) we also add to the customer's balance -> 1000 + (-25) = 975 balance (payment)
 */

//todo add a method: if the number of the account repeats in the main file, then
// leave the first record, but the next records will be deleted

public class StartApp {

    private CreateTextFile createTextFile;

    public static void main(String[] args) {

        new StartApp().start();
    }

    private void start() {

        Path pathToMainFile = Paths.get("src/filesText/mainFile.txt");
        Path pathToTransactionFile = Paths.get("src/filesText/transactionFile.txt");

        createTextFile = new CreateTextFile(pathToMainFile, pathToTransactionFile);

        createTextFile.checksForAnyTxtFile(); //create 2 files text -> mainFile.txt , transactionFile.txt by users or automatically

        createsObjectsAndSegregatesRecordsInFiles(pathToMainFile, pathToTransactionFile);

    }


    private void createsObjectsAndSegregatesRecordsInFiles(Path pathToMainFile, Path pathToTransactionFile) {

        Accounts accounts = createTextFile.segregateRecordsInTheMainTextFile(pathToMainFile);

        TransactionRecord transactionRecord = createTextFile.segregateRecordsInTheTransactionTextFile(pathToTransactionFile);

        FileMatch fileMatch = new FileMatch(accounts, transactionRecord, pathToMainFile, pathToTransactionFile);

        fileMatch.matchTheGivenFiles(); // matching records

        System.out.println("displays 2 files after increasing segregation (account number): ");
        ReadTextFile.ShowReadTextFromFile(pathToMainFile, pathToTransactionFile);
        System.out.println("\n\n");
        ReadTextFile.showReadTextFromFilesResultAndLog ( fileMatch.getResultFile(), fileMatch.getLogFile() ); //displays the result after changes to 2 files:
                                                                                                              // the main file and the transaction file
    }

}
