package bo.edu.ucb.sis213;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class Menu extends JFrame{

	private JFrame frame2;
	private static double sueldo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu(sueldo);
					window.frame2.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @param saldo2 
	 * @throws SQLException 
	 */
	public Menu(double sueldo) throws SQLException {
        setTitle("MENU PRINCIPAL");
        setSize(450, 300);
        setLocationRelativeTo(null);
		initialize(sueldo);
		
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 */
	private void initialize(double sueldo) throws SQLException {
		frame2 = new JFrame();
		frame2.setSize(450, 300);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.getContentPane().setLayout(null);
		
		JLabel lblMenuPrincipal = new JLabel("MENU PRINCIPAL");
		lblMenuPrincipal.setBounds(169, 12, 121, 15);
		frame2.getContentPane().add(lblMenuPrincipal);
		
		
		
		JButton btnSaldo = new JButton("Consultar Saldo");
		btnSaldo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PopupSaldo saldo2 = new PopupSaldo(sueldo);
			}
		});
		btnSaldo.setBounds(143, 39, 160, 25);
		frame2.getContentPane().add(btnSaldo);
		
		JButton btnRealizarDep = new JButton("Realizar depósito");
		btnRealizarDep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PopupDeposito depo = new PopupDeposito();
				depo.setLocationRelativeTo(null);
				depo.setVisible(true);
			}
		});
		btnRealizarDep.setBounds(143, 76, 160, 25);
		frame2.getContentPane().add(btnRealizarDep);
		
		JButton btnRetiro = new JButton("Realizar Retiro");
		btnRetiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PopupRetiro retiro = new PopupRetiro();
				retiro.setLocationRelativeTo(null);
				retiro.setVisible(true);
			}
		});
		btnRetiro.setBounds(143, 113, 160, 25);
		frame2.getContentPane().add(btnRetiro);
		
		JButton btnPin = new JButton("Cambiar Pin");
		btnPin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PopupPinChange pinChange = new PopupPinChange();
				pinChange.setLocationRelativeTo(null);
				pinChange.setVisible(true);
			}
		});
		btnPin.setBounds(143, 150, 160, 25);
		frame2.getContentPane().add(btnPin);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame2.dispose();
				System.exit(0);
			}
		});
		btnSalir.setBounds(143, 214, 160, 25);
		frame2.getContentPane().add(btnSalir);
		
		JLabel lblUsuario = new JLabel("usuario: ");
		lblUsuario.setBounds(221, 251, 70, 15);
		frame2.getContentPane().add(lblUsuario);
		
		String user = App.mostrarUsuario();
		JLabel lblNewLabel = new JLabel(user);
		lblNewLabel.setBounds(286, 251, 142, 15);
		frame2.getContentPane().add(lblNewLabel);
		
	    frame2.setLocationRelativeTo(null);

		frame2.setVisible(true);
	}

}
