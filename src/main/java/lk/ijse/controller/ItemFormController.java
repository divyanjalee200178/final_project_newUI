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
import lk.ijse.bo.custom.ItemBO;
import lk.ijse.entity.Item;
import lk.ijse.models.ItemDTO;
import lk.ijse.view.tdm.ItemTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemFormController {

    @FXML
    private Button btnExit;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<ItemTm, String> colCode;

    @FXML
    private TableColumn<ItemTm, String> colDesc;

    @FXML
    private TableColumn<ItemTm,String > colLoacation;

    @FXML
    private TableColumn<ItemTm, Integer> colQty;

    @FXML
    private TableColumn<ItemTm, Double> colUnitPrice;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<ItemTm> tblItem;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtLocation;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtUnitPrice;

    ItemBO itemBO= (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    public void initialize() {
        setCellValueFactory();
        loadAllItem();

        addRegex(txtCode);
        addRegex(txtUnitPrice);
        addRegex(txtQty);

    }

    private void addRegex(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (textField == txtCode && !newValue.matches("^I.*$")){
                new Alert(Alert.AlertType.INFORMATION,"You can only use 'I'").show();
                txtCode.clear();
            }
        });

        textField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (textField == txtUnitPrice && !newValue.matches("^\\d+(\\.\\d{1,2})?$")){
                //new Alert(Alert.AlertType.INFORMATION,"you can only use 'double'").show();
                txtUnitPrice.setStyle("-fx-focus-color:#f21e0f");
                txtUnitPrice.clear();
            }else {
                txtUnitPrice.setStyle("-fx-focus-color:#c4c1c0");
            }
        });

        textField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (textField == txtQty && !newValue.matches("^-?\\d+$")){
                //new Alert(Alert.AlertType.INFORMATION,"you can only use 'int'").show();
                txtQty.clear();
            }
        });
    }


    private void loadAllItem(){
        tblItem.getItems().clear();
        try {
            ArrayList<ItemDTO> allItems = itemBO.getAllItems();

            for (ItemDTO i : allItems) {
                tblItem.getItems().add(new ItemTm(i.getCode(), i.getDescription(), i.getUnitPrice(),i.getQtyOnHand(),i.getLocation()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colLoacation.setCellValueFactory(new PropertyValueFactory<>("location"));
    }



    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        txtCode.requestFocus();
    }

    private void clearFields(){
        txtCode.setText("");
        txtDescription.setText("");
        txtUnitPrice.setText("");
        txtQty.setText("");
        txtLocation.setText("");
    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) throws ClassNotFoundException {
        String code=txtCode.getText();

        try{
            boolean isDeleted=itemBO.deleteItem(code);
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Item deleted!").show();
                tblItem.refresh();
            }
            }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException {
        String code=txtCode.getText();
        String description=txtDescription.getText();
        double unitPrice=Double.parseDouble(txtUnitPrice.getText());
        int qty=Integer.parseInt(txtQty.getText());
        String location=txtLocation.getText();

        //String sql="INSERT INTO Item VALUES(?,?,?,?,?)";
        if(btnSave.getText().equalsIgnoreCase("Save")) {
            try {
                if (existItem(code)) {
                    new Alert(Alert.AlertType.ERROR, code + " already exists").show();
                } else {

                    itemBO.saveItem(new ItemDTO(code,description,unitPrice,qty,location));
                    tblItem.getItems().add(new ItemTm(code,description,unitPrice,qty,location));
                }

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
        initialize();
    }

    boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return itemBO.existItem(code);
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String code=txtCode.getText();

        Item item=itemBO.searchByCode(code);
        if(item !=null){
            txtCode.setText(item.getCode());
            txtDescription.setText(item.getDescription());
            txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
            txtQty.setText(String.valueOf(item.getQtyOnHand()));
            txtLocation.setText(item.getLocation());
        }else{
            new Alert(Alert.AlertType.INFORMATION, "Item not found");
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws ClassNotFoundException {
        String code = txtCode.getText();
        String description = txtDescription.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        String location = txtLocation.getText();

        //Item item = new Item(code, description, qty, unitPrice, location);

        try{
            if(!existItem(code)){
                new Alert(Alert.AlertType.ERROR,"Can't updated");
            }
            itemBO.updateItem(new ItemDTO(code,description,unitPrice,qty,location));
            tblItem.getItems().add(new ItemTm(code,description,unitPrice,qty,location));
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
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
    void btnExitOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane= FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Stage stage=(Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();

    }

    @FXML
    void txtCodeOnAction(ActionEvent event) {
        txtDescription.requestFocus();
    }

    @FXML
    void txtDescriptionOnAction(ActionEvent event) {
        txtQty.requestFocus();
    }

    @FXML
    void txtLocationOnAction(ActionEvent event) {
        txtUnitPrice.requestFocus();
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        txtLocation.requestFocus();
    }

    @FXML
    void txtUnitPriceOnAction(ActionEvent event) {
        txtCode.requestFocus();
    }


}
