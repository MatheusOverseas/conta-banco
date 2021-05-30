package contas;

import java.text.NumberFormat;

public class CurrentAccount extends Account {

    private double limit;

    public CurrentAccount(int number, String ownerName, String ownerID, double limit) {
        super(number, ownerName, ownerID);
        this.limit = limit;
    }

    public CurrentAccount(int number, String ownerName, String ownerID) {
        super(number, ownerName, ownerID);
        this.limit = 0.00;
    }

    public double getLimit() {
        return limit;
    }

    public boolean isOnLimit() {
        return getBalance() < 0 && limit != 0 && getBalance() * -1 <= limit;
    }

    @Override
    public boolean withdraw(double value) {
        var bal = getBalance() - value; // Valor futuro
        return bal < 0 ? bal * -1 > limit ? false : super.withdraw(value) : super.withdraw(value);
    }

    @Override
    public String toString() {
        return super.toString() + "Limite: " + NumberFormat.getCurrencyInstance().format(limit) + "\n";
    }

}
