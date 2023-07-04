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
import Model.customerData;
import Model.productData;
import Model.userData;
import checkData.checkDataInput;

/**
 *
 * @author Admin
 */
public class customerController   implements Initializable{
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
public void customerAdd() throws SQLException {
    String sql = "INSERT INTO customer (fullname, address, phonenumber, id) VALUES (?, ?, ?, ?)";

    connect = database.connectDb();

    try {
        if (customer_fullname.getText().isEmpty()
                || customer_address.getText().isEmpty()
                || customer_phonenumber.getText().isEmpty()
                || customer_id.getText().isEmpty()) {
            alert.errorMessage("Vui lòng điền tất cả các trường dữ liệu");
        } else if (!checkdata.isVietnameseAlphabet(customer_fullname.getText())) {
            alert.errorMessage("Vui lòng chỉ nhập Tên là chữ cái");
        } else if (!checkdata.isVietnameseAlphabet(customer_address.getText())) {
            alert.errorMessage("Vui lòng chỉ nhập Địa chỉ là chữ cái");
        } else if (!checkdata.containsNonNumericCharacters(customer_phonenumber.getText())) {
            alert.errorMessage("Vui lòng nhập Số điện thoại chỉ có chữ số!");
        } else if (customer_phonenumber.getText().length() < 6 || customer_phonenumber.getText().length() > 13) {
            alert.errorMessage("Vui lòng nhập Số điện thoại có độ dài từ 6 đến 13 chữ số");
        } else if (!checkdata.containsNonNumericCharacters(customer_id.getText())) {
            alert.errorMessage("Vui lòng nhập ID chỉ có chữ số!");
        } else if (Integer.parseInt(customer_id.getText()) <= 0) {
            alert.errorMessage("Vui lòng nhập ID lớn hơn 0");
        } else {
            // Kiểm tra ID khách hàng trong cơ sở dữ liệu
            String checkData = "SELECT id FROM customer WHERE id = '" + customer_id.getText() + "'";
            statement = connect.createStatement();
            result = statement.executeQuery(checkData);

            if (result.next()) {
                alert.errorMessage("Mã khách hàng: " + customer_id.getText() + " đã tồn tại!");
            } else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, checkdata.capitalizeFirstLetter(customer_fullname.getText()));
                prepare.setString(2, checkdata.capitalizeFirstLetter(customer_address.getText()));
                prepare.setString(3, customer_phonenumber.getText());
                prepare.setString(4, customer_id.getText());

                prepare.executeUpdate();

                customerShowListData();
                customerReset();
            }
        }
    } catch (NumberFormatException | SQLException e) {
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
                String searchKey = newValue.toLowerCase();
                if (predicateCustomerData.getCustomerFullName().toLowerCase().contains(searchKey)) {
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
   public void customerUpdate() throws SQLException {
    String sql = "UPDATE customer SET fullname = ?, address = ?, phonenumber = ? WHERE id = ?";

    connect = database.connectDb();

    try {
        if (customer_fullname.getText().isEmpty()
                || customer_address.getText().isEmpty()
                || customer_phonenumber.getText().isEmpty()
                || customer_id.getText().isEmpty()) {
            alert.errorMessage("Vui lòng điền đầy đủ các trường dữ liệu!");
        } else if (!checkdata.isVietnameseAlphabet(customer_fullname.getText())) {
            alert.errorMessage("Vui lòng chỉ nhập Tên là chữ cái");
        } else if (!checkdata.isVietnameseAlphabet(customer_address.getText())) {
            alert.errorMessage("Vui lòng chỉ nhập Địa chỉ là chữ cái");
        } else if (!checkdata.containsNonNumericCharacters(customer_phonenumber.getText())) {
            alert.errorMessage("Vui lòng nhập Số điện thoại chỉ có chữ số!");
        } else if (customer_phonenumber.getText().length() < 6 && customer_phonenumber.getText().length() > 13) {
            alert.errorMessage("Vui lòng nhập Số điện thoại 6-13 chữ số");
        } else {
            // Kiểm tra ID khách hàng trong cơ sở dữ liệu
            String checkData = "SELECT id FROM customer WHERE id = '" + customer_id.getText() + "'";
            statement = connect.createStatement();
            result = statement.executeQuery(checkData);

            if (result.next()) {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, checkdata.capitalizeFirstLetter(customer_fullname.getText()));
                prepare.setString(2, checkdata.capitalizeFirstLetter(customer_address.getText()));
                prepare.setString(3, customer_phonenumber.getText());
                prepare.setString(4, customer_id.getText());

                prepare.executeUpdate();

                alert.successMessage("Cập nhật thành công!");

                customerShowListData();
                customerReset();
            } else {
                alert.errorMessage("Không tìm thấy Mã khách hàng: " + customer_id.getText());
            }
        }
    } catch (NumberFormatException | SQLException e) {
        e.printStackTrace();
    }
}

    //
    //DELETE
public void customerDelete() {
    String customerId = customer_id.getText();
    if (customerId.isEmpty()) {
        alert.errorMessage("Vui lòng nhập ID khách hàng để xóa");
        return;
    }

    String checkData = "SELECT id FROM customer WHERE id = '" + customerId + "'";
    String sql = "DELETE FROM customer WHERE id = '" + customerId + "'";

    connect = database.connectDb();
    try {
        statement = connect.createStatement();
        ResultSet result = statement.executeQuery(checkData);

        if (result.next()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CONFIRMATION MESSAGE");
            alert.setHeaderText(null);
            alert.setContentText("ARE YOU WANT TO DELETE PRODUCT ID: " + customerId + "?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                statement.executeUpdate(sql);

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("INFORMATION MESSAGE");
                successAlert.setHeaderText("XXX");
                successAlert.setContentText("SUCCESSFULLY DELETED");
                successAlert.showAndWait();

                customerShowListData();

                customerReset();
            }
        } else {
            alert.errorMessage("Không tìm thấy Mã khách hàng: " + customerId);
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

        @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerShowListData();

    }
    
}
