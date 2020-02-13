import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Accounts {
    private List<Account> accountList = new ArrayList<>();

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public List<Account> getAccountList() {
        return accountList;
    }


    public Accounts getAccounts(Path pathOfMainFile, Accounts accounts) {

        try {
            List<String> list = Files.readAllLines(pathOfMainFile);

            for(String str : list) {
                String[] strings = str.split(" ");
                accounts.getAccountList().add(new Account(Integer.parseInt(strings[0]),strings[1], strings[2], Double.parseDouble(strings[3])  ));
            }

        } catch (IOException e) {
            System.err.println("problem with the mainFile.txt");
            e.printStackTrace();
        }

        accounts = sortAscendingByAccountNumber(pathOfMainFile, accounts);

        return accounts;
    }

    /**
     * sort ascending list of accounts
     * @param pathOfMainFile
     * @param accounts
     */
    private Accounts sortAscendingByAccountNumber(Path pathOfMainFile, Accounts accounts) {

        List<Account> sortedList = accounts.getAccountList();
        sortedList = sortedList.stream().sorted((o1, o2) -> o1.compareTo(o2)).collect(Collectors.toList());

        accounts.setAccountList(sortedList);

        sortMainFile(accounts, pathOfMainFile);


        return accounts;
    }


    /**
     * sorts the main txt file in ascending order
     * @param accounts
     * @param path
     */
    private void sortMainFile(Accounts accounts, Path path) {

        FileWriter fileWriter = null;
        StringBuilder stringBuilder = new StringBuilder();
        List<Account> listRecords = accounts.getAccountList();
        for(Account account : listRecords) {
            stringBuilder.append(account.getAccountNumber() + " " + account.getFirstName() + " " + account.getLastName() + " " + account.getBalance() + "\n");
        }

        try {

            fileWriter = new FileWriter(path.toString());
            fileWriter.write( stringBuilder.toString() ); // adds new text
            fileWriter.close();

        } catch (IOException e) {
            System.err.println("wrong file mainFile.txt");
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        int i = 1;
        for (Account account : accountList) {
            stringBuilder.append( (i++) + " " + account + "\n" );
        }

        return "Accounts{\n"+ stringBuilder +
                '}';
    }

}
