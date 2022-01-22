package view.object;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import model.Drink;

public class DrinkButton extends JButton {

	private Drink product = null;

	public DrinkButton(Drink product, ActionListener listener) {
		super(product.getDrinkName(), new ImageIcon(product.getIconPath()));
		this.product = product;
		this.addActionListener(listener);
	}

	public Drink getProduct() {
		return product;
	}
}
