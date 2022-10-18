/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package utspbol_2019130004;

import java.net.URL;
import java.text.SimpleDateFormat;
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
public class FXMLInputJadwalController implements Initializable {

    boolean editdata=false;

    
    @FXML
    private TextField txtno;
    @FXML
    private TextField txthari;
    @FXML
    private TextField txtberangkat;
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
    
    public void execute(JadwalModel d){
        if(!d.getNo().isEmpty()){
            editdata=true;
            txtno.setText(d.getNo());
            txthari.setText(d.getHari()); 
            txtberangkat.setText(d.getBerangkat());
            txtno.setEditable(false);
            txthari.requestFocus();         
        }
    }

    @FXML
    private void simpanklik(ActionEvent event) {
        JadwalModel n=new JadwalModel();        
        n.setNo(txtno.getText());
        n.setHari(txthari.getText());
        n.setBerangkat(txtberangkat.getText());
        FXMLDocumentController.dtjadwal.setJadwalModel(n);
        if(editdata){
            if(FXMLDocumentController.dtjadwal.update()){
                Alert a=new Alert(Alert.AlertType.INFORMATION,"Data jadwal berhasil diubah",ButtonType.OK);
                a.showAndWait();   
                txtno.setEditable(true);        
                batalklik(event);                
            } else {               
                Alert a=new Alert(Alert.AlertType.ERROR,"Data jadwal gagal diubah",ButtonType.OK);
                a.showAndWait(); 
            }
        }else if(FXMLDocumentController.dtjadwal.validasi(n.getNo())<=0){
            if(FXMLDocumentController.dtjadwal.insert()){
                Alert a=new Alert(Alert.AlertType.INFORMATION,"Data jadwal berhasil disimpan",ButtonType.OK);
                a.showAndWait();            
                batalklik(event);
            } else {
                Alert a=new Alert(Alert.AlertType.ERROR,"Data jadwal gagal disimpan",ButtonType.OK);
                a.showAndWait();            
            }
        }else{Alert a=new Alert(Alert.AlertType.ERROR,"Data jadwal sudah ada",ButtonType.OK);
            a.showAndWait();
            txtno.requestFocus();
        }
    }

    @FXML
    private void batalklik(ActionEvent event) {
        txtno.setText("");        
        txthari.setText("");    
        txtberangkat.setText("");
        txtno.requestFocus();
    }
    
}
