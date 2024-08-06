
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Asus
 */
public class koneksi {
     Connection koneksi = null;
     public static Connection koneksiDB(){      
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection koneksi = DriverManager.getConnection("jdbc:mysql://localhost/perpus_app", "root", "");
            //JOptionPane.showMessageDialog(null, "Koneksi Sukses");
            return koneksi;
        }catch(Exception e){
           JOptionPane.showMessageDialog(null,e);
            return null;
        }
    }
    
}
