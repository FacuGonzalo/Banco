package GUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.sql.*;
import javax.swing.*;
import quick.dbtable.DBTable;
import banco.ConexionDB;
import banco.Fechas;

public class PrestamoFrame extends JFrame {

	protected Connection conexionBD = null;
	
	private JPanel panel;
	private JPanel panel_consulta;
	private JButton btn_crear;
	private JButton btn_pago;
	private JButton btn_listar;
	private JPanel panel_cliente;
	private DBTable tabla;
	private String fecha_hoy;
	private Date hoy;
	final ConexionDB conect;
	JList<String> tablaLista = new JList<String>();
	
	private Connection conexionDataBase = null;
	private JScrollPane scrollPane;
	private JTextField textDoc;
	private JButton btn_pagar;
	private JTextField txtIngreseNroCuota;
	
	public PrestamoFrame(){
		super();
		setTitle("Prestamos");
		conect = ConexionDB.getIntance();
		initGUI();
	}
	
	private void initGUI() {
			setBounds(200, 200, 679, 497);
			panel = new JPanel();
			setContentPane(panel);
			panel.setLayout(null);
			
			panel_cliente = new JPanel();
			panel_cliente.setBounds(10, 0, 643, 379);
			panel.add(panel_cliente);
			panel_cliente.setVisible(false);
			panel_cliente.setBackground(Color.LIGHT_GRAY);
			panel_cliente.setLayout(null);
			
			scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 11, 623, 287);
			panel_cliente.add(scrollPane);
			
			textDoc = new JTextField();
			textDoc.setBounds(308, 309, 208, 29);
			panel_cliente.add(textDoc);
			textDoc.setColumns(10);
			
			btn_pago = new JButton("Buscar Cuotas");
			btn_pago.setBounds(526, 312, 107, 23);
			panel_cliente.add(btn_pago);
			
			JComboBox comboBox = new JComboBox();
			comboBox.setModel(new DefaultComboBoxModel(new String[] {"DNI", "LE", "LC"}));
			comboBox.setBounds(250, 309, 49, 29);
			panel_cliente.add(comboBox);
		
