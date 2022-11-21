/**
 * 
 */
package cashMachine;

import java.util.Scanner;

/**
 * @author joelmcconvey
 *
 */
public class ATM {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// initialise scanner
		Scanner sc = new Scanner(System.in);

		// initialise bank
		Bank theBank = new Bank("Java Bank");

		// add user to bank, creates savings account
		User user = theBank.addUser("John", "Brown", "1234");

		// add checking account
		Account newAccount = new Account("Checking", user, theBank);
		user.addAccount(newAccount);
		theBank.addAccount(newAccount);

		User curUser;
		while (true) {
			// stay in the login prompt until success
			curUser = ATM.mainMenuPrompt(theBank, sc);

			// stay in menu until user quits
			ATM.printUserMenu(curUser, sc);
		}
	}

	/**
	 * Print ATM's login
	 * 
	 * @param theBank
	 * @param sc
	 * @return
	 */
	public static User mainMenuPrompt(Bank theBank, Scanner sc) {

		// initialise
		String userID;
		String pin;
		User authUser;

		// Prompt user for userID and pin until correct
		do {
			System.out.printf("\n\nWelcome to %S\n\n", theBank.getName());
			System.out.print("Enter user ID: ");
			userID = sc.nextLine();
			System.out.print("Enter pin: ");
			pin = sc.nextLine();

			// try to get user object corresponding to id and pin combo
			authUser = theBank.userLogin(userID, pin);
			if (authUser == null) {
				System.out.println("Incorrect user ID/pin combination. " + "Please try again.");
			}
		} while (authUser == null);

		return authUser;
	}

	/**
	 * Displays the user menu and intakes user choice/s
	 * 
	 * @param theUser
	 * @param sc
	 */
	private static void printUserMenu(User theUser, Scanner sc) {

		// print a summary of user accounts
		theUser.printAccountsSummary();

		// initialise
		int choice;

		// user menu
		do {
			System.out.printf("Welcome %s, what would you like to do?\n", theUser.getFirstName());
			System.out.println("	1) Show account transaction history");
			System.out.println("	2) Withdrawl");
			System.out.println("	3) Deposit");
			System.out.println("	4) Transfer");
			System.out.println("	5) Log out");
			System.out.println();
			System.out.println("Enter choice: ");
			choice = sc.nextInt();

			if (choice < 1 || choice > 5) {
				System.out.println("Invalid choice. Please choose 1 - 5.");
			}
		} while (choice < 1 || choice > 5);

		// Process Choice
		switch (choice) {
		case 1:
			ATM.showTransHistory(theUser, sc);
			break;
		case 2:
			ATM.withdrawFunds(theUser, sc);
			break;
		case 3:
			ATM.depositFunds(theUser, sc);
			break;
		case 4:
			ATM.transferFunds(theUser, sc);
			break;
		case 5:
			sc.nextLine();
			break;
		}

		// redisplay the menu unless user quits
		if (choice != 5) {
			ATM.printUserMenu(theUser, sc);
		}
	}

	/**
	 * Shows transaction history for an account
	 * 
	 * @param theUser - logged in user object
	 * @param sc      - the scanner
	 */
	public static void showTransHistory(User theUser, Scanner sc) {

		// initialise
		int theAcct;

		// get the account to view history
		do {
			System.out.printf("Enter the number (1 - %d) of the account whose transactions you want to see",
					theUser.numAccounts());
			theAcct = sc.nextInt() - 1;
			if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (theAcct < 0 || theAcct >= theUser.numAccounts());

		// Print transaction history
		theUser.printAcctTransactionHistory(theAcct);
	}

	/**
	 * transferring funds from one account to another
	 * 
	 * @param theUser
	 * @param sc
	 */
	private static void transferFunds(User theUser, Scanner sc) {

		// initialise
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;

		// get the account to transfer from
		do {
			System.out.printf("Enter the number (1 - %d) of the account to transfer from: ", theUser.numAccounts());
			fromAcct = sc.nextInt() - 1;
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid accouunt. Please try again.");
			}
		} while (fromAcct < 0 || fromAcct >= theUser.numAccounts());

		acctBal = theUser.getAcctBalance(fromAcct);

		// get the account to transfer to
		do {
			System.out.printf("Enter the number (1 - %d) of the account to transfer to: ", theUser.numAccounts());
			toAcct = sc.nextInt() - 1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid accouunt. Please try again.");
			}
		} while (toAcct < 0 || toAcct >= theUser.numAccounts());

		// get the amount to transfer
		do {
			System.out.printf("Enter the amount to transfer (max £%.02f): £", acctBal);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than 0");
			} else if (amount > acctBal) {
				System.out.println("Insufficent balance");
			}
		} while (amount < 0 || amount > acctBal);

		// do transfer
		theUser.addAccountTransaction(fromAcct, -1 * amount,
				String.format("Transfer to account %s", theUser.getAcctUUID(toAcct)));
		theUser.addAccountTransaction(toAcct, amount,
				String.format("Transfer to account %s", theUser.getAcctUUID(fromAcct)));
	}

	/**
	 * Process a fund withdraw from an account
	 * 
	 * @param theUser
	 * @param sc
	 */
	public static void withdrawFunds(User theUser, Scanner sc) {

		// initialise
		int fromAcct;
		double amount;
		double acctBal;
		String memo;

		// get the account to transfer from
		do {
			System.out.printf("Enter the number (1 - %d) of the account to withdraw from: ", theUser.numAccounts());
			fromAcct = sc.nextInt() - 1;
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid accouunt. Please try again.");
			}
		} while (fromAcct < 0 || fromAcct >= theUser.numAccounts());

		acctBal = theUser.getAcctBalance(fromAcct);

		// get the amount to transfer
		do {
			System.out.printf("Enter the amount to withdraw (max £%.02f): £", acctBal);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than 0");
			} else if (amount > acctBal) {
				System.out.println("Insufficent balance");
			}
		} while (amount < 0 || amount > acctBal);

		sc.nextLine();

		// get memo
		System.out.print("Enter a memo: ");
		memo = sc.nextLine();

		// do the withdrawl
		theUser.addAccountTransaction(fromAcct, -1 * amount, memo);
	}

	/**
	 * Process a fund deposit to an account
	 * 
	 * @param theUser
	 * @param sc
	 */
	public static void depositFunds(User theUser, Scanner sc) {

		// initialise
		int toAcct;
		double amount;
		double acctBal;
		String memo;

		// get the account to transfer to
		do {
			System.out.printf("Enter the number (1 - %d) of the account to deposit in: ", theUser.numAccounts());
			toAcct = sc.nextInt() - 1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid accouunt. Please try again.");
			}
		} while (toAcct < 0 || toAcct >= theUser.numAccounts());

		acctBal = theUser.getAcctBalance(toAcct);

		// get the amount to transfer
		do {
			System.out.printf("Enter the amount to transfer (max £%.02f): £", acctBal);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than 0");
			}
		} while (amount < 0);

		sc.nextLine();

		// get memo
		System.out.print("Enter a memo: ");
		memo = sc.nextLine();

		// do the deposit
		theUser.addAccountTransaction(toAcct, amount, memo);
	}

}
