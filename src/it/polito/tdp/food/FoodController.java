/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */
package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.db.Condiment;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	Model model = new Model();

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField txtCalorie;

	@FXML
	private Button btnCreaGrafo;

	@FXML
	private ComboBox<Condiment> boxIngrediente;

	@FXML
	private Button btnDietaEquilibrata;

	@FXML
	private TextArea txtResult;

	@FXML
	void doCalcolaDieta(ActionEvent event) {
		Condiment c1= boxIngrediente.getValue();
		if(c1==null) {
			txtResult.appendText("devi selezionare un condimento");
			return;
		}
		
		List<Condiment> insieme= this.model.calcolaInsieme(c1);
		txtResult.clear();
		txtResult.appendText("L'insieme dei condimenti e' \n\n");
		for(Condiment c: insieme) {
			txtResult.appendText(c.getDisplay_name()+"\n");
		}

	}

	@FXML
	void doCreaGrafo(ActionEvent event) {
  
		try { 
			double calorie=0;
			calorie  = Double.parseDouble(txtCalorie.getText());
			 model.getCondimentCalorie(calorie);
      boxIngrediente.getItems().addAll(  model.getCondimentCalorie(calorie));
      model.creaGrafo();
     String s= model.getCalorieeTot();

      txtResult.appendText("Gli ingredienti sono : \n "+s);

		} catch (NumberFormatException e ) {
		
		return ;
			
		}
     
      
      
	}

	@FXML
	void initialize() {
		assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
		assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Food.fxml'.";
		assert boxIngrediente != null : "fx:id=\"boxIngrediente\" was not injected: check your FXML file 'Food.fxml'.";
		assert btnDietaEquilibrata != null : "fx:id=\"btnDietaEquilibrata\" was not injected: check your FXML file 'Food.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
	}
}
