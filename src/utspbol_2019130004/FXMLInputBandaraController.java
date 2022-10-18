/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package utspbol_2019130004;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Laurent Glorian - 2019130004
 */
public class FXMLInputBandaraController implements Initializable {
    
    boolean editdata=false;

    @FXML
    private TextField txtiata;
    @FXML
    private TextField txtnama;
    @FXML
    private TextField txtkota;
    @FXML
    private TextField txtnegara;
    @FXML
    private Button btnsimpan;
    @FXML
    private Button btnbatal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void execute(BandaraModel d){
        if(!d.getIata().isEmpty()){
            editdata=true;
            txtiata.setText(d.getIata());
            txtnama.setText(d.getNama());
            txtkota.setText(d.getKota());
            txtnegara.setText(d.getNegara());
            txtiata.setEditable(false);
            txtnama.requestFocus();         
        }
    }

    @FXML
    private void simpanklik(ActionEvent event) {
        BandaraModel n=new BandaraModel();        
        n.setIata(txtiata.getText());
        n.setNama(txtnama.getText());
        n.setKota(txtkota.getText());
        n.setNegara(txtnegara.getText());
        FXMLDocumentController.dtbandara.setBandaraModel(n);
        if(editdata){
            if(FXMLDocumentController.dtbandara.update()){
                Alert a=new Alert(Alert.AlertType.INFORMATION,"Data bandara berhasil diubah",ButtonType.OK);
                a.showAndWait();   
                txtiata.setEditable(true);        
                batalklik(event);                
            } else {               
                Alert a=new Alert(Alert.AlertType.ERROR,"Data bandara gagal diubah",ButtonType.OK);
                a.showAndWait(); 
            }
        }else if(FXMLDocumentController.dtbandara.validasi(n.getIata())<=0){
            if(FXMLDocumentController.dtbandara.insert()){
                Alert a=new Alert(Alert.AlertType.INFORMATION,"Data bandara berhasil disimpan",ButtonType.OK);
                a.showAndWait();            
                batalklik(event);
            } else {
                Alert a=new Alert(Alert.AlertType.ERROR,"Data bandara gagal disimpan",ButtonType.OK);
                a.showAndWait();            
            }
        }else{Alert a=new Alert(Alert.AlertType.ERROR,"Data bandara sudah ada",ButtonType.OK);
            a.showAndWait();
            txtiata.requestFocus();
        }
    }

    @FXML
    private void batalklik(ActionEvent event) {
        txtiata.setText("");        
        txtnama.setText("");    
        txtkota.setText("");  
        txtnegara.setText("");
        txtiata.requestFocus();
    }
    
}
