package bo.edu.ucb.sis213;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PopupSaldo {

	private JFrame frame3;
	private static double sueldo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PopupSaldo window = new PopupSaldo(sueldo);
					window.frame3.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PopupSaldo(double sueldo) {
		initialize(sueldo);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(double sueldo) {
		frame3 = new JFrame();
		frame3.setBounds(100, 100, 450, 300);
		frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame3.getContentPane().setLayout(null);
		
		JLabel lblSuSaldoEs = new JLabel("SU SALDO ES: $");
		lblSuSaldoEs.setBounds(113, 90, 212, 15);
		frame3.getContentPane().add(lblSuSaldoEs);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame3.dispose();
			}
		});
		btnOk.setBounds(192, 200, 72, 15);
		frame3.getContentPane().add(btnOk);
		JLabel lblNewLabel = new JLabel(Double.toString(sueldo));
		lblNewLabel.setBounds(233, 90, 70, 15);
		frame3.getContentPane().add(lblNewLabel);
		frame3.setLocationRelativeTo(null);
		
		frame3.setVisible(true);
	}
}
