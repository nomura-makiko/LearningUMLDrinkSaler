package model;

import javax.swing.ImageIcon;

public class Coin {

	private int amount = 0;
	private String label = "";
	private String iconPath = null;

	public Coin(int amount, String label, String iconPath) {
		this.amount = amount;
		this.label = label;
		this.iconPath = iconPath;
	}

	public int getAmount() {
		return this.amount;
	}
	public String getLabel() {
		return this.label;
	}
	public String getIconPath() {
		return this.iconPath;
	}
}
