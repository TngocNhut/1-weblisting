package murach.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import murach.business.User;

public class UserDB {
    public static void insert(User user) throws SQLException {
        String url = System.getenv("EMAIL_DB_URL");
        String username = System.getenv("EMAIL_DB_USER");
        String password = System.getenv("EMAIL_DB_PASSWORD");
        if (url == null || username == null || password == null) {
            throw new SQLException("Database environment variables are not configured");
        }
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC driver is missing", e);
        }
        String sql = "INSERT INTO email_subscribers (email, first_name, last_name) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.executeUpdate();
        }
    }
}
