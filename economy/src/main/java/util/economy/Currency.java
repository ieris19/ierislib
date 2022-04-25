package util.economy;

/**
 * A currency is a more specific version of Money, it includes everything from the
 */
public class Currency extends Money {
	/**
	 * The symbol that the balance should be displayed along with, in order to differentiate different
	 * currencies
	 */
	protected String currencySymbol;

	public Currency(double balance, String currencySymbol) {
		super(balance);
		setCurrencySymbol(currencySymbol);
	}

	/**
	 * Changes the currency symbol
	 *
	 * @param currencySymbol The string that will be used as a currency symbol
	 */
	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	/**
	 * Returns the currency symbol
	 */
	public String getCurrencySymbol() {
		return currencySymbol;
	}
}
