package AP.Controller;

import javafx.scene.control.ListCell;
import same.Mail;

import java.io.IOException;

public class ListMessageCell extends ListCell<Mail> {

    @Override
    public void updateItem(Mail mail, boolean empty) {
        super.updateItem(mail, empty);
        if (mail != null) {
            setStyle("-fx-background-color: #efd0ef");
            try {
                setGraphic(new mailCellController(mail).init());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
