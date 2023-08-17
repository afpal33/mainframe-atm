package bo.edu.ucb.sis213;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class PinWindow extends JFrame {
    private JTextField pinTextField;
    private JButton submitButton;
    private int intentos =3;
    public PinWindow() {
        setTitle("Ingresar PIN");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        pinTextField = new JTextField(10);
        submitButton = new JButton("Aceptar");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int pin = Integer.parseInt(pinTextField.getText());
                try {
                    if (App.handleEnteredPin(pin)) {
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
        panel.add(new JLabel("Ingrese su PIN: "));
        panel.add(pinTextField);
        panel.add(submitButton);

        add(panel);
        setVisible(true);
    }
}
