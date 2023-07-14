package it.polito.tdp.gosales;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.gosales.model.ClasseComponente;
import it.polito.tdp.gosales.model.Model;
import it.polito.tdp.gosales.model.Retailers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAnalizzaComponente;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnSimula;

    @FXML
    private ComboBox<Integer> cmbAnno;

    @FXML
    private ComboBox<String> cmbNazione;

    @FXML
    private ComboBox<?> cmbProdotto;

    @FXML
    private ComboBox<Retailers> cmbRivenditore;

    @FXML
    private TextArea txtArchi;

    @FXML
    private TextField txtN;

    @FXML
    private TextField txtNProdotti;

    @FXML
    private TextField txtQ;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextArea txtVertici;

    @FXML
    void doAnalizzaComponente(ActionEvent event) {
    	Retailers venditore = this.cmbRivenditore.getValue();
    	if (this.cmbRivenditore==null) {
    		txtResult.setText("Scegli il venditore");
    		return;
    	}
    	//Se sono qui ho tutte le informazioni per trovare la componente
    	ClasseComponente componente = this.model.analizzaComponente(venditore);
    	txtResult.appendText("\nLa componente connessa di "+venditore.getName() +" ha dimensione"+componente.getDimensione()+"\n");
    	txtResult.appendText("Il peso totale degli archi della componente connessa è "+componente.getSommaPesi());
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	Integer anno = this.cmbAnno.getValue();
    	String nazione = this.cmbNazione.getValue();
    	if (anno == null || nazione ==null) {
    		txtResult.setText("Inserire un anno e una nazione della tendina");
    		return;
    	}
    		
    	int nCommonProducts = 0;
    	try {
    		nCommonProducts = Integer.parseInt(this.txtNProdotti.getText());
    	}catch(NumberFormatException e) {
    		txtResult.setText("Inserisci un valore numerico");
    		return;
    	}
    	//Se sono qui tutti i valori sono corretti
    	this.model.BuildGraph(anno, nazione, nCommonProducts);
    	if (this.model.getArchi().size()!=0) {
    		txtResult.setText("Grafo creato con successo \n");
    		txtResult.appendText(this.model.getVertici().size()+"-"+ this.model.getArchi().size()+"\n");
    	}
    	this.btnAnalizzaComponente.setDisable(false);
    	this.cmbRivenditore.getItems().clear();
    	this.cmbRivenditore.getItems().addAll(this.model.getVertici());
    	this.cmbRivenditore.setDisable(false);
    	
    }

    @FXML
    void doSimulazione(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnAnalizzaComponente != null : "fx:id=\"btnAnalizzaComponente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNazione != null : "fx:id=\"cmbNazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbProdotto != null : "fx:id=\"cmbProdotto\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbRivenditore != null : "fx:id=\"cmbRivenditore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtArchi != null : "fx:id=\"txtArchi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNProdotti != null : "fx:id=\"txtNProdotti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtQ != null : "fx:id=\"txtQ\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtVertici != null : "fx:id=\"txtVertici\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	List<Integer> listaAnni = new ArrayList<>();
    	for (int i = 2015; i<= 2018; i++) {
    		listaAnni.add(i);
    	}
    	this.cmbAnno.getItems().addAll(listaAnni);
    	this.cmbNazione.getItems().addAll(this.model.getCountry());
    	this.btnAnalizzaComponente.setDisable(true);
    }

}
