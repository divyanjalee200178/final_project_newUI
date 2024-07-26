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
import lk.ijse.bo.custom.EmployeeBO;
import lk.ijse.entity.Employee;
import lk.ijse.models.EmployeeDTO;
import lk.ijse.view.tdm.EmployeeTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeFormController {

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
    private TableView<EmployeeTm> tblEmployee;

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

    EmployeeBO employeeBO= (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);
    public void initialize(){
        setCellValueFactory();
        loadAllEmployee();
        addRegex(txtId);
        addRegex(txtTel);
    }

    private void addRegex(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (textField == txtId && !newValue.matches("^E.*$")){
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

    private void loadAllEmployee(){
        tblEmployee.getItems().clear();
        try {
            /*Get all customers*/
            ArrayList<EmployeeDTO> allEmployees = employeeBO.getAllEmployees();

            for (EmployeeDTO e : allEmployees) {
                tblEmployee.getItems().add(new EmployeeTm(e.getId(), e.getName(), e.getAddress(),e.getEmail(),e.getTel()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane= FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage=(Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFeilds();
    }
    private void clearFeilds(){
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtTel.setText("");
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException {
        String id=txtId.getText();
        String name=txtName.getText();
        String address=txtAddress.getText();
        String email=txtEmail.getText();
        String tel=txtTel.getText();

       // String sql="INSERT INTO Employee VALUES(?,?,?,?,?)";
        if (btnSave.getText().equalsIgnoreCase("Save")) {
            try {
                if (existEmployee(id)) {
                    new Alert(Alert.AlertType.ERROR, id + " already exists").show();
                } else {

                    employeeBO.saveEmployee(new EmployeeDTO(id, name, address, email, tel));
                    tblEmployee.getItems().add(new EmployeeTm(id, name,email,address,tel));
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "Error saving customer: " + e.getMessage()).show();
            }
        }

        initialize();
    }


    boolean existEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeBO.existEmployee(id);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws ClassNotFoundException {
        String id=txtId.getText();

        try{
            boolean isDeleted=employeeBO.deleteEmployee(id);
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Employee deleted!").show();
                tblEmployee.refresh();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
    }

    @FXML
    void btnExitOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane=FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Stage stage=(Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Login Form");
        stage.centerOnScreen();
    }



    @FXML
    void btnSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id=txtId.getText();

        try {
            Employee employee = employeeBO.searchEmployee(id);
            if (employee != null) {
                txtId.setText(employee.getId());
                txtName.setText(employee.getName());
                txtAddress.setText(employee.getAddress());
                txtTel.setText(employee.getTel());
                txtEmail.setText(employee.getEmail());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Employee not found!").show();
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

       // Employee employee=new Employee(id,name,address,email,tel);

        try{
            if(!existEmployee(id)){
                new Alert(Alert.AlertType.ERROR,"Can't updated");
            }
            employeeBO.updateEmployee(new EmployeeDTO(id,name,email,tel,address));
            tblEmployee.getItems().add(new EmployeeTm(id, name,email,tel,address));
        }catch(SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        initialize();

    }

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
            Employee employee = employeeBO.searchByNumber(tele);
            if (employee != null) {
                System.out.println(employee.getId());
                //txtId.setText(customer.getId());
                txtId.setText(employee.getId());
                txtName.setText(employee.getName());
                txtAddress.setText(employee.getAddress());
                txtEmail.setText(employee.getEmail());
                txtTel.setText(employee.getTel());

            } else {
                new Alert(Alert.AlertType.INFORMATION, "Customer not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }



}
