package banco;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import quick.dbtable.DBTable;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

//import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;


import quick.dbtable.*;
import javax.swing.JList;

public class AdminFrame {

	JFrame frame;
	private JTextField consultPane;
	
	
	private DBTable tabla;
	private JButton btnTirarConsulta;
	private JList tablaLista;
	
	protected Connection conexionBD = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminFrame window = new AdminFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
			
	

	/**
	 * Create the application.
	 */
	public AdminFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		//final ConexionDB conect = ConexionDB.getIntance(); 
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		frame.addComponentListener(new ComponentAdapter() {
            public void componentHidden(ComponentEvent evt) {
                thisComponentHidden(evt);
             }
             public void componentShown(ComponentEvent evt) {
                thisComponentShown(evt);
             }
          });
		
		JPanel pnlConsulta = new JPanel();
		pnlConsulta.setBounds(0, 0, 774, 212);
		frame.getContentPane().add(pnlConsulta);
		pnlConsulta.setLayout(null);
		
		consultPane = new JTextField();
		consultPane.setHorizontalAlignment(SwingConstants.CENTER);
		consultPane.setBounds(10, 68, 620, 138);
		pnlConsulta.add(consultPane);
		consultPane.setColumns(10);
		
		JLabel lblAdminPanel = new JLabel("ADMIN PANEL ");
		lblAdminPanel.setBounds(242, 11, 282, 46);
		pnlConsulta.add(lblAdminPanel);
		lblAdminPanel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 40));
		lblAdminPanel.setHorizontalAlignment(SwingConstants.CENTER);
		
		btnTirarConsulta = new JButton("CONSULTAR");
		btnTirarConsulta.setBounds(640, 68, 124, 33);
		pnlConsulta.add(btnTirarConsulta);
		btnTirarConsulta.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		tabla = new DBTable();
		tabla.setBounds(10, 223, 573, 427);
		frame.getContentPane().add(tabla);
		tabla.setEditable(false);
		
		JPanel panel = new JPanel();
		panel.setBounds(593, 223, 181, 427);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		tablaLista = new JList();
		tablaLista.setBounds(174, 420, -166, -415);
		panel.add(tablaLista);
		
		btnTirarConsulta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	btnTirarConsultaActionPerformed(evt);
             }
          });

	}
	
	private void btnTirarConsultaActionPerformed(ActionEvent evt)  {
	      this.refrescarTabla();      
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
		    
		    DefaultListModel model = new DefaultListModel(); //create a new list model

		    String sql = new String("select * from empleado");
		    
		    Statement statement = conexionBD.createStatement();
		    ResultSet resultSet = statement.executeQuery(sql); //run your query

		    while (resultSet.next()) //go through each row that your query returns
		    {
		        String itemCode = resultSet.getString("item_code"); //get the element in column "item_code"
		        model.addElement(itemCode); //add each item to the model
		    }
		    tablaLista.setModel(model);

		    resultSet.close();
		    statement.close();
		    
		    
		   
		 }
		 catch (SQLException ex) {
		     /*JOptionPane.showMessageDialog(this,
		                                   "Se produjo un error al intentar conectarse a la base de datos.\n" + ex.getMessage(),
		                                   "Error",
		                                   JOptionPane.ERROR_MESSAGE);*/
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
}
