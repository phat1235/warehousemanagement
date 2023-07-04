/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import alert.alertMessage;
import database.database;
import database.getData;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import Model.productData;
import Model.userData;
import java.util.regex.Pattern;

import checkData.checkDataInput;
import static java.util.Locale.filter;

/**
 *
 * @author Admin
 */
public class productController   implements Initializable{
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
    private AnchorPane main_form;

     //DATABASE TOOLS
    private  Connection connect;
    private   PreparedStatement prepare;
    private   Statement statement;
    private   ResultSet result;
    private Image image;
    //------------------------------------------------------------------------------------

    //
    alertMessage alert = new alertMessage();
    checkDataInput checkdata = new checkDataInput();

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
    String sql = "INSERT INTO product (product_id, product_name, product_type, product_quantity, product_supliername, product_price, product_date, product_image) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

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

        } else if (!checkdata.containsNonNumericCharacters(addProduct_productID.getText())) {
            alert.errorMessage("Vui lòng nhập ID chỉ có chữ số!");
        } else if (Integer.parseInt(addProduct_productID.getText()) <= 0 || Integer.parseInt(addProduct_productID.getText()) > 10000000) {
            alert.errorMessage("Vui lòng nhập ID > 0 -  < 1000000");
        } else if (!checkdata.isVietnameseAlphabet(addProduct_productName.getText())) {
            alert.errorMessage("Vui lòng chỉ Tên là chữ cái");
        } else if (!checkdata.containsNonNumericCharacters(addProduct_productQuantity.getText())) {
            alert.errorMessage("Vui lòng nhập Quantity chỉ có chữ số!");
        } else if (Integer.parseInt(addProduct_productQuantity.getText()) <= 0 || Integer.parseInt(addProduct_productQuantity.getText()) > 10000000) {
            alert.errorMessage("Vui lòng nhập Quantity > 0 -  < 1000000");
        } else if (!checkdata.isVietnameseAlphabet(addProduct_productSuplierName.getText())) {
            alert.errorMessage("Vui lòng chỉ SupplierName là chữ cái");
        } else if (!checkdata.containsNonNumericCharacters(addProduct_productPrice.getText())) {
            alert.errorMessage("Vui lòng nhập Price chỉ có chữ số!");
        } else if (Integer.parseInt(addProduct_productPrice.getText()) <= 0 || Integer.parseInt(addProduct_productPrice.getText()) > 10000000) {
            alert.errorMessage("Vui lòng nhập Price > 0 -  < 1000000");
        } else {
            //CHECK ID PRO IN DATABASE
            String checkData = "SELECT product_id FROM product WHERE product_id = '" + addProduct_productID.getText() + "'";
            statement = connect.createStatement();
            result = statement.executeQuery(checkData);

            if (result.next()) {
                alert.errorMessage("PRODUCT ID: " + addProduct_productID.getText() + " Đã tồn tại!");
            } else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, addProduct_productID.getText());
                prepare.setString(2, checkdata.capitalizeFirstLetter(addProduct_productName.getText()));
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
        // Xử lý ngoại lệ ở đây (hoặc báo cáo lỗi)
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
                
                } else {
                    
                    return false;
                }

            });
        });

        SortedList<productData> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(addProduct_productTableView.comparatorProperty());
        addProduct_productTableView.setItems(sortList);

    }

    //
    
  //  Alert alert = new alertMessage();
   public void addProductsUpdate() {
    String uri = getData.path;
    uri = uri.replace("\\", "\\\\");

    Date date = new Date();
    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

    String sql = "UPDATE product SET product_name = '"
            + checkdata.capitalizeFirstLetter(addProduct_productName.getText())
            + "', product_type ='" + addProduct_productType.getSelectionModel().getSelectedItem()
            + "', product_quantity ='" + (addProduct_productQuantity.getText())
            + "', product_supliername ='" + checkdata.capitalizeFirstLetter(addProduct_productSuplierName.getText())
            + "', product_price = '" + (addProduct_productPrice.getText())
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
            alert.errorMessage("Vui lòng điền đầy đủ các trường dữ liệu!");
        } else if (!checkdata.containsNonNumericCharacters(addProduct_productID.getText())) {
             alert.errorMessage("Vui lòng nhập ID chỉ có chữ số!!");
        } else if (Integer.parseInt(addProduct_productID.getText()) <= 0) {
        
           alert.errorMessage("\"Vui lòng nhập ID > 0");
        } else {
            alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("CONFIRMATION MESSAGE");
            alert1.setHeaderText(null);
            alert1.setContentText("Bạn có muốn cập nhật sản phẩm có ID: " + addProduct_productID.getText() + "?");

            Optional<ButtonType> option = alert1.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                statement = connect.createStatement();
                statement.executeUpdate(sql);
                alert.successMessage("Cập nhật thành công");
 
                addProductsShowListData();
                addProductsReset();
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    //
    //DELETE
    public void addProductsDelete() {
    String productId = addProduct_productID.getText();

    if (productId.isEmpty()) {
        alert.errorMessage("Vui lòng nhập ID sản phẩm để xóa");
        return;
    }

    String sql = "DELETE FROM product WHERE product_id = '" + productId + "'";

    connect = database.connectDb();
    try {
        statement = connect.createStatement();

        // Kiểm tra xem sản phẩm có tồn tại trong cơ sở dữ liệu hay không
        String checkData = "SELECT product_id FROM product WHERE product_id = '" + productId + "'";
        ResultSet result = statement.executeQuery(checkData);

        if (result.next()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CONFIRMATION MESSAGE");
            alert.setHeaderText(null);
            alert.setContentText("ARE YOU WANT TO DELETE PRODUCT ID: " + productId + "?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                statement.executeUpdate(sql);

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("INFORMATION MESSAGE");
                successAlert.setHeaderText("XXX");
                successAlert.setContentText("SUCCESSFULLY DELETE");
                successAlert.showAndWait();

                addProductsShowListData();
                addProductsReset();
            }
        } else {
            alert.errorMessage("Không tìm thấy Mã sản phẩm: " + productId);
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
    
    
    
        @Override
    public void initialize(URL location, ResourceBundle resources) {
        addProductsShowListData();
        addProductsSelect1();
        addProductsListType();
         addProductsSearch();

    }
    
}
