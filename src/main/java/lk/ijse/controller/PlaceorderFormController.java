package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.bo.custom.ItemBO;
import lk.ijse.bo.custom.PlaceOrderBO;
import lk.ijse.db.DbConnection;
import lk.ijse.entity.Customer;
import lk.ijse.entity.Item;
import lk.ijse.models.CustomerDTO;
import lk.ijse.models.ItemDTO;
import lk.ijse.models.OrderDTO;
import lk.ijse.models.OrderDetailDTO;
import lk.ijse.view.tdm.CartTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class PlaceorderFormController {

    @FXML
    private Button btnAddToCart;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnPlaceOrder;

    @FXML
    private ComboBox<String> cmbCustomer;

    @FXML
    private ComboBox<String> cmbItemCode;


    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<CartTm, String> colCode;

    @FXML
    private TableColumn<CartTm, String> colDescription;

    @FXML
    private TableColumn<CartTm, Integer> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<CartTm, Double> colUnitPrice;

    @FXML
    private Button btnPrint;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<CartTm> tblOrderCart;

    @FXML
    private TextField txtQty;
    String tempId;
    private String orderId;

    private ObservableList<CartTm> obList = FXCollections.observableArrayList();
    CustomerBO customerBO= (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    ItemBO itemBO=(ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    PlaceOrderBO placeOrderBO  = (PlaceOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PLACE_ORDER);
    public void initialize() throws ClassNotFoundException {
        setDate();
        getCurrentOrderId();
        getCustomerIds();
        getItemCodes();
        setCellValueFactory();
    }

    private void getCustomerIds() {
        try {
            ArrayList<CustomerDTO> allCustomers = placeOrderBO.getAllCustomers();
            for (CustomerDTO c : allCustomers) {
                cmbCustomer.getItems().add(c.getId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customer ids").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getCurrentOrderId() throws ClassNotFoundException {
        try {
            String currentId = placeOrderBO.getCurrentId();

            String nextOrderId = generateNextOrderId(currentId);
            lblOrderId.setText(nextOrderId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//change
    private String generateNextOrderId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("O");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "O" + ++idNum;
        }
        return "O1";
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblOrderDate.setText(String.valueOf(now));
    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    private void getItemCodes() {
        try{
        ArrayList<ItemDTO> allItems = placeOrderBO.getAllItems();
        for (ItemDTO i : allItems) {
            cmbItemCode.getItems().add(i.getCode());
        }

    } catch (SQLException e) {
        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }

}
    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String code = cmbItemCode.getValue();
        String description = lblDescription.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double total = qty * unitPrice;
        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblOrderCart.getSelectionModel().getSelectedIndex();
                obList.remove(selectedIndex);

                tblOrderCart.refresh();
                calculateNetTotal();
            }
        });

        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            if(code.equals(colCode.getCellData(i))) {

                CartTm tm = obList.get(i);
                qty += tm.getQty();
                total = qty * unitPrice;

                tm.setQty(qty);
                tm.setTotal(total);

                tblOrderCart.refresh();
                calculateNetTotal();
                return;
            }
        }

        CartTm tm = new CartTm(code, description, qty, unitPrice, total, btnRemove);
        obList.add(tm);

        tblOrderCart.setItems(obList);
        calculateNetTotal();
        txtQty.setText("");
    }

    private void calculateNetTotal() {
        int netTotal = 0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            netTotal += (double) colTotal.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(netTotal));
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
    void btnPlaceOrderOnAction(ActionEvent event) throws ClassNotFoundException {
        String orderId = lblOrderId.getText();
        String customerId = cmbCustomer.getValue();
        String description=lblDescription.getText();
        Date date = Date.valueOf(LocalDate.now());


        var order = new OrderDTO(orderId, customerId, description);

        List<OrderDetailDTO> odtList = new ArrayList<>();

        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            CartTm tm = obList.get(i);

            OrderDetailDTO od = new OrderDetailDTO(
                    orderId,
                    tm.getCode(),
                    tm.getQty(),
                    tm.getUnitPrice()
            );

            odtList.add(od);
        }


       // OrderDetail od = new OrderDetail(order, odList);
        try {
            boolean isPlaced = saveOrder(order ,odtList);
            //tempId=lblOrderId.getText();
            //getCurrentOrderId();
            if(isPlaced) {
                System.out.println("hello");
                tempId=lblOrderId.getText();
                getCurrentOrderId();
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
           } else {
                new Alert(Alert.AlertType.WARNING, "Order Placed Unsccessfully!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    public boolean saveOrder(OrderDTO orderDTO,List<OrderDetailDTO> orderDetailList) throws SQLException, ClassNotFoundException {
        return placeOrderBO.placeOrder(orderDTO,orderDetailList);
    }
        /*try {
            boolean b = saveOrder(orderId,LocalDate.now(), cmbCustomer.getValue(),
                    tblOrderCart.getItems().stream().map(tm -> new OrderDetail(orderId, tm.getCode(), tm.getQty(), tm.getUnitPrice())).collect(Collectors.toList()));

            if (b) {
                new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


        orderId = generateNextOrderId(orderId);
        lblOrderId.setText("Order Id: " + orderId);
        cmbCustomer.getSelectionModel().clearSelection();
        cmbItemCode.getSelectionModel().clearSelection();
        tblOrderCart.getItems().clear();
        txtQty.clear();
        //calculateTotal();
    }

    public boolean saveOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetail> orderList) throws SQLException, ClassNotFoundException {
        OrderDTO orderDTO = new OrderDTO(orderId,customerId, orderDate, orderList);
        return placeOrderBO.placeOrder(orderDTO);
    }
    public String generateNewOrderId() {
        try {
            return placeOrderBO.generateOrderID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "OID-001";
    }*/





    @FXML
    void cmbCustomerOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = cmbCustomer.getValue();

        try {
            Customer customer =customerBO.searchById(id);

            lblCustomerName.setText(customer.getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  String getCurrentId() throws SQLException {
        //String sql = "SELECT orderId FROM Orders ORDER BY orderId DESC LIMIT 1";
        String sql="select concat('O',max(cast(substring(orderId,2)as unsigned)))as max_order_id from Orders";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String orderId = resultSet.getString(1);
            return orderId;
        }
        return null;
    }

    @FXML
    void cmbItemCodeOnAction(ActionEvent event) throws ClassNotFoundException {
        String code = cmbItemCode.getValue();

        try {
            Item item =itemBO.searchByCode(code);

            lblDescription.setText(item.getDescription());
            lblUnitPrice.setText(String.valueOf(item.getUnitPrice()));
            lblQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
            txtQty.requestFocus();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddToCartOnAction(event);
    }

    @FXML
    void btnPrintOnAction(ActionEvent event) throws JRException, SQLException {

           /* JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Report/SupRepo.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);*/
        printBill();


        }

    private void printBill() throws JRException, SQLException {
        JasperDesign jasperDesign=JRXmlLoader.load("src/main/resources/Report/SupRepo.jrxml");
        JasperReport jasperReport=JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object>data =new HashMap<>();
//        data.put("tempId","O29");
        data.put("OrderId",tempId);
        data.put("NetTotal",lblNetTotal.getText());

        JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,data,DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }


}
