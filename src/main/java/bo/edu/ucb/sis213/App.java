package bo.edu.ucb.sis213;

import java.util.Scanner;
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
        } catch (SQLException ex) {
            System.err.println("No se puede conectar a Base de Datos");
            JFrame frameErr = new JFrame("Error");
            frameErr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            JLabel labelErr = new JLabel("<html><br>No se pudo conectar con la base de datos.<br><br></html>");
            labelErr.setHorizontalAlignment(JLabel.CENTER);
            ex.printStackTrace();
            System.exit(1);
            
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PinWindow PinWindow = new PinWindow();
                PinWindow.setVisible(true);
            }
        });
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

    public static void consultarSaldo() {
        System.out.println("Su saldo actual es: $" + saldo);
    }

    public static void realizarDeposito(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la cantidad a depositar: $");
        double cantidad = scanner.nextDouble();

        if (cantidad <= 0) {
            System.out.println("Cantidad no válida.");
        } else {
            saldo += cantidad;
            System.out.println("Depósito realizado con éxito. Su nuevo saldo es: $" + saldo);
            actualizarSaldoEnBaseDeDatos(connection, saldo);

        }
    }

    public static void realizarRetiro(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la cantidad a retirar: $");
        double cantidad = scanner.nextDouble();

        if (cantidad <= 0) {
            System.out.println("Cantidad no válida.");
        } else if (cantidad > saldo) {
            System.out.println("Saldo insuficiente.");
        } else {
            saldo -= cantidad;
            System.out.println("Retiro realizado con éxito. Su nuevo saldo es: $" + saldo);
            actualizarSaldoEnBaseDeDatos(connection, saldo);

        }
    }

    public static void cambiarPIN(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese su PIN actual: ");
        int pinIngresado = scanner.nextInt();

        if (pinIngresado == pinActual) {
            System.out.print("Ingrese su nuevo PIN: ");
            int nuevoPin = scanner.nextInt();
            System.out.print("Confirme su nuevo PIN: ");
            int confirmacionPin = scanner.nextInt();

            if (nuevoPin == confirmacionPin) {
                pinActual = nuevoPin;
                System.out.println("PIN actualizado con éxito.");
                actualizarPinEnBaseDeDatos(connection, nuevoPin);
            } else {
                System.out.println("Los PINs no coinciden.");
            }
        } else {
            System.out.println("PIN incorrecto.");
        }
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
    public static boolean handleEnteredPin(int pin) throws SQLException {
    	Connection connection=getConnection();
          if (validarPIN(connection, pin)) {
              pinActual = pin;
              Menu menu = new Menu();
              return true;
          } else {
              return false; 
          }
		
	}
    
}