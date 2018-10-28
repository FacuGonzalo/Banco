package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.SpringLayout;
import javax.swing.BoxLayout;
import java.awt.Component;
import net.miginfocom.swing.MigLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class Banco {

	
	private JFrame frame;
	
	
	private login log ;
	
	//private AdminFrame adm;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Banco window = new Banco();
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
	public Banco() {
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnEmpleado = new JButton("Empleado");
		btnEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEmpleado.setBounds(165, 76, 79, 23);
		frame.getContentPane().add(btnEmpleado);
		
		JPanel panel = new JPanel();
		panel.setBounds(267, 0, 167, 261);
		frame.getContentPane().add(panel);
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);
		
		JButton btnAdmin = new JButton("Administrador");
		sl_panel.putConstraint(SpringLayout.NORTH, btnAdmin, 68, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, btnAdmin, 39, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, btnAdmin, -145, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, btnAdmin, -29, SpringLayout.EAST, panel);
		panel.add(btnAdmin);
		
		JPanel atmPanel = new JPanel();
		atmPanel.setBounds(0, 0, 139, 261);
		frame.getContentPane().add(atmPanel);
		
		JButton btnAtm = new JButton("ATM");
		btnAtm.setAlignmentY(0.2f);
		GroupLayout gl_atmPanel = new GroupLayout(atmPanel);
		gl_atmPanel.setHorizontalGroup(
			gl_atmPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_atmPanel.createSequentialGroup()
					.addContainerGap(46, Short.MAX_VALUE)
					.addComponent(btnAtm)
					.addGap(40))
		);
		gl_atmPanel.setVerticalGroup(
			gl_atmPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_atmPanel.createSequentialGroup()
					.addGap(75)
					.addComponent(btnAtm)
					.addContainerGap(163, Short.MAX_VALUE))
		);
		atmPanel.setLayout(gl_atmPanel);
		btnAtm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				log = new login();
				log.setVisible(true);
				frame.setVisible(false);
				
			}
		});
		btnAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log = new login();
				log.setVisible(true);
				frame.setVisible(false);
				
			}
		});
	}
}
