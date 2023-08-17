package bo.edu.ucb.sis213;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;

public class PopupSaldo {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PopupSaldo window = new PopupSaldo();
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
	public PopupSaldo() {
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
		
		JLabel lblSuSaldoEs = new JLabel("SU SALDO ES: $");
		lblSuSaldoEs.setBounds(113, 90, 212, 15);
		frame.getContentPane().add(lblSuSaldoEs);
		
		JButton btnOk = new JButton("Ok");
		btnOk.setBounds(192, 200, 72, 15);
		frame.getContentPane().add(btnOk);
	}

}
