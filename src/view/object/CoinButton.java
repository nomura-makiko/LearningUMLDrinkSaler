package view.object;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import controller.AmountController;
import model.Coin;

public class CoinButton extends JButton {

	private int amount = 0;
	public CoinButton(Coin coin) {
		this.amount = coin.getAmount();
		this.setIcon(new ImageIcon(coin.getIconPath()));
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AmountController.addAmount(coin);
			}
		});
	}
	public int getAmount() {
		return amount;
	}
}
