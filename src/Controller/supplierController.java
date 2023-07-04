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
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import Model.suplierData;
import checkData.checkDataInput;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class supplierController implements Initializable{
   
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

    
  private  Connection connect;
    private   PreparedStatement prepare;
    private   Statement statement;
    private   ResultSet result;
    private Image image;
    //------------------------------------------------------------------------------------

    //
    alertMessage alert = new alertMessage();
        checkDataInput checkdata = new checkDataInput();
   public void addSupliersAdd() {
    String sql = "INSERT INTO suplier (suplier_id, suplier_fullname, suplier_address, suplier_phonenumber)"
            + "VALUES (?, ?, ?, ?)";

    connect = database.connectDb();

    try {
        Alert alert;
        if (supplier_code.getText().isEmpty()
                || supplier_fullname.getText().isEmpty()
                || supplier_address.getText().isEmpty()
                || supplier_phonenumber.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR MESSAGE");
            alert.setHeaderText("XXX");
            alert.setContentText("Vui lòng điền đầy đủ các trường dữ liệu!");
            alert.showAndWait();
        } else if (!checkdata.containsNonNumericCharacters(supplier_code.getText())) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR MESSAGE");
            alert.setHeaderText("XXX");
            alert.setContentText("Vui lòng nhập mã nhà cung cấp chỉ có chữ số!!");
            alert.showAndWait();
        } else if (Integer.parseInt(supplier_code.getText()) <= 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR MESSAGE");
            alert.setHeaderText("XXX");
            alert.setContentText("Vui lòng nhập mã nhà cung cấp lớn hơn 0");
            alert.showAndWait();
        } else {
            // CHECK SUPPLIER ID IN DATABASE
            String checkData = "SELECT suplier_id FROM suplier WHERE suplier_id = '"
                    + supplier_code.getText() + "'";
            statement = connect.createStatement();
            result = statement.executeQuery(checkData);

            if (result.next()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR MESSAGE");
                alert.setHeaderText("XXX");
                alert.setContentText("Mã nhà cung cấp: " + supplier_code.getText() + " đã tồn tại!");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, supplier_code.getText());
                prepare.setString(2, checkdata.capitalizeFirstLetter(supplier_fullname.getText()));
                prepare.setString(3, checkdata.capitalizeFirstLetter(supplier_address.getText()));
                prepare.setString(4, supplier_phonenumber.getText());

                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFORMATION MESSAGE");
                alert.setHeaderText("XXX");
                alert.setContentText("Thêm nhà cung cấp thành công!");
                alert.showAndWait();

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

    String sql = "UPDATE suplier SET suplier_fullname = ?, suplier_address = ?, suplier_phonenumber = ? WHERE suplier_id = ?";
    connect = database.connectDb();

    try {
        Alert alert1;
        if (supplier_fullname.getText().isEmpty()
                || supplier_address.getText().isEmpty()
                || supplier_phonenumber.getText().isEmpty()
                || supplier_code.getText().isEmpty()) {
            alert.errorMessage("Vui lòng điền tất cả các trường dữ liệu");

        } else if (!checkdata.isVietnameseAlphabet(supplier_fullname.getText())) {
            alert.errorMessage("Vui lòng chỉ nhập Tên là chữ cái");
        } else if (!checkdata.isVietnameseAlphabet(supplier_address.getText())) {
            alert.errorMessage("Vui lòng chỉ nhập Địa chỉ là chữ cái");
        } else if (!checkdata.containsNonNumericCharacters(supplier_phonenumber.getText())) {
            alert.errorMessage("Vui lòng nhập Số điện thoại chỉ có chữ số!");
        } else if (supplier_phonenumber.getText().length() < 6 || supplier_phonenumber.getText().length() > 13) {
            alert.errorMessage("Vui lòng nhập Số điện thoại từ 6 đến 13 chữ số");
        } else {
            alert1 = new Alert(AlertType.CONFIRMATION);
            alert1.setTitle("CONFIRMATION MESSAGE");
            alert1.setHeaderText(null);
            alert1.setContentText("ARE YOU WANT TO UPDATE PRODUCT ID: " + supplier_code.getText() + "?");

            Optional<ButtonType> option = alert1.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, checkdata.capitalizeFirstLetter(supplier_fullname.getText()));
                prepare.setString(2, checkdata.capitalizeFirstLetter(supplier_address.getText()));
                prepare.setString(3, supplier_phonenumber.getText());
                prepare.setString(4, supplier_code.getText());

                prepare.executeUpdate();

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
                alert1.setContentText("ARE YOU WANT TO delete SUPPLIER ID: " + supplier_code.getText() + "?");

                Optional<ButtonType> option = alert1.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert.successMessage("Delete SUPPLIER ID: " + supplier_code.getText() + " thành công!");

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

       
        @Override
    public void initialize(URL location, ResourceBundle resources) {
        
addSupliersShowListData();
      
    }
}
