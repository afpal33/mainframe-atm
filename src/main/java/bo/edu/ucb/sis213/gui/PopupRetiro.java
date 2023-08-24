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
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class PopupRetiro extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PopupRetiro frame5 = new PopupRetiro();
					frame5.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PopupRetiro() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 348, 164);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Ingrese monto a retirar");
		lblNewLabel.setBounds(85, 12, 253, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("$");
		lblNewLabel_1.setBounds(106, 67, 27, 15);
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(116, 65, 114, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String montoString = textField.getText();
				try {
		            double monto = Double.parseDouble(montoString);
		            OperacionesCajero.realizarRetiro(monto);
		            dispose();
		        } catch (NumberFormatException | SQLException ex) {
		            System.out.println("Monto no válido. Ingrese un número válido.");
		        }
			}
		});
		btnOk.setBounds(49, 97, 117, 25);
		contentPane.add(btnOk);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(178, 97, 117, 25);
		contentPane.add(btnCancelar);
	}
}
