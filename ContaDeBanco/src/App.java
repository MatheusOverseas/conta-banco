import java.util.Scanner;
import java.text.NumberFormat;

import contas.*;

public class App {
    
    private static AccountManager repo = new AccountManager(10);
    private static Scanner sc = new Scanner(System.in);
    private static CLI cli = new CLI();

    private static final String OK_LOG = "Oba! Operação realizada com sucesso!";
    private static final String ERR_LOG = "Opa! A operação não pôde ser realizada!";

    private static int numberIndex = 1;


    
    public static void main(String[] args) throws Exception {
        openMainMenu();  
    }

    private static String value(double v){
        return NumberFormat.getCurrencyInstance().format(v);
    }

    private static void openMainMenu(){
        cli.startInteraction("Digite o número da opção desejada:", new String[]{
            "Pesquisar contas", "Operações bancárias", "Adicionar contas", "Remover contas"
        }, new Action[]{
            new Action(){ public void act(String input){openSearcherMenu();}},
            new Action(){ public void act(String input){openOperationsMenu();}},
            new Action(){ public void act(String input){openAdditionMenu();}},
            new Action(){ public void act(String input){
                try {
                    var num = sc.nextInt(); sc.nextLine();
                    var acc = repo.searchAccount(num);
                    if (acc != null){
                        System.out.println(acc.toString());
                        openDeletionMenu(num);
                    } else System.out.println("Conta não encontrada");
                } catch (Exception e) {
                    System.out.println("Entrada inválida");
                }
            }}
        }, null ,true).run();
    }

    private static void openSearcherMenu(){
        cli.startInteraction("Digite o número da opção desejada:", new String[]{
            "Pesquisar conta pelo número", "Pesquisar contas especiais", "Pesquisar contas poupanças", "Pesquisar contas usando o limite", "Listar todas as contas"  
        }, new Action[]{
            new Action(){ public void act(String vl){ 
                try {
                    System.out.println("Digite o número da conta");
                    System.out.println(repo.searchAccount(sc.nextInt()).toString());
                    System.out.println("Pressione qualquer tecla para continuar..."); 
                    sc.nextLine(); sc.nextLine();
                } catch (Exception e) {
                    System.out.println("Entrada inválida!");
                }
            }},
            new Action(){ public void act(String vl){ System.out.println(repo.searchSpecialAccounts()); System.out.println("Pressione qualquer tecla para continuar..."); sc.nextLine();}},
            new Action(){ public void act(String vl){ System.out.println(repo.searchSavingsAccounts()); System.out.println("Pressione qualquer tecla para continuar..."); sc.nextLine();}},
            new Action(){ public void act(String vl){ System.out.println(repo.searchClientsOnLimit());  System.out.println("Pressione qualquer tecla para continuar..."); sc.nextLine();}},
            new Action(){ public void act(String vl){ System.out.println(repo.listAccounts());  System.out.println("Pressione qualquer tecla para continuar..."); sc.nextLine();}},
        },  new Action(){ public void act(String vl){ openMainMenu(); }}, true)
        .run();
    }

    private static void openAdditionMenu(){
        cli.startInteraction("Selecione o tipo de conta a ser adicionado:", new String[]{
            "Conta corrente", "Conta poupança", "Conta especial"  
        }, new Action[]{
            new Action(){ public void act(String vl){ addCurrentAccount(); }},
            new Action(){ public void act(String vl){ addSavingsAccount(); }},
            new Action(){ public void act(String vl){ addSpecialAccount(); }},
        }, 
         
        new Action(){ public void act(String vl){ openMainMenu(); }}, true)
        .run();
    }

    private static void openDeletionMenu(int number){
        cli.startInteraction("Deseja mesmo excluir esta conta? :", new String[]{ "Sim" }, new Action[]{
            new Action(){ public void act(String vl){ 
                var ok = repo.remove(number);
                System.out.println(ok ? OK_LOG : ERR_LOG);
            }},
        },  new Action(){ public void act(String vl){ openMainMenu(); }}, true)
        .run();
    }

    private static void openOperationsMenu(){
        cli.startInteraction("Digite o número da opção desejada:", new String[]{"Transferir valor", "Sacar valor", "Depositar valor", "Calcular e pagar rendimentos para poupanças" }, new Action[]{
            new Action(){ public void act(String vl){ transfer(); }},
            new Action(){ public void act(String vl){ withdraw(); }},
            new Action(){ public void act(String vl){ deposit(); }},
            new Action(){ public void act(String vl){ computeSavingsAccount(); }},

        },  new Action(){ public void act(String vl){ openMainMenu(); }}, true)
        .run();
    }

    public static void logAccountBalance(Account acc){
        System.out.println("Saldo atual: "+value(acc.getBalance()));
        if (!(acc instanceof SavingsAccount) && acc.getBalance() < 0){
            if (acc instanceof CurrentAccount) System.out.println("Limite usado: " + value(((CurrentAccount)acc).getLimit() + acc.getBalance()));
        }
    }

    private static void addCurrentAccount(){
        try {
                    
            System.out.print("Digite o nome do proprietário da conta: ");
            var name = sc.nextLine();

            System.out.print("Digite o CPF do proprietário da conta: ");
            var cpf = sc.nextLine();

            System.out.print("Digite o limite de saque da conta em situação negativa: ");
            var limit = sc.nextDouble(); sc.nextLine();

            repo.add(new CurrentAccount(numberIndex, name, cpf, limit));
            System.out.println("\n");
            System.out.println(OK_LOG + " Sua conta é de número "+numberIndex);

            numberIndex++;

        } catch (Exception e) {
            System.out.println("Entrada inválida!");
        }
    }

