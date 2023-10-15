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
