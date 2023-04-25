package AP.Controller;

import AP.PageLoader;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.apache.commons.io.IOUtils;
import same.Mail;
import same.Message;
import same.MessageType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static AP.App.currentUser;
import static AP.App.out;
import static AP.Controller.mainPanelController.selectedMail;
import static AP.Controller.receivedMailPageController.reply;
import static AP.Model.Thread.Listener.serverRespond;
import static java.lang.Thread.sleep;

public class SendMessagePageController {
    @FXML
    TextField recepientField , subjectField;
    @FXML
    ImageView attachIcon , emojiIcon , sendIcon , emojiSet;
    @FXML
    Button attachButton , emojiButton , sendButton;
    @FXML
    TextArea messageInput;
    @FXML
    AnchorPane attachTypePane;
    boolean left = false;
    Mail mail = new Mail();

    public void initialize() {
        if (reply) {
            recepientField.setText(selectedMail.getSender().getUsername());
            reply = false;
        }
    }

    public void attachFile(ActionEvent actionEvent) {
        attachTypePane.setVisible(true);
    }

    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void showEmoji(ActionEvent actionEvent) {
        if (!left) {
            TranslateTransition tt = new TranslateTransition(Duration.millis(2000), emojiSet);
            tt.setToX(-380);
            tt.playFromStart();
            left = true;
        }
        else {
            TranslateTransition tt = new TranslateTransition(Duration.millis(2000), emojiSet);
            tt.setToX(0);
            tt.playFromStart();
            left = false;
        }
    }

    public void sendMessage(ActionEvent actionEvent) throws IOException, InterruptedException {

        if (messageInput.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "you can't send an empty message");
            alert.showAndWait();
        }
        else {
            mail.setSender(currentUser);
            mail.setReciever(recepientField.getText());
            mail.setTextMessage(messageInput.getText());
            System.out.println(getDate());
            mail.setDate(getDate());
            if (!subjectField.getText().isEmpty())
                mail.setSubject(subjectField.getText());
            out.writeObject(new Message(mail , MessageType.sendMail));
            sleep(500);
            if (serverRespond instanceof Boolean){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "sorry... this user has blocked you");
                alert.showAndWait();
            }
            else {
                System.out.println("here");
                new PageLoader().load("sendMessagePage");
            }
        }


    }

    public void returnToMainPanel(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("mainPanel");
    }

    public void moveSendRight(MouseEvent mouseEvent) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000) , sendButton);
        tt.setToX(50);
        TranslateTransition tt2 = new TranslateTransition(Duration.millis(1000) , sendIcon);
        tt2.setToX(50);
        tt.playFromStart();
        tt2.playFromStart();
    }

    public void moveSendLeft(MouseEvent mouseEvent) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000) , sendButton);
        tt.setToX(0);
        TranslateTransition tt2 = new TranslateTransition(Duration.millis(1000) , sendIcon);
        tt2.setToX(0);
        tt.playFromStart();
        tt2.playFromStart();
    }

    public void moveEmojiRight(MouseEvent mouseEvent) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000) , emojiButton);
        tt.setToX(50);
        TranslateTransition tt2 = new TranslateTransition(Duration.millis(1000) , emojiIcon);
        tt2.setToX(50);
        tt.playFromStart();
        tt2.playFromStart();
    }

    public void moveEmojiLeft(MouseEvent mouseEvent) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000) , emojiButton);
        tt.setToX(0);
        TranslateTransition tt2 = new TranslateTransition(Duration.millis(1000) , emojiIcon);
        tt2.setToX(0);
        tt.playFromStart();
        tt2.playFromStart();
    }

    public void moveAttachRight(MouseEvent mouseEvent) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000) , attachButton);
        tt.setToX(50);
        TranslateTransition tt2 = new TranslateTransition(Duration.millis(1000) , attachIcon);
        tt2.setToX(50);
        tt.playFromStart();
        tt2.playFromStart();
    }

    public void moveAttachLeft(MouseEvent mouseEvent) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000) , attachButton);
        tt.setToX(0);
        TranslateTransition tt2 = new TranslateTransition(Duration.millis(1000) , attachIcon);
        tt2.setToX(0);
        tt.playFromStart();
        tt2.playFromStart();
    }

    public void attachPhoto(MouseEvent mouseEvent) throws IOException {
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(imageFilter);
        File file =  chooser.showOpenDialog(null);
        if (file != null) {
            FileOutputStream userProfilePhoto = new FileOutputStream("src/sentFiles/" + currentUser.getUsername() + ".png");
            Files.copy(file.toPath(), userProfilePhoto);
            File img = new File("src/sentFiles/" + currentUser.getUsername() + "image.png");
            Image photo = new Image(file.toURI().toString());
            BufferedImage bufferedImage = ImageIO.read(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            mail.setAttachedPhoto(byteArrayOutputStream.toByteArray());
        }
        attachTypePane.setVisible(false);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "photo is attached to your mail successfully :)");
        alert.showAndWait();
    }

    public void attachVideo(MouseEvent mouseEvent) throws IOException {
//        FileChooser.ExtensionFilter videoFilter = new FileChooser.ExtensionFilter("Video Files", "*.mp4");
//        FileChooser chooser = new FileChooser();
//        chooser.getExtensionFilters().add(videoFilter);
//        File file =  chooser.showOpenDialog(null);
//        if (file != null) {
//            FileOutputStream fos = new FileOutputStream("src/sentFiles/" + currentUser.getUsername() +"video" + ".mp4");
//            Files.copy(file.toPath(), fos);
//            File video = new File("src/sentFiles/" + currentUser.getUsername() + "video.mp4");
//            FileInputStream fileInputStream = new FileInputStream(video);
//            mail.setAttachedVideo(Files.readAllBytes(video.toPath()));
//        }
//        attachTypePane.setVisible(false);
//        Alert alert = new Alert(Alert.AlertType.INFORMATION, "video is attached to your mail successfully :)");
//        alert.showAndWait();
    }

    public void attachMusic(MouseEvent mouseEvent) throws IOException {
        FileChooser.ExtensionFilter musicFilter = new FileChooser.ExtensionFilter("Music Files", "*.mp3");
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(musicFilter);
        File file =  chooser.showOpenDialog(null);
        if (file != null) {
            FileOutputStream musicFile = new FileOutputStream("src/sentFiles/" + currentUser.getUsername() + "music.mp3");
            Files.copy(file.toPath(), musicFile);
            File music = new File("src/sentFiles/" + currentUser.getUsername() + "music.mp3");
            FileInputStream fis = new FileInputStream(music);
            mail.setAttachedMusic(Files.readAllBytes(music.toPath()));
        }
        attachTypePane.setVisible(false);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "music is attached to your mail successfully :)");
        alert.showAndWait();
    }

}
