/**
 * 
 */
package cashMachine;

import java.util.ArrayList;

/**
 * @author joelmcconvey
 *
 */
public class Account {

	/**
	 * Name of the account
	 */
	private String name;
	/**
	 * Account unique ID
	 */
	private String uuid;
	/**
	 * The user objects that owns this account
	 */
	private User holder;
	/**
	 * List of transactions for this account
	 */
	private ArrayList<Transaction> transactions;

	/**
	 * Create new account
	 * 
	 * @param name
	 * @param holder
	 * @param theBank
	 */
	public Account(String name, User holder, Bank theBank) {

		// Set the account name and holder
		this.name = name;
		this.holder = holder;

		// get new account uuid
		this.uuid = theBank.getNewAccountUUID();

		// initialise transactions arraylist
		this.transactions = new ArrayList<Transaction>();
	}

	/**
	 * 
	 * @return
	 */
	public String getUUID() {
		return this.uuid;
	}

	/**
	 * Get summary line for account
	 * 
	 * @return the string summary
	 */
	public String getSummaryLine() {

		// Get balance
		double balance = this.getBalance();

		// format summary line
		if (balance >= 0) {
			return String.format("%s : £%.02f : %s", this.uuid, balance, this.name);
		} else {
			return String.format("%s : £(%.02f) : %s", this.uuid, balance, this.name);
		}
	}

	/**
	 * Gets the balance for the account
	 * 
	 * @return the account balance
	 */
	public double getBalance() {

		double balance = 0;
		for (Transaction t : this.transactions) {
			balance += t.getAmount();
		}
		return balance;
	}

	/**
	 * Print transaction history of account
	 */
	public void printTransHistory() {

		System.out.printf("\nTransaction history for account %s\n", this.uuid);
		for (int t = this.transactions.size() - 1; t >= 0; t--) {
			System.out.println(this.transactions.get(t).getSummaryLine());
		}
		System.out.println();
	}

	/**
	 * Add transaction in this account
	 * 
	 * @param amount the amount transacted
	 * @param memo   the transaction memo
	 */
	public void addTransaction(double amount, String memo) {

		// create new transaction object and add to list
		Transaction newTrans = new Transaction(amount, memo, this);
		this.transactions.add(newTrans);
	}

}
