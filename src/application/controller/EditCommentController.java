package application.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import application.database.Comment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EditCommentController {

    private String commentId;

    @FXML
    private TextArea description;

    @FXML
    private TextArea timestamp;

    void setComment(String commentId) {
        DatabaseController dbc = new DatabaseController();
        Comment curr = dbc.getComment(commentId);
        this.commentId = commentId;
        // outputs the comment that user can edit then just save it 
        description.setText(curr.getText());

        // Set to current time this is just for show tbh 
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        timestamp.setText(now);
    }

    @FXML
    private void editComment(ActionEvent event) {
        DatabaseController dbc = new DatabaseController();

        // Generate current timestamp
        String currentTimestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Update comment
        dbc.editComment(commentId, description.getText(), currentTimestamp);
        goHome(event);
    }

    void goHome(ActionEvent event) {
        MainController mc = new MainController();
        mc.redirectHelper(event, "view/Main.fxml");
    }
}
