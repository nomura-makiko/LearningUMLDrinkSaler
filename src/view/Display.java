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
		setTitle("�����̔��@");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 10, 700, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// ���b�Z�[�W�\���G���A
		announceTextPane.setText("���i��I�����Ă��������B");
		announceTextPane.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 36));
		contentPane.add(announceTextPane, BorderLayout.NORTH);

		// �������G���A
		Panel inputAmountPanel = new Panel();
		inputAmountPanel.setLayout(new BoxLayout(inputAmountPanel, BoxLayout.Y_AXIS));
		// �������\�����x��
		inputAmountTextPane.setText("0�~");
		inputAmountTextPane.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 48));
		AmountController.setAmountChangedListener(new AmountChangedListener() {
			@Override
			public void onAmountChanged(int amount) {
				inputAmountTextPane.setText(amount + "�~");
			}
		});
		inputAmountPanel.add(inputAmountTextPane);
		// �����d�݃{�^��
		for (Coin coin : AmountController.getAmountList()) {
			CoinButton coinButton = new CoinButton(coin);
			coinButton.setSize(50, 50);
			inputAmountPanel.add(coinButton);
		}
		contentPane.add(inputAmountPanel, BorderLayout.EAST);

		// ���i�G���A
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
					announceTextPane.setText(drink.getDrinkName() + "�̍w�����������܂����B"
							+ (AmountController.getInputAmount() > 0
									? "\r\n���ނ��" + AmountController.getInputAmount() + "�~�ł��B"
											: ""));
					AmountController.payAmountAll();
					inputAmountTextPane.setText(String.valueOf(AmountController.getInputAmount()) + "�~");
					break;
				case ERROR_INVALID_PRODUCT:
					// NG -> ���肦�Ȃ����i��I���A�̏�Ɣ��f
					announceTextPane.setText("�̏ᒆ�ł��B�w�w�w�|�w�w�|�w�w�w�w�܂ł��A�������肢���܂��B");
					AmountController.payAmountAll();
					break;
				case ERROR_NO_STOK:
					// NG�@-> �݌ɂ��Ȃ��ƕ\������
					announceTextPane.setText("�݌ɂ�����܂���B");
					break;
				case ERROR_NOT_ENOUGH_AMOUNT:
					// NG�@-> ���z������Ȃ��ƕ\������
					announceTextPane.setText("���������s�����Ă��܂��B");
					break;
				}
			}
		};
	};
}
