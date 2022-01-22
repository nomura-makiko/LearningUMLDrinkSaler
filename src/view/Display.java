package view;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import controller.AmountController;
import controller.AmountController.AmountChangedListener;
import controller.SellController;
import controller.StokController;
import model.Coin;
import model.Drink;
import view.object.CoinButton;
import view.object.DrinkButton;

public class Display extends JFrame {

	private JPanel contentPane;
	private JTextPane inputAmountTextPane = new JTextPane();
	private JTextPane announceTextPane = new JTextPane();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Display frame = new Display();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Display() {
		setTitle("自動販売機");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 10, 700, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// メッセージ表示エリア
		announceTextPane.setText("商品を選択してください。");
		announceTextPane.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 36));
		contentPane.add(announceTextPane, BorderLayout.NORTH);

		// 投入金エリア
		Panel inputAmountPanel = new Panel();
		inputAmountPanel.setLayout(new BoxLayout(inputAmountPanel, BoxLayout.Y_AXIS));
		// 投入金表示ラベル
		inputAmountTextPane.setText("0円");
		inputAmountTextPane.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 48));
		AmountController.setAmountChangedListener(new AmountChangedListener() {
			@Override
			public void onAmountChanged(int amount) {
				inputAmountTextPane.setText(amount + "円");
			}
		});
		inputAmountPanel.add(inputAmountTextPane);
		// 投入硬貨ボタン
		for (Coin coin : AmountController.getAmountList()) {
			CoinButton coinButton = new CoinButton(coin);
			coinButton.setSize(50, 50);
			inputAmountPanel.add(coinButton);
		}
		contentPane.add(inputAmountPanel, BorderLayout.EAST);

		// 商品エリア
		Panel drinkPanel = new Panel();
		drinkPanel.setLayout(new BoxLayout(drinkPanel, BoxLayout.X_AXIS));
		for (Drink drink : StokController.getDrinkList()) {
			DrinkButton drinkButton = new DrinkButton(drink, getResultActionListener(drink));
			drinkPanel.add(drinkButton);
		}
		contentPane.add(drinkPanel, BorderLayout.CENTER);
	}

	private ActionListener getResultActionListener(Drink drink) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SellController.SellResult result = 
						SellController.buyDrink(drink);
				switch(result) {
				case SUCCESS:
					announceTextPane.setText(drink.getDrinkName() + "の購入が完了しました。"
							+ (AmountController.getInputAmount() > 0
									? "\r\nお釣りは" + AmountController.getInputAmount() + "円です。"
											: ""));
					AmountController.payAmountAll();
					inputAmountTextPane.setText(String.valueOf(AmountController.getInputAmount()) + "円");
					break;
				case ERROR_INVALID_PRODUCT:
					// NG -> ありえない商品を選択、故障と判断
					announceTextPane.setText("故障中です。ＸＸＸ－ＸＸ－ＸＸＸＸまでご連絡をお願いします。");
					AmountController.payAmountAll();
					break;
				case ERROR_NO_STOK:
					// NG　-> 在庫がないと表示する
					announceTextPane.setText("在庫がありません。");
					break;
				case ERROR_NOT_ENOUGH_AMOUNT:
					// NG　-> 金額が足りないと表示する
					announceTextPane.setText("投入金が不足しています。");
					break;
				}
			}
		};
	};
}
