package banco;

public class ATM {
	
	final ConexionDB conect;
	
	public  ATM (){
		conect = ConexionDB.getIntance();
	}
	
	public boolean ingresar (int nroTarjeta, int pin) {
		return true;
	}
	
	public void consultarSaldo() {
				
	}
	
	public void ultimosMovimientos() {
	
	}
	
	public void movimientosPeriodo(int periodo ) {
		
	}
	
}
