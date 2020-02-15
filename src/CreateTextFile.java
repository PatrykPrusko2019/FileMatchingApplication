import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * creates 2 text file : mainFile.txt and transactionFile.txt
 */
public class CreateTextFile {

    private Scanner input = new Scanner(System.in);
    private Path mainFile, transactionFile;

    public CreateTextFile(Path pathToMainFile, Path pathToTransactionFile) {
        this.mainFile = pathToMainFile;
        this.transactionFile = pathToTransactionFile;
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

    public void checksForAnyTxtFile() {
        File file = getMainFile().toFile();
        File file2 = getTransactionFile().toFile();

        checkTheFiles(file, file2);

        choiceUser();

    }

    private boolean checkTheFiles(File file, File file2) {
        System.out.println("******************\n");
        if( file.exists() || file2.exists() ) { // if files exist, notify the user
            System.out.println("full file -> filesText, there are text files : main file and transaction file\n\n" +
                    "******************\n");
            return true;
        } else {
            System.out.println("empty file -> filesText\n\n" + "******************\n");
            return false;
        }
    }


    /**
     * user selection
     */
    private void choiceUser() {
            System.out.println("******************\nFile Comparison Program\n******************\n" + "what you want to do, select the appropriate option:");
            showOptions();
    }

    private void showOptions() {
        showMenu();

        boolean exit = true;

        while(exit) {
            exit = false;
            System.out.print("> ");
            if(input.hasNextInt()) {
                int valueByUser = input.nextInt();
                input.nextLine();

                switch (valueByUser) {
                    case 1: {
                        System.out.println("*******************************\nWE CREATE 2 NEW FILES: main and transactional file\n" +
                                "*******************************\n");
                        createTwoFilesByTheUser();
                        System.out.println("currently created 2 files by the user:");
                        showTwoFiles();
                        break;
                    }
                    case 2: {
                        System.out.println("*******************************\nWE CREATE 2 NEW FILES AUTOMATICALLY: main file and transactional file\n" +
                                "*******************************\n");
                        createTwoFilesAutomatically();
                        System.out.println("currently created 2 files automatically:");
                        showTwoFiles();
                        break;
                    }
                    case 3: {
                        System.out.println("*******************************\nshow current 2 files\n" +
                                "*******************************\n");
                        if(checkTheFiles(getMainFile().toFile(), getTransactionFile().toFile())) {
                            showTwoFiles();
                        } else {
                            System.out.println("no Main file and no transaction file ...");
                        }
                        showMenu();
                        exit = true; //continue
                        break;
                    }
                    case 4: {
                        System.out.println("*******************************\n2 current files\n" +
                                "*******************************\n");
                        System.out.println("2 current files:");
                        showTwoFiles();
                        break;
                    }
                    case 5: {
                        System.out.println("*******************************\nexit\n" +
                                "*******************************\n");
                        System.exit(0); //exit from application
                    }
                    default: {
                        System.out.println("wrong value, range 1 - 4");
                        exit = true; //continue
                    }
                }

            } else {
                System.out.println("wrong value ... please again");
                input.nextLine();
            }
        }

    }

    private void showTwoFiles() {
        ReadTextFile.ShowReadTextFromFile(getMainFile(), getTransactionFile());
        System.out.println("\n\n");
    }

    private void showMenu() {
        System.out.println("1. Creates new 2 text files by user\n2. Creates new 2 text files automatically" +
                "\n3. show current 2 files\n4. use the current two files and show the result\n5. exit ");
    }


    /**
     * for example : 200 Patrick Pallot 8909.22
     *               600 Andrew Jakd 899
     */
    public void createTwoFilesByTheUser() {

        System.out.println("Provide customer data as in the example:\n" +
                "300 Patryk Prusko 9000.99\n" +
                "(use spaces between data, don't use comma)");

        boolean exit = true;
        try(  BufferedWriter output = Files.newBufferedWriter(getMainFile() )  ) { //oldmast.txt

            while(exit) {
                System.out.printf("%s%n",
                        "give me a number of account, name , surname, balance( for example: 100 Patrick Pat 567.99 or Pati Pat 200 -13.92 ):" );
                try {

                    output.write( String.format("%d %s %s %.2f%n", input.nextInt(), input.next(),
                            input.next(), input.nextDouble()) ); //adds new record to mainFile.txt
                    System.out.println("adding a new record ...");

                } catch (NoSuchElementException ex) {
                    System.err.println("error , wrong value");
                    input.nextLine();
                }

                 exit = checkTheUserDecision();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        exit = true;
        try(Formatter output = new Formatter( getTransactionFile().toString() ) ) { //another way



            while(exit) {

                System.out.printf("%s%n", "give me the account number for the transaction, transaction amount( for example: 100 567.99 or 200 -13.92 ): ");

                try {

                    output.format("%d %.2f%n", input.nextInt(), input.nextDouble()); //adds new record to transactionFile.txt
                    System.out.println("adding a new record ...");
                } catch (NoSuchElementException ex) {
                    System.err.println("error, wrong value");
                    input.nextLine();
                }

                exit = checkTheUserDecision();
            }

        } catch (SecurityException | FileNotFoundException |
                FormatterClosedException e) {
            e.printStackTrace();
        }

    }

    // checks the user's decision
    private boolean checkTheUserDecision() {

        boolean exit = false, decision = false;
        System.out.println("if you want to finish adding records, press: y or continue press : n");
        while(! exit) {

                String str = input.next();
                if (str.equals("y")) {
                    exit = true;
                } else if (str.equals("n")) {
                    decision = true;
                    exit = true;
                } else {
                    System.err.println("wrong value ... please again");
                    System.out.println("if you want to finish adding records, press: y or continue press : n");
                    input.nextLine();
                }

        }
        return decision;
    }

    /**
     * Sort records by account number in ascending orde
     * @param pathOfMainFile
     * @param
     * @return accounts
     */
    public Accounts segregateRecordsInTheMainTextFile(Path pathOfMainFile)  {

       Accounts accounts = new Accounts();
       accounts = accounts.getAccounts(pathOfMainFile, accounts);

        return accounts;
    }


    private void createTwoFilesAutomatically() {

        File fileMain = new File(getMainFile().toString());
        File fileTransaction = new File(getTransactionFile().toString());

        try (FileWriter output = new FileWriter(fileMain) ) {

            output.write("100 Jan Kowalski 24.98\n200 Anna Nowak -345.67\n800 Jakub Sroka 224.62\n" +
                    "400 Ola Rudnik -42.16\n" +
                    "300 Zofia Czekaj 0.00\n" +
                    "500 Jakub Sroka 224.62\n" +
                    "700 Artur Mistrz 800.00\n" +
                    "500 Jakub Sroka 32.00\n" +
                    "300 Zo Fa 0.00");
            System.out.println("created mainFile.txt");
        } catch (IOException e) {
            System.err.println("problem file main txt");
            e.printStackTrace();
        }

        try (FileWriter output = new FileWriter(fileTransaction)) {

            output.write("100 27.14\n" +
                        "300 0\n"      +
                        "900 82.17\n"  +
                        "400 66.56\n"  +
                        "400 -6.56\n"  +
                        "500 -30.12\n" +
                        "400 0.00\n"   +
                        "200 23.56\n"  +
                        "200 -36.56\n" +
                        "90 -36.56\n" +
                        "-900 -36.56\n" +
                        "700 -30.00");
            System.out.println("created transactionFile.txt");

        } catch (IOException e) {
            System.err.println("problem file transaction txt");
            e.printStackTrace();
        }

    }

    /**
     * sorts the transaction file in ascending order
     * @param pathToTransactionFile
     * @return
     */
    public TransactionRecord segregateRecordsInTheTransactionTextFile(Path pathToTransactionFile) {
        TransactionRecord transactionRecord = new TransactionRecord();
        transactionRecord.getTransactionRecord(pathToTransactionFile, transactionRecord );

        return transactionRecord;
    }
}



