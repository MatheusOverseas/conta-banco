package contas;

public class SpecialAccount extends CurrentAccount {

    public SpecialAccount(int number, String ownerName, String ownerID, String managerName) {
        super(number, ownerName, ownerID);
        this.managerName = managerName;
    }

    public SpecialAccount(int number, String ownerName, String ownerID, double limit, String managerName) {
        super(number, ownerName, ownerID, limit);
        this.managerName = managerName;
    }

    private String managerName;

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    @Override
    public String toString() {
        return super.toString() + "Nome do gerente: " + managerName + "\n";
    }

}
