package invetorymanagermentsystem_phat;

import Model.customerData;
import database.database;
import database.getData;
import Model.productData;
import Model.orderData;
import Model.userData;
import com.mysql.cj.xdevapi.Result;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//
import alert.alertMessage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import javafx.scene.chart.XYChart;
import javafx.scene.control.SpinnerValueFactory;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import Model.employeeData;
import Model.suplierData;

/**
 *
 * @author Admin
 */
public class dashboardController implements Initializable {

    @FXML
    private Button addProduct_addBtn;

    @FXML
    private TableColumn<productData, String> addProduct_col_ProductID;

    @FXML
    private TableColumn<productData, String> addProduct_col_ProductName;

    @FXML
    private TableColumn<productData, String> addProduct_col_ProductPrice;

    @FXML
    private TableColumn<productData, String> addProduct_col_ProductQuantity;

    @FXML
    private TableColumn<productData, String> addProduct_col_ProductSuplierName;

    @FXML
    private TableColumn<productData, String> addProduct_col_ProductType;

    @FXML
    private Button addProduct_deleteBtn;

    @FXML
    private Button addProduct_imageBtn;

    @FXML
    private TextField addProduct_productID;

    @FXML
    private TextField addProduct_productName;

    @FXML
    private TextField addProduct_productPrice;

    @FXML
    private TextField addProduct_productQuantity;

    @FXML
    private TextField addProduct_productSearch;

    @FXML
    private TextField addProduct_productSuplierName;

    @FXML
    private TableView<productData> addProduct_productTableView;

    @FXML
    private ComboBox<?> addProduct_productType;
    @FXML
    private TextField addProduct_productType1;

    @FXML
    private Button addProduct_resetBtn;

    @FXML
    private Button addProduct_updateBtn;

    @FXML
    private AnchorPane addProducts_Form;

    @FXML
    private ImageView addProducts_imageView;

    @FXML
    private Button close;

    @FXML
    private Button customer_Btn;
    @FXML
    private Button revenue_Btn;

    @FXML
    private Button customer_addBtn;

    @FXML
    private TextField customer_address;

    @FXML
    private TextField customer_id;

    @FXML
    private TableColumn<customerData, String> customer_col_address;
    @FXML
    private TableColumn<customerData, String> customer_col_id;
    @FXML
    private TableColumn<customerData, String> customer_col_fullname;

    @FXML
    private TableColumn<customerData, String> customer_col_phonenumber;

    @FXML
    private Button customer_deleteBtn;

    @FXML
    private TextField customer_fullname;

    @FXML
    private TextField customer_phonenumber;

    @FXML
    private TextField customer_search;

    @FXML
    private TextField customer_sex;

    @FXML
    private TableView<customerData> customer_tableView;

    @FXML
    private TableView<userData> customer_tableView1;

    @FXML
    private Button customer_updateBtn;

    @FXML
    private AnchorPane customers_Form;

    @FXML
    private Button employee_Btn;

    @FXML
    private Button employee_Btn1;

    @FXML
    private Button employee_addBtn;

    @FXML
    private TextField employee_address;

    @FXML
    private TextField employee_age;

    @FXML
    private TableColumn<employeeData, String> employee_col_address;

    @FXML
    private TableColumn<employeeData, String> employee_col_age;

    @FXML
    private TableColumn<employeeData, String> employee_col_date;

    @FXML
    private TableColumn<employeeData, String> employee_col_fullname;

    @FXML
    private TableColumn<employeeData, String> employee_col_phonenumber;

    @FXML
    private TableColumn<employeeData, String> employee_col_sex;
    @FXML
    private TableColumn<employeeData, String> employee_col_salary;

    @FXML
    private TableColumn<employeeData, String> employee_col_id;
    @FXML
    private Button employee_deleteBtn;

    @FXML
    private TextField employee_fullname;
    @FXML
    private TextField employee_salary;
    @FXML
    private TextField employee_date;
    @FXML
    private TextField employee_id;

    @FXML
    private TextField employee_phonenumber;

    @FXML
    private TextField employee_search;

    @FXML
    private TextField employee_sex;

    @FXML
    private TableView<employeeData> employee_tabaleView;

    @FXML
    private Button employee_updateBtn;

    @FXML
    private AnchorPane employees_form;

    @FXML
    private Label home_AvalableProduct;

    @FXML
    private Button home_Btn;

    @FXML
    private AreaChart<?, ?> home_IncomeChart;

    @FXML
    private Label home_NumberOrder;

    @FXML
    private BarChart<?, ?> home_OrderChart;

    @FXML
    private Label home_TotalIncome;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Button logout_Btn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minimize;

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

    @FXML
    private Button product_Btn;

    @FXML
    private Button supplier_addBtn;

    @FXML
    private TextField supplier_address;

    @FXML
    private TextField supplier_code;

    @FXML
    private TableColumn<suplierData, String> supplier_col_address;

    @FXML
    private TableColumn<suplierData, String> supplier_col_code;
    @FXML
    private TableColumn<suplierData, String> supplier_col_name;

    @FXML
    private TableColumn<suplierData, String> supplier_col_phonenumber;

    @FXML
    private Button supplier_deleteBtn;

    @FXML
    private TextField supplier_fullname;

    @FXML
    private TextField supplier_phonenumber;

    @FXML
    private TableView<suplierData> supplier_tableview;

    @FXML
    private Button supplier_updateBtn;

    @FXML
    private AnchorPane suppliers_form;

    @FXML
    private TextField suppliers_search;

    @FXML
    private Button user_Btn;

    @FXML
    private Button user_add_Btn;

    @FXML
    private TableColumn<userData, String> user_col_date;

    @FXML
    private TableColumn<userData, String> user_col_fullname;

    @FXML
    private TableColumn<userData, String> user_col_password;

    @FXML
    private TableColumn<userData, String> user_col_phonenumber;

    @FXML
    private TableColumn<userData, String> user_col_id;

    @FXML
    private TableColumn<userData, String> user_col_username;

    @FXML
    private Button user_deleteBtn;

    @FXML
    private TextField user_face_fullname;

    @FXML
    private TextField user_face_password;

