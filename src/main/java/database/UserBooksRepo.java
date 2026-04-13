package database;



import java.sql.*;
import java.util.*;

public class UserBooksRepo {

    public void addBook(int userId, int gutenbergId, 
                        String title, String author) throws SQLException {
        String sql = """
            INSERT OR IGNORE INTO user_books 
            (user_id, gutenberg_id, title, author, status, date_added)
            VALUES (?, ?, ?, ?, 'NOT_STARTED', datetime('now'))
        """;
        try (PreparedStatement ps = 
                DatabaseManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, gutenbergId);
            ps.setString(3, title);
            ps.setString(4, author);
            ps.executeUpdate();
        }
    }

    public void removeBook(int userId, int gutenbergId) throws SQLException {
        String sql = "DELETE FROM user_books WHERE user_id = ? AND gutenberg_id = ?";
        try (PreparedStatement ps = 
                DatabaseManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, gutenbergId);
            ps.executeUpdate();
        }
    }

    public List<Map<String, String>> getBooksForUser(int userId) throws SQLException {
        String sql = "SELECT * FROM user_books WHERE user_id = ?";
        List<Map<String, String>> books = new ArrayList<>();
        try (PreparedStatement ps = 
                DatabaseManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, String> book = new HashMap<>();
                book.put("gutenberg_id", String.valueOf(rs.getInt("gutenberg_id")));
                book.put("title", rs.getString("title"));
                book.put("author", rs.getString("author"));
                book.put("status", rs.getString("status"));
                books.add(book);
            }
        }
        return books;
    }

    public void updateStatus(int userId, int gutenbergId, 
                             String status) throws SQLException {
        String sql = """
            UPDATE user_books SET status = ? 
            WHERE user_id = ? AND gutenberg_id = ?
        """;
        try (PreparedStatement ps = 
                DatabaseManager.getConnection().prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, userId);
            ps.setInt(3, gutenbergId);
            ps.executeUpdate();
        }
    }
}