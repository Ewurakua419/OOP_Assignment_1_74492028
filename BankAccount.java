/**
 * Bank account class
 * @author: Ewurakua Amoah
 * Date: 10 October 2025 
 */
public class BankAccount{
    enum AccountType {
        CURRENT,
        SAVINGS,    
    }
    final double CURRENT_ACCT_MIN_BALANCE= 100.00;
    final double SAVINGS_ACCT_MIN_BALANCE= 10.00;
    final double SAVINGS_ACCT_INTEREST_RATE= 10;
    final double CURRENT_ACCT_MAINTENANCE_FEE= 10;
    final int SAVINGS_WITHDRAWAL_LIMIT=2;
    private AccountType acctType;//type of account, instantiated from enum
    private String accID;//account’s identifier
    private double balance; //current balance f the account
    private int numWithdrawals=0;// number of withdrawals that have been made in the current month
    private boolean inTheRed=false;//whether or not the account is currently in the red
    private double minBalance;// required minimum balance
    private double interestRate =0;//The annual interest rate is stored in a member variable can be 0
    private double maintenanceFee=0;//An account also has a monthly maintenance fee deducted every month from the account balance
    private int withdrawalLimit= -1;// limit on the number of withdrawals that can be made in a month,
    
    /**
     * 
     * @param type
     * @param id
     */
    public BankAccount(AccountType type, String id){
        balance=0;
        accID=id;
        numWithdrawals=0;
        acctType=type;
        if (acctType==AccountType.CURRENT){
            minBalance=CURRENT_ACCT_MIN_BALANCE;
            interestRate=0;
            maintenanceFee=CURRENT_ACCT_MAINTENANCE_FEE;
        }
        else if (acctType==AccountType.SAVINGS){
            minBalance=SAVINGS_ACCT_MIN_BALANCE;
            interestRate=SAVINGS_ACCT_INTEREST_RATE;
            maintenanceFee=0;     
            }
        if ((balance-minBalance)<0){
            inTheRed=true;
        }
        else{
            inTheRed=false;
        }
    }

    /**
     * 
     * @param type
     * @param id
     * @param openingBalance
     */
    public BankAccount(AccountType type, String id, double openingBalance){
        balance=openingBalance;
        accID=id;
        numWithdrawals=0;
        acctType=type;
        if (acctType==AccountType.CURRENT){
            minBalance=CURRENT_ACCT_MIN_BALANCE;
            interestRate=0;
            maintenanceFee=CURRENT_ACCT_MAINTENANCE_FEE;
        }
        else if (acctType==AccountType.SAVINGS){
            minBalance=SAVINGS_ACCT_MIN_BALANCE;
            interestRate=SAVINGS_ACCT_INTEREST_RATE;
            maintenanceFee=0;     
            }
        if ((balance-minBalance)<0){
            inTheRed=true;
        }
        else{
            inTheRed=false;
        }
    }

    public  AccountType getAccountType(){
        return acctType;//returns the account type.
    }

    public String getAccountID(){
        return accID;//: returns the account id.
    }

    public double getMinBalance(){
        return minBalance;
    }

    /**
     * 
     * @param amount
     * @return
     */
    public boolean withdraw(double amount){
        if (amount<=0){
            System.out.println("Please check that the number is positive");
            return false;
        }
        else{
            double intermediary=balance-amount;//this intermediary would be used to compare to see if it is less than the minimum balance
            boolean alwaysallowed;
            if (withdrawalLimit==-1){
                alwaysallowed=true;
            }
            else{
                alwaysallowed=false;
            }
            if ((numWithdrawals<withdrawalLimit||alwaysallowed==true) && inTheRed==false && intermediary>=minBalance){
                balance=intermediary;
                numWithdrawals++;// increments number of withdrawals
                System.out.println("Successfull new balance is : "+balance);
                return true;
            }
            else{
                // prints out error messages fitting all situation
                System.out.println("Sorry, could not perform withdrawal:");
                if (numWithdrawals>withdrawalLimit && withdrawalLimit!=-1){
                    System.out.println("number of withrawals exceeded");
                }

                else if (inTheRed==true){
                    System.out.println("account in the red");
                }

                else if (intermediary<minBalance){
                    System.out.println("Too big amount withdrawn");
                }
                
                return false;
            }


        }
        
    }

    // adding an amount to the balance
    /**
     * 
     * @param amount
     */
    public void deposit(double amount){
        balance+=amount;
    }

    /**
     *  performs monthly maintenance on the account. 
     * It computes any interest earned on the account 
     * and adds the earned interest to the current balance.
     * 
     */
    public void performMonthlyMaintenance(){
        double monthlyInterestRate=interestRate/12;
        double calculatedInterest= monthlyInterestRate*balance;
        balance+=calculatedInterest;//the balance plus the new interest
        balance-=maintenanceFee;//the balance after maintainance is subtracted
        System.out.println("After monthly maintenance");
        System.out.println("Earned interest: "+(Math.round(calculatedInterest*100.0)/100.0));
        System.out.println("Maintenance fee: "+(Math.round(maintenanceFee*100.0)/100.0));
        System.out.println("Updated balance: "+(Math.round(balance*100.0)/100.0));
        if (balance<minBalance){//checking if the account is in the red
            inTheRed=true;
            System.out.println("WARNING: This account is now in red!");//output warning message for when its in red
        }
    }

    /**
     * 
     * @param transferTo
     * @param otherAccount
     * @param amount
     * @return boolean
     */
    public boolean transfer(boolean transferTo, BankAccount otherAccount, double amount){
        // in the case that the account is transfering to another account
        if (transferTo==true){
            
            if (withdraw(amount)==true){
                withdraw(amount);
                otherAccount.deposit(amount);
                System.out.println("Transfer to account "+otherAccount.getAccountID() +": successful");
                return true;
            }
            else{
                System.out.println("Unsuccessful transfer");
                return false;

            }
        }
        //in the case that the account is recieving from another account 
        else{
            otherAccount.withdraw(amount);
            if (otherAccount.withdraw(amount)==true){
                deposit(amount);
                System.err.println("Deposit in account from account "+otherAccount.getAccountID() +": successful");
                return true;
                
            }
            else{
                System.out.println("Unsuccessful");
                return false;
            }
        }
    }
    
}
