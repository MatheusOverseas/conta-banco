package contas;

public class SavingsAccount extends Account {

    public SavingsAccount(int number, String ownerName, String ownerID) {
        super(number, ownerName, ownerID);
    }

    public void computeYield(double percentage) {
        if (percentage != 0)
            super.deposit(getBalance() * percentage / 100);
    }

}
