import java.util.ArrayList;
import contas.*;

public class AccountManager {

    public AccountManager(double yieldPercentage) {
        this.yieldPercentage = yieldPercentage;
    }

    private double yieldPercentage = 10;

    private ArrayList<Account> accounts = new ArrayList<Account>();

    public void add(Account account) {
        if (account != null)
            if (searchAccount(account.getNumber()) == null)
                accounts.add(account);
    }

    public boolean remove(int number) {
        return accounts.removeIf(acc -> acc.getNumber() == number);
    }

    public Account searchAccount(int number) {
        return accounts.stream().filter(acc -> acc.getNumber() == number).findFirst().orElse(null);
    }

    public String searchSpecialAccounts() {
        return accounts.stream().filter(acc -> acc instanceof SpecialAccount).map(acc -> acc.toString() + "\n")
                .reduce("", (str, add) -> str + add);
    }

    public String searchSavingsAccounts() {
        return accounts.stream().filter(acc -> acc instanceof SavingsAccount).map(acc -> acc.toString() + "\n")
                .reduce("", (str, add) -> str + add);
    }

    public String searchClientsOnLimit() {
        return accounts.stream().filter(acc -> acc instanceof CurrentAccount).map(CurrentAccount.class::cast)
                .filter(acc -> acc.isOnLimit()).map(acc -> acc.toString() + "\n").reduce("", (str, add) -> str + add);
    }

    public String listAccounts() {
        return accounts.stream().map(acc -> acc.toString() + "\n").reduce("", (str, add) -> str + add);
    }

    public boolean transfer(int origin, int target, double value) {
        var sacarOK = withdraw(origin, value);
        if (!sacarOK)
            return sacarOK;
        var depotOK = deposit(target, value);
        if (!depotOK) {
            deposit(origin, value);
            return depotOK;
        }
        return true;
    }

    public boolean deposit(int accountNumber, double value) {
        var account = searchAccount(accountNumber);
        return account == null ? false : account.deposit(value);
    }

    public boolean withdraw(int accountNumber, double value) {
        var account = searchAccount(accountNumber);
        return account == null ? false : account.withdraw(value);
    }

    public boolean computeSavingsAccount(int accountNumber) {
        var account = searchAccount(accountNumber);
        if (account == null)
            return false;
        if (account instanceof SavingsAccount) {
            ((SavingsAccount) account).computeYield(yieldPercentage);
            return true;
        }
        return false;
    }

}
