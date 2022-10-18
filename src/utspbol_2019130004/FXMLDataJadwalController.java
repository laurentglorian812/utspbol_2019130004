/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package utspbol_2019130004;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
public class FXMLDataJadwalController implements Initializable {
    private JadwalModel dt=new JadwalModel();

    @FXML
    private TextField searchjadwal;
    @FXML
    private TableView<JadwalModel> tbvjadwal;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showdata();
    }    
    
    public void showdata(){       
        ObservableList<JadwalModel> data=FXMLDocumentController.dtjadwal.Load();
        if(data!=null){            
            tbvjadwal.getColumns().clear();            
            tbvjadwal.getItems().clear();
            TableColumn col=new TableColumn("No Jadwal");
            col.setCellValueFactory(new PropertyValueFactory<JadwalModel, String>("no"));
            tbvjadwal.getColumns().addAll(col);
            col=new TableColumn("Hari");
            col.setCellValueFactory(new PropertyValueFactory<JadwalModel, String>("hari"));
            tbvjadwal.getColumns().addAll(col);
            col=new TableColumn("Keberangkatan");
            col.setCellValueFactory(new PropertyValueFactory<JadwalModel, String>("berangkat"));
            tbvjadwal.getColumns().addAll(col);
            tbvjadwal.setItems(data);
        }else {
            Alert a=new Alert(Alert.AlertType.ERROR,"Data jadwal kosong",ButtonType.OK);
            a.showAndWait();
            tbvjadwal.getScene().getWindow().hide();
        }                
    }

    @FXML
    private void cariData(KeyEvent event) {
        JadwalModel s = new JadwalModel();
        String key = searchjadwal.getText();
        if(key!=""){
            ObservableList<JadwalModel> data=FXMLDocumentController.dtjadwal.CariJadwal(key,key);
            if(data!=null){            
                tbvjadwal.getColumns().clear();
                tbvjadwal.getItems().clear();
                TableColumn col=new TableColumn("No Jadwal");
                col.setCellValueFactory(new PropertyValueFactory<JadwalModel, String>("no"));
                tbvjadwal.getColumns().addAll(col);
                col=new TableColumn("Hari");
                col.setCellValueFactory(new PropertyValueFactory<JadwalModel, String>("hari"));
                tbvjadwal.getColumns().addAll(col);
                col=new TableColumn("Keberangkatan");
                col.setCellValueFactory(new PropertyValueFactory<JadwalModel, String>("berangkat"));
                tbvjadwal.getColumns().addAll(col);
                tbvjadwal.setItems(data);
            }else {
                Alert a=new Alert(Alert.AlertType.ERROR,"Data jadwal yang dicari tidak ada",ButtonType.OK);
                a.showAndWait();
                tbvjadwal.getScene().getWindow().hide();
            }            
        } else{
            showdata();
        }
    }

    @FXML
    private void awalklik(ActionEvent event) {
        tbvjadwal.getSelectionModel().selectFirst();        
        tbvjadwal.requestFocus();
    }

    @FXML
    private void sebelumklik(ActionEvent event) {
        tbvjadwal.getSelectionModel().selectAboveCell();       
        tbvjadwal.requestFocus();
    }

    @FXML
    private void sesudahklik(ActionEvent event) {
        tbvjadwal.getSelectionModel().selectBelowCell();        
        tbvjadwal.requestFocus();
    }

    @FXML
    private void akhirklik(ActionEvent event) {
        tbvjadwal.getSelectionModel().selectLast();        
        tbvjadwal.requestFocus();
    }

    @FXML
    private void tambahklik(ActionEvent event) {
        try{
            FXMLLoader loader=new FXMLLoader(getClass().getResource("FXMLInputJadwal.fxml"));    
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
        JadwalModel s= new JadwalModel();       
        s=tbvjadwal.getSelectionModel().getSelectedItem();
        Alert a=new Alert(Alert.AlertType.CONFIRMATION,"Mau dihapus?",ButtonType.YES,ButtonType.NO);
        a.showAndWait();
        if(a.getResult()==ButtonType.YES){
            if(FXMLDocumentController.dtjadwal.delete(s.getNo())){
                Alert b=new Alert(Alert.AlertType.INFORMATION,"Data jadwal berhasil dihapus", ButtonType.OK);
                b.showAndWait();
            } else {
                Alert b=new Alert(Alert.AlertType.ERROR,"Data jadwal gagal dihapus", ButtonType.OK);
                b.showAndWait();               
            }    
           showdata();           
           awalklik(event);       
        }
    }

    @FXML
    private void ubahklik(ActionEvent event) {
        JadwalModel s= new JadwalModel();
        s=tbvjadwal.getSelectionModel().getSelectedItem();
        try{
            FXMLLoader loader=new FXMLLoader(getClass().getResource("FXMLInputJadwal.fxml"));    
            Parent root = (Parent)loader.load();
            FXMLInputJadwalController isidt=(FXMLInputJadwalController)loader.getController();
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
}
