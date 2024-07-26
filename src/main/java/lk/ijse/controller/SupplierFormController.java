package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.models.SupplierDTO;
import lk.ijse.entity.Supplier;
import lk.ijse.view.tdm.SupplierTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierFormController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTel;

    @FXML
    private Button btnEnter;

    @FXML
    private TextField txtTelSearch;

    SupplierBO supplierBO= (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);
    public void initialize(){
        setCellValueFactory();
        loadAllSupplier();
        addRegex(txtId);
        addRegex(txtTel);
    }

    private void addRegex(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (textField == txtId && !newValue.matches("^S.*$")){
                txtId.setStyle("-fx-focus-color:#f21e0f");
                txtId.clear();
            }else {
                txtId.setStyle("-fx-focus-color:#c4c1c0");
            }
        });

        textField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (textField == txtTel && !newValue.matches("^\\d{1,10}$")){
                txtTel.setStyle("-fx-focus-color:#f21e0f");
                txtTel.clear();
            }else {
                txtTel.setStyle("-fx-focus-color:#c4c1c0");
            }
        });
    }
    private void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void loadAllSupplier(){
        tblSupplier.getItems().clear();
        try {
            ArrayList<SupplierDTO> allSuppliers = supplierBO.getAllSuppliers();

            for (SupplierDTO s : allSuppliers) {
                tblSupplier.getItems().add(new SupplierTm(s.getId(), s.getName(), s.getAddress(),s.getEmail(),s.getTel()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws ClassNotFoundException {
        String id=txtId.getText();

        //String sql="DELETE FROM Supplier WHERE id=?";

        try{
            boolean isDeleted=supplierBO.deleteSupplier(id);
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Supplier deleted!").show();
                tblSupplier.refresh();
            }

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
    }

    @FXML
    void btnExitOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Stage stage = (Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Login Form");
        stage.centerOnScreen();

    }

    boolean existSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierBO.existSupplier(id);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String tel = txtTel.getText();

       // String sql = "INSERT INTO Supplier VALUES(?,?,?,?,?)";

        if (btnSave.getText().equalsIgnoreCase("Save")) {
            try {
                // Check if customer with the same ID already exists
                if (existSupplier(id)) {
                    new Alert(Alert.AlertType.ERROR, id + " already exists").show();
                } else {
                    // Save customer if it doesn't exist
                    supplierBO.saveSupplier(new SupplierDTO(id, name, address, email, tel));
                    tblSupplier.getItems().add(new SupplierTm(id, name,email,address,tel));
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "Error saving customer: " + e.getMessage()).show();
            }
        }

        initialize();
    }




    @FXML
    void btnSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id=txtId.getText();

        try {
            Supplier supplier = supplierBO.searchSupplier(id);
            if (supplier != null) {
                txtId.setText(supplier.getId());
                txtName.setText(supplier.getName());
                txtAddress.setText(supplier.getAddress());
                txtTel.setText(supplier.getTel());
                txtEmail.setText(supplier.getEmail());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Customer not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws ClassNotFoundException {
        String id=txtId.getText();
        String name=txtName.getText();
        String address=txtAddress.getText();
        String email=txtEmail.getText();
        String tel=txtTel.getText();


        //String sql = "UPDATE Supplier SET name = ?, address = ?, email = ?, number=? WHERE id = ?";
        //String sql= "UPDATE Supplier SET name = ?, number = ?, address = ?, email=? WHERE id = ?";
        try {
            if(!existSupplier(id)){
                new Alert(Alert.AlertType.ERROR,"Can't updated");
            }
            supplierBO.updateSupplier(new SupplierDTO(id,name,email,tel,address));
            tblSupplier.getItems().add(new SupplierTm(id, name,email,tel,address));
        }catch(SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
    }
    private void clearFields(){
        txtId.setText("");
        txtName.setText("");
        txtTel.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
    }

   /* @FXML
    void txtSupplierIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.ID,txtId);
    }*/

    @FXML
    void txtAddressOnAction(ActionEvent event) {
        txtEmail.requestFocus();
    }

    @FXML
    void txtEmailOnAction(ActionEvent event) {
        txtTel.requestFocus();
    }

    @FXML
    void txtIdOnAction(ActionEvent event) {
        txtName.requestFocus();
    }

    @FXML
    void txtNameOnAction(ActionEvent event) {
        txtAddress.requestFocus();
    }

    @FXML
    void txtTelOnAction(ActionEvent event) {

    }

    @FXML
    void btnEnterOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String tele = txtTelSearch.getText();
        try {
            Supplier supplier = supplierBO.searchByNumber(tele);
            if (supplier!= null) {
                //System.out.println(employee.getId());
                //txtId.setText(customer.getId());
                txtId.setText(supplier.getId());
                txtName.setText(supplier.getName());
                txtAddress.setText(supplier.getAddress());
                txtEmail.setText(supplier.getEmail());
                txtTel.setText(supplier.getTel());

            } else {
                new Alert(Alert.AlertType.INFORMATION, "Customer not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
}
