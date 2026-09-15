package Clases;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;


public class CONECTAR {
    Connection conectar = null;
    
    public Connection conexion(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conectar = DriverManager.getConnection("jdbc:mysql://localhost/cesmagparqueadero","root","");
            
        }
        catch(Exception e){
            System.out.print(e.getMessage());
        }
        return conectar;
    }

    public com.mysql.jdbc.PreparedStatement prepareStatement(String query) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
