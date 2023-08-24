package bo.edu.ucb.sis213;

import java.util.Scanner;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import bo.edu.ucb.sis213.core.*;
import bo.edu.ucb.sis213.gui.*;

public class App extends JFrame{

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 3306;
    private static final String USER = "root";
    private static final String PASSWORD = "123456";
    private static final String DATABASE = "atm";
    
    public static Connection getConnection() throws SQLException {
        String jdbcUrl = String.format("jdbc:mysql://%s:%d/%s", HOST, PORT, DATABASE);
        try {
            // Asegúrate de tener el driver de MySQL agregado en tu proyecto
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL Driver not found.", e);
        }

        return DriverManager.getConnection(jdbcUrl, USER, PASSWORD);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int intentos = 3;

        JFrame frame = new JFrame("Conexión con Base de Datos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel label = new JLabel("<html><br>Conectando con base de datos...<br><br></html>");
        label.setHorizontalAlignment(JLabel.CENTER);
        
        frame.getContentPane().add(label);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        int tiempoCierre = 1000;
        Timer timer = new Timer(tiempoCierre, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        timer.setRepeats(false);
        timer.start();
        Connection connection = null;
        try {
            connection = getConnection();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    PinWindow PinWindow = new PinWindow();
                    PinWindow.setVisible(true);
                }
            });
        } catch (SQLException ex) {
        	   JFrame frameErr = new JFrame("Error");
        	    frameErr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	    frameErr.setSize(300, 150);
        	    frameErr.setLayout(null);
        	    frameErr.setLocationRelativeTo(null);
        	    
        	    JLabel labelErr = new JLabel("<html><br>No se pudo conectar con la base de datos.<br><br></html>");
        	    labelErr.setHorizontalAlignment(JLabel.CENTER);
        	    labelErr.setBounds(0, 30, 300, 50);
        	    frameErr.add(labelErr);
        	    
        	    JButton btnOk = new JButton("Ok");
        	    btnOk.setBounds(100, 90, 100, 30);
        	    btnOk.addActionListener(new ActionListener() {
        	        public void actionPerformed(ActionEvent e) {
        	            ex.printStackTrace();
        	            System.exit(1);
        	        }
        	    });
        	    frameErr.add(btnOk);
        	    
        	    frameErr.setVisible(true);
        }
    }
    
    
}