import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class ReadTextFile {


    public static void ShowReadTextFromFile(Path fileText, Path fileText2) {

        try {
            List<String> listOfRecords = Files.readAllLines(fileText);

            System.out.printf("%-33s%-18s%10s%n" ,"account number in the main file", "name and surname", "balance");
            for(String str : listOfRecords) {
                String[] strings = str.split(" ");
                System.out.printf("%-33s%-18s%10s%n", strings[0], strings[1] + " " + strings[2], strings[3] );
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("wrong file");
        }



        //another way
        try ( Scanner input = new Scanner(fileText2) ) {

            System.out.printf("%n%n%n%-40s%14s%n" ,"account number in the transaction file", "transaction amount");

            while(input.hasNext()) {
                System.out.printf("%-40s%.2f%n", input.nextInt(), input.nextDouble());
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("wrong file");
        }

    }

    public static void showReadTextFromFilesResultAndLog(Path fileResult, Path fileLog) {
        System.out.println("displays 2 new files: result.txt and log.txt:\n" +
                "- result.txt -> list of customers with their transactions (shopping or payment)\n" +
                "- log.txt -> list of account numbers that are incorrect (from the transaction file)");
        try {

            List<String> recordsOfFileResult = Files.readAllLines(fileResult);

            System.out.println("\n*********************************\nshow file result: \n\n");

            for(String record : recordsOfFileResult) {
                System.out.println(record);
            }

            System.out.println("\n*********************************\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            List<String> recordsOfFileLog = Files.readAllLines(fileLog);

            System.out.println("\n*********************************\nshow file log: \n\n");

            int i = 1;
            for(String record : recordsOfFileLog) {
                System.out.println( (i++) + ". Invalid account number:\n " + record);
            }

            System.out.println("\n*********************************\n");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
