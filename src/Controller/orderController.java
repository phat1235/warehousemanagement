package Controller;


import alert.alertMessage;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import Model.userData;
import database.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import Model.orderData;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class orderController implements Initializable{
   
     @FXML
    private Button order_Btn;

    @FXML
    private TableColumn<orderData, String> order_col_ProductName;
    @FXML
    private TableColumn<orderData, String> order_col_ID;

    @FXML
    private TableColumn<orderData, String> order_col_ProductPrice;

    @FXML
    private TableColumn<orderData, String> order_col_ProductQuantity;

    @FXML
    private TableColumn<orderData, String> order_col_ProductSuplierName;

    @FXML
    private TableColumn<orderData, String> order_col_ProductType;

    @FXML
    private Button orders_AddBtn;

    @FXML
    private TextField orders_Amount;

    @FXML
    private Label orders_Balance;

    @FXML
    private AnchorPane orders_Form;

    @FXML
    private Button orders_PayBtn;

    @FXML
    private ComboBox<?> orders_ProductName;

    @FXML
    private ComboBox<?> orders_ProductType;

    @FXML
    private Spinner<Integer> orders_Quantity;

    @FXML
    private Button orders_ReceiptBtn;

    @FXML
    private Button orders_ResetBtn;

    @FXML
    private ComboBox<?> orders_SuplierName;

    @FXML
    private TableView<orderData> orders_TableViews;

    @FXML
    private Label orders_Total;
    
  private  Connection connect;
    private   PreparedStatement prepare;
    private   Statement statement;
    private   ResultSet result;
    private Image image;
    //------------------------------------------------------------------------------------

    //
    alertMessage alert = new alertMessage();
     public ObservableList<orderData> ordersListData() {
        // customerId();
        orderId();
        ObservableList<orderData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM order1 WHERE order_id = '" + OrderId + "'";

        connect = database.connectDb();

        try {
            orderData orderD;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                orderD = new orderData(result.getInt("order_id"),
                        result.getString("productname"),
                        result.getInt("quantity"),
                        result.getString("type"),
                        result.getDouble("price"),
                        result.getString("supliername"),
                        result.getDate("date"));
                listData.add(orderD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    //

    private ObservableList<orderData> ordersList;

    public void ordersShowListData() {
        ordersList = ordersListData();
        order_col_ID.setCellValueFactory(new PropertyValueFactory<>("order_Id"));
        order_col_ProductName.setCellValueFactory(new PropertyValueFactory<>("order_productName"));
        order_col_ProductQuantity.setCellValueFactory(new PropertyValueFactory<>("order_quantity"));
        order_col_ProductType.setCellValueFactory(new PropertyValueFactory<>("order_type"));
        order_col_ProductPrice.setCellValueFactory(new PropertyValueFactory<>("order_price"));
        order_col_ProductSuplierName.setCellValueFactory(new PropertyValueFactory<>("order_suplierName"));

        orders_TableViews.setItems(ordersList);
        ordersDisplayTotal();

    }

    public void ordersListProductName() {

        
String sql = "SELECT product_name FROM product WHERE product_supliername = '"
         + orders_SuplierName.getSelectionModel().getSelectedItem() 
         + "' AND  product_type = '" + orders_ProductType.getSelectionModel().getSelectedItem() 
         + "' AND product_quantity > 0";
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("product_name"));
            }
            orders_ProductName.setItems(listData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ordersListSuplierName() {

        String sql = "SELECT product_supliername FROM product WHERE product_type = '"
                + orders_ProductType.getSelectionModel().getSelectedItem()
                + "' AND product_quantity > 0 ";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("product_supliername"));
            }
            orders_SuplierName.setItems(listData);
            ordersListProductName();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //
    private String[] orderlistType = {"Food", "Snack", "Drink"};

    public void ordersListType() {

        List<String> listT = new ArrayList<>();
        for (String data : orderlistType) {
            listT.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listT);
        orders_ProductType.setItems(listData);
        ordersListSuplierName();
    }

    //
    private int OrderId;

    public void orderId() {
        String sql = "SELECT MAX(order_id) FROM order1";

        connect = database.connectDb();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                OrderId = result.getInt("MAX(order_id)");
            }

            String checkID = "SELECT MAX(order_id) FROM customerreceipt";

            prepare = connect.prepareStatement(checkID);
            result = prepare.executeQuery();
            int cID = 0;

            if (result.next()) {
                cID = result.getInt("MAX(order_id)");
            }

            if (OrderId == 0) {
                OrderId += 1;
            } else if (OrderId == cID) {
                OrderId = OrderId + 1;
            }

            getData.cID = OrderId;

        } catch (Exception e) {
        };
    }
//

    private SpinnerValueFactory<Integer> spinner;

  public void ordersSpinner() {
    try {
        // Thực hiện câu truy vấn SQL để lấy giá trị MIN của cột product_quantity
        String query = "SELECT MIN(product_quantity) FROM product";
        Connection connection = database.connectDb(); // Phương thức kết nối đến cơ sở dữ liệu
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        // Lấy giá trị MIN của cột product_quantity nếu có
        if (resultSet.next()) {
            int minValue = resultSet.getInt(1);
            spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, minValue, 0);
            orders_Quantity.setValueFactory(spinner);
        }

        // Đóng tài nguyên (kết nối, câu lệnh và kết quả)
        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    private int qty;

    public void ordersShowPinner() {
        qty = orders_Quantity.getValue();
    }

    public void ordersAdd() {
        orderId();
        String sql = "INSERT INTO order1(order_id, productname, quantity, type, price, supliername, date)"
                + "VALUE(?,?,?,?,?,?,?)";

        connect = database.connectDb();
        try {
            String checkData = "SELECT * FROM  product  WHERE  product_name = '"
                    + orders_ProductName.getSelectionModel().getSelectedItem()
                    + "' AND product_type =  '" + orders_ProductType.getSelectionModel().getSelectedItem()
                    + "' AND product_supliername =  '" + orders_SuplierName.getSelectionModel().getSelectedItem() + "'";
            double priceData = 0;

            statement = connect.createStatement();
            result = statement.executeQuery(checkData);
            if (result.next()) {
                priceData = result.getDouble("product_price");
            }

            double totalPData = (priceData * qty);

            if ((String) orders_ProductName.getSelectionModel().getSelectedItem() == null
                    || (String) orders_ProductType.getSelectionModel().getSelectedItem() == null
                    || (String) orders_SuplierName.getSelectionModel().getSelectedItem() == null
                    || totalPData == 0) {
                alert.errorMessage("Vui long dien day du cac truowng du lieu!");

            } else {

                prepare = connect.prepareStatement(sql);

                prepare.setString(1, String.valueOf(OrderId));
                prepare.setString(2, (String) orders_ProductName.getSelectionModel().getSelectedItem());
                prepare.setString(3, String.valueOf(qty));
                prepare.setString(4, (String) orders_ProductType.getSelectionModel().getSelectedItem());

                prepare.setString(5, String.valueOf(totalPData));
                prepare.setString(6, (String) orders_SuplierName.getSelectionModel().getSelectedItem());
                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                prepare.setString(7, String.valueOf(sqlDate));
                prepare.executeUpdate();
                ordersShowListData();
                ordersDisplayTotal();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //
    public void ordersReset() {
        orderId();
        String sql = "DELETE FROM order1 WHERE order_id =  '" + OrderId + "'  ";
        connect = database.connectDb();

        try {

            if (!orders_TableViews.getItems().isEmpty()) {
                Alert alert1 = new Alert(AlertType.CONFIRMATION);
                alert1.setTitle("Confirmation Message");
                alert1.setHeaderText(null);
                alert1.setContentText("Are you sure you want to reset?");
                Optional<ButtonType> option = alert1.showAndWait();
                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();

                    statement.executeUpdate(sql);
                    ordersShowListData();
                    totalP = 0;
                    balanceP = 0;
                    amountP = 0;
                    orders_Balance.setText("$0.0");
                    orders_Total.setText("$0.0");
                    orders_Amount.setText("");

                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void ordersPay() {
        orderId();
        String sql = "INSERT INTO customerreceipt (order_id, total, amount, balance, date) "
                + "VALUES (?, ?, ?, ?, ?)";

        connect = database.connectDb();

        try {

            if (totalP > 0 || orders_Amount.getText().isEmpty()) {
                Alert alert;
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("cofirm");
                alert.setHeaderText(null);
                alert.setContentText("BAN CO MUON?");
                Optional<ButtonType> option = alert.showAndWait();
                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);

                    prepare.setString(1, String.valueOf(OrderId));
                    prepare.setString(2, String.valueOf(totalP));
                    prepare.setString(3, String.valueOf(amountP));
                    prepare.setString(4, String.valueOf(balanceP));
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(5, String.valueOf(sqlDate));
                    prepare.executeUpdate();
                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("cofirm");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfull!");
                    alert.showAndWait();
                    //  ordersShowListData();

                    totalP = 0;
                    balanceP = 0;
                    amountP = 0;
                    orders_Balance.setText("$0.0");

                    orders_Amount.setText("");

                } else {
                    return;
                }

            } else {
                Alert alert;
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("error");
                alert.setContentText("loi");
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private double amountP;
    private double balanceP;

    public void ordersAmount() {

        Alert alert;

        if (!orders_Amount.getText().isEmpty()) {
            amountP = Double.parseDouble(orders_Amount.getText());
            if (totalP > 0) {
                if (amountP >= totalP) {
                    balanceP = (amountP - totalP);
                    orders_Balance.setText("$" + String.valueOf(balanceP));
                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("ERROR  - AMOUNT");
                    alert.setHeaderText("LỖI TẠI AI?");
                    alert.setContentText("Lỗi - Vui lòng nhập Amount >= Balance");
                    alert.showAndWait();

                    orders_Amount.setText("");

                }
            } else {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("error");
                alert.setContentText("Lỗi - Không có giá  hay sao í ? ");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        } else {
            alert = new Alert(AlertType.ERROR);

            alert.setTitle("error");
            alert.setContentText("Lỗi - Amount chua nhap ? ");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    private double totalP;

    public void ordersDisplayTotal() {
        orderId();
        String sql = "SELECT SUM(price) FROM order1 WHERE order_id = '" + OrderId + "'";
        connect = database.connectDb();
        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            while (result.next()) {
                totalP = result.getDouble("SUM(price)");
            }
            orders_Total.setText("$" + String.valueOf(totalP));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void orderReceipt() {

        HashMap hash = new HashMap();
        hash.put("inventory5", OrderId);

        try {

            JasperDesign jDesign = JRXmlLoader.load("C:\\Users\\Admin\\Documents\\NetBeansProjects\\invetoryManagermentSystem_Phat\\src\\_Print\\report2.jrxml");
            JasperReport jReport = JasperCompileManager.compileReport(jDesign);
            JasperPrint jPrint = JasperFillManager.fillReport(jReport, hash, connect);

            JasperViewer.viewReport(jPrint, false);
            ordersShowListData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
       
        @Override
    public void initialize(URL location, ResourceBundle resources) {
        ordersListType();
       
         ordersListType();
        ordersShowListData();
        ordersListSuplierName();
        ordersSpinner();
    }
}
