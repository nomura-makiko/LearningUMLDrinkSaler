package controller;

import java.util.ArrayList;
import java.util.List;

import model.Coin;

public class AmountController {

	private static int inputAmount = 0;
	private static List<Coin> amountList = new ArrayList<Coin>() {{
		add(new Coin(10, "10‰~", "./resource/10yen.png"));
		add(new Coin(50, "50‰~", "./resource/50yen.png"));
		add(new Coin(100, "100‰~", "./resource/100yen.png"));
		add(new Coin(500, "500‰~", "./resource/500yen.png"));
	}};
	private static AmountChangedListener changedListener = null;

	public static List<Coin> getAmountList() {
		return amountList;
	}

	public static void addAmount(Coin coin) {
		inputAmount += coin.getAmount();
		notifyChanged();
	}

	public static int getInputAmount() {
		return inputAmount;
	}

	public static void payAmount(int amount) {
		inputAmount -= amount;
		notifyChanged();
	}

	public static void setAmountChangedListener(AmountChangedListener listener) {
		changedListener = listener;
	}

	public static void payAmountAll() {
		inputAmount = 0;
		notifyChanged();
	}

	private static void notifyChanged() {
		if (changedListener != null) {
			changedListener.onAmountChanged(inputAmount);
		}
	}
	public interface AmountChangedListener {
		public void onAmountChanged(int amount);
	}
}
