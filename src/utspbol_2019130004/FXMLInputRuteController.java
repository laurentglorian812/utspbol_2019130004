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
public class FXMLInputRuteController implements Initializable {
    
    boolean editdata=false;

    @FXML
    private TextField txtid;
    @FXML
    private TextField txtmaskapai;
    @FXML
    private TextField txtdari;
    @FXML
    private Button btnsimpan;
    @FXML
    private Button btnbatal;
    @FXML
    private TextField txtke;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void execute(RuteModel d){
        if(!d.getIdpenerbangan().isEmpty()){
            editdata=true;
            txtid.setText(d.getIdpenerbangan());
            txtmaskapai.setText(d.getMaskapai());
            txtdari.setText(d.getDari());
            txtke.setText(d.getKe());
            txtid.setEditable(false);
            txtmaskapai.requestFocus();         
        }
    }

    @FXML
    private void simpanklik(ActionEvent event) {
        RuteModel n=new RuteModel();        
        n.setIdpenerbangan(txtid.getText());
        n.setMaskapai(txtmaskapai.getText());
        n.setDari(txtdari.getText());
        n.setKe(txtke.getText());
        FXMLDocumentController.dtrute.setRuteModel(n);
        if(editdata){
            if(FXMLDocumentController.dtrute.update()){
                Alert a=new Alert(Alert.AlertType.INFORMATION,"Data rute penerbangan berhasil diubah",ButtonType.OK);
                a.showAndWait();   
                txtid.setEditable(true);        
                batalklik(event);                
            } else {               
                Alert a=new Alert(Alert.AlertType.ERROR,"Data rute penerbangan gagal diubah",ButtonType.OK);
                a.showAndWait(); 
            }
        }else if(FXMLDocumentController.dtrute.validasi(n.getIdpenerbangan())<=0){
            if(FXMLDocumentController.dtrute.insert()){
                Alert a=new Alert(Alert.AlertType.INFORMATION,"Data rute penerbangan berhasil disimpan",ButtonType.OK);
                a.showAndWait();            
                batalklik(event);
            } else {
                Alert a=new Alert(Alert.AlertType.ERROR,"Data rute penerbangan gagal disimpan",ButtonType.OK);
                a.showAndWait();            
            }
        }else{Alert a=new Alert(Alert.AlertType.ERROR,"Data rute penerbangan sudah ada",ButtonType.OK);
            a.showAndWait();
            txtid.requestFocus();
        }
    }

    @FXML
    private void batalklik(ActionEvent event) {
        txtid.setText("");        
        txtmaskapai.setText("");    
        txtdari.setText("");  
        txtke.setText("");
        txtid.requestFocus();
    }
    
}
