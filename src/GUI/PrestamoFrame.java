package GUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

import banco.ConexionDB;
import banco.Fechas;

public class PrestamoFrame extends JFrame {

	protected Connection conexionBD = null;
	
	private JPanel panel;
	private JPanel panel_consulta;
	private JButton btn_crear;
	private JButton btn_pago;
	private JButton btn_listar;
	private JComboBox<String> comboBoxDNI;
	private JPanel panel_creacion;
	private JPanel panel_pago;
	private JPanel panel_cliente;
	private JTable tabla;
	private String fecha_hoy;
	private Date hoy;
	final ConexionDB conect;
	
	private Connection conexionDataBase = null;
	private JScrollPane scrollPane;
	
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
			panel_cliente.setBounds(362, 0, 291, 349);
			panel.add(panel_cliente);
			panel_cliente.setVisible(false);
			panel_creacion = new JPanel();
			panel_creacion.setBounds(0, 0, 352, 281);
			panel.add(panel_creacion);
			panel_creacion.setVisible(false);
			panel_pago = new JPanel();
			panel_pago.setBounds(0, 0, 287, 281);
			panel.add(panel_pago);
			panel_pago.setVisible(false);
			
			comboBoxDNI = new JComboBox<String>();
			comboBoxDNI.setModel(new DefaultComboBoxModel<String>(new String[] {"DNI", "LC", "LE"}));
			panel_pago.add(comboBoxDNI);
			
			panel_pago.setBackground(Color.red);
			panel_creacion.setBackground(Color.green);
			panel_cliente.setBackground(Color.BLUE);
			panel_cliente.setLayout(null);
			
			scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 11, 271, 287);
			panel_cliente.add(scrollPane);
			
			panel_consulta = new JPanel();
			panel_consulta.setBounds(96, 368, 434, 79);
			
			btn_crear = new JButton("Crear prestamo");
			btn_crear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					panel_creacion.setVisible(true);
					panel_pago.setVisible(false);
					panel_cliente.setVisible(false);
					oyente_creacion();
				}
			});
			
			btn_crear.setBounds(10, 29, 130, 25);
			panel_consulta.add(btn_crear);
			
			btn_pago = new JButton("Pago Cuotas");
			btn_pago.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					panel_pago.setVisible(true);
					panel_creacion.setVisible(false);
					panel_cliente.setVisible(false);
					oyente_pago();
				}
			});
			btn_pago.setBounds(174, 29, 107, 23);
			panel_consulta.add(btn_pago);
			
			btn_listar = new JButton("Clientes Morosos");
			btn_listar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					panel_cliente.setVisible(true);
					panel_pago.setVisible(false);
					panel_creacion.setVisible(false);
					oyente_morosos();
				}
			});
			btn_listar.setBounds(317, 29, 107, 23);
			panel_consulta.add(btn_listar);
			
			panel_consulta.setLayout(null);
			panel.add(panel_consulta);
			
			
	}
	
	private void oyente_creacion(){
		
	}
	
	private void oyente_pago(){
		
	}
	
	private void oyente_morosos(){
		
		hoy = new Date(1);
		fecha_hoy = Fechas.convertirDateAStringDB(hoy);
		
		try{
		String sql = "SELECT pr.nro_cliente, c.tipo_doc, c.nro_doc, c.nombre, c.apellido, pa.nro_prestamo, pr.monto, pr.cant_meses, pr.valor_cuota, COUNT(pr.nro_prestamo) " +
	         		 "FROM  prestamo as pr, pago as pa, cliente as c WHERE pr.nro_cliente = c.nro_cliente and pa.nro_prestamo = pr.nro_prestamo and fecha_pago = NULL and fecha_venc < '"+fecha_hoy+"' " +
	         		 "GROUP BY nro_prestamo;";
		Statement stmt = conect.getConexion().createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		if (rs.next())
			System.out.println("Encontro");
		else
			System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main (String args[]){ PrestamoFrame p = new PrestamoFrame(); p.setVisible(true);}
}
	
