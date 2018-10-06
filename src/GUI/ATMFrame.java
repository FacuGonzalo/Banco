package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class ATMFrame {

	private JFrame frame;
	
	private JTable tabla;
	private JTable table;

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
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton btnConsultSaldo = new JButton("Consultar Saldo");
		btnConsultSaldo.setBounds(10, 11, 107, 23);
		panel.add(btnConsultSaldo);
		
		JButton btnUltMov = new JButton("Ultimos Movimientos");
		btnUltMov.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnUltMov.setBounds(10, 81, 129, 23);
		panel.add(btnUltMov);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(165, 11, 409, 339);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	
		try {  //modelo de la tabla donde se almacenaran las tuplas 
			@SuppressWarnings("serial")
			TableModel BarcosModel =   new DefaultTableModel (new String[][] {},new String[] {"Fecha", "Hora", "Tipo", "Monto", }) {                   
         	     // define la clase java asociada a cada columna de la tabla
         	     Class[] types = new Class[] {java.lang.String.class, 
         	    		 java.lang.Integer.class, java.lang.String.class };
         	    // define si una columna es editable
                  boolean[] canEdit = new boolean[] { false, false, false };
                   
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
            scrollPane.setViewportView(tabla); //setea la tabla dentro del JScrollPane srcTabla               
            tabla.setModel(BarcosModel); // setea el modelo de la tabla  
            tabla.setAutoCreateRowSorter(true); // activa el ordenamiento por columnas, para
                                                // que se ordene al hacer click en una columna


		}
		catch(Exception e) {
	         e.printStackTrace();
	      }
			

			
		
		
	}
}
