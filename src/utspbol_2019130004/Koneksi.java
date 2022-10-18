/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utspbol_2019130004;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 *
 * @author Laurent Glorian - 2019130004
 */
public class Koneksi {
    public Connection db_uts;
    public Statement statement;
    public PreparedStatement preparedStatement;
    
    public Koneksi() {
        this.db_uts = null;
    }
    
    public void bukaKoneksi() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            db_uts = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_uts?user=root&password=");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void tutupKoneksi() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (db_uts != null) {
                db_uts.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