    @FXML
    private TextField user_face_phonenumber;
    @FXML
    private TextField user_face_id;

    @FXML
    private TextField user_face_username;

    @FXML
    private TextField user_search;

    @FXML
    private Button user_updateBtn;

    @FXML
    private Label username;

    @FXML
    private AnchorPane users_form;
    @FXML
    private ComboBox<?> revenue_income;
    @FXML
    private ComboBox<?> revenue_order;

    //DATABASE TOOLS
    private  Connection connect;
    private   PreparedStatement prepare;
    private   Statement statement;
    private   ResultSet result;
    private Image image;
    //------------------------------------------------------------------------------------

    //
    alertMessage alert = new alertMessage();

    public void addProductsImportImage() {
        FileChooser open = new FileChooser();

        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*jpg", "*png"));
        File file = open.showOpenDialog(main_form.getScene().getWindow());
        if (file != null) {
            getData.path = file.getAbsolutePath();

            image = new Image(file.toURI().toString(), 100, 128, false, true);
            addProducts_imageView.setImage(image);
        }
    }

    public void addProductsAdd() {
        String sql = "INSERT INTO product (product_id, product_name, product_type, product_quantity, product_supliername, product_price, product_date, product_image)"
                + "VALUE(?,?,?,?,?,?,?,?)";

        connect = database.connectDb();
        try {

            if (addProduct_productID.getText().isEmpty()
                    || addProduct_productName.getText().isEmpty()
                    || addProduct_productType.getSelectionModel().getSelectedItem() == null
                    || addProduct_productQuantity.getText().isEmpty()
                    || addProduct_productSuplierName.getText().isEmpty()
                    || addProduct_productPrice.getText().isEmpty()
                    || "null".equals(getData.path)) {

                alert.errorMessage("Điền tất cả các trường dữ liệu");

            }
            if (Integer.parseInt(addProduct_productID.getText()) < 0) {
                alert.errorMessage("Vui lòng nhập ID > 0");
            }
            if (Double.parseDouble(addProduct_productPrice.getText()) < 0) {
                alert.errorMessage("Vui lòng nhập Price > 0");
            } else {
                //CHECK ID PRO IN DATABASE
                String checkData = "SELECT product_id FROM product WHERE product_id = '"
                        + addProduct_productID.getText() + "'";
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {

                    alert.errorMessage("PRODUCT ID: " + addProduct_productID.getText() + " Đã tồn tại!");

                } else {

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, addProduct_productID.getText());
                    prepare.setString(2, addProduct_productName.getText());
                    prepare.setString(3, (String) addProduct_productType.getSelectionModel().getSelectedItem());
                    prepare.setString(4, addProduct_productQuantity.getText());
                    prepare.setString(5, addProduct_productSuplierName.getText());
                    prepare.setString(6, addProduct_productPrice.getText());

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(7, String.valueOf(sqlDate));

                    String uri = getData.path;
                    uri = uri.replace("\\", "\\\\");
                    prepare.setString(8, uri);

                    prepare.executeUpdate();

                    addProductsShowListData();
                    addProductsReset();
                }
            }
        } catch (NumberFormatException | SQLException e) {
        }

    }
    
    

    //
    public void addProductsSearch() {
        FilteredList<productData> filter = new FilteredList<>(addProductList, e -> true);

        addProduct_productSearch.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(predicateProductData -> {
                if (newValue == null) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (predicateProductData.getProductName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateProductData.getProductType().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateProductData.getProductSuplierName().toLowerCase().contains(searchKey)) {
                    return true;

                } else {
                    alert.erorSyntax("Thông Tin Không Có Trong Cơ Sở Dữ Liệu!");
                    return false;
                }

            });
        });

        SortedList<productData> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(addProduct_productTableView.comparatorProperty());
        addProduct_productTableView.setItems(sortList);

    }

    //
    public void addProductsUpdate() {

        String uri = getData.path;
        uri = uri.replace("\\", "\\\\");

        Date date = new Date();

        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "UPDATE product SET product_name = '"
                + addProduct_productName.getText()
                + "', product_type ='" + addProduct_productType.getSelectionModel().getSelectedItem()
                + "', product_quantity ='" + addProduct_productQuantity.getText()
                + "', product_supliername ='" + addProduct_productSuplierName.getText()
                + "', product_price = '" + addProduct_productPrice.getText()
                + "', product_date = '" + sqlDate
                + "', product_image  = '" + uri + "' WHERE product_id = '"
                + addProduct_productID.getText() + "'";
        connect = database.connectDb();
        try {
            Alert alert1;
            if (addProduct_productID.getText().isEmpty()
                    || addProduct_productName.getText().isEmpty()
                    || addProduct_productType.getSelectionModel().getSelectedItem() == null
                    || addProduct_productQuantity.getText().isEmpty()
                    || addProduct_productSuplierName.getText().isEmpty()
                    || addProduct_productPrice.getText().isEmpty()
                    || getData.path == "null") {
                alert1 = new Alert(AlertType.ERROR);
                alert1.setTitle("ERROR MESSAGE");
                alert1.setHeaderText("XXX");
                alert1.setContentText("FILL ALL BANK FIELDS");
                alert1.showAndWait();

            } else {
                alert1 = new Alert(AlertType.CONFIRMATION);
                alert1.setTitle("CONFIRMATION MESSAGE");
                alert1.setHeaderText(null);
                alert1.setContentText("ARE YOU WANT TO UPDATE PRODUCT ID: " + addProduct_productID.getText() + "?");

                Optional<ButtonType> option = alert1.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert1 = new Alert(AlertType.INFORMATION);
                    alert1.setTitle("INFORMATION MESSAGE");
                    alert1.setHeaderText("XXX");
                    alert1.setContentText("SUCCESSFLLY UPDATE");
                    alert1.showAndWait();

                    addProductsShowListData();

                    addProductsReset();

                }
            }
        } catch (SQLException e) {
        }

    }

    //
    //DELETE
    public void addProductsDelete() {
        String sql = "DELETE FROM product WHERE product_id = '" + addProduct_productID.getText() + "'";

        connect = database.connectDb();
        try {
            Alert alert;
            if (addProduct_productID.getText().isEmpty()
                    || addProduct_productName.getText().isEmpty()
                    || addProduct_productQuantity.getText().isEmpty()
                    || addProduct_productSuplierName.getText().isEmpty()
                    || addProduct_productPrice.getText().isEmpty()
                    || getData.path == "null") {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR MESSAGE");
                alert.setHeaderText("XXX");
                alert.setContentText("FILL ALL BANK FIELDS");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("CONFIRMATION MESSAGE");
                alert.setHeaderText(null);
                alert.setContentText("ARE YOU WANT TO DELETE PRODUCT ID: " + addProduct_productID.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("INFORMATION MESSAGE");
                    alert.setHeaderText("XXX");
                    alert.setContentText("SUCCESSFLLY DELETE");
                    alert.showAndWait();

                    addProductsShowListData();

                    addProductsReset();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //
    public void addProductsReset() {
        addProduct_productID.setText("");
        addProduct_productName.setText("");
        addProduct_productType.getSelectionModel().clearSelection();
        addProduct_productQuantity.setText("");
        addProduct_productSuplierName.setText("");
        addProduct_productPrice.setText("");
        addProducts_imageView.setImage(null);
        getData.path = "";
    }

    public ObservableList<productData> addProductsListData() {
        ObservableList<productData> productList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM product";
        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            productData prodD;

            while (result.next()) {
                prodD = new productData(result.getInt("product_id"),
                        result.getString("product_name"),
                        result.getString("product_type"),
                        result.getInt("product_quantity"),
                        result.getString("product_supliername"),
                        result.getDouble("product_price"),
                        result.getDate("product_date"),
                        result.getString("product_image"));

                productList.add(prodD);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return productList;
    }

    //ADD PRODUCTLIST SHOW IN TABLE;
    private ObservableList<productData> addProductList;
    private final String[] listType = {"Food", "Snack", "Drink"};

    public void addProductsListType() {
        List<String> listT = new ArrayList<>();
        for (String data : listType) {
            listT.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listT);
        addProduct_productType.setItems(listData);

    }

    ///
    ///
    ///
    public void addProductsShowListData() {
        addProductList = addProductsListData();
        addProduct_col_ProductID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        addProduct_col_ProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        addProduct_col_ProductType.setCellValueFactory(new PropertyValueFactory<>("productType"));
        addProduct_col_ProductQuantity.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        addProduct_col_ProductSuplierName.setCellValueFactory(new PropertyValueFactory<>("productSuplierName"));
        addProduct_col_ProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        addProduct_productTableView.setItems(addProductList);
    }

    //
    //
    public void addProductsSelect1() {
        addProduct_productType.setVisible(true);
        addProduct_productType1.setVisible(false);
    }

    public void addProductsSelect() {
        productData prodD = addProduct_productTableView.getSelectionModel().getSelectedItem();
        int num = addProduct_productTableView.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            return;
        } else {
            addProduct_productID.setText(String.valueOf(prodD.getProductId()));
            addProduct_productName.setText(prodD.getProductName());
            addProduct_productQuantity.setText(String.valueOf(prodD.getProductQuantity()));
            addProduct_productSuplierName.setText(prodD.getProductSuplierName());
            addProduct_productPrice.setText(String.valueOf(prodD.getPrice()));
            addProduct_productType.setVisible(false);
            addProduct_productType1.setVisible(true);
            addProduct_productType1.setText(prodD.getProductType());
            String uri = "file:" + prodD.getProductImage();

            image = new Image(uri, 100, 128, false, true);
            addProducts_imageView.setImage(image);

            getData.path = prodD.getProductImage();
        }
    }
    
    
    


    //USER_FROMMMMMMMMMMMMMMMMM--------------------------------------START--------
    //USER_FROMMMMMMMMMMMMMMMMM--------------------------------------START-------------------
   
//    public void addUsersAdd() {
//        String sql = "INSERT INTO users (fullname, username, password, phonenumber, id)"
//                + "VALUE(?,?,?,?,?)";
//
//        connect = database.connectDb();
//
//        try {
//            Alert alert;
//            if (user_face_fullname.getText().isEmpty()
//                    || user_face_username.getText().isEmpty()
//                    || user_face_password.getText().isEmpty()
//                    || user_face_phonenumber.getText().isEmpty()) {
//                alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("ERROR MESSAGE 1");
//                alert.setHeaderText("XXX");
//                alert.setContentText("FILL ALL BANK FIELDS");
//                alert.showAndWait();
//            } else {
//                //CHECK ID PRO IN DATABASE
//                String checkData = "SELECT username FROM users WHERE id = '"
//                        + user_face_id.getText() + "'";
//                statement = connect.createStatement();
//                result = statement.executeQuery(checkData);
//
//                if (result.next()) {
//                    alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("ERROR MESSAGE");
//                    alert.setHeaderText("XXX");
//                    alert.setContentText("PRODUCT ID: " + user_face_id.getText() + " WAS ALREADY EXIST!");
//                    alert.showAndWait();
//
//                } else {
//                    prepare = connect.prepareStatement(sql);
//                    prepare.setString(1, user_face_fullname.getText());
//                    prepare.setString(2, user_face_username.getText());
//                    prepare.setString(3, user_face_password.getText());
//
//                    prepare.setString(4, user_face_phonenumber.getText());
//                    prepare.setString(5, user_face_id.getText());
//
//                    prepare.executeUpdate();
//
//                    addUsersShowListData();
//
//                    addUsersReset();
//                    //  addProductsReset();
//
//                    //   addProductsReset();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
   
//
//    public ObservableList<userData> addUsersListData() {
//        ObservableList<userData> userList = FXCollections.observableArrayList();
//        String sql = "SELECT * FROM users";
//        connect = database.connectDb();
//
//        try {
//            prepare = connect.prepareStatement(sql);
//            result = prepare.executeQuery();
//            userData userD;
//
//            while (result.next()) {
//                userD = new userData(result.getString("fullname"),
//                        result.getString("username"),
//                        result.getString("password"),
//                        result.getString("phonenumber"),
//                        result.getInt("id"));
//                userList.add(userD);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//        return userList;
//    }
//
//    //
//    private ObservableList<userData> addUsersList;
//
  
//    public void addUsersShowListData() {
//        addUsersList = addUsersListData();
//        user_col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
//        user_col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
//        user_col_fullname.setCellValueFactory(new PropertyValueFactory<>("fullname"));
//        //      user_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
//        user_col_phonenumber.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
//        user_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
//
//        customer_tableView1.setItems(addUsersList);
//    }
//
   
//    public void userSelect() {
//        userData userD = customer_tableView1.getSelectionModel().getSelectedItem();
//        int num = customer_tableView1.getSelectionModel().getSelectedIndex();
//        if ((num - 1) < -1) {
//            return;
//        } else {
//            user_face_fullname.setText(userD.getFullname());
//            user_face_username.setText(userD.getUsername());
//            user_face_password.setText(userD.getPassword());
//            user_face_phonenumber.setText(userD.getPhonenumber());
//            user_face_id.setText(String.valueOf(userD.getId()));
//        }
//    }
//    //
//
     
//    public void addUsersUpdate() {
//
//        String sql = "UPDATE users SET fullname =  '"
//                + user_face_fullname.getText()
//                + "', username  = '" + user_face_username.getText()
//                + "', password  = '" + user_face_password.getText()
//                + "', phonenumber  = '" + user_face_phonenumber.getText()
//                + "' WHERE id = '"
//                + user_face_id.getText() + "'";
//        connect = database.connectDb();
//
//        //
//        try {
//            Alert alert;
//            if (user_face_username.getText().isEmpty()
//                    || user_face_password.getText().isEmpty()
//                    || user_face_fullname.getText().isEmpty()
//                    || user_face_phonenumber.getText().isEmpty()) {
//                alert = new Alert(AlertType.ERROR);
//                alert.setTitle("ERROR MESSAGE");
//                alert.setHeaderText("XXXXX-ORDER 1");
//                alert.setContentText("FILL ALL BANK FIELDS");
//                alert.showAndWait();
//
//            } else {
//                alert = new Alert(AlertType.CONFIRMATION);
//                alert.setTitle("CONFIRMATION MESSAGE");
//                alert.setHeaderText(null);
//                alert.setContentText("ARE YOU WANT TO UPDATE PRODUCT ID: " + user_face_id.getText() + "?");
//
//                Optional<ButtonType> option = alert.showAndWait();
//
//                if (option.get().equals(ButtonType.OK)) {
//                    statement = connect.createStatement();
//                    statement.executeUpdate(sql);
//
//                    alert = new Alert(AlertType.INFORMATION);
//                    alert.setTitle("INFORMATION MESSAGE");
//                    alert.setHeaderText("XXX");
//                    alert.setContentText("SUCCESSFLLY UPDATE");
//                    alert.showAndWait();
//
//                    addUsersShowListData();
//
//                    addUsersReset();
//
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    //
//    //DELETE
    
//    public void addUsersDelete() {
//        String sql = "DELETE FROM users WHERE username = '" + user_face_username.getText() + "'";
//
//        connect = database.connectDb();
//        try {
//            Alert alert;
//            if (user_face_username.getText().isEmpty()
//                    || user_face_password.getText().isEmpty()
//                    || user_face_fullname.getText().isEmpty()
//                    || user_face_phonenumber.getText().isEmpty()) {
//                alert = new Alert(AlertType.ERROR);
//                alert.setTitle("ERROR MESSAGE");
//                alert.setHeaderText("XXXXX-ORDER");
//                alert.setContentText("FILL ALL BANK FIELDS");
//                alert.showAndWait();
//
//            } else {
//                alert = new Alert(AlertType.CONFIRMATION);
//                alert.setTitle("CONFIRMATION MESSAGE");
//                alert.setHeaderText(null);
//                alert.setContentText("ARE YOU WANT TO DELETE PRODUCT ID: " + user_face_username.getText() + "?");
//
//                Optional<ButtonType> option = alert.showAndWait();
//
//                if (option.get().equals(ButtonType.OK)) {
//                    statement = connect.createStatement();
//                    statement.executeUpdate(sql);
//
//                    alert = new Alert(AlertType.INFORMATION);
//                    alert.setTitle("INFORMATION MESSAGE");
//                    alert.setHeaderText("XXX");
//                    alert.setContentText("SUCCESSFLLY DELETE");
//                    alert.showAndWait();
//
//                    addUsersShowListData();
//
//                    addUsersReset();
//                }
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //
       
//    public void addUsersReset() {
//        user_face_username.setText("");
//        user_face_fullname.setText("");
//        user_face_password.setText("");
//        user_face_phonenumber.setText("");
//        user_face_id.setText("");
//    }
//
      
//    public void userSearch() {
//        FilteredList<userData> filter = new FilteredList<>(addUsersList, e -> true);
//
//        user_search.textProperty().addListener((Observable, oldValue, newValue) -> {
//
//            filter.setPredicate(predicateUserData -> {
//                if (newValue == null) {
//                    return false;
//                }
//
//                String searchKey = newValue.toLowerCase();
//                if (predicateUserData.getFullname().toLowerCase().contains(searchKey)) {
//                    return true;
//                } else if (predicateUserData.getUsername().toLowerCase().contains(searchKey)) {
//                    return true;
//
//                } else {
//                    return false;
//                }
//            });
//        });
//
//        SortedList<userData> sortList = new SortedList<>(filter);
//
//        sortList.comparatorProperty().bind(customer_tableView1.comparatorProperty());
//        customer_tableView1.setItems(sortList);
//
//    }

    //USER_FROMMMMMMMMMMMMMMMMM-------------------------------------END
    //Suplier
    //
    public ObservableList<suplierData> adduUsersListData() {
        ObservableList<suplierData> suplist = FXCollections.observableArrayList();
        String sql = "SELECT * FROM suplier";
        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            suplierData userD;

            while (result.next()) {
                userD = new suplierData(result.getInt("id"),
                        result.getString("fullname"),
                        result.getString("address"),
                        result.getString("phonenumber"));
                suplist.add(userD);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return suplist;
    }

    //suplier
    //ORDER_FUNCTION
    //
    //LOGOUT(); 
    private double x = 0;
    private double y = 0;

    //////////////////////////////////////////////////////
    public void logout() {
        try {// LINK LOGIN DE CHUYEN TOI
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("CONFIRMATION MESSAGE");
            alert.setHeaderText(null);
            alert.setContentText("ARE YOU WANT TO LOGOUT?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                // AN LOGIN DI KHI THANH CONG
                logout_Btn.getScene().getWindow().hide();
                //LINK LOGIN
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                // NHAP MOUSE VAO VA DI CHUYEN GIAO DIEN  X0Y,
                root.setOnMousePressed((event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });
                root.setOnMouseDragged((event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                    stage.setOpacity(0.8);
                });
                root.setOnMouseReleased((event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(scene);
                stage.show();

            } else {
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);

    }

    public void close() {
        System.exit(0);
    }
//-------------------------------------===============================================================================================
    //////////////////////////////////////////////////////

    public void customerAdd() {
        String sql = "INSERT INTO customer (fullname, address, phonenumber, id)"
                + "VALUE(?,?,?,?)";

        connect = database.connectDb();
        try {

            if (customer_fullname.getText().isEmpty()
                    || customer_address.getText().isEmpty()
                    || customer_phonenumber.getText().isEmpty()
                    || customer_id.getText().isEmpty()) {

                alert.errorMessage("Điền tất cả các trường dữ liệu");

            }
            if (Integer.parseInt(customer_id.getText()) < 0) {
                alert.errorMessage("Vui lòng nhập ID > 0");

            } else {
                //CHECK ID PRO IN DATABASE
                String checkData = "SELECT id FROM customer WHERE id = '"
                        + customer_id.getText() + "'";
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {

                    alert.errorMessage("PRODUCT ID: " + customer_id.getText() + " Đã tồn tại!");

                } else {

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, customer_fullname.getText());
                    prepare.setString(2, customer_address.getText());
                    prepare.setString(3, customer_phonenumber.getText());
                    prepare.setString(4, customer_id.getText());

                    prepare.executeUpdate();

                    customerShowListData();
                    customerReset();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //
    public void customerSearch() {
        FilteredList<customerData> filter = new FilteredList<>(customerList, e -> true);

        customer_search.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(predicateCustomerData -> {
                if (newValue == null) {
                    return true;
                }
                //  predicateCustomerData.getCustomerPhonenumber()
                String searchKey = newValue.toLowerCase();
                if (predicateCustomerData.getCustomerFullName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCustomerData.getCustomerAddress().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCustomerData.getCustomerPhonenumber().toLowerCase().contains(searchKey)) {
                    return true;

                } else {
                    return false;
                }
            });
        });

        SortedList<customerData> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(customer_tableView.comparatorProperty());
        customer_tableView.setItems(sortList);

    }
//

    //
    public void customerUpdate() {

        String sql = "UPDATE customer SET fullname = '"
                + customer_fullname.getText()
                + "', address ='" + customer_address.getText()
                + "', phonenumber ='" + customer_phonenumber.getText()
                + "' WHERE id = '"
                + customer_id.getText() + "'";

        connect = database.connectDb();
        try {
            // Alert alert;
            if (customer_fullname.getText().isEmpty()
                    || customer_address.getText().isEmpty()
                    || customer_phonenumber.getText().isEmpty()
                    || customer_id.getText().isEmpty()) {

                alert.errorMessage("Vui lòng điền đầy đủ các trường dữ liệu!");
            } else {
                alert.successMessage("ARE YOU WANT TO UPDATE PRODUCT ID: " + customer_id.getText() + "?");

                statement = connect.createStatement();
                statement.executeUpdate(sql);
                alert.successMessage("Cập nhật thành công!");

                customerShowListData();

                customerReset();

                //   }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //
    //DELETE
    public void customerDelete() {
        String sql = "DELETE FROM customer WHERE id = '" + customer_id.getText() + "'";

        connect = database.connectDb();
        try {
            Alert alert;
            if (customer_fullname.getText().isEmpty()
                    || customer_address.getText().isEmpty()
                    || customer_phonenumber.getText().isEmpty()
                    || customer_id.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR MESSAGE");
                alert.setHeaderText("XXX");
                alert.setContentText("FILL ALL BANK FIELDS");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("CONFIRMATION MESSAGE");
                alert.setHeaderText(null);
                alert.setContentText("ARE YOU WANT TO DELETE PRODUCT ID: " + customer_id.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("INFORMATION MESSAGE");
                    alert.setHeaderText("XXX");
                    alert.setContentText("SUCCESSFLLY DELETE");
                    alert.showAndWait();

                    customerShowListData();

                    customerReset();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //
    public void customerReset() {
        customer_fullname.setText("");
        customer_address.setText("");
        customer_phonenumber.setText("");
        customer_id.setText("");
    }

    public ObservableList<customerData> customerListData() {
        ObservableList<customerData> customerList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customer";
        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            customerData customerD;

            while (result.next()) {
                customerD = new customerData(result.getString("fullname"),
                        result.getString("address"),
                        result.getString("phonenumber"),
                        result.getInt("id"));

                customerList.add(customerD);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return customerList;
    }

    //ADD PRODUCTLIST SHOW IN TABLE;
    private ObservableList<customerData> customerList;

    ///
    ///
    ///
    public void customerShowListData() {
        customerList = customerListData();
        customer_col_fullname.setCellValueFactory(new PropertyValueFactory<>("customerFullName"));
        customer_col_address.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customer_col_phonenumber.setCellValueFactory(new PropertyValueFactory<>("customerPhonenumber"));
        customer_col_id.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        customer_tableView.setItems(customerList);
    }

    //
    public void customerSelect() {
        customerData customerD = customer_tableView.getSelectionModel().getSelectedItem();
        int num = customer_tableView.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            return;
        } else {

            customer_fullname.setText(customerD.getCustomerFullName());
            customer_address.setText(customerD.getCustomerAddress());
            customer_phonenumber.setText(customerD.getCustomerPhonenumber());
            customer_id.setText(String.valueOf(customerD.getCustomerId()));

        }
    }

    //Code supplier function
    //================================================================================
    public void addSupliersAdd() {
        String sql = "INSERT INTO suplier (suplier_id, suplier_fullname, suplier_address, suplier_phonenumber)"
                + "VALUE(?,?,?,?)";

        connect = database.connectDb();

        try {
            Alert alert1;
            if (supplier_code.getText().isEmpty()
                    || supplier_fullname.getText().isEmpty()
                    || supplier_address.getText().isEmpty()
                    || supplier_col_phonenumber.getText().isEmpty()) {
                alert.errorMessage("Vui lòng điền đầy đủ các trường dữ liệu!");
            } else {
                //CHECK ID PRO IN DATABASE
                String checkData = "SELECT suplier_id FROM suplier WHERE suplier_id = '"
                        + supplier_code.getText() + "'";
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert.errorMessage("SUPLIER ID: " + supplier_code.getText() + " WAS ALREADY EXIST!");

                } else {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, supplier_code.getText());
                    prepare.setString(2, supplier_fullname.getText());
                    prepare.setString(3, supplier_address.getText());

                    prepare.setString(4, supplier_phonenumber.getText());

                    prepare.executeUpdate();

                    addSupliersShowListData();

                    addSupliersReset();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //
    //
    public ObservableList<suplierData> addSupliersListData() {
        ObservableList<suplierData> suplierList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM suplier";
        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            suplierData suplierD;

            while (result.next()) {
                suplierD = new suplierData(result.getInt("suplier_id"),
                        result.getString("suplier_fullname"),
                        result.getString("suplier_address"),
                        result.getString("suplier_phonenumber"));
                suplierList.add(suplierD);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return suplierList;
    }
    private ObservableList<suplierData> addSupliersList;
    //
    // private ObservableList<suplierData> addSupliersList;

    public void addSupliersShowListData() {
        addSupliersList = addSupliersListData();
        supplier_col_code.setCellValueFactory(new PropertyValueFactory<>("id"));
        supplier_col_name.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        supplier_col_address.setCellValueFactory(new PropertyValueFactory<>("address"));

        supplier_col_phonenumber.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));

        supplier_tableview.setItems(addSupliersList);
    }

    public void suplierSelect() {
        suplierData suplierD = supplier_tableview.getSelectionModel().getSelectedItem();
        int num = supplier_tableview.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            return;
        } else {
            supplier_code.setText(String.valueOf(suplierD.getId()));
            supplier_fullname.setText(suplierD.getFullname());
            supplier_address.setText(suplierD.getAddress());
            supplier_phonenumber.setText(suplierD.getPhonenumber());
        }
    }
    //

    public void addSupliersUpdate() {

        String sql = "UPDATE suplier SET suplier_fullname =  '"
                + supplier_fullname.getText()
                + "',  suplier_address = '" + supplier_address.getText()
                + "', suplier_phonenumber  = '" + supplier_phonenumber.getText()
                + "' WHERE suplier_id = '"
                + supplier_code.getText() + "'";
        connect = database.connectDb();

        //
        try {
            Alert alert1;
            if (supplier_fullname.getText().isEmpty()
                    || supplier_address.getText().isEmpty()
                    || supplier_phonenumber.getText().isEmpty()
                    || supplier_code.getText().isEmpty()) {
                alert.errorMessage("Vui lòng điền tất cả các trường dữ liệu");

            } else {
                alert1 = new Alert(AlertType.CONFIRMATION);
                alert1.setTitle("CONFIRMATION MESSAGE");
                alert1.setHeaderText(null);
                alert1.setContentText("ARE YOU WANT TO UPDATE PRODUCT ID: " + supplier_code.getText() + "?");

                Optional<ButtonType> option = alert1.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert.successMessage("Cập nhật thành công!");

                    addSupliersShowListData();

                    addSupliersReset();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //
    //DELETE
    public void addSupliersDelete() {
        String sql = "DELETE FROM suplier WHERE suplier_id = '" + supplier_code.getText() + "'";

        connect = database.connectDb();
        try {
            Alert alert1;
            if (supplier_fullname.getText().isEmpty()
                    || supplier_address.getText().isEmpty()
                    || supplier_phonenumber.getText().isEmpty()
                    || supplier_code.getText().isEmpty()) {
                alert.errorMessage("Vui lòng điền đầy đủ tất cả các trường dữ liệu");

            } else {
                alert1 = new Alert(AlertType.CONFIRMATION);
                alert1.setTitle("CONFIRMATION MESSAGE");
                alert1.setHeaderText(null);
                alert1.setContentText("ARE YOU WANT TO UPDATE SUPPLIER ID: " + supplier_code.getText() + "?");

                Optional<ButtonType> option = alert1.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert.successMessage("Cập nhật SUPPLIER ID: " + supplier_code.getText() + " thành công!");

                    addSupliersShowListData();

                    addSupliersReset();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //
    public void addSupliersReset() {
        supplier_code.setText("");
        supplier_fullname.setText("");
        supplier_address.setText("");
        supplier_phonenumber.setText("");

    }

    public void supliersSearch() {
        FilteredList<suplierData> filter = new FilteredList<>(addSupliersList, e -> true);

        suppliers_search.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(predicateSuplierData -> {
                if (newValue == null) {
                    return false;
                }

                String searchKey = newValue.toLowerCase();
                if (predicateSuplierData.getFullname().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<suplierData> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(supplier_tableview.comparatorProperty());
        supplier_tableview.setItems(sortList);

    }

    //-------------------------------------------------
    //======================================================================
    public void addEmployeesAdd() {
        String sql = "INSERT INTO employee (employee_id, employee_fullname, employee_address, employee_sex,employee_age,employee_phonenumber,employee_salary, employee_date)"
                + "VALUE(?,?,?,?,?,?,?,?)";

        connect = database.connectDb();

        try {
            Alert alert1;
            if (employee_fullname.getText().isEmpty()
                    || employee_address.getText().isEmpty()
                    || employee_age.getText().isEmpty()
                    || employee_sex.getText().isEmpty()
                    || employee_phonenumber.getText().isEmpty()
                    || employee_id.getText().isEmpty()
                    || employee_salary.getText().isEmpty()) {
                alert.errorMessage("Vui lòng điền đầy đủ các trường dữ liệu!");
            } else {
                //CHECK ID PRO IN DATABASE
                String checkData = "SELECT employee_id FROM employee WHERE employee_id = '"
                        + employee_id.getText() + "'";
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert.errorMessage("EMPLOYEE ID: " + employee_id.getText() + " WAS ALREADY EXIST!");

                } else {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, employee_id.getText());
                    prepare.setString(2, employee_fullname.getText());
                    prepare.setString(3, employee_address.getText());

                    prepare.setString(4, employee_sex.getText());
                    prepare.setString(5, employee_age.getText());
                    prepare.setString(6, employee_phonenumber.getText());
                    prepare.setString(7, employee_salary.getText());
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(8, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    addEmployeesShowListData();

                    addEmployeesReset();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //
    //
    public ObservableList<employeeData> addEmployeesListData() {
        ObservableList<employeeData> employeeList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM employee";
        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            employeeData employeeD;

            while (result.next()) {
                employeeD = new employeeData(result.getInt("employee_id"),
                        result.getInt("employee_age"),
                        result.getString("employee_fullname"),
                        result.getString("employee_address"),
                        result.getString("employee_sex"),
                        result.getString("employee_phonenumber"),
                        result.getDouble("employee_salary"),
                        result.getDate("employee_date"));
                employeeList.add(employeeD);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return employeeList;
    }
    private ObservableList<employeeData> addEmployeesList;
    //
    // private ObservableList<suplierData> addSupliersList;

    public void addEmployeesShowListData() {
        addEmployeesList = addEmployeesListData();
        employee_col_fullname.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        employee_col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        employee_col_sex.setCellValueFactory(new PropertyValueFactory<>("sex"));

        employee_col_age.setCellValueFactory(new PropertyValueFactory<>("age"));
        employee_col_phonenumber.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        employee_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        employee_col_salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        employee_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        employee_tabaleView.setItems(addEmployeesList);
    }

    public void employeesSelect() {
        employeeData emphoyeeD = employee_tabaleView.getSelectionModel().getSelectedItem();
        int num = employee_tabaleView.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            return;
        } else {
            employee_id.setText(String.valueOf(emphoyeeD.getId()));
            employee_fullname.setText(emphoyeeD.getFullname());
            employee_sex.setText(emphoyeeD.getSex());
            employee_age.setText(String.valueOf(emphoyeeD.getAge()));
            employee_address.setText(emphoyeeD.getAddress());
            employee_salary.setText(String.valueOf(emphoyeeD.getSalary()));
            employee_phonenumber.setText(emphoyeeD.getPhonenumber());

        }
    }
    //

    public void addEmployeesUpdate() {

        String sql = "UPDATE employee SET employee_fullname =  '"
                + employee_fullname.getText()
                + "',  employee_address = '" + employee_address.getText()
                + "', employee_sex  = '" + employee_sex.getText()
                + "',  employee_age = '" + employee_age.getText()
                + "', employee_phonenumber  = '" + employee_phonenumber.getText()
                + "',  employee_salary = '" + employee_salary.getText()
                + "' WHERE employee_id = '"
                + employee_id.getText() + "'";
        connect = database.connectDb();

        //
        try {
            Alert alert1;
            if (employee_fullname.getText().isEmpty()
                    || employee_address.getText().isEmpty()
                    || employee_sex.getText().isEmpty()
                    || employee_age.getText().isEmpty()
                    || employee_phonenumber.getText().isEmpty()
                    || employee_salary.getText().isEmpty()
                    || employee_id.getText().isEmpty()) {
                alert.errorMessage("Vui lòng điền tất cả các trường dữ liệu");

            } else {
                alert1 = new Alert(AlertType.CONFIRMATION);
                alert1.setTitle("CONFIRMATION MESSAGE");
                alert1.setHeaderText(null);
                alert1.setContentText("ARE YOU WANT TO UPDATE employee ID: " + employee_id.getText() + "?");

                Optional<ButtonType> option = alert1.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert.successMessage("Cập nhật thành công!");

                    addEmployeesShowListData();

                    addEmployeesReset();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //
    //DELETE
    public void addEployeesDelete() {
        String sql = "DELETE FROM employee WHERE employee_id = '" + employee_id.getText() + "'";

        connect = database.connectDb();
        try {
            Alert alert1;
            if (employee_fullname.getText().isEmpty()
                    || employee_address.getText().isEmpty()
                    || employee_sex.getText().isEmpty()
                    || employee_age.getText().isEmpty()
                    || employee_phonenumber.getText().isEmpty()
                    || employee_salary.getText().isEmpty()
                    || employee_id.getText().isEmpty()) {
                alert.errorMessage("Vui lòng điền đầy đủ tất cả các trường dữ liệu");

            } else {
                alert1 = new Alert(AlertType.CONFIRMATION);
                alert1.setTitle("CONFIRMATION MESSAGE");
                alert1.setHeaderText(null);
                alert1.setContentText("ARE YOU WANT TO DELETE EMPLOYEE ID: " + employee_id.getText() + "?");

                Optional<ButtonType> option = alert1.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert.successMessage("DELETE EMPLOYEE ID: " + employee_id.getText() + " thành công!");

                    addEmployeesShowListData();

                    addEmployeesReset();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //
    public void addEmployeesReset() {

        employee_fullname.setText("");
        employee_address.setText("");
        employee_sex.setText("");
        employee_age.setText("");
        employee_phonenumber.setText("");
        employee_salary.setText("");
        employee_id.setText("");
    }

    public void employeesSearch() {
        FilteredList<employeeData> filter = new FilteredList<>(addEmployeesList, e -> true);

        employee_search.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(predicateEmployeeData -> {
                if (newValue == null) {
                    return false;
                }

                String searchKey = newValue.toLowerCase();
                if (predicateEmployeeData.getFullname().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<employeeData> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(employee_tabaleView.comparatorProperty());
        employee_tabaleView.setItems(sortList);

    }
//

    //ORDER
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
                + orders_SuplierName.getSelectionModel().getSelectedItem() + "' ";

        connect = database.connectDb();

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
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0);
        orders_Quantity.setValueFactory(spinner);
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

            JasperDesign jDesign = JRXmlLoader.load("C:\\Users\\Admin\\Documents\\NetBeansProjects\\invetoryManagermentSystem_Phat\\src\\invetorymanagermentsystem_phat\\report2.jrxml");
            JasperReport jReport = JasperCompileManager.compileReport(jDesign);
            JasperPrint jPrint = JasperFillManager.fillReport(jReport, hash, connect);

            JasperViewer.viewReport(jPrint, false);
            ordersShowListData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //
    //THONG KE DOANH THU THEO THANG
    public void homeDisplayTotalOrder() {
        String sql = "SELECT COUNT(id) FROM customerreceipt WHERE MONTH(DATE) =  '" + revenue_order.getSelectionModel().getSelectedItem() + "'";
        connect = database.connectDb();
        int countOrders = 0;

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                countOrders = result.getInt("COUNT(id)");
            }
            home_NumberOrder.setText(String.valueOf(countOrders));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void homeDisplayIncomeOrder() {
        String sql = "SELECT TOTAL FROM customerreceipt WHERE MONTH(DATE) =  '" + revenue_income.getSelectionModel().getSelectedItem() + "'";
        connect = database.connectDb();
        Double countOrdersS = 0.0;

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                countOrdersS += result.getDouble("TOTAL");
            }
            home_TotalIncome.setText(String.valueOf(countOrdersS));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void honeDisplayAvalProduct() {
        String sql = "SELECT COUNT(id) FROM product WHERE  product_quantity > 0";
        connect = database.connectDb();
        int countProducts = 0;

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                countProducts = result.getInt("COUNT(id)");
            }
            home_AvalableProduct.setText(String.valueOf(countProducts));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //
    public void homeIncomeChart() {
        home_IncomeChart.getData().clear();
        String sql = "SELECT DATE, SUM(TOTAL) FROM customerreceipt GROUP BY DATE ORDER BY TIMESTAMP(DATE) ASC LIMIT 6";
        connect = database.connectDb();
        try {
            XYChart.Series chart = new XYChart.Series();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }
            home_IncomeChart.getData().add(chart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void homeOrderChart() {
        home_OrderChart.getData().clear();
        String sql = "SELECT DATE, SUM(TOTAL) FROM customerreceipt GROUP BY DATE ORDER BY TIMESTAMP(DATE) ASC LIMIT 6";
        connect = database.connectDb();
        try {
            XYChart.Series chart = new XYChart.Series();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }
            home_OrderChart.getData().add(chart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //
    private final int[] listMonth = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    public void revenueOrder() {
        List<Integer> listM = new ArrayList<>();
        for (Integer data : listMonth) {
            listM.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listM);
        revenue_order.setItems(listData);
        revenue_income.setItems(listData);

    }

    public void displayUsername() {
//       username.setText(getData.username);
    }
// CHUYEN CANH

    public void switchForm(ActionEvent event) throws IOException {
        if (event.getSource() == home_Btn) {
            home_form.setVisible(true);
            addProducts_Form.setVisible(false);
            customers_Form.setVisible(false);
            employees_form.setVisible(false);
            suppliers_form.setVisible(false);
            orders_Form.setVisible(false);
            users_form.setVisible(false);
            revenueOrder();
            honeDisplayAvalProduct();
            homeOrderChart();
            homeIncomeChart();
        } else if (event.getSource() == product_Btn) {
            home_form.setVisible(false);
            addProducts_Form.setVisible(true);
            customers_Form.setVisible(false);
            employees_form.setVisible(false);
            suppliers_form.setVisible(false);
            orders_Form.setVisible(false);
            users_form.setVisible(false);
            addProductsShowListData();
            //TYPE
            addProductsListType();
            //
            addProductsSearch();

        } else if (event.getSource() == customer_Btn) {
            home_form.setVisible(false);
            addProducts_Form.setVisible(false);
            customers_Form.setVisible(true);
            employees_form.setVisible(false);
            suppliers_form.setVisible(false);
            orders_Form.setVisible(false);
            users_form.setVisible(false);
            // customerUpdate();
            customerShowListData();
            // customerSelect();

        } else if (event.getSource() == employee_Btn) {
            home_form.setVisible(false);
            addProducts_Form.setVisible(false);
            customers_Form.setVisible(false);
            employees_form.setVisible(true);
            addEmployeesShowListData();
            suppliers_form.setVisible(false);
            orders_Form.setVisible(false);
            users_form.setVisible(false);
            //     addUsersShowListData();
        } else if (event.getSource() == employee_Btn1) { //chua doi ten fx id
            home_form.setVisible(false);
            addProducts_Form.setVisible(false);
            customers_Form.setVisible(false);
            employees_form.setVisible(false);
            suppliers_form.setVisible(true);
            orders_Form.setVisible(false);
            users_form.setVisible(false);
            addSupliersShowListData();
        } else if (event.getSource() == order_Btn) {
            home_form.setVisible(false);
            addProducts_Form.setVisible(false);
            customers_Form.setVisible(false);
            employees_form.setVisible(false);
            suppliers_form.setVisible(false);
            orders_Form.setVisible(true);
            users_form.setVisible(false);
            ordersListType();
            //ordersShowListData();
            ordersListSuplierName();
            ordersSpinner();

        } else if (event.getSource() == user_Btn) {
            home_form.setVisible(false);
            addProducts_Form.setVisible(false);
            customers_Form.setVisible(false);
            employees_form.setVisible(false);
            suppliers_form.setVisible(false);
            orders_Form.setVisible(false);
            users_form.setVisible(true);
            //   addUsersUpdate();
           // addUsersShowListData();

        }

    }

    //========================================================================
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();
        customerShowListData();
        addProductsShowListData();
        addProductsListType();

        ordersListType();
        ordersShowListData();
        ordersListSuplierName();
        ordersSpinner();
        homeOrderChart();
        homeIncomeChart();
    }

}
