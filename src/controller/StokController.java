package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Drink;

public class StokController {
	private static List<Drink> productList = new ArrayList<Drink>() {{
		add(new Drink("�R�[��", 120, "./resource/cola.jpg"));
		add(new Drink("���b�h�u��", 190, "./resource/redbull.jpg"));
		add(new Drink("���񂮂�O���g", 100, "./resource/gungungrut.jpg"));
	}};
	private static Map<String, Integer> stockProductMap = new HashMap<String, Integer>() {{
		put("�R�[��", 5);
		put("���b�h�u��", 10);
		put("���񂮂�O���g", 0);
	}};

	public static List<Drink> getDrinkList() {
		return productList;
	}
	public static int getStockCount(Drink drink) {
		Integer stockCount = stockProductMap.get(drink.getDrinkName());
		if (stockCount == null) {
			return 0;
		} else {
			return stockCount;
		}
	}

	public static void sellDrink(Drink drink) {
		Integer stockCount = stockProductMap.get(drink.getDrinkName());
		if (stockCount != null && stockCount > 0) {
			stockProductMap.put(drink.getDrinkName(), stockCount - 1);
		}
	}
}
