package solo.Guzov.Ilya.Example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {
    @FXML
    private Label label;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        System.out.println(Client.getRespServer());
        label.setText(Client.getRespServer());
        Client.setClientCommand(codeArea.getText());
        codeArea.clear();
    }

    @FXML
    private TextArea codeArea;

    public void initialize() {
        // TODO
    }

}
