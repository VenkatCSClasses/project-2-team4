package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:gutenberg.db";
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }
        return connection;
    }

    public static void setConnection(Connection conn) {
        connection = conn;
}

    public static void initialize() throws SQLException {
        String createUsers = """
            CREATE TABLE IF NOT EXISTS users (
                id            INTEGER PRIMARY KEY AUTOINCREMENT,
                username      TEXT UNIQUE NOT NULL,
                password_hash TEXT NOT NULL,
                role          TEXT NOT NULL DEFAULT 'USER'
            )
        """;
        String createUserBooks = """
            CREATE TABLE IF NOT EXISTS user_books (
                user_id       INTEGER REFERENCES users(id),
                gutenberg_id  INTEGER NOT NULL,
                title         TEXT,
                author        TEXT,
                status        TEXT DEFAULT 'NOT_STARTED',
                progress      INTEGER DEFAULT 0,
                date_added    TEXT,
                date_finished TEXT,
                PRIMARY KEY (user_id, gutenberg_id)
            )
        """;
        try (Statement stmt = getConnection().createStatement()) {
            stmt.execute(createUsers);
            stmt.execute(createUserBooks);
        }
    }
}