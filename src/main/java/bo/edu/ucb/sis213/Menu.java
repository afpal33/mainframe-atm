package bo.edu.ucb.sis213;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu extends JFrame{

	private JFrame frame2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
					window.frame2.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Menu() {
        setTitle("MENU PRINCIPAL");
        setSize(450, 300);
        setLocationRelativeTo(null);
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame2 = new JFrame();
		frame2.setSize(450, 300);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.getContentPane().setLayout(null);
		
		JLabel lblMenuPrincipal = new JLabel("MENU PRINCIPAL");
		lblMenuPrincipal.setBounds(169, 12, 121, 15);
		frame2.getContentPane().add(lblMenuPrincipal);
		
		JButton btnSaldo = new JButton("Consultar Saldo");
		btnSaldo.setBounds(143, 39, 160, 25);
		frame2.getContentPane().add(btnSaldo);
		
		JButton btnRealizarDep = new JButton("Realizar depósito");
		btnRealizarDep.setBounds(143, 76, 160, 25);
		frame2.getContentPane().add(btnRealizarDep);
		
		JButton btnRetiro = new JButton("Realizar Retiro");
		btnRetiro.setBounds(143, 113, 160, 25);
		frame2.getContentPane().add(btnRetiro);
		
		JButton btnPin = new JButton("Cambiar Pin");
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
		
	    frame2.setLocationRelativeTo(null);

		frame2.setVisible(true);
	}

}
