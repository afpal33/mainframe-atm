package bo.edu.ucb.sis213.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import bo.edu.ucb.sis213.*;
import bo.edu.ucb.sis213.gui.Menu;

public class OperacionesCajero {
    private Connection dbConnection;
    private static double pinActual;
    private static int usuarioId;
    private static double saldo;

    public OperacionesCajero(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public static boolean validarPIN(Connection connection, int pin) {
        String query = "SELECT id, saldo FROM usuarios WHERE pin = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, pin);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                usuarioId = resultSet.getInt("id");
                saldo = resultSet.getDouble("saldo");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static double consultarSaldo(Connection connection) {
    	double nuevosaldo = mostrarNuevoSaldo(connection);
		return nuevosaldo;
    }

    public static void realizarDepositoConMonto(double monto) {
        Connection connection = null;
        try {
            connection = App.getConnection();
            if (monto <= 0) {
            	JFrame frame = new JFrame("Error");
        	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	    frame.setSize(300, 150);
        	    frame.setLayout(null);
        	    frame.setLocationRelativeTo(null);
        	    
        	    JLabel label = new JLabel("<html><br>Monto inválido<br><br></html>");
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
            } else {
                saldo += monto;
                JFrame frame = new JFrame("Depósito Realizado");
        	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	    frame.setSize(300, 150);
        	    frame.setLayout(null);
        	    frame.setLocationRelativeTo(null);
        	    
        	    JLabel label = new JLabel("<html><br>El depósito se realizó con éxito. Su saldo es: "+saldo+"<br><br></html>");
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
                actualizarSaldoEnBaseDeDatos(connection, saldo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public static void realizarRetiro(double monto) throws SQLException {
    	Connection connection = App.getConnection();

        if (monto <= 0) {
        	JFrame frame = new JFrame("Error");
    	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	    frame.setSize(300, 150);
    	    frame.setLayout(null);
    	    frame.setLocationRelativeTo(null);
    	    
    	    JLabel label = new JLabel("<html><br>Monto inválido<br><br></html>");
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
        } else if (monto > saldo) {
        	JFrame frame = new JFrame("Error");
    	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	    frame.setSize(300, 150);
    	    frame.setLayout(null);
    	    frame.setLocationRelativeTo(null);
    	    
    	    JLabel label = new JLabel("<html><br>Saldo insuficiente. <br><br></html>");
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
        } else {
            saldo -= monto;
            JFrame frame = new JFrame("Retiro Realizado");
    	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	    frame.setSize(300, 150);
    	    frame.setLayout(null);
    	    frame.setLocationRelativeTo(null);
    	    
    	    JLabel label = new JLabel("<html><br>El retiro se realizó con éxito. Su saldo es: "+saldo+"<br><br></html>");
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
            actualizarSaldoEnBaseDeDatos(connection, saldo);

        }
    }

    public static void cambiarPIN(int pinA) throws SQLException {
        Connection connection = App.getConnection();
        actualizarPinEnBaseDeDatos(connection, pinA);
    }
    public static void actualizarSaldoEnBaseDeDatos(Connection connection, double nuevoSaldo) {
        String query = "UPDATE usuarios SET saldo = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, nuevoSaldo);
            preparedStatement.setInt(2, usuarioId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static double mostrarNuevoSaldo(Connection connection) {
        double nuevoSaldo = 0;
        String query = "SELECT saldo FROM usuarios WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, usuarioId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                nuevoSaldo = resultSet.getDouble("saldo");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return nuevoSaldo;
    }
    
    public static void actualizarPinEnBaseDeDatos(Connection connection, int nuevoPin) {
        String query = "UPDATE usuarios SET pin = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, nuevoPin);
            preparedStatement.setInt(2, usuarioId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String mostrarUsuario() throws SQLException {
        Connection connection = App.getConnection();
    	String usuario = null;
        String query = "SELECT nombre FROM usuarios WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, usuarioId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                usuario = resultSet.getString("nombre");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return usuario;
    }
    
    public static boolean handleEnteredPin(int pin) throws SQLException {
    	double sueldo = 0;
    	Connection connection=App.getConnection();;
          if (validarPIN(connection, pin)) {
              pinActual = pin;
              double sueldo2=consultarSaldo(connection);
              Menu menu = new Menu(sueldo2);
              return true;
          } else {
              return false;
          }
		
	}

}
