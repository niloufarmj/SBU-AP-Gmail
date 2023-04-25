package AP.Controller;

import AP.PageLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import same.Message;
import same.MessageType;
import same.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import static AP.App.currentUser;
import static AP.App.out;


public class additionalInformationController {

    ObservableList<String> countryList = FXCollections.observableArrayList("Select country" , "Iran" , "USA" , "UK" , "Russia" , "Japan" , "Scotland" , "Canada" , "Germany" , "Spain");
    ObservableList<String> genders = FXCollections.observableArrayList("Select gender" , "male" , "female" , "non" );
    ObservableList<String> questions = FXCollections.observableArrayList(
                "Select a recovery question" ,
                "What's your favorite teacher's name ?" ,
                "What's your pet's name ?" ,
                "what's your favorite color?" ,
                "what's your favorite colour?" ,
                "what's your favorite singer's name?");
    @FXML
    TextField numberField , recoveryMailField , answerField ;
    @FXML
    ChoiceBox chooseCountry , chooseGender , chooseQuestion;
    @FXML
    ImageView image, countryIcon , iranIcon , usIcon , ukIcon , russiaIcon, germanyIcon, japanIcon, scotlandIcon, canadaIcon, spainIcon, maleIcon, femaleIcon, genderIcon;
    @FXML
    Label errorLabel , emptyFieldsLabel;
    @FXML
    private void initialize() {
        chooseCountry.setValue("Select country");
        chooseCountry.setItems(countryList);
        chooseGender.setValue("Select gender");
        chooseGender.setItems(genders);
        chooseQuestion.setValue("Select a recovery question");
        chooseQuestion.setItems(questions);
    }

    public void hideLabels(MouseEvent mouseEvent) {
        emptyFieldsLabel.setVisible(false);
        errorLabel.setVisible(false);
    }

    public void setPhoto(MouseEvent mouseEvent) throws IOException {
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        FileChooser chooser = new FileChooser();
        chooser.setTitle("select an image");
        chooser.getExtensionFilters().add(imageFilter);
        File file =  chooser.showOpenDialog(null);
        if(file != null){
            image.setImage(new Image(file.getAbsoluteFile().toURI().toString(),130,130,true,true));
        }
        if (image.getImage() != null) {
            FileOutputStream userProfilePhoto = new FileOutputStream("images/profile photos/" + currentUser.getUsername() + ".png");
            Files.copy(file.toPath(), userProfilePhoto);
            File img = new File("images/profile photos/" + currentUser.getUsername() + ".png");
            Image photo = new Image(file.toURI().toString());
            BufferedImage bufferedImage = ImageIO.read(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            currentUser.setProfilePhoto(byteArrayOutputStream.toByteArray());
        }
    }

    public void loadMainPanel(ActionEvent actionEvent) throws IOException {
        if (numberField.getText().isEmpty() || recoveryMailField.getText().isEmpty() || answerField.getText().isEmpty() || chooseCountry.getValue().toString().equals("Select country") || chooseGender.getValue().toString().equals("Select gender") || chooseQuestion.getValue().toString().equals("Select recovery question"))
            emptyFieldsLabel.setVisible(true);

        if (chooseQuestion.getValue().toString().equals("Select a recovery question") && !answerField.getText().isEmpty()) {
            errorLabel.setText("Select a question for your answer");
            errorLabel.setVisible(true);
        } else if (!numberField.getText().isEmpty() && !numberField.getText().matches("^[0-9]+$")) {
            errorLabel.setText("Enter a valid number :/");
            errorLabel.setVisible(true);
        } else if (!recoveryMailField.getText().isEmpty() && !recoveryMailField.getText().matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")) {
            errorLabel.setText("Enter a valid mail address :/");
            errorLabel.setVisible(true);
        } else {
            currentUser.setCountry(chooseCountry.getValue().toString());
            currentUser.setGender(chooseGender.getValue().toString());
            currentUser.setPhoneNumber(numberField.getText());
            currentUser.setRecoveryMail(recoveryMailField.getText());
            currentUser.setRecoveryQuestion(chooseQuestion.getValue().toString());
            currentUser.setRecoveryAnswer(answerField.getText());
            out.writeObject(new Message(new userMaker().makeTempUser(currentUser), MessageType.AdditionalSignUp));
            new PageLoader().load("mainPanel");
        }
    }

    public void setCountryIcon(MouseEvent keyEvent) {

        {
            countryIcon.setVisible(false);
            iranIcon.setVisible(false);
            japanIcon.setVisible(false);
            usIcon.setVisible(false);
            ukIcon.setVisible(false);
            spainIcon.setVisible(false);
            russiaIcon.setVisible(false);
            scotlandIcon.setVisible(false);
            canadaIcon.setVisible(false);
        }
        switch ((String) chooseCountry.getValue()) {
            case "Select country" :
                countryIcon.setVisible(true);
                break;
            case "Iran" :
                iranIcon.setVisible(true);
                break;
            case "Japan" :
                japanIcon.setVisible(true);
                break;
            case "USA" :
                usIcon.setVisible(true);
                break;
            case "UK" :
                ukIcon.setVisible(true);
                break;
            case "Spain" :
                spainIcon.setVisible(true);
                break;
            case "Russia" :
                russiaIcon.setVisible(true);
                break;
            case "Scotland" :
                scotlandIcon.setVisible(true);
                break;
            case "Canada" :
                canadaIcon.setVisible(true);
                break;
        }
    }

    public void setGenderIcon (MouseEvent mouseevent) {
        {
            genderIcon.setVisible(false);
            maleIcon.setVisible(false);
            femaleIcon.setVisible(false);
        }
        switch (chooseGender.getValue().toString()) {
            case "Select gender":
                genderIcon.setVisible(true);
                break;
            case "male" :
                maleIcon.setVisible(true);
                break;
            case "female" :
                femaleIcon.setVisible(true);
                break;
            case "none" :
                genderIcon.setVisible(true);
        }
    }

}
