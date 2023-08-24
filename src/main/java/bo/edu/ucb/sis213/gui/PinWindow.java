package bo.edu.ucb.sis213.gui;

import javax.swing.*;
import bo.edu.ucb.sis213.core.*;

import bo.edu.ucb.sis213.App;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class PinWindow extends JFrame {
    private JTextField pinTextField;
    private JButton submitButton;
    private int intentos =3;
    public PinWindow() {
        setTitle("Ingresar PIN");
        setSize(300, 113);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        pinTextField = new JPasswordField(4);
        pinTextField.setHorizontalAlignment(SwingConstants.CENTER);
        pinTextField.setBounds(145, 8, 111, 19);
        submitButton = new JButton("Aceptar");
        submitButton.setBounds(91, 46, 89, 25);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int pin = Integer.parseInt(pinTextField.getText());
                try {
                    if (OperacionesCajero.handleEnteredPin(pin)) {
                    	dispose(); 
                    } else {
                        intentos--;
                        if (intentos > 0) {
                            JOptionPane.showMessageDialog(null, "PIN incorrecto. Le quedan " + intentos + " intentos.");
                        } else {
                            JOptionPane.showMessageDialog(null, "PIN incorrecto. Ha excedido el número de intentos.");
                            System.exit(0);
                        }
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(null);
        JLabel label = new JLabel("Ingrese su PIN: ");
        label.setBounds(16, 10, 111, 15);
        panel.add(label);
        panel.add(pinTextField);
        panel.add(submitButton);

        getContentPane().add(panel);
        setVisible(true);
    }
}
