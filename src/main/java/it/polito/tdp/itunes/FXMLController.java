/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.itunes.model.Album;
import it.polito.tdp.itunes.model.Model;
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

    @FXML // fx:id="btnComponente"
    private Button btnComponente; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnSet"
    private Button btnSet; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA1"
    private ComboBox<Album> cmbA1; // Value injected by FXMLLoader

    @FXML // fx:id="txtDurata"
    private TextField txtDurata; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML
    void doComponente(ActionEvent event) {
    	
    	Album a1 = this.cmbA1.getValue();
    	
    	if(a1==null){
    		this.txtResult.clear();
    		this.txtResult.setText("Seleziona un album!"+"\n");
    		
    	}
    	
    	else {
    		Set<Album> componenti=this.model.getComponente(a1);
    		
    		 Double somma=0.0;
    		for(Album a: componenti) {
    			somma+= a.getDurata();
    		}
    		this.txtResult.clear();
    		this.txtResult.appendText("Componente connessa- "+ a1.getTitle()+"\n");
    		this.txtResult.appendText("Dimensione componente="+ componenti.size()+"\n");
    		this.txtResult.appendText("Durata compnente="+ somma+"\n");
    		
    	}
    	
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	 txtResult.clear();
     	
     	String sDouble =  txtDurata.getText() ;
  
     	
     	//CONTROLLI RIEMPIMENTO
     	if(sDouble.equals("")) {
     		txtResult.setText("Devi inserire un valore per la durata!\n");
     		return ;
     	}
     	 
     	 //INIZIALIZZO
     
     	Double durata;
     	 
     	//SOLO PER INT E DOUBLE
     	                                                      
     	try {
     	    durata = Double.parseDouble(sDouble) ;
     		                                                    
     		
     	} catch(NumberFormatException e) {
     		 txtResult.clear();
     		txtResult.appendText("La durata deve essere un valore numerico!\n");
     		return ;
     	}
     	
     	//CONTROLLI VARI
     	
     	
     	if (durata<=0) {
     		txtResult.clear();
     		txtResult.setText("La durata deve essere>0");
     		return;
     	}
   
     	
     	model.creaGrafo(durata);
     	
     	this.txtResult.setText("Grafo Creato!\n");
     	this.txtResult.appendText("#Vertici:"+this.model.nVertici()+"\n");
     	this.txtResult.appendText("#Archi:"+ this.model.nArchi()+"\n");
     	List<Album> album = this.model.getvertici();
     	Collections.sort(album);
     	this.cmbA1.getItems().addAll(album);
     	this.btnComponente.setDisable(false);
     	
    	
    }

    @FXML
    void doEstraiSet(ActionEvent event) {
    	Album a1 = cmbA1.getValue() ;
    	if(a1==null) {
    		txtResult.appendText("Seleziona un album\n");
    		return ;
    	}

    	String dTotS = txtX.getText() ;
    	if(dTotS.equals("")) {
    		txtResult.appendText("Specificare durata totale\n");
    		return ;
    	}
    	double dTot ;
    	try {
    		dTot = Double.parseDouble(dTotS) ;
    	} catch(NumberFormatException e) {
    		txtResult.appendText("Formato numero dTOT errato\n");
    		return ;
    	}
    
    this.model.creaSet(a1, dTot);
    Set<Album> ottimi = this.model.getBest();
    	
    	txtResult.appendText(ottimi+"\n");

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnComponente != null : "fx:id=\"btnComponente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSet != null : "fx:id=\"btnSet\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA1 != null : "fx:id=\"cmbA1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDurata != null : "fx:id=\"txtDurata\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";
        this.btnComponente.setDisable(true);

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    }

}
