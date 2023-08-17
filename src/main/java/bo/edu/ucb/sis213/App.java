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

public class App extends JFrame{
    private static int usuarioId;
    private static double saldo;
    private static int pinActual;
    

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
            connection = getConnection();
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
    	Connection connection = getConnection();

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
        Connection connection = getConnection();
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
        Connection connection = getConnection();
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
    	Connection connection=getConnection();
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