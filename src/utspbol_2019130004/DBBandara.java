/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utspbol_2019130004;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Laurent Glorian - 2019130004
 */
public class DBBandara {
    private BandaraModel dt=new BandaraModel();
    
    public BandaraModel getBandaraModel(){
        return(dt);
    }
    
    public void setBandaraModel(BandaraModel s){
        dt=s;
    }
    
    public ObservableList<BandaraModel>  Load() {
        try {
            ObservableList<BandaraModel> tableData=FXCollections.observableArrayList();
            Koneksi con = new Koneksi();            
            con.bukaKoneksi();
            con.statement = con.db_uts.createStatement();
            ResultSet rs = con.statement.executeQuery("select * from bandara");
            int i = 1;
            while (rs.next()) {
                BandaraModel d=new BandaraModel();
                d.setIata(rs.getString("iata"));
                d.setNama(rs.getString("namabandara"));
                d.setKota(rs.getString("kota"));                
                d.setNegara(rs.getString("negara"));
                tableData.add(d);                
                i++;            
            }
            con.tutupKoneksi();            
            return tableData;
        } catch (Exception e) {            
            e.printStackTrace();            
            return null;        
        }
    }
    
    public int validasi(String nomor) {
        int val = 0;
        try {         
            Koneksi con = new Koneksi();            
            con.bukaKoneksi();
            con.statement = con.db_uts.createStatement();
            ResultSet rs = con.statement.executeQuery("select count (*) as jml from bandara where iata = '" + nomor + "'");
            while (rs.next()) {
                val = rs.getInt("jml");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {            
            e.printStackTrace();
        }
        return val;
    }
    
    public boolean insert() {
        boolean berhasil = false;        
        Koneksi con = new Koneksi();
        try {       
            con.bukaKoneksi();
            con.preparedStatement = con.db_uts.prepareStatement("insert into bandara (iata, namabandara, kota, negara) values (?,?,?,?)");
            con.preparedStatement.setString(1, getBandaraModel().getIata());           
            con.preparedStatement.setString(2, getBandaraModel().getNama());
            con.preparedStatement.setString(3, getBandaraModel().getKota());
            con.preparedStatement.setString(4, getBandaraModel().getNegara()); 
            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {            
            e.printStackTrace();            
            berhasil = false;
        } finally {            
            con.tutupKoneksi();            
            return berhasil;        
        }    
    }
    
    public boolean delete(String nomor) {
        boolean berhasil = false;        
        Koneksi con = new Koneksi();
        try {            
            con.bukaKoneksi();;
            con.preparedStatement = con.db_uts.prepareStatement("delete from bandara where iata = ?");
            con.preparedStatement.setString(1, nomor);
            con.preparedStatement.executeUpdate();            
            berhasil = true;
        } catch (Exception e) {            
            e.printStackTrace();
        } finally {            
            con.tutupKoneksi();            
            return berhasil;        
        }
    }
    
    public boolean update() {
        boolean berhasil = false;        
        Koneksi con = new Koneksi();
        try {            
            con.bukaKoneksi();
            con.preparedStatement = con.db_uts.prepareStatement("update bandara set namabandara = ?, kota = ?, negara = ? where iata = ?");
            con.preparedStatement.setString(1, getBandaraModel().getNama());
            con.preparedStatement.setString(2, getBandaraModel().getKota());
            con.preparedStatement.setString(3, getBandaraModel().getNegara()); 
            con.preparedStatement.setString(4, getBandaraModel().getIata()); 
            con.preparedStatement.executeUpdate();            
            berhasil = true;
        } catch (Exception e) {            
            e.printStackTrace();            
            berhasil = false;
        } finally {            
            con.tutupKoneksi();            
            return berhasil;        
        }    
    }
    
    public ObservableList<BandaraModel>  CariBandara(String iata, String nama, String kota, String negara) {
        try {    
            ObservableList<BandaraModel> 	tableData;
            tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi(); 
            con.bukaKoneksi();
            con.statement = (Statement) con.db_uts.createStatement();
            ResultSet rs = (ResultSet) con.statement.executeQuery("select * from bandara WHERE iata LIKE '" + iata + "%' OR namabandara LIKE '" + nama + "%' OR kota LIKE '" + kota + "%' OR negara LIKE '" + negara + "%'");
            int i = 1;
            while(rs.next()){
                BandaraModel d = new BandaraModel();
                d.setIata(rs.getString("iata"));
                d.setNama(rs.getString("namabandara"));
                d.setKota(rs.getString("kota"));
                d.setNegara(rs.getString("negara"));
                tableData.add(d);
                i++;
            }
            con.tutupKoneksi();
            return tableData;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
