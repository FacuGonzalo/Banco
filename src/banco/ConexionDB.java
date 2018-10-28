package banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionDB {
	//comentario
	
	private static ConexionDB instance = null;
	
	protected Connection conexionBD;
	
	private ConexionDB () {
		conexionBD = null;
		/*try
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
            //String usuario = "admin";
            //String clave = "admin";
            String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos +"?serverTimezone=UTC"; //&useSSL=false
            
            //se intenta establecer la conexión
            this.conexionBD = DriverManager.getConnection(uriConexion, u, c);
         }
         catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
         }*/
	}

	public boolean ingresar(String usr,String pass) throws SQLException {
		@SuppressWarnings("unused")
		String error = null;
		try {      
           Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        }
        catch (Exception ex) {  
           System.out.println(ex.getMessage());
        }
		
		
		//se genera el string que define los datos de la conexión 
		String servidor = "localhost:3306";
		String baseDatos = "banco";
		String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos +"?serverTimezone=UTC"; //&useSSL=false           
		
		//se intenta establecer la conexión
         try { 
            this.conexionBD = DriverManager.getConnection(uriConexion, usr, pass);
            return true;
         }
         catch (SQLException ex) {
        	 if(ex.getSQLState().equals("28000")) //Si es una error de autentificación
 				error = "Usuario o Contraseña incorrectos";
 			else
 				error = "Se produjo un error al intentar conectarse a la base de datos"+ex.getMessage();
        	 
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
         }
		
		return false;
	}
	
	public boolean ingresarATM(String usr,String pass) throws SQLException {
		boolean ingr = false;
		@SuppressWarnings("unused")
		String error = null;
		try {      
           Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        }
        catch (Exception ex) {  
           System.out.println(ex.getMessage());
        }
		
		
		//se genera el string que define los datos de la conexión 
		String servidor = "localhost:3306";
		String baseDatos = "banco";
		String usuario = "atm";
		String clave = "atm";
		String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos +"?serverTimezone=UTC"; //&useSSL=false           
		
		//se intenta establecer la conexión
         try { 
            this.conexionBD = DriverManager.getConnection(uriConexion, usuario, clave);
            
            String sql = "Select nro_tarjeta,PIN  FROM tarjeta WHERE nro_tarjeta = '"+usr+"' AND PIN = '"+pass+"';";
            
            Statement stmt = this.conexionBD.createStatement();

	        ResultSet rs = stmt.executeQuery(sql);
	        
	        while (rs.next()){
	        	ingr = true;
	        }
            
         }
         catch (SQLException ex) {
        	 
 			error = "Se produjo un error al intentar conectarse a la base de datos"+ex.getMessage();
        	 
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
         }
         
        		
		return ingr;
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
