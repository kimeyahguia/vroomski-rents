
package VRSdatabase;
import java.sql.*;
import javax.swing.JOptionPane;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class DBConnect {
    private static Connection vehicle_rental;
    private static DBConnect dbconnect;
    private static String url = "jdbc:mysql://localhost:3306/vehiclerentalsystem";
    private static String user = "root";
    private static String password = "";
    
    public DBConnect (){
        vehicle_rental = null;
        
        try {
           vehicle_rental = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "HINDE!");
        }
    }
    
    public static DBConnect getBConnect(){
        if (dbconnect == null){
            dbconnect = new DBConnect();
            
        }
        return dbconnect;
    }
    
    public static Connection getConnection(){
        return vehicle_rental;
    }
}
