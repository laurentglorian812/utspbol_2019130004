/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utspbol_2019130004;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Laurent Glorian - 2019130004
 */
public class DBJadwal {
    private JadwalModel dt=new JadwalModel();
    
    public JadwalModel getJadwalModel(){
        return(dt);
    }
    
    public void setJadwalModel(JadwalModel s){
        dt=s;
    }
    
    public ObservableList<JadwalModel>  Load() {
        try {
            ObservableList<JadwalModel> tableData=FXCollections.observableArrayList();
            Koneksi con = new Koneksi();            
            con.bukaKoneksi();
            con.statement = con.db_uts.createStatement();
            ResultSet rs = con.statement.executeQuery("select * from jadwal");
            int i = 1;
            while (rs.next()) {
                JadwalModel d=new JadwalModel();
                d.setNo(rs.getString("nojadwal"));
                d.setHari(rs.getString("hari"));
                d.setBerangkat(rs.getString("keberangkatan"));
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
            ResultSet rs = con.statement.executeQuery("select count (*) as jml from jadwal where nojadwal = '" + nomor + "'");
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
            con.preparedStatement = con.db_uts.prepareStatement("insert into jadwal (nojadwal, hari, keberangkatan) values (?,?,?)");
            con.preparedStatement.setString(1, getJadwalModel().getNo());           
            con.preparedStatement.setString(2, getJadwalModel().getHari());
            con.preparedStatement.setString(3, getJadwalModel().getBerangkat());
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
            con.preparedStatement = con.db_uts.prepareStatement("delete from jadwal where nojadwal = ?");
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
            con.preparedStatement = con.db_uts.prepareStatement("update jadwal set hari = ?, keberangkatan = ? where nojadwal = ?");
            con.preparedStatement.setString(1, getJadwalModel().getHari());
            con.preparedStatement.setString(2, getJadwalModel().getBerangkat());
            con.preparedStatement.setString(3, getJadwalModel().getNo());
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
    
    public ObservableList<JadwalModel>  CariJadwal(String no, String hari) {
        try {    
            ObservableList<JadwalModel> 	tableData;
            tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi(); 
            con.bukaKoneksi();
            con.statement = (Statement) con.db_uts.createStatement();
            ResultSet rs = (ResultSet) con.statement.executeQuery("select * from jadwal WHERE nojadwal LIKE '" + no + "%' OR hari LIKE '" + hari + "%'");
            int i = 1;
            while(rs.next()){
                JadwalModel d = new JadwalModel();
                d.setNo(rs.getString("nojadwal"));
                d.setHari(rs.getString("hari"));
                d.setBerangkat(rs.getString("keberangkatan"));
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
