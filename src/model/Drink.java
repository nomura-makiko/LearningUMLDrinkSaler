package model;

public class Drink {

	private String drinkName = "";
	private int amount = 0;
	private String resourcePath = "";

	private Drink() {}
	public Drink(String drinkName, int amount, String resourcePath) {
		this.drinkName = drinkName;
		this.amount = amount;
		this.resourcePath = resourcePath;
	}
	public String getDrinkName() {
		return drinkName;
	}
	public int getAmount() {
		return amount;
	}
	public String getIconPath() {
		return this.resourcePath;
	}
}