			btn_pago.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					oyente_pago();
				}
			});
			
			btn_pagar = new JButton("Pagar cuota");
			btn_pagar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					oyente_pagar_cuota();
				}
			});
			btn_pagar.setBounds(10, 309, 107, 23);
			panel_cliente.add(btn_pagar);
			
			txtIngreseNroCuota = new JTextField();
			txtIngreseNroCuota.setText("Nro cuota");
			txtIngreseNroCuota.setBounds(20, 343, 86, 20);
			panel_cliente.add(txtIngreseNroCuota);
			txtIngreseNroCuota.setColumns(10);
			
			panel_consulta = new JPanel();
			panel_consulta.setBounds(96, 390, 434, 57);
			
			btn_listar = new JButton("Clientes Morosos");
			btn_listar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					oyente_morosos();
				}
			});
			
			btn_listar.setBounds(240, 29, 138, 23);
			panel_consulta.add(btn_listar);
			
			panel_consulta.setLayout(null);
			panel.add(panel_consulta);
			
			btn_crear = new JButton("Crear prestamo");
			btn_crear.setBounds(61, 28, 138, 25);
			panel_consulta.add(btn_crear);
			btn_crear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					oyente_creacion();
				}
			});
			
			
	}
	
	private void oyente_creacion(){
		crearPrestamo();
	}
	
	private void oyente_pago(){
		pagoCuotas(textDoc.getText());
	}
	
	private void oyente_morosos(){
		listaMorosos();
	}
	
	private void oyente_pagar_cuota(){
		pagar_cuota(txtIngreseNroCuota.getText());
	}
	
	public void pagar_cuota(String c){
		
		
	}
	
	public void crearPrestamo(){
			
		
	}
	
	public void pagoCuotas(String doc){
		
		try{
		String sql = "SELECT monto, fecha_venc, nro_pago FROM prestamo natural join cliente natural join pago WHERE nro_doc = '"+doc+"' and fecha_pago IS NULL";
		Statement stmt = conect.getConexion().createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next())
			System.out.println("Usted adeuda cuotas");
		else
			System.out.println("No tiene cuotas adeudadas");
			
		
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void listaMorosos(){
		
		int cant = 0;
		hoy = new Date(1);
		fecha_hoy = Fechas.convertirDateAStringDB(hoy);
		
		try{
		String sql = "SELECT nro_cliente, tipo_doc, nro_doc, nombre, apellido, nro_prestamo, monto, cant_meses, valor_cuota, COUNT(nro_prestamo) " +
	         		 "FROM  prestamo natural join pago natural join cliente WHERE fecha_pago IS NULL and fecha_venc < '"+fecha_hoy+"' " +
	         		 "GROUP BY nro_prestamo;";
		Statement stmt = conect.getConexion().createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		if (rs.next())
			System.out.println("Encontro");
		else
			System.out.println("Sin resultados");
		
		while(rs.next()){
			
			cant = rs.getInt("count(nro_prestamo)");
			if (cant >= 2){ /*Agrego a la tabla*/ }
			
		}
		
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
		
	private void conectarBD() {
		
		if (this.conexionBD == null) {
	      
		
		try {
			String driver ="com.mysql.cj.jdbc.Driver";
			String servidor = "localhost:3306";
		    String baseDatos = "banco";
		    String usuario = "admin";
		    String clave = "admin";
		    String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos+"?serverTimezone=UTC";
		   
		            //establece una conexión con la  B.D. "batallas"  usando directamante una tabla DBTable    
		    tabla.connectDatabase(driver, uriConexion, usuario, clave);
		    
		    this.conexionBD = DriverManager.getConnection(uriConexion, usuario, clave);
		    
		    DefaultListModel<String> model = new DefaultListModel<String>(); //create a new list model
		    tablaLista.setModel(model);
		    
		    String sql = new String("SHOW TABLES;");
		    
		    Statement statement = conexionBD.createStatement();
		    ResultSet resultSet = statement.executeQuery(sql); //run your query
		    
		    while (resultSet.next()) //go through each row that your query returns
		    {
		        String nmbTabla = resultSet.getString("Tables_in_banco"); //get the element in column "item_code"
		        model.addElement(nmbTabla); //add each item to the model
		    }
		    

		    resultSet.close();
		    statement.close();
		    
		    
		   
		 }
		 catch (SQLException ex) {
		     JOptionPane.showMessageDialog(btn_pago, "Se produjo un error al intentar conectarse a la base de datos.\n" + ex.getMessage(),
		                                   "Error",
		                                   JOptionPane.ERROR_MESSAGE);
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		 }
		 catch (ClassNotFoundException e) {
		    e.printStackTrace();
		 }
		
		}
	      
	}
	
	private void desconectarBD() {
         try {
            tabla.close();            
         }
         catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
         }      
    }
	
	

	private void thisComponentShown(ComponentEvent evt) {
		this.conectarBD();
	}
	   
	private void thisComponentHidden(ComponentEvent evt) {
		this.desconectarBD();
	}
		
	public void refrescarTabla() {
		try {    		
				// seteamos la consulta a partir de la cual se obtendrán los datos para llenar la tabla
				tabla.setSelectSql(this.consultPane.getText().trim());

		    	// obtenemos el modelo de la tabla a partir de la consulta para 
		    	// modificar la forma en que se muestran de algunas columnas  
		    	tabla.createColumnModelFromQuery();    	    
		    	for (int i = 0; i < tabla.getColumnCount(); i++)  { // para que muestre correctamente los valores de tipo TIME (hora)  		   		  
		    		if (tabla.getColumn(i).getType()==Types.TIME)     		 
		    			tabla.getColumn(i).setType(Types.CHAR);  
		  	       	 
		    		// cambiar el formato en que se muestran los valores de tipo DATE
		    		if (tabla.getColumn(i).getType()==Types.DATE)
		    			tabla.getColumn(i).setDateFormat("dd/MM/YYYY");	
		    	}  
		    	// actualizamos el contenido de la tabla.   	     	  
		    	tabla.refresh();
		    	// No es necesario establecer  una conexión, crear una sentencia y recuperar el 
		    	// resultado en un resultSet, esto lo hace automáticamente la tabla (DBTable) a 
		    	// patir de la conexión y la consulta seteadas con connectDatabase() y setSelectSql() respectivamente.          
		}
		catch (SQLException ex) {
			// en caso de error, se muestra la causa en la consola
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			
		}
		
	}
	
	public static void main (String args[]){ PrestamoFrame p = new PrestamoFrame(); p.setVisible(true);}
}
	
