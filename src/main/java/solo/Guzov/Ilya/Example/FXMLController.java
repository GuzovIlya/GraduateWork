package solo.Guzov.Ilya.Example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController{
    @FXML
    private Label label;

    @FXML
    private TextArea codeArea;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        Client.setClientCommand(codeArea.getText());
        label.setText(Client.getRespServer());
        codeArea.clear();
    }

    public void refreshCodeArea(ActionEvent event){
        label.setText(Client.getRespServer());
    }

    public void initialize() {
        // TODO
    }

}
