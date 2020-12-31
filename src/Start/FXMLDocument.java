package Start;

import Account.QuaterArriarsCheck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Ranga Rathnayake on 2020-12-28.
 */
public class FXMLDocument implements Initializable {

    @FXML
    private Button txt_update;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("OKKKKK");
    }

    @FXML
    void clickOnFirst(ActionEvent event) {

        QuaterArriarsCheck quaterArriarsCheck = new QuaterArriarsCheck();
        quaterArriarsCheck.start();


    }


}
