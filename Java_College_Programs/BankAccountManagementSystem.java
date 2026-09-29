class BankAccount {
    private int number; private String holder; private double balance;
    BankAccount(int number,String holder,double balance){this.number=number;this.holder=holder;this.balance=balance;}
    void deposit(double amount){if(amount>0){balance+=amount;System.out.println("Deposited: "+amount);}}
    void withdraw(double amount){
        if(amount>0&&amount<=balance){balance-=amount;System.out.println("Withdrawn: "+amount);}
        else System.out.println("Invalid amount or insufficient balance.");
    }
    void display(){System.out.println("Account: "+number+" | Holder: "+holder+" | Balance: "+balance);}
}
public class BankAccountManagementSystem {
    public static void main(String[] args){
        System.out.println("=== Bank Account Management System ===");
        BankAccount a=new BankAccount(1001,"Barani",10000);
        a.display(); a.deposit(2500); a.withdraw(1500); a.display();
    }
}