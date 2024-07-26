package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.PasswordChangeBO;
import lk.ijse.models.UserDTO;
//import lk.ijse.repository.UserRepo;

import java.io.IOException;
import java.sql.SQLException;

//import static jdk.internal.misc.OSEnvironment.initialize;

public class PasswordChangeFormController {

    @FXML
    private Button btnExit;

    @FXML
    private Button btnUpdate;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtConfirmPass;

    @FXML
    private TextField txtNewPass;

    @FXML
    private TextField txtUserId;
    PasswordChangeBO passwordChangeBO= (PasswordChangeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @FXML
    void btnExitOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Stage stage = (Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Login Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = txtUserId.getText();
        String name = txtConfirmPass.getText();
        String pw = txtNewPass.getText();

        //String sql = "UPDATE Customer SET name = ?, number = ?, address = ?, email=? WHERE id = ?";
        try {
            passwordChangeBO.updateUser(new UserDTO(id,name,pw));
            //tblCustomer.getItems().add(new CustomerTm(id, name,email,tel,address));
        }catch(SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }




    }
}

