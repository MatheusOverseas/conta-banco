package contas;

import java.text.NumberFormat;

public abstract class Account {

    private int number;
    private String ownerName;
    private String CPFCliente;
    private double balance = 0;

    public Account(int number, String ownerName, String ownerID) {
        this.number = number;
        this.ownerName = ownerName;
        this.CPFCliente = ownerID;
    }

    public boolean withdraw(double valor) {
        balance -= valor;
        return true;
    }

    public boolean deposit(double valor) {
        balance += valor;
        return true;
    }

    public int getNumber() {
        return number;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerID() {
        return CPFCliente;
    }

    public double getBalance() {
        return balance;
    }

    public void setOwnerName(String name) {
        this.ownerName = name;
    }

    public String toString() {
        String summup = "Conta\n";
        summup += "Número da conta: " + number + "\n";
        summup += "Nome do cliente: " + ownerName + "\n";
        summup += "CPF: " + CPFCliente + "\n";
        summup += "Saldo: " + NumberFormat.getCurrencyInstance().format(balance) + "\n";
        return summup;

    }

}
