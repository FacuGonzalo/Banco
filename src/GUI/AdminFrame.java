package GUI;


import javax.swing.JFrame;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import quick.dbtable.DBTable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;


public class AdminFrame  extends JFrame {
	
	//JFrame frame;	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel pnlConsulta;
	
	private JScrollPane panelTablas;
	
	private JTextField consultPane;
	
	private DBTable tabla ;
	
	private JButton btnTirarConsulta;
	
	private JList<String> tablaLista;
	
	private JList<String> atributosLista;
	
	private DefaultListModel<String> model;
	
	private JLabel lblAdminPanel;

	protected Connection conexion;
	private JScrollPane panelAtributos;
	
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminFrame window = new AdminFrame();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/
	
	
	
	
	public AdminFrame(Connection conexionBD) {
		/*
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Carlito", Font.PLAIN, 43));
		frame.setBounds(100, 100, 800, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);*/
		
		
		super();
		conexion = conexionBD;
		this.setBounds(100, 100, 945, 700);
		this.getContentPane().setFont(new Font("Carlito", Font.PLAIN, 43));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setVisible(true);
	
		initialize();
	}


	private void initialize() {
		
		//final ConexionDB conect = ConexionDB.getIntance(); 
		tabla = new DBTable();
		tabla.setBounds(10, 198, 573, 452);
		tabla.setEditable(false);
		
		tablaLista = new JList<String>();
		model = new DefaultListModel<String>(); //create a new list model
	    tablaLista.setModel(model);
	    
	    atributosLista = new JList<String>();
		
	    
	    conectarBD();
				
		pnlConsulta = new JPanel();
		pnlConsulta.setBounds(0, 0, 925, 187);
		pnlConsulta.setLayout(null);
		
		consultPane = new JTextField();
		consultPane.setHorizontalAlignment(SwingConstants.CENTER);
		consultPane.setBounds(10, 68, 765, 112);
		consultPane.setColumns(10);
		
		lblAdminPanel = new JLabel("ADMIN PANEL ");
		lblAdminPanel.setBounds(242, 11, 282, 46);
		lblAdminPanel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 40));
		lblAdminPanel.setHorizontalAlignment(SwingConstants.CENTER);
			
		btnTirarConsulta = new JButton("CONSULTAR");
		btnTirarConsulta.setBounds(785, 68, 124, 33);
		btnTirarConsulta.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnTirarConsulta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	btnTirarConsultaActionPerformed(evt);
             }
          });
		
		
		panelTablas = new JScrollPane();
		panelTablas.setBounds(595, 200, 160, 450);
		panelTablas.setViewportView(tablaLista);
		
		panelAtributos = new JScrollPane();
		panelAtributos.setBounds(765, 200, 160, 450);
		panelAtributos.setViewportView(atributosLista);
		
		pnlConsulta.add(consultPane);
		pnlConsulta.add(btnTirarConsulta);
		pnlConsulta.add(lblAdminPanel);
		
		
		
		this.getContentPane().add(tabla);
		this.getContentPane().add(panelTablas);
		this.getContentPane().add(pnlConsulta);
		this.getContentPane().add(panelAtributos);
		
		
		
		
		
		try {
			
			this.addComponentListener(new ComponentAdapter() {
	            public void componentHidden(ComponentEvent evt) {
	                thisComponentHidden(evt);
	             }
	             public void componentShown(ComponentEvent evt) {
	                thisComponentShown(evt);
	             }
	          });
			
		} 
		catch (Exception e) {
			System.out.println("Error 404");
			e.printStackTrace();
		}

	}
	
	   
	private void thisComponentHidden(ComponentEvent evt) {
		this.desconectarBD();
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
	
	
	private void conectarBD() {

		//if (this.conexion == null) {
		try {
			String driver ="com.mysql.cj.jdbc.Driver";
			String servidor = "localhost:3306";
		    String baseDatos = "banco";
		    String usuario = "admin";
		    String clave = "admin";
		    String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos+"?serverTimezone=UTC";
		   
		    this.conexion = DriverManager.getConnection(uriConexion, usuario, clave);  
		    
		    tabla.connectDatabase(driver, uriConexion, usuario, clave);
		    
		    mostarTablas();
		    
		   
		 }
		 catch (SQLException ex) {
			 System.out.println("EREROOOOOOOOR404");
		    
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		 }
		 catch (ClassNotFoundException e) {
		    e.printStackTrace();
		 }
		
		//}
	      
	}
	
	private void mostarTablas(){
		
		String sql = new String("SHOW TABLES;");
		
		try {
			model = new DefaultListModel<String>(); //create a new list model
			tablaLista.setModel(model);
			    
			
			    
			Statement statement = conexion.createStatement();
			ResultSet resultSet = statement.executeQuery(sql); //run your query
			    
			while (resultSet.next()) //go through each row that your query returns 
			{
			String nmbTabla = resultSet.getString("Tables_in_banco"); //get the element in column "item_code"
			model.addElement(nmbTabla); //add each item to the model
			}
			
			tablaLista.addMouseListener( new MouseAdapter() {
			    public void mouseClicked(MouseEvent e) {
					String nombre = tablaLista.getSelectedValue();
					if (!nombre.equals(" "))
						agregarAtributos(nombre);
					else{
						Vector<String> atributos = new Vector<String>();
						atributosLista.setListData(atributos);

					}}
			});
			    
			
			
			resultSet.close();
			statement.close();
		}
		catch (Exception e) {
			System.out.println(e);
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
					"Se produjo un error al obtener el listado de tablas: \n" +e.getMessage(), 
					"Error al ejecutar el comando.",
					JOptionPane.ERROR_MESSAGE);

		}
	}
	
	private void agregarAtributos(String nombre){
		String sql = "DESCRIBE "+nombre+";";

		try{
			java.sql.Statement stmt = conexion.createStatement();		

			stmt.executeQuery(sql);
			java.sql.ResultSet rs = stmt.getResultSet();
			
			// Por cada atributo recibido, lo agrego a la lista de atributos
			Vector<String> atributos = new Vector<String>();
			while(rs.next()) {
				atributos.addElement(rs.getString(1));
			}	
			atributosLista.setListData(atributos);

			// Se liberan los recursos
			rs.close();

		}catch(SQLException ex)
		{
			//En caso de error se muestra un mensaje al usuario
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
					"Se produjo un error: \n" +ex.getMessage(), 
					"Error al ejecutar el comando.",
					JOptionPane.ERROR_MESSAGE);
		} 
	}
	
	
	
	
	private void btnTirarConsultaActionPerformed(ActionEvent evt)  {
	      this.refrescarTabla();      
	}
	
		
	public void refrescarTabla() {
		try {   
			String sql = this.consultPane.getText().trim();
			java.sql.Statement stmt = conexion.createStatement();
			
			if (stmt.execute(sql)){
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
			else {
				 mostarTablas();
				 
			}
			
		}
		catch (SQLException ex) {
			// en caso de error, se muestra la causa en la consola
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			
		}
		
	}
}
