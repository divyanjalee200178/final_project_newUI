package lk.ijse.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.PasswordChangeBO;
import lk.ijse.entity.User;

import java.io.IOException;
import java.sql.SQLException;

import static javafx.fxml.FXMLLoader.load;


public class LoginFormController {
@FXML
private Button btnLogin;

@FXML
private Button btnRegister;

@FXML
private TextField txtPassword;

@FXML
private TextField txtUserID;
@FXML
private AnchorPane rootNode;

    PasswordChangeBO passwordChangeBO= (PasswordChangeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);


@FXML
    void btnLoginOnAction(ActionEvent event) throws IOException, ClassNotFoundException {
    String userID = txtUserID.getText();
    String password = txtPassword.getText();

    try {
        checkCredential(userID, password);
    } catch (SQLException e) {
        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
    }
}

   private void checkCredential(String userId, String password) throws SQLException, IOException, ClassNotFoundException {
       if(userId.isEmpty() && password.isEmpty()){
           new Alert(Alert.AlertType.INFORMATION,"Empty fields Try again!").show();
           return;
       }
       if(userId.isEmpty()){
           new Alert(Alert.AlertType.INFORMATION,"User is Empty!").show();
           return;
       }
       if(password.isEmpty()){
           new Alert(Alert.AlertType.INFORMATION,"Password is empty!").show();
       }
       User userDTO=passwordChangeBO.checkCreden(userId,password);
       if(userDTO==null){
           new Alert(Alert.AlertType.INFORMATION,"Sorry userId can't be find").show();
           return;
       }
       if(!userDTO.getPassword().equals(password)){
           new Alert(Alert.AlertType.ERROR,"Sorry! wrong password").show();
          // System.out.println("Sorry");
           return;
       }
       navigateToTheDashboard();
   }



    private void navigateToTheDashboard() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }


    public void btnRegisterOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/register_form.fxml"));
        Stage stage = (Stage) this.rootNode.getScene().getWindow();

        Scene scene = new Scene(rootNode);
        //Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Registration Form");
        stage.show();
    }






    @FXML
    void txtUsernameOnAction(ActionEvent event) {

    }

    void txtPassword(ActionEvent event){

    }

    public void txtPasswordOnAction(MouseEvent mouseEvent) {
    }

    public void hypForgetOnAction(ActionEvent actionEvent) throws IOException {
        navigateToThePasswordChangeForm();
    }

    private void navigateToThePasswordChangeForm() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/Passwordchange_form.fxml"));
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        Scene scene = new Scene(rootNode);
        //Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Change Password Form");
        stage.show();
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {

    }

    @FXML
    void txtUserIdOnAction(ActionEvent event) {

    txtPassword.requestFocus();
    }
}



