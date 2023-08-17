package bo.edu.ucb.sis213;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
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
		
		JButton btnOk = new JButton("Ok");
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
		
		SecPass = new JPasswordField(4);
		SecPass.setHorizontalAlignment(SwingConstants.CENTER);
		SecPass.setBounds(132, 101, 114, 19);
		contentPane.add(SecPass);
		
		FirstPass = new JPasswordField(4);
		FirstPass.setHorizontalAlignment(SwingConstants.CENTER);
		FirstPass.setBounds(132, 43, 114, 19);
		contentPane.add(FirstPass);
	}
}
