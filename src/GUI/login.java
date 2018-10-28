package GUI;

import java.awt.EventQueue;

import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;


import banco.ConexionDB;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.JPasswordField;
import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class login extends JFrame {
	

	//private JFrame frame;
	private JPasswordField password;	
	private JTextField id;
		
	private JLabel lblLogin; 
	private JLabel lblUsuario;
	private JLabel lblContrasenia;

	private JButton btnIngresar;
		
	@SuppressWarnings("unused")
	private AdminFrame adm;
	private ATMFrame atm;
	
	//private final Banco Bank;
	
	final ConexionDB conect = ConexionDB.getIntance(); 

	public login() {
		
        this.setSize(220, 300);
        this.setLocation(350, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
        this.setVisible(true);
	
        //initialize();
        initializeATM();
	}
	
	
	private static final char[] CONSTS_HEX = { '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f' };
	
    public static String getMD5(String stringAEncriptar) {
        try {
           MessageDigest msgd = MessageDigest.getInstance("MD5");
           byte[] bytes = msgd.digest(stringAEncriptar.getBytes());
           StringBuilder strbCadenaMD5 = new StringBuilder(2 * bytes.length);
           for (int i = 0; i < bytes.length; i++) {
               int bajo = (int)(bytes[i] & 0x0f);
               int alto = (int)((bytes[i] & 0xf0) >> 4);
               strbCadenaMD5.append(CONSTS_HEX[alto]);
               strbCadenaMD5.append(CONSTS_HEX[bajo]);
           }
           return strbCadenaMD5.toString();
        } catch (NoSuchAlgorithmException e) {
           return null;
        }
    }
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		 						
		lblLogin = new JLabel();
		lblUsuario = new JLabel();
		lblContrasenia = new JLabel();
		
		password = new JPasswordField();
		id = new JTextField();
		
		btnIngresar = new JButton("Ingresar");
	
		
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setFont(new Font("Yu Gothic Medium", Font.BOLD, 24));
		lblLogin.setBounds(10, 11, 174, 54);
		this.getContentPane().add(lblLogin);		
		
		lblUsuario.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsuario.setBounds(20, 65, 117, 14);
		this.getContentPane().add(lblUsuario);		
		
		lblContrasenia.setHorizontalAlignment(SwingConstants.LEFT);
		lblContrasenia.setBounds(20, 118, 117, 14);
		this.getContentPane().add(lblContrasenia);
		
		password.setBounds(20, 143, 161, 20);
		this.getContentPane().add(password);
		
		id.setBounds(20, 87, 161, 20);
		this.getContentPane().add(id);
		id.setColumns(10);
		
		btnIngresar.setBounds(20, 187, 161, 23);
		this.getContentPane().add(btnIngresar);
		
		btnIngresar.addActionListener(new ActionListener() {
			@SuppressWarnings("null")
			public void actionPerformed(ActionEvent arg0) {
				try {
				
					String passString = new String(password.getPassword());
					
						if (conect.ingresarATM(id.getText(),passString)) {
							accederAdmin();
						}
				}
				catch (SQLException ex) {
			
		            System.out.println("SQLException: " + ex.getMessage());
		            System.out.println("SQLState: " + ex.getSQLState());
		            System.out.println("VendorError: " + ex.getErrorCode());
		         }
			}
		});
		
		
	}
	
	private void initializeATM() {
			
		lblLogin = new JLabel("ATM");
		lblUsuario = new JLabel("Nro De Tarjeta");
		lblContrasenia = new JLabel("PIN de la Tarjeta");
		
		password = new JPasswordField();
		id = new JTextField();
		
		btnIngresar = new JButton("Ingresar");
	
		
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setFont(new Font("Yu Gothic Medium", Font.BOLD, 24));
		lblLogin.setBounds(10, 11, 174, 54);
		this.getContentPane().add(lblLogin);		
		
		lblUsuario.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsuario.setBounds(20, 65, 117, 14);
		this.getContentPane().add(lblUsuario);		
		
		lblContrasenia.setHorizontalAlignment(SwingConstants.LEFT);
		lblContrasenia.setBounds(20, 118, 117, 14);
		this.getContentPane().add(lblContrasenia);
		
		password.setBounds(20, 143, 161, 20);
		this.getContentPane().add(password);
		
		id.setBounds(20, 87, 161, 20);
		this.getContentPane().add(id);
		id.setColumns(10);
		
		btnIngresar.setBounds(20, 187, 161, 23);
		this.getContentPane().add(btnIngresar);
		
		btnIngresar.addActionListener(new ActionListener() {
			@SuppressWarnings("null")
			public void actionPerformed(ActionEvent arg0) {
				try {
				
					String passString = new String(password.getPassword());
					
						if (conect.ingresarATM(id.getText(),getMD5(passString))) {
							accederATM();
						}
				}
				catch (SQLException ex) {
			
		            System.out.println("SQLException: " + ex.getMessage());
		            System.out.println("SQLState: " + ex.getSQLState());
		            System.out.println("VendorError: " + ex.getErrorCode());
		         }
			}
		});
		
		
	}
	
	public void accederAdmin () {
		this.setVisible(false);
		 adm = new AdminFrame(conect.getConexion());
		 //adm.setVisible(true);
		
	}
	public void accederATM () {
		this.setVisible(false);
		 atm = new ATMFrame(conect.getConexion());
		 //adm.setVisible(true);
		
	}
	

}
