package conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/magazin";
    private static final String USER = "root";
    private static final String PASSWORD = "marco23";

    /**
     * Constructor non-parametrizabil
     */
    public DataBaseConnection() {}

    /**
     * Metoda pentru obtinerea conexiunii la baza de date
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
