package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.CaretEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JLabel;
import banco.Fechas;

public class ATMFrame {

	private JFrame frame;
	private JTable tabla;

	
	protected Connection conexionBD = null;
	private JTextField FechaIni;
	private JTextField FechaFin;
	
	
	private Fechas ini = new Fechas ();  

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ATMFrame window = new ATMFrame();
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
	public ATMFrame() {
		initialize();
	}
	
	//comentario

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		frame.addComponentListener(new ComponentAdapter() {
            public void componentHidden(ComponentEvent evt) {
                thisComponentHidden(evt);
             }
             public void componentShown(ComponentEvent evt) {
                thisComponentShown(evt);
             }
          });
		
		
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton btnConsultSaldo = new JButton("Consultar Saldo");
		btnConsultSaldo.setHorizontalAlignment(SwingConstants.LEFT);
		btnConsultSaldo.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 9));
		btnConsultSaldo.setBounds(10, 11, 119, 29);
		panel.add(btnConsultSaldo);
		
		JButton btnUltMov = new JButton("Ultimos Movimientos");
		btnUltMov.setHorizontalAlignment(SwingConstants.LEFT);
		btnUltMov.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 9));
		btnUltMov.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ultMov(evt);
			}
		});
		
		btnUltMov.setBounds(10, 60, 133, 29);
		panel.add(btnUltMov);
		
		JScrollPane scrTabla = new JScrollPane();
		scrTabla.setBounds(153, 10, 521, 339);
		panel.add(scrTabla);
		
		tabla = new JTable();
		scrTabla.setViewportView(tabla);
		
		
		
	
		try {  //modelo de la tabla donde se almacenaran las tuplas 
			@SuppressWarnings("serial")
			TableModel BancoModel =   new DefaultTableModel (new String[][] {},new String[] {"Codigo de la Caja", "Fecha", "Hora", "Monto", "Destino", "Tipo" }) {                   
         	     // define la clase java asociada a cada columna de la tabla
         	     Class[] types = new Class[] {java.lang.String.class, java.lang.String.class,
         	    		 java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class };
         	    // define si una columna es editable
                  boolean[] canEdit = new boolean[] { false, false, false, false, false, false };
                   
                 // recupera la clase java de cada columna de la tabla
                  public Class getColumnClass(int columnIndex) 
                  {
                     return types[columnIndex];
                  }
                // determina si una celda es editable
                  public boolean isCellEditable(int rowIndex, int columnIndex) 
                  {
                     return canEdit[columnIndex];
                  }
               };
		 
               
            tabla = new JTable(); // Crea una tabla
            scrTabla.setViewportView(tabla); //setea la tabla dentro del JScrollPane srcTabla               
            tabla.setModel(BancoModel); // setea el modelo de la tabla  
            tabla.setAutoCreateRowSorter(true); // activa el ordenamiento por columnas, para
            
		}
		catch(Exception e) {
	         e.printStackTrace();
	    } 
            
        
        FechaIni = new JTextField();
        FechaIni.setBounds(10, 170, 108, 29);
        panel.add(FechaIni);
        FechaIni.setColumns(10);
        
        FechaFin = new JTextField();
        FechaFin.setColumns(10);
        FechaFin.setBounds(10, 225, 108, 29);
        panel.add(FechaFin);
        
        JLabel lblDdmmaaaa = new JLabel("DD/MM/AAAA");
        lblDdmmaaaa.setHorizontalAlignment(SwingConstants.CENTER);
        lblDdmmaaaa.setBounds(10, 200, 108, 14);
        panel.add(lblDdmmaaaa);
        
        JLabel label = new JLabel("DD/MM/AAAA");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(10, 255, 108, 14);
        panel.add(label);
        
        JButton btnNewButton = new JButton("Movimientos por fecha");
        btnNewButton.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 9));
        btnNewButton.setBounds(10, 131, 108, 28);
        btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					UltimosMovPorFecha(evt);
			}
		});
        
        
        panel.add(btnNewButton);
                                                
		
	}
	
	private void UltimosMovPorFecha(ActionEvent evt)  {
	      this.UltMovFecha();      
	}
	
	
	
	private void UltMovFecha() {
		
		String Finicio = this.FechaIni.getText().trim();
		String Ffin = this.FechaFin.getText().trim();
		java.util.Date ini = Fechas.convertirStringADate(Finicio); 
		java.util.Date fin = Fechas.convertirStringADate(Ffin);
		
		if (Fechas.validar(Finicio) && Fechas.validar(Ffin) && Fechas.inferior(Finicio, Ffin)) {
		
			String sql = "SELECT fecha, hora, tipo, monto, cod_caja, destino FROM "+
		   			" tarjeta as t1 INNER JOIN trans_cajas_ahorro as t2 ON t1.nro_ca = t2.nro_ca"+
		   			" WHERE nro_tarjeta='1' AND PIN=md5(1111) AND fecha BETWEEN '"+Fechas.convertirDateAStringDB(ini)+
		   			"' AND '"+Fechas.convertirDateAStringDB(fin)+"' ORDER BY fecha DESC, hora DESC";
			
			refrescarTabla(sql);
		}
		else
			JOptionPane.showMessageDialog(null, "Fecha erronia, revise las fechas de inicio y fin");
	}
	
	
	private void thisComponentShown(ComponentEvent evt) {
	      this.conectarBD();
	      
	   }
	   
	   private void thisComponentHidden(ComponentEvent evt)  {
	      this.desconectarBD();
	   }

	   
	   private void ultMov(ActionEvent evt)  {
		   	String sql = "SELECT fecha, hora, tipo, monto, cod_caja, destino FROM "+
		   			" tarjeta as t1 INNER JOIN trans_cajas_ahorro as t2 ON t1.nro_ca = t2.nro_ca"+
		   			" WHERE nro_tarjeta='1' AND PIN=md5(1111) ORDER BY fecha DESC, hora DESC" ;
		   
		   	this.refrescarTabla(sql);
		   	
	   }


	   private void conectarBD() {
	      if (this.conexionBD == null) { 
	         try{  
	        	// Se carga y registra el driver JDBC de MySQL  
	        	// no es necesario para versiones de jdbc posteriores a 4.0 
	            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	         }
	         catch (Exception ex) {  
	            System.out.println(ex.getMessage());
	         }
	     
	         try {  
	        	//se genera el string que define los datos de la conección 
	            String servidor = "localhost:3306";
	            String baseDatos = "banco";
	            String usuario = "atm";
	            String clave = "atm";
	            String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos +"?serverTimezone=UTC";
	            //se intenta establecer la conección
	            this.conexionBD = DriverManager.getConnection(uriConexion, usuario, clave);
	         }
	         catch (SQLException ex) {
	            JOptionPane.showMessageDialog(null,
	                                          "Se produjo un error al intentar conectarse a la base de datos.\n" + 
	                                           ex.getMessage(),
	                                          "Error",
	                                          JOptionPane.ERROR_MESSAGE);
	            System.out.println("SQLException: " + ex.getMessage());
	            System.out.println("SQLState: " + ex.getSQLState());
	            System.out.println("VendorError: " + ex.getErrorCode());
	         }
	      }
	   }

	   private void desconectarBD() {
	      if (this.conexionBD != null) {
	         try {
	            this.conexionBD.close();
	            this.conexionBD = null;
	         }
	         catch (SQLException ex) {
	            System.out.println("SQLException: " + ex.getMessage());
	            System.out.println("SQLState: " + ex.getSQLState());
	            System.out.println("VendorError: " + ex.getErrorCode());
	         }
	      }
	   }
	   
	   
	   private void refrescarTabla(String sql) {
	      try {
	         // se crea una sentencia o comando jdbc para realizar la consulta 
	    	 // a partir de la coneccion establecida (conexionBD)
	         Statement stmt = this.conexionBD.createStatement();

	         // se prepara el string SQL de la consulta

	         // se ejecuta la sentencia y se recibe un resultset
	         ResultSet rs = stmt.executeQuery(sql);
	         // se recorre el resulset y se actualiza la tabla en pantalla
	         ((DefaultTableModel) this.tabla.getModel()).setRowCount(0);
	         int i = 0;
	         while (rs.next()) {
	        	 // agrega una fila al modelo de la tabla
	            ((DefaultTableModel) this.tabla.getModel()).setRowCount(i + 1);
	            // se agregan a la tabla los datos correspondientes cada celda de la fila recuperada
	            this.tabla.setValueAt(rs.getString("cod_caja"), i, 0);
	            this.tabla.setValueAt(rs.getString("fecha"), i, 1); 
	            this.tabla.setValueAt(rs.getString("hora"), i, 2);
	            this.tabla.setValueAt(rs.getString("monto"), i, 3);
	            this.tabla.setValueAt(rs.getString("destino"), i, 4);
	            this.tabla.setValueAt(rs.getString("tipo"), i, 5);
	            i++;
	         }
	         // se cierran los recursos utilizados 
	         rs.close();
	         stmt.close();
	      }
	      catch (SQLException ex) {
	         // en caso de error, se muestra la causa en la consola
	         System.out.println("SQLException: " + ex.getMessage());
	         System.out.println("SQLState: " + ex.getSQLState());
	         System.out.println("VendorError: " + ex.getErrorCode());
	      }
	   }
}
