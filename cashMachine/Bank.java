package cashMachine;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author joelmcconvey
 *
 */
public class Bank {

	/**
	 * Name of the bank
	 */
	private String name;
	/**
	 * List of bank user objects
	 */
	private ArrayList<User> users;
	/**
	 * List of bank account objects
	 */
	private ArrayList<Account> accounts;

	/**
	 * Create a bank object with list of users and accounts
	 * 
	 * @param name
	 */
	public Bank(String name) {
		this.name = name;
		this.users = new ArrayList<User>();
		this.accounts = new ArrayList<Account>();
	}

	/**
	 * Create user uuid
	 * 
	 * @return
	 */
	public String getNewUserUUID() {

		// initialise
		String uuid;
		Random rand = new Random();
		int length = 6;
		boolean nonUnique;

		// Continue loop until until ID generated
		do {
			// Generate Number
			uuid = "";
			for (int i = 0; i < length; i++) {
				uuid += ((Integer) rand.nextInt(10)).toString();
			}
			// Check for uniqueness
			nonUnique = false;
			for (User user : this.users) {
				if (uuid.compareTo(user.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}
		} while (nonUnique);

		// Return uuid
		return uuid;
	}

	/**
	 * Create account uuid
	 * 
	 * @return
	 */
	public String getNewAccountUUID() {

		// initialise
		String uuid;
		Random rand = new Random();
		int length = 10;
		boolean nonUnique;

		// Continue loop until until ID generated
		do {
			// Generate Number
			uuid = "";
			for (int i = 0; i < length; i++) {
				uuid += ((Integer) rand.nextInt(10)).toString();
			}
			// Check for uniqueness
			nonUnique = false;
			for (Account account : this.accounts) {
				if (uuid.compareTo(account.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}
		} while (nonUnique);

		// Return uuid
		return uuid;
	}

	/**
	 * Add account object to accounts arraylist
	 * 
	 * @param account
	 */
	public void addAccount(Account account) {
		this.accounts.add(account);
	}

	/**
	 * Create new user and account object and add to users and accounts arraylists
	 * 
	 * @param firstName
	 * @param lastName
	 * @param pin
	 * @return
	 */
	public User addUser(String firstName, String lastName, String pin) {

		// Create new user object and add to list
		User newUser = new User(firstName, lastName, pin, this);
		this.users.add(newUser);

		// Create a savings account and add to user and bank accounts list
		Account newAccount = new Account("Savings", newUser, this);
		newUser.addAccount(newAccount);
		this.accounts.add(newAccount);

		return newUser;
	}

	/**
	 * Checks users login details to match with user objects within users arraylist
	 * 
	 * @param userID
	 * @param pin
	 * @return
	 */
	public User userLogin(String userID, String pin) {

		// Search list of users
		for (User user : this.users) {
			// Check userID is correct
			if (user.getUUID().compareTo(userID) == 0 && user.validatePin(pin)) {
				return user;
			}
		}
		// Haven't found user or incorrect pin
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}
}
