package it.polito.tdp.food.db;


public class TestDao {

	public static void main(String[] args) {
		FoodDao dao = new FoodDao();
		
		System.out.println(dao.listAllFood());
		System.out.println(dao.listAllCondiment());
		System.out.println("Condimnento calorie");
		System.out.println(dao.listAllCondimentCalories(50));
		//System.out.println(dao.getAdiacenze(74303000, 83112500).toString());

	}

}
