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
public class DBRute {
    private RuteModel dt=new RuteModel();
    
    public RuteModel getRuteModel(){
        return(dt);
    }
    
    public void setRuteModel(RuteModel s){
        dt=s;
    }
    
    public ObservableList<RuteModel>  Load() {
        try {
            ObservableList<RuteModel> tableData=FXCollections.observableArrayList();
            Koneksi con = new Koneksi();            
            con.bukaKoneksi();
            con.statement = con.db_uts.createStatement();
            ResultSet rs = con.statement.executeQuery("select * from rute");
            int i = 1;
            while (rs.next()) {
                RuteModel d=new RuteModel();
                d.setIdpenerbangan(rs.getString("idpenerbangan"));
//                String id = (rs.getString("idpenerbangan")).substring(2,3);
                d.setMaskapai(rs.getString("maskapai"));
                d.setDari(rs.getString("dari"));                
                d.setKe(rs.getString("ke"));
//                String jenis = null;
//                if (id != "D") jenis = "Internasional";
//                else jenis = "Domestik";
//                d.setJenis(jenis);
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
            ResultSet rs = con.statement.executeQuery("select count (*) as jml from rute where idpenerbangan = '" + nomor + "'");
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
            con.preparedStatement = con.db_uts.prepareStatement("insert into rute (idpenerbangan, maskapai, dari, ke) values (?,?,?,?)");
            con.preparedStatement.setString(1, getRuteModel().getIdpenerbangan());           
            con.preparedStatement.setString(2, getRuteModel().getMaskapai());
            con.preparedStatement.setString(3, getRuteModel().getDari());
            con.preparedStatement.setString(4, getRuteModel().getKe()); 
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
            con.preparedStatement = con.db_uts.prepareStatement("delete from rute where idpenerbangan = ?");
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
            con.preparedStatement = con.db_uts.prepareStatement("update rute set maskapai = ?, dari = ?, ke = ? where idpenerbangan = ?");
            con.preparedStatement.setString(1, getRuteModel().getMaskapai());
            con.preparedStatement.setString(2, getRuteModel().getDari());
            con.preparedStatement.setString(3, getRuteModel().getKe());
            con.preparedStatement.setString(4, getRuteModel().getIdpenerbangan());
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
    
    public ObservableList<RuteModel>  CariRute(String id, String maskapai, String dari, String ke) {
        try {    
            ObservableList<RuteModel> 	tableData;
            tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi(); 
            con.bukaKoneksi();
            con.statement = (Statement) con.db_uts.createStatement();
            ResultSet rs = (ResultSet) con.statement.executeQuery("select * from rute WHERE idpenerbangan LIKE '" + id + "%' OR maskapai LIKE '" + maskapai + "%' OR dari LIKE '" + dari + "%' OR ke LIKE '" + ke + "%'");
            int i = 1;
            while(rs.next()){
                RuteModel d = new RuteModel();
                d.setIdpenerbangan(rs.getString("idpenerbangan"));
                d.setMaskapai(rs.getString("maskapai"));
                d.setDari(rs.getString("dari"));
                d.setKe(rs.getString("ke"));
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
