package banco;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ATM {
	
	//comentario
	//otro commen
	
	final ConexionDB conect;
	
	public  ATM() {
		conect = ConexionDB.getIntance();
	}
	
	public boolean ingresar (String nroTarjeta, String pin) {
		boolean ingresar = false;
		try {
			String sql = "SELECT * from tarjeta WHERE nro_tarjeta='"+nroTarjeta +"' AND pin='"+pin+"'";
			Statement stmt = conect.getConexion().createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next())
				ingresar = true;
			
			rs.close();
			stmt.close();
			
			return ingresar;
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public int consultarSaldo(String nroTarj) {
		int rta = -1;
		try {
			String sql = "select * from (tarjeta natural join cliente ) natural join caja_ahorro where nro_tarjeta='"+nroTarj+"'";
			Statement stmt = conect.getConexion().createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next())
				System.out.println("consulta realizada............");
				rta = rs.getInt("saldo");
			
			
			rs.close();
			stmt.close();
			
			return rta; 
			
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return rta;
				
	}
	
	public void ultimosMovimientos(String nroTarj) {
	
		String sql = "select fecha, hora, cod_caja, monto, tipo, destino from trans_cajas_ahorro where nro_tarjeta='"+nroTarj+"'";
		
		try {
			Statement stmt = conect.getConexion().createStatement();
		
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next())
				System.out.println("consulta realizada............");
			
		
		
			rs.close();
			stmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void movimientosPeriodo(int periodo ) {
		
	}
	
}
