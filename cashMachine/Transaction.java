/**
 * 
 */
package cashMachine;

import java.util.Date;

/**
 * @author joelmcconvey
 *
 */
public class Transaction {

	/**
	 * Amount of this transaction
	 */
	private double amount;
	/**
	 * Time and date of this transaction
	 */
	private Date timestamp;
	/**
	 * Memo for this transaction
	 */
	private String memo;
	/**
	 * Account in which the transaction was performed
	 */
	private Account inAccount;

	/**
	 * Constructor
	 * 
	 * @param amount
	 * @param inAccount
	 */
	public Transaction(double amount, Account inAccount) {

		this.amount = amount;
		this.inAccount = inAccount;
		this.timestamp = new Date();
		this.memo = "";
	}

	/**
	 * Constructor where memo is created
	 * 
	 * @param amount
	 * @param memo
	 * @param inAccount
	 */
	public Transaction(double amount, String memo, Account inAccount) {

		// Call two argument constructor
		this(amount, inAccount);

		// Set memo
		this.memo = memo;
	}

	/**
	 * Get the amount of the transaction
	 * 
	 * @return the amount
	 */
	public double getAmount() {
		return this.amount;
	}

	/**
	 * Get string summarising the transaction
	 * 
	 * @return the summary string
	 */
	public String getSummaryLine() {

		if (this.amount >= 0) {
			return String.format("%s : £%.02f : %s", this.timestamp.toString(), this.amount, this.memo);
		} else {
			return String.format("%s : £(%.02f) : %s", this.timestamp.toString(), -this.amount, this.memo);
		}
	}
}
