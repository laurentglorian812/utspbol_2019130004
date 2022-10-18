/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package utspbol_2019130004;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Laurent Glorian - 2019130004
 */
public class FXMLDataRuteController implements Initializable {
    private RuteModel dt=new RuteModel();

    @FXML
    private Button btnawal;
    @FXML
    private Button btnsebelum;
    @FXML
    private Button btnsesudah;
    @FXML
    private Button btnakhir;
    @FXML
    private Button btntambah;
    @FXML
    private Button btnhapus;
    @FXML
    private Button btnubah;
    @FXML
    private TableView<RuteModel> tbvrute;
    @FXML
    private TextField searchrute;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showdata();
    }    
    
    public void showdata(){       
        ObservableList<RuteModel> data=FXMLDocumentController.dtrute.Load();
        if(data!=null){            
            tbvrute.getColumns().clear();            
            tbvrute.getItems().clear();
            TableColumn col=new TableColumn("ID Penerbangan");
            col.setCellValueFactory(new PropertyValueFactory<RuteModel, String>("idpenerbangan"));
            tbvrute.getColumns().addAll(col);
            col=new TableColumn("Maskapai");
            col.setCellValueFactory(new PropertyValueFactory<RuteModel, String>("maskapai"));
            tbvrute.getColumns().addAll(col);
            col=new TableColumn("Dari");
            col.setCellValueFactory(new PropertyValueFactory<RuteModel, String>("dari"));
            tbvrute.getColumns().addAll(col);
            col=new TableColumn("Ke");
            col.setCellValueFactory(new PropertyValueFactory<RuteModel, String>("ke"));
            tbvrute.getColumns().addAll(col);
//            col=new TableColumn("Jenis");
//            col.setCellValueFactory(new PropertyValueFactory<RuteModel, String>("jenis"));
//            tbvrute.getColumns().addAll(col);
            tbvrute.setItems(data);
        }else {
            Alert a=new Alert(Alert.AlertType.ERROR,"Data rute penerbangan kosong",ButtonType.OK);
            a.showAndWait();
            tbvrute.getScene().getWindow().hide();
        }                
    }

    @FXML
    private void awalklik(ActionEvent event) {
        tbvrute.getSelectionModel().selectFirst();        
        tbvrute.requestFocus();
    }

    @FXML
    private void sebelumklik(ActionEvent event) {
        tbvrute.getSelectionModel().selectAboveCell();       
        tbvrute.requestFocus();
    }

    @FXML
    private void sesudahklik(ActionEvent event) {
        tbvrute.getSelectionModel().selectBelowCell();        
        tbvrute.requestFocus();
    }

    @FXML
    private void akhirklik(ActionEvent event) {
        tbvrute.getSelectionModel().selectLast();        
        tbvrute.requestFocus();
    }

    @FXML
    private void tambahklik(ActionEvent event) {
        try{
            FXMLLoader loader=new FXMLLoader(getClass().getResource("FXMLInputRute.fxml"));    
            Parent root = (Parent)loader.load();        
            Scene scene = new Scene(root);        
            Stage stg=new Stage();
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setResizable(false);        
            stg.setIconified(false);        
            stg.setScene(scene);
            stg.showAndWait();
        } catch (IOException e){   
            e.printStackTrace();
        }
        showdata();        
        awalklik(event);
    }

    @FXML
    private void hapusklik(ActionEvent event) {
        RuteModel s= new RuteModel();       
        s=tbvrute.getSelectionModel().getSelectedItem();
        Alert a=new Alert(Alert.AlertType.CONFIRMATION,"Mau dihapus?",ButtonType.YES,ButtonType.NO);
        a.showAndWait();
        if(a.getResult()==ButtonType.YES){
            if(FXMLDocumentController.dtrute.delete(s.getIdpenerbangan())){
                Alert b=new Alert(Alert.AlertType.INFORMATION,"Data rute penerbangan berhasil dihapus", ButtonType.OK);
                b.showAndWait();
            } else {
                Alert b=new Alert(Alert.AlertType.ERROR,"Data rute penerbangan gagal dihapus", ButtonType.OK);
                b.showAndWait();               
            }    
           showdata();           
           awalklik(event);       
        }
    }

    @FXML
    private void ubahklik(ActionEvent event) {
        RuteModel s= new RuteModel();
        s=tbvrute.getSelectionModel().getSelectedItem();
        try{
            FXMLLoader loader=new FXMLLoader(getClass().getResource("FXMLInputRute.fxml"));    
            Parent root = (Parent)loader.load();
            FXMLInputRuteController isidt=(FXMLInputRuteController)loader.getController();
            isidt.execute(s);                
            Scene scene = new Scene(root);
            Stage stg=new Stage();
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setResizable(false);
            stg.setIconified(false);
            stg.setScene(scene);
            stg.showAndWait();
        } catch (IOException e){
            e.printStackTrace();
        }
        showdata();  
        awalklik(event);
    }

    @FXML
    private void cariData(KeyEvent event) {
        RuteModel s = new RuteModel();
        String key = searchrute.getText();
        if(key!=""){
            ObservableList<RuteModel> data=FXMLDocumentController.dtrute.CariRute(key,key,key,key);
            if(data!=null){            
                tbvrute.getColumns().clear();
                tbvrute.getItems().clear();
                TableColumn col=new TableColumn("ID Penerbangan");
                col.setCellValueFactory(new PropertyValueFactory<RuteModel, String>("idpenerbangan"));
                tbvrute.getColumns().addAll(col);
                col=new TableColumn("Maskapai");
                col.setCellValueFactory(new PropertyValueFactory<RuteModel, String>("maskapai"));
                tbvrute.getColumns().addAll(col);
                col=new TableColumn("Dari");
                col.setCellValueFactory(new PropertyValueFactory<RuteModel, String>("dari"));
                tbvrute.getColumns().addAll(col);
                col=new TableColumn("Ke");
                col.setCellValueFactory(new PropertyValueFactory<RuteModel, String>("ke"));
                tbvrute.getColumns().addAll(col);
                tbvrute.setItems(data);
            }else {
                Alert a=new Alert(Alert.AlertType.ERROR,"Data rute penerbangan yang dicari tidak ada",ButtonType.OK);
                a.showAndWait();
                tbvrute.getScene().getWindow().hide();
            }            
        } else{
            showdata();
        }
    }
}
