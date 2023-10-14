package application.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDatabase {
    private Connection connection;

    public CommentDatabase(String dbFilePath) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath);
            createCommentsTableIfNotExists();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void createCommentsTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS comments (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "text TEXT NOT NULL, " +
                "timeStamp TEXT NOT NULL)";
        try (PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertComment(Comment comment) {
        String insertSQL = "INSERT INTO comments (text, timeStamp) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
            statement.setString(1, comment.getText());
            statement.setString(2, comment.getTimeStamp());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<>();
        String selectSQL = "SELECT * FROM comments";
        try (PreparedStatement statement = connection.prepareStatement(selectSQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String text = resultSet.getString("text");
                String timeStamp = resultSet.getString("timeStamp");
                Comment comment = new Comment(id, text, timeStamp);
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    public void updateComment(Comment comment) {
        String updateSQL = "UPDATE comments SET text = ?, timeStamp = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateSQL)) {
            statement.setString(1, comment.getText());
            statement.setString(2, comment.getTimeStamp());
            statement.setInt(3, comment.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteComment(int commentId) {
        String deleteSQL = "DELETE FROM comments WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteSQL)) {
            statement.setInt(1, commentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}