    private static void addSavingsAccount(){
        try {
                    
            System.out.print("Digite o nome do proprietário da conta: ");
            var name = sc.nextLine();

            System.out.print("Digite o CPF do proprietário da conta: ");
            var cpf = sc.nextLine();

            repo.add(new SavingsAccount(numberIndex, name, cpf)); 

            System.out.println("\n");
            System.out.println(OK_LOG + " Sua conta é de número "+numberIndex);

            numberIndex++;

        } catch (Exception e) {
            System.out.println("Entrada inválida!");
        }
    }

    private static void addSpecialAccount(){
        try {
                    
            System.out.print("Digite o nome do proprietário da conta: ");
            var name = sc.nextLine();

            System.out.print("Digite o CPF do proprietário da conta: ");
            var cpf = sc.nextLine();

            System.out.print("Digite o nome do gerente da conta: ");
            var manager = sc.nextLine();

            System.out.print("Digite o limite de saque da conta em situação negativa: ");
            var limit = sc.nextDouble(); sc.nextLine();

            repo.add(new SpecialAccount(numberIndex, name, cpf, limit, manager)); 
            System.out.println("\n");
            System.out.println(OK_LOG + " Sua conta é de número "+numberIndex);

            numberIndex++;

        } catch (Exception e) {
            System.out.println("Entrada inválida!");
        }
    }

    private static void transfer(){
        try {
            System.out.print("Digite o número da conta de origem: ");
            var origin = sc.nextInt(); sc.nextLine();

            System.out.print("Digite o número da conta de destino: ");
            var target = sc.nextInt(); sc.nextLine();

            System.out.print("Digite o valor a ser transferido: ");
            var value = sc.nextDouble(); sc.nextLine();

            var ok = repo.transfer(origin, target, value);
            System.out.println("\n");
            System.out.println(ok ? OK_LOG : ERR_LOG);
            System.out.println("\n");

            logAccountBalance(repo.searchAccount(origin));
        } catch (Exception e) {
            System.out.println("Entrada inválida!");
        }
    }

    private static void deposit(){
        try {
            System.out.print("Digite o número da conta de destino: ");
            var origin = sc.nextInt(); sc.nextLine();

            System.out.print("Digite o valor a ser depositado: ");
            var value = sc.nextDouble(); sc.nextLine();

            var ok = repo.deposit(origin, value);
            System.out.println("\n");
            System.out.println(ok ? OK_LOG : ERR_LOG);
            System.out.println("\n");

            logAccountBalance(repo.searchAccount(origin));
        } catch (Exception e) {
            System.out.println("Entrada inválida!");
        }
    }

    private static void withdraw(){
        try {
            System.out.print("Digite o número da conta de origem: ");
            var origin = sc.nextInt(); sc.nextLine();

            System.out.print("Digite o valor a ser sacado: ");
            var value = sc.nextDouble(); sc.nextLine();

            
            var ok = repo.withdraw(origin, value);
            System.out.println("\n");
            System.out.println(ok ? OK_LOG : ERR_LOG);
            System.out.println("\n");

            logAccountBalance(repo.searchAccount(origin));
        } catch (Exception e) {
            System.out.println("Entrada inválida!");
        }
    }

    private static void computeSavingsAccount(){
        try {
            System.out.print("Digite o número da conta poupança: ");
            var origin = sc.nextInt();
            sc.nextLine();

            var ok = repo.computeSavingsAccount(origin);
            System.out.println("\n");
            System.out.println(ok ? OK_LOG : ERR_LOG);
            System.out.println("\n");

            logAccountBalance(repo.searchAccount(origin));

        } catch (Exception e) {
            System.out.println("Entrada inválida!");
        }
    }

}


class CLI {

    

    public Interaction startInteraction(String title, String[] options, Action[] logic, Action onEnd, boolean showBack){
        var render = new Action(){
            public void act(String args){
                System.out.println(title + "\n");
                for (int i = 0; i < options.length; i++) System.out.println("    " + (i + 1) + " - " + options[i]);
                if (showBack) System.out.println("    " + (onEnd == null ? "0 - Sair <-----" : "0 - Voltar <-----"));
                System.out.println("\n");
            }
        };
        return new Interaction(logic, onEnd, render);
    }

    class Interaction {

        Interaction(Action[] actions, Action back, Action textRenderActions){ 
            this.actions = actions; this.back = back; this.textRenderActions = textRenderActions;
            sc = new Scanner(System.in);
        }

        private Scanner sc;

        public Action textRenderActions;

        private Action[] actions;
        
        private Action back;

        public void run(){
            var valid = false;
            var runit = true;

            do {
                try {
                    System.out.println("\n");
                    textRenderActions.act("");
                    System.out.println("\n");
                    var input = sc.nextInt(); valid = input <= actions.length;
                    sc.nextLine();
                    if (valid){

                        if (input == 0){
                            System.out.println("Voltando...");
                            runit = false;
                            break;
                        }

                        actions[input - 1].act(Integer.toString(input));

                    } else System.out.println("Opção inválida. Digite outra");

                } catch (Exception e) {
                    if (!valid) System.out.println("Opção inválida. Digite outra");
                }
                System.out.println("\n");

            } while (runit); 
            System.out.println("\n\n\n");
            if (back == null) System.exit(0);
            back.act("");
        }
    }

}

interface Action { void act(String args); }

