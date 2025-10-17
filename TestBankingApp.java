

/**
 * @author Ewurakua Amoah
 * Date: 17-10-2025
 * Tests BankingApp class
 */

public class TestBankingApp{
    public static void main(String[] args){
        BankAccount savingsaccount=new BankAccount(BankAccount.AccountType.SAVINGS, "220029299", 2000);
        BankAccount account1= new BankAccount(BankAccount.AccountType.CURRENT,"74491018" ,79999);
        BankAccount currAccount= new BankAccount(BankAccount.AccountType.CURRENT, "7565443", 10);
        System.out.println("Account Type "+account1.getAccountType());//prints out the account type
        System.out.println("Account ID "+account1.getAccountID());//prints the account id
        System.out.println("Account minimum balance "+account1.getMinBalance());//print min balance

        account1.withdraw(11);//withdraws a positive amount
        account1.withdraw(-23);//trys to withdraw a negative amount
        account1.deposit(200);
        account1.withdraw(10);
        account1.transfer(false, savingsaccount, 22);
        
        System.out.println("withdrawal when in the red");
        currAccount.withdraw(14);
        System.out.println("transfer when in red");
        currAccount.transfer(true, account1, 18);

        account1.performMonthlyMaintenance();//monthly maintenance
        savingsaccount.performMonthlyMaintenance();
        currAccount.performMonthlyMaintenance();





    }
    
}
