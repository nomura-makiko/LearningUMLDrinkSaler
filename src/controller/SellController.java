package controller;

import model.Drink;

public class SellController {

	public static SellResult buyDrink(Drink selectedDrink){

		// �̔��ł��邩�`�F�b�N����
		if (StokController.getStockCount(selectedDrink) <= 0) {
			// NG�@-> �݌ɂ��Ȃ�
			return SellResult.ERROR_NO_STOK;
		} else if(AmountController.getInputAmount() < selectedDrink.getAmount()) {
			// NG�@-> ���z������Ȃ�
			return SellResult.ERROR_NOT_ENOUGH_AMOUNT;
		} else {
			// OK
			StokController.sellDrink(selectedDrink);
			AmountController.payAmount(selectedDrink.getAmount());
			return SellResult.SUCCESS;
		}
	}

	public enum SellResult {
		ERROR_NO_STOK,
		ERROR_NOT_ENOUGH_AMOUNT,
		ERROR_INVALID_PRODUCT,
		SUCCESS
	}
}
