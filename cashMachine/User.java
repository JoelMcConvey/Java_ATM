/**
 * 
 */
package cashMachine;

import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author joelmcconvey
 *
 */
public class User {

	/**
	 * First name of the user
	 */
	private String firstName;
	/**
	 * Last name of the user
	 */
	private String lastName;
	/**
	 * Users unique id
	 */
	private String uuid;
	/**
	 * MD5 Hash of the users pin
	 */
	private byte pinHash[];
	/**
	 * List of the users accounts
	 */
	private ArrayList<Account> accounts;

	/**
	 * Create new user
	 * 
	 * @param firstName
	 * @param lastName
	 * @param pin
	 * @param theBank
	 */
	public User(String firstName, String lastName, String pin, Bank theBank) {

		// Set user's name
		this.firstName = firstName;
		this.lastName = lastName;

		// Hash and store pin
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.err.println("error, caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}

		// Get a new, unique id for user
		this.uuid = theBank.getNewUserUUID();

		// create empty list of accounts
		this.accounts = new ArrayList<Account>();

		// Print log message
		System.out.printf("New user %s, %s with ID %s created.\n", lastName, firstName, uuid);
	}

	/**
	 * Add account to accounts list
	 * 
	 * @param account
	 */
	public void addAccount(Account account) {
		this.accounts.add(account);
	}

	/**
	 * 
	 * @return
	 */
	public String getUUID() {
		return this.uuid;
	}

	/**
	 * Check whether pin given matches true pin
	 * 
	 * @param pin
	 * @return
	 */
	public boolean validatePin(String pin) {

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(pin.getBytes()), pinHash);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("error, caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Displays the relevant details for each account that a user object has
	 */
	public void printAccountsSummary() {

		System.out.printf("\n\n%s's accounts summary\n", this.firstName);
		for (int i = 0; i < this.accounts.size(); i++) {
			System.out.printf("  %d) %s", i + 1, this.accounts.get(i).getSummaryLine());
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * 
	 * @return
	 */
	public int numAccounts() {
		return this.accounts.size();
	}

	/**
	 * Print transaction history for a particular account
	 * 
	 * @param acctIdx the index of the account to use
	 */
	public void printAcctTransactionHistory(int acctIdx) {
		this.accounts.get(acctIdx).printTransHistory();
	}

	/**
	 * Get account balance of particular account
	 * 
	 * @param acctIdx index of the account to use
	 * @return the balance of the account
	 */
	public double getAcctBalance(int acctIdx) {
		return this.accounts.get(acctIdx).getBalance();
	}

	/**
	 * Get the uuid of a particular account
	 * 
	 * @param acctIdx the index of the account
	 * @return the uuid of the account
	 */
	public String getAcctUUID(int acctIdx) {
		return this.accounts.get(acctIdx).getUUID();
	}

	/**
	 * Add transaction to particular account
	 * 
	 * @param acctIdx the index of the account
	 * @param amount  the amount of the transaction
	 * @param memo    the memo of the transaction
	 */
	public void addAccountTransaction(int acctIdx, double amount, String memo) {

		this.accounts.get(acctIdx).addTransaction(amount, memo);
	}
}
