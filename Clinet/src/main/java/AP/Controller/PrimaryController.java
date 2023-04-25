package AP.Controller;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        AP.App.setRoot("secondary");
    }

}
