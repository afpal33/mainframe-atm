package bo.edu.ucb.sis213.gui;

import java.awt.EventQueue;
import bo.edu.ucb.sis213.core.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bo.edu.ucb.sis213.App;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class PopupPinChange extends JFrame {

	private JPanel contentPane;
	private JPasswordField SecPass;
	private JPasswordField FirstPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PopupPinChange frame = new PopupPinChange();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PopupPinChange() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 384, 212);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIngreseSuNuevo = new JLabel("Ingrese su nuevo PIN:");
		lblIngreseSuNuevo.setBounds(117, 12, 186, 19);
		contentPane.add(lblIngreseSuNuevo);
		
		JLabel lblRepitaSuNuevo = new JLabel("Repita su nuevo PIN:");
		lblRepitaSuNuevo.setBounds(117, 70, 186, 19);
		contentPane.add(lblRepitaSuNuevo);
		
		SecPass = new JPasswordField(4);
		SecPass.setHorizontalAlignment(SwingConstants.CENTER);
		SecPass.setBounds(132, 101, 114, 19);
		contentPane.add(SecPass);
		
		FirstPass = new JPasswordField(4);
		FirstPass.setHorizontalAlignment(SwingConstants.CENTER);
		FirstPass.setBounds(132, 43, 114, 19);
		contentPane.add(FirstPass);
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pinA = Integer.parseInt(FirstPass.getText());
				int pinB = Integer.parseInt(SecPass.getText());
				if (pinA != pinB) {
					JFrame framesito = new JFrame("Error");
	        	    framesito.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        	    framesito.setSize(300, 150);
	        	    framesito.setLayout(null);
	        	    framesito.setLocationRelativeTo(null);
	        	    
	        	    JLabel label = new JLabel("<html><br>Las contraseñas no coinciden<br><br></html>");
	        	    label.setHorizontalAlignment(JLabel.CENTER);
	        	    label.setBounds(0, 30, 300, 50);
	        	    framesito.add(label);
	        	    
	        	    JButton btnOk = new JButton("Ok");
	        	    btnOk.setBounds(100, 90, 100, 30);
	        	    btnOk.addActionListener(new ActionListener() {
	        	        public void actionPerformed(ActionEvent e) {
	        	            framesito.dispose();
	        	        }
	        	    });
	        	    framesito.add(btnOk);
	        	    
	        	    framesito.setVisible(true);
				} else {
					
					try {
						OperacionesCajero.cambiarPIN(pinA);
						dispose();
						JFrame frame = new JFrame("Operación exitosa");
		        	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        	    frame.setSize(300, 150);
		        	    frame.setLayout(null);
		        	    frame.setLocationRelativeTo(null);
		        	    
		        	    JLabel label = new JLabel("<html><br>Se cambió el pin de forma exitosa.<br><br></html>");
		        	    label.setHorizontalAlignment(JLabel.CENTER);
		        	    label.setBounds(0, 30, 300, 50);
		        	    frame.add(label);
		        	    
		        	    JButton btnOk = new JButton("Ok");
		        	    btnOk.setBounds(100, 90, 100, 30);
		        	    btnOk.addActionListener(new ActionListener() {
		        	        public void actionPerformed(ActionEvent e) {
		        	            frame.dispose();
		        	        }
		        	    });
		        	    frame.add(btnOk);
		        	    
		        	    frame.setVisible(true);
					} catch (SQLException e1) {
						JFrame frame = new JFrame("Error");
		        	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        	    frame.setSize(300, 150);
		        	    frame.setLayout(null);
		        	    frame.setLocationRelativeTo(null);
		        	    
		        	    JLabel label = new JLabel("<html><br>Hubo un error inesperado. Por favor, inténtelo de nuevo.<br><br></html>");
		        	    label.setHorizontalAlignment(JLabel.CENTER);
		        	    label.setBounds(0, 30, 300, 50);
		        	    frame.add(label);
		        	    
		        	    JButton btnOk = new JButton("Ok");
		        	    btnOk.setBounds(100, 90, 100, 30);
		        	    btnOk.addActionListener(new ActionListener() {
		        	        public void actionPerformed(ActionEvent e) {
		        	            frame.dispose();
		        	        }
		        	    });
		        	    frame.add(btnOk);
		        	    
		        	    frame.setVisible(true);
					}
				}
			}
		});
		btnOk.setBounds(59, 145, 117, 25);
		contentPane.add(btnOk);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(203, 145, 117, 25);
		contentPane.add(btnCancelar);
		
		
	}
}
