package GUI;

import java.awt.EventQueue;

import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;

import banco.ATM;
import banco.ConexionDB;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class login {
	
	//comentario

	private JFrame frame;
	private JTextField usuario;
	private JTextField contraseña;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login window = new login();
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
	public login() {
		initialize();
	}
	
	private static final char[] CONSTS_HEX = { '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f' };
	
    public static String getMD5(String stringAEncriptar)
    {
        try
        {
           MessageDigest msgd = MessageDigest.getInstance("MD5");
           byte[] bytes = msgd.digest(stringAEncriptar.getBytes());
           StringBuilder strbCadenaMD5 = new StringBuilder(2 * bytes.length);
           for (int i = 0; i < bytes.length; i++)
           {
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
		
		 ATM cajero = new ATM ();
		 
		 System.out.println(cajero.ingresar("2",getMD5("2222")));
		
		 System.out.println(cajero.consultarSaldo("2"));
		 System.out.println(cajero.consultarSaldo("4"));
		 
		final ConexionDB conect = ConexionDB.getIntance(); 
		
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Carlito", Font.PLAIN, 43));
		frame.setBounds(100, 100, 220, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblLogin = new JLabel("login rancio");
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
		lblLogin.setBounds(10, 11, 184, 54);
		frame.getContentPane().add(lblLogin);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuario.setBounds(10, 76, 56, 14);
		frame.getContentPane().add(lblUsuario);
		
		usuario = new JTextField();
		usuario.setBounds(20, 89, 161, 20);
		frame.getContentPane().add(usuario);
		usuario.setColumns(10);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setHorizontalAlignment(SwingConstants.CENTER);
		lblContrasea.setBounds(10, 120, 78, 14);
		frame.getContentPane().add(lblContrasea);
		
		contraseña = new JTextField();
		contraseña.setBounds(20, 137, 161, 20);
		frame.getContentPane().add(contraseña);
		contraseña.setColumns(10);
		
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			@SuppressWarnings("null")
			public void actionPerformed(ActionEvent arg0) {
				try {
					System.out.println("aca llego");
					
					
					//Class.forName("com.mysql.jdbc.Driver");
					//Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login","maestro","pwadmin");
					//Statement stmt = con.createStatement();
					
					//String sql = "SELECT * from usuarios WHERE nombre='"+ usuario.getText()+"' AND password='"+contraseña.getText()+"'";
					String sql = "SELECT * from tarjeta WHERE nro_tarjeta='"+"2" +"' AND pin='"+getMD5("2222")+"'";
					//Statement stmt = conect.getConexion().createStatement();
					//ResultSet rs = stmt.executeQuery(sql);
					
					
					ResultSet rs = conect.consulta(sql);
					if (rs.next())
						JOptionPane.showMessageDialog(null, "CONECTO, AGUANTE LA FAFAFAFA");
					else
						JOptionPane.showMessageDialog(null, "ERRADO MAQUINOLA ");
						
					
					//rs.close();
			        //stmt.close();
						
				} 
				catch (Exception e) {
					System.out.print(e);
				}
				
			}
		});
		btnIngresar.setBounds(55, 186, 89, 23);
		frame.getContentPane().add(btnIngresar);
	}
}
