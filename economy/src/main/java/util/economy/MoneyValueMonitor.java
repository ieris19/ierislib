package util.economy;

public class MoneyValueMonitor implements Runnable {
	private Money monitored;
	private String name;
	private double oldBalance;

	public MoneyValueMonitor(Money money, String name) {
		this.name = name;
		this.monitored = money;
		this.oldBalance = money.getAmount();
	}

	@Override public void run() {
		while (true) {
			if (monitored.getAmount() != oldBalance) {
				System.out.println(name + ": " + monitored.getAmount());
				oldBalance = monitored.getAmount();
			}
		}
	}
}
