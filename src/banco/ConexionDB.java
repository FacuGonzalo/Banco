package banco;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionDB {
	
	private static ConexionDB instance = null;
	
	protected Connection conexionBD = null;
	
	private ConexionDB () {
		try
        {  // Se carga y registra el driver JDBC de MySQL  
		    // no es necesario para versiones de jdbc posteriores a 4.0 
           Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        }
        catch (Exception ex)
        {  
           System.out.println(ex.getMessage());
        }
		
		
		//se genera el string que define los datos de la conexión 
		 try {  
            String servidor = "localhost:3306";
            String baseDatos = "banco";
            String usuario = "admin";
            String clave = "admin";
            String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos +"?serverTimezone=UTC"; //&useSSL=false
            
            //se intenta establecer la conexión
            this.conexionBD = DriverManager.getConnection(uriConexion, usuario, clave);
         }
         catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
         }
	}

	public static ConexionDB getIntance() {

		if (instance == null) 
			instance = new ConexionDB();
			
		return instance;
	}
	
	
	public Connection getConexion() {
		return conexionBD;
	}
	
	public ResultSet consulta(String sql) {
		try {
	         // se crea una sentencia o comando jdbc para realizar la consulta 
	    	 // a partir de la coneccion establecida (conexionBD)
	         Statement stmt = conexionBD.createStatement();

	         // se ejecuta la sentencia y se recibe un resultset
	         ResultSet rs = stmt.executeQuery(sql);    
	        
	         // se cierran los recursos utilizados 
	         //rs.close();
	         //stmt.close();
	         
	         return rs;
	      }
		
	      catch (SQLException ex) {
	         // en caso de error, se muestra la causa en la consola
	         System.out.println("SQLException: " + ex.getMessage());
	         System.out.println("SQLState: " + ex.getSQLState());
	         System.out.println("VendorError: " + ex.getErrorCode());
	      }
		
		return null;
	}
}
