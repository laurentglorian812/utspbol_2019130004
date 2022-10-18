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
public class FXMLDataBandaraController implements Initializable {
    private BandaraModel dt=new BandaraModel();

    @FXML
    private TextField searchbandara;
    @FXML
    private TableView<BandaraModel> tbvbandara;
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
        ObservableList<BandaraModel> data=FXMLDocumentController.dtbandara.Load();
        if(data!=null){            
            tbvbandara.getColumns().clear();            
            tbvbandara.getItems().clear();
            TableColumn col=new TableColumn("IATA");
            col.setCellValueFactory(new PropertyValueFactory<BandaraModel, String>("iata"));
            tbvbandara.getColumns().addAll(col);
            col=new TableColumn("Nama Bandara");
            col.setCellValueFactory(new PropertyValueFactory<BandaraModel, String>("nama"));
            tbvbandara.getColumns().addAll(col);
            col=new TableColumn("Kota");
            col.setCellValueFactory(new PropertyValueFactory<BandaraModel, String>("kota"));
            tbvbandara.getColumns().addAll(col);
            col=new TableColumn("Negara");
            col.setCellValueFactory(new PropertyValueFactory<BandaraModel, String>("negara"));
            tbvbandara.getColumns().addAll(col);
            tbvbandara.setItems(data);
        }else {
            Alert a=new Alert(Alert.AlertType.ERROR,"Data bandara kosong",ButtonType.OK);
            a.showAndWait();
            tbvbandara.getScene().getWindow().hide();
        }                
    }

    @FXML
    private void cariData(KeyEvent event) {
        BandaraModel s = new BandaraModel();
        String key = searchbandara.getText();
        if(key!=""){
            ObservableList<BandaraModel> data=FXMLDocumentController.dtbandara.CariBandara(key,key,key,key);
            if(data!=null){            
                tbvbandara.getColumns().clear();
                tbvbandara.getItems().clear();
                TableColumn col=new TableColumn("IATA");
                col.setCellValueFactory(new PropertyValueFactory<BandaraModel, String>("iata"));
                tbvbandara.getColumns().addAll(col);
                col=new TableColumn("Nama Bandara");
                col.setCellValueFactory(new PropertyValueFactory<BandaraModel, String>("nama"));
                tbvbandara.getColumns().addAll(col);
                col=new TableColumn("Kota");
                col.setCellValueFactory(new PropertyValueFactory<BandaraModel, String>("kota"));
                tbvbandara.getColumns().addAll(col);
                col=new TableColumn("Negara");
                col.setCellValueFactory(new PropertyValueFactory<BandaraModel, String>("Negara"));
                tbvbandara.getColumns().addAll(col);
                tbvbandara.setItems(data);
            }else {
                Alert a=new Alert(Alert.AlertType.ERROR,"Data bandara yang dicari tidak ada",ButtonType.OK);
                a.showAndWait();
                tbvbandara.getScene().getWindow().hide();
            }            
        } else{
            showdata();
        }
    }

    @FXML
    private void awalklik(ActionEvent event) {
        tbvbandara.getSelectionModel().selectFirst();        
        tbvbandara.requestFocus();
    }

    @FXML
    private void sebelumklik(ActionEvent event) {
        tbvbandara.getSelectionModel().selectAboveCell();       
        tbvbandara.requestFocus();
    }

    @FXML
    private void sesudahklik(ActionEvent event) {
        tbvbandara.getSelectionModel().selectBelowCell();        
        tbvbandara.requestFocus();
    }

    @FXML
    private void akhirklik(ActionEvent event) {
        tbvbandara.getSelectionModel().selectLast();        
        tbvbandara.requestFocus();
    }

    @FXML
    private void tambahklik(ActionEvent event) {
        try{
            FXMLLoader loader=new FXMLLoader(getClass().getResource("FXMLInputBandara.fxml"));    
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
        BandaraModel s= new BandaraModel();       
        s=tbvbandara.getSelectionModel().getSelectedItem();
        Alert a=new Alert(Alert.AlertType.CONFIRMATION,"Mau dihapus?",ButtonType.YES,ButtonType.NO);
        a.showAndWait();
        if(a.getResult()==ButtonType.YES){
            if(FXMLDocumentController.dtbandara.delete(s.getIata())){
                Alert b=new Alert(Alert.AlertType.INFORMATION,"Data bandara berhasil dihapus", ButtonType.OK);
                b.showAndWait();
            } else {
                Alert b=new Alert(Alert.AlertType.ERROR,"Data bandara gagal dihapus", ButtonType.OK);
                b.showAndWait();               
            }    
           showdata();           
           awalklik(event);       
        }
    }

    @FXML
    private void ubahklik(ActionEvent event) {
        BandaraModel s= new BandaraModel();
        s=tbvbandara.getSelectionModel().getSelectedItem();
        try{
            FXMLLoader loader=new FXMLLoader(getClass().getResource("FXMLInputBandara.fxml"));    
            Parent root = (Parent)loader.load();
            FXMLInputBandaraController isidt=(FXMLInputBandaraController)loader.getController();
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
