package banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection("jdbc:mysql://localhost:3306/coelhorapido", "root", "qct9aKejrXxh@");
    }

    public static void main(String[] args) throws SQLException {

        Connection c;
        try {
            c = ConnectionFactory.getConnection();
            System.out.println("" + c.getCatalog());
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro ao conectar no banco de dados");
        }
    }
}
