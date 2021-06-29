/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.genes;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Model;
import it.polito.tdp.genes.model.Vicino;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="cmbGeni"
    private ComboBox<Genes> cmbGeni; // Value injected by FXMLLoader

    @FXML // fx:id="btnGeniAdiacenti"
    private Button btnGeniAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="txtIng"
    private TextField txtIng; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("grafo creato\n");
    	model.creaGrafo();
    	txtResult.appendText("#vertici: "+model.getNumVertici()+ "\n");
    	txtResult.appendText("#archi: "+model.getNumArchi()+"\n");
    	
    	cmbGeni.getItems().clear();
    	this.cmbGeni.getItems().addAll(model.getVertici());

    }

    @FXML
    void doGeniAdiacenti(ActionEvent event) {

    	txtResult.clear();
    	Genes g= cmbGeni.getValue();
    	if(g==null) {
    		txtResult.appendText("selezioanre gene e creare il grafo");
    		return;
    	}
    	if(!model.grafoCreato()) {
    		txtResult.appendText("creare prima il grafo");
    		return;
    	}
    	for(Vicino v: model.getVicini(g)) {
    		txtResult.appendText(v+"\n");
    	}
    	
    }

    @FXML
    void doSimula(ActionEvent event) {

    	txtResult.clear();
    	Genes g= cmbGeni.getValue();
    	if(g==null) {
    		txtResult.appendText("selezioanre gene e creare il grafo");
    		return;
    	}
    	int n;
    	try {
    		n= Integer.parseInt(this.txtIng.getText());
    	}catch(NumberFormatException e) {
    		txtResult.appendText("selezionare numerodi ingegneri");
    		return;
    	}
    	Map<Genes, Integer> res= model.simula(n, g);
    	for(Genes gene: res.keySet()) {
    		txtResult.appendText(gene+ " studiato da "+ res.get(gene)+"\n");
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbGeni != null : "fx:id=\"cmbGeni\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnGeniAdiacenti != null : "fx:id=\"btnGeniAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtIng != null : "fx:id=\"txtIng\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
    
}
