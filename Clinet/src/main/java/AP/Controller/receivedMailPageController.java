package AP.Controller;

import AP.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.w3c.dom.ls.LSSerializer;
import same.Mail;
import same.Message;
import same.MessageType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static AP.App.currentUser;
import static AP.App.out;
import static AP.Controller.mainPanelController.selectedMail;
import static AP.Model.Thread.Listener.serverRespond;
import static java.lang.Thread.sleep;

public class receivedMailPageController {

    @FXML
    Label subjectLabel, mailDateLabel;
    @FXML
    TextArea messageInput;
    @FXML
    ImageView yellowStar;
    @FXML
    Button blockButton , unblockButton;
    @FXML
    AnchorPane fileAddressPane;
    @FXML
    TextField addressField;
    public static boolean reply = false;

    public Mail makeTempMail(Mail currentMail) {
        Mail tempMail = new Mail();
        tempMail.setRead(currentMail.isRead());
        tempMail.setDate(currentMail.getDate());
        tempMail.setImportant(currentMail.isImportant());
        tempMail.setSender(currentMail.getSender());
        tempMail.setTextMessage(currentMail.getTextMessage());
        tempMail.setSubject(currentMail.getSubject());
        tempMail.setReciever(currentMail.getReciever());
        return tempMail;
    }

    public void initialize() throws IOException, InterruptedException {
        subjectLabel.setText(selectedMail.getSubject());
        mailDateLabel.setText("from " + selectedMail.getSender().getUsername() + "@gmail.com    at " + selectedMail.getDate());
        messageInput.setText(selectedMail.getTextMessage());
        if (selectedMail.isImportant())
            yellowStar.setVisible(true);
        else
            yellowStar.setVisible(false);
        out.writeObject(new Message(selectedMail , MessageType.CheckBlocked));
        sleep(400);
        System.out.println((Boolean) serverRespond);
        if ((Boolean) serverRespond) {
            unblockButton.setVisible(true);
            blockButton.setVisible(false);
        }
        else {
            blockButton.setVisible(true);
            unblockButton.setVisible(false);
        }
    }

    public void showConversations(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("ConversationPage");
    }

    public void blockSender(ActionEvent actionEvent) throws IOException {
        out.writeObject(new Message(selectedMail, MessageType.BlockSender));
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "user is blocked successfully");
        alert.showAndWait();
    }

    public void unblockSender(ActionEvent actionEvent) throws IOException {
        out.writeObject(new Message(selectedMail, MessageType.UnblockSender));
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "user is unblocked successfully");
        alert.showAndWait();
    }

    public void deleteMessage(ActionEvent actionEvent) throws IOException {
        out.writeObject(new Message(selectedMail, MessageType.DeleteMail));
        new PageLoader().load("mainPanel");
    }

    public void setImportant(ActionEvent actionEvent) throws IOException {
        if (selectedMail.isImportant()) {
            selectedMail.setImportant(false);
            System.out.println("unimportant");
            out.writeObject(new Message(selectedMail , MessageType.ImportantMail , selectedMail.isImportant()));
            yellowStar.setVisible(false);
        }
        else {
            selectedMail.setImportant(true);
            System.out.println("important");
            out.writeObject(new Message(selectedMail , MessageType.ImportantMail , selectedMail.isImportant()));
            out.flush();
            yellowStar.setVisible(true);
        }
    }

    public void downloadFiles(ActionEvent actionEvent) throws IOException {
        fileAddressPane.setVisible(true);
    }

    public void returnToPanel(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("mainPanel");
    }

    public void replyMessage(ActionEvent actionEvent) throws IOException {
        reply = true;
        new PageLoader().load("sendMessagePage");
    }

    public void setAddress(ActionEvent actionEvent) throws IOException {
        if (selectedMail.getAttachedPhoto()!= null) {
            File imageFile = new File(addressField.getText() + "/" + currentUser.getUsername() + "image.png");
            FileOutputStream fis = new FileOutputStream(imageFile);
            fis.write(selectedMail.getAttachedPhoto());
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "your photo is downloaded successfully :)");
            alert.showAndWait();
        }
        else if (selectedMail.getAttachedMusic()!= null) {
            File musicFile = new File(addressField.getText() + "/" + currentUser.getUsername() + "music.mp3");
            FileOutputStream fis = new FileOutputStream(musicFile);
            fis.write(selectedMail.getAttachedMusic());
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "your music is downloaded successfully :)");
            alert.showAndWait();
        }
//        else if (selectedMail.getAttachedVideo()!= null) {
//            File videoFile = new File(addressField.getText() + "/" + currentUser.getUsername() + "video.mp4");
//            FileOutputStream fis = new FileOutputStream(videoFile);
//            fis.write(selectedMail.getAttachedVideo());
//            Alert alert = new Alert(Alert.AlertType.INFORMATION, "your video is downloaded successfully :)");
//            alert.showAndWait();
//        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "this message contains no attached file");
            alert.showAndWait();
        }
        fileAddressPane.setVisible(false);
    }
}
