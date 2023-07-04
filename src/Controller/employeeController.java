/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import alert.alertMessage;
import database.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import Model.employeeData;
import checkData.checkDataInput;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 *
 * @author Admin
 */
public class employeeController    implements Initializable{
    
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
    
    private  Connection connect;
    private   PreparedStatement prepare;
    private   Statement statement;
    private   ResultSet result;
    private Image image;
    
    alertMessage alert = new alertMessage();
    checkDataInput checkdata = new checkDataInput();

    public void addEmployeesAdd() throws SQLException {
    String sql = "INSERT INTO employee (employee_id, employee_fullname, employee_address, employee_sex, employee_age, employee_phonenumber, employee_salary, employee_date) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    connect = database.connectDb();

    try {
        if (employee_id.getText().isEmpty()
                || employee_fullname.getText().isEmpty()
                || employee_address.getText().isEmpty()
                || employee_sex.getText().isEmpty()
                || employee_age.getText().isEmpty()
                || employee_phonenumber.getText().isEmpty()
                || employee_salary.getText().isEmpty()) {
            alert.errorMessage("Điền tất cả các trường dữ liệu");

        } else if (!checkdata.containsNonNumericCharacters(employee_id.getText())) {
            alert.errorMessage("Vui lòng nhập ID chỉ có chữ số!");
        } else if (Integer.parseInt(employee_id.getText()) <= 0 || Integer.parseInt(employee_id.getText()) > 10000000) {
            alert.errorMessage("Vui lòng nhập ID > 0 - < 1000000");
        } else if (!checkdata.isVietnameseAlphabet(employee_fullname.getText())) {
            alert.errorMessage("Vui lòng chỉ nhập Tên là chữ cái");
        } else if (!checkdata.isVietnameseAlphabet(employee_address.getText())) {
            alert.errorMessage("Vui lòng chỉ nhập Địa chỉ là chữ cái");
        } else if (!checkdata.isVietnameseAlphabet(employee_sex.getText())) {
            alert.errorMessage("Vui lòng chỉ nhập Giới tính là chữ cái");
        } else if (!checkdata.containsNonNumericCharacters(employee_age.getText())) {
            alert.errorMessage("Vui lòng nhập Tuổi chỉ có chữ số!");
        } else if (Integer.parseInt(employee_age.getText()) <= 0 || Integer.parseInt(employee_age.getText()) > 120) {
            alert.errorMessage("Vui lòng nhập Tuổi > 0 - < 120");
        } else if (!checkdata.containsNonNumericCharacters(employee_phonenumber.getText())) {
            alert.errorMessage("Vui lòng nhập Số điện thoại chỉ có chữ số!");
        } else  if (employee_phonenumber.getText().length() < 6 ||   employee_phonenumber.getText().length()  >  13) {
            alert.errorMessage("Vui lòng nhập Số điện thoại 6-13 chữ số");
        } else if (!checkdata.containsNonNumericCharacters(employee_salary.getText())) {
            alert.errorMessage("Vui lòng nhập Lương chỉ có chữ số!");
        } else if (Integer.parseInt(employee_salary.getText()) <= 0 || Integer.parseInt(employee_salary.getText()) > 10000000) {
            alert.errorMessage("Vui lòng nhập Lương > 0 - < 1000000");
        } else {
            // Kiểm tra ID nhân viên trong cơ sở dữ liệu
            String checkData = "SELECT employee_id FROM employee WHERE employee_id = '" + employee_id.getText() + "'";
            statement = connect.createStatement();
            result = statement.executeQuery(checkData);

            if (result.next()) {
                alert.errorMessage("Mã nhân viên: " + employee_id.getText() + " đã tồn tại!");
            } else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, employee_id.getText());
                prepare.setString(2, checkdata.capitalizeFirstLetter(employee_fullname.getText()));
                prepare.setString(3, checkdata.capitalizeFirstLetter(employee_address.getText()));
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
    } catch (NumberFormatException | SQLException e) {
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

   // checkDataInput checkdata = new checkDataInput();

public void addEmployeesUpdate() throws SQLException {
    String sql = "UPDATE employee SET employee_fullname =  ?, employee_address = ?, employee_sex  = ?, employee_age = ?, employee_phonenumber  = ?,  employee_salary = ? WHERE employee_id = ?";

    connect = database.connectDb();

    try {
        if (employee_fullname.getText().isEmpty()
                || employee_address.getText().isEmpty()
                || employee_sex.getText().isEmpty()
                || employee_age.getText().isEmpty()
                || employee_phonenumber.getText().isEmpty()
                || employee_salary.getText().isEmpty()
                || employee_id.getText().isEmpty()) {
            alert.errorMessage("Vui lòng điền tất cả các trường dữ liệu");

        } else if (!checkdata.isVietnameseAlphabet(employee_fullname.getText())) {
            alert.errorMessage("Vui lòng chỉ nhập Tên là chữ cái");
        } else if (!checkdata.isVietnameseAlphabet(employee_address.getText())) {
            alert.errorMessage("Vui lòng chỉ nhập Địa chỉ là chữ cái");
        } else if (!checkdata.isVietnameseAlphabet(employee_sex.getText())) {
            alert.errorMessage("Vui lòng chỉ nhập Giới tính là chữ cái");
        } else if (!checkdata.containsNonNumericCharacters(employee_age.getText())) {
            alert.errorMessage("Vui lòng nhập Tuổi chỉ có chữ số!");
        } else if (Integer.parseInt(employee_age.getText()) <= 0 || Integer.parseInt(employee_age.getText()) > 100) {
            alert.errorMessage("Vui lòng nhập Tuổi > 0 - < 100");
        } else if (!checkdata.containsNonNumericCharacters(employee_phonenumber.getText())) {
            alert.errorMessage("Vui lòng nhập Số điện thoại chỉ có chữ số!");
        } else if (employee_phonenumber.getText().length() < 6 ||   employee_phonenumber.getText().length()  >  13) {
            alert.errorMessage("Vui lòng nhập Số điện thoại 6-13 chữ số");
        } else if (!checkdata.containsNonNumericCharacters(employee_salary.getText())) {
            alert.errorMessage("Vui lòng nhập Lương chỉ có chữ số!");
        } else if (Integer.parseInt(employee_salary.getText()) <= 0.0) {
            alert.errorMessage("Vui lòng nhập Lương > 0 - < 1000000");
        } else {
            // Kiểm tra ID nhân viên trong cơ sở dữ liệu
            String checkData = "SELECT employee_id FROM employee WHERE employee_id = '" + employee_id.getText() + "'";
            statement = connect.createStatement();
            result = statement.executeQuery(checkData);

            if (result.next()) {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, checkdata.capitalizeFirstLetter(employee_fullname.getText()));
                prepare.setString(2, checkdata.capitalizeFirstLetter(employee_address.getText()));
                prepare.setString(3, checkdata.capitalizeFirstLetter(employee_sex.getText()));
                prepare.setString(4, employee_age.getText());
                prepare.setString(5, employee_phonenumber.getText());
                prepare.setString(6, employee_salary.getText());
                prepare.setString(7, employee_id.getText());

                prepare.executeUpdate();

                alert.successMessage("Cập nhật thành công!");

                addEmployeesShowListData();
                addEmployeesReset();
            } else {
                alert.errorMessage("Không tìm thấy Mã nhân viên: " + employee_id.getText());
            }
        }
    } catch (NumberFormatException | SQLException e) {
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
                alert1 = new Alert(Alert.AlertType.CONFIRMATION);
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
      public void initialize(URL location, ResourceBundle resources) {
        addEmployeesShowListData();
        employeesSelect();
    }
}
