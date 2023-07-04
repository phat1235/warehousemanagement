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
import checkData.checkDataInput;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class userController implements Initializable{
   
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
    private TableView<userData> customer_tableView1;
    
  private  Connection connect;
    private   PreparedStatement prepare;
    private   Statement statement;
    private   ResultSet result;
    private Image image;
    //------------------------------------------------------------------------------------

    //
    checkDataInput checkdata = new checkDataInput();

    alertMessage alert = new alertMessage();
  public void addUsersAdd() {
    String sql = "INSERT INTO users (fullname, username, password, phonenumber, id) VALUE(?,?,?,?,?)";

    connect = database.connectDb();

    try {
        Alert alert;
        if (user_face_fullname.getText().isEmpty()
                || user_face_username.getText().isEmpty()
                || user_face_password.getText().isEmpty()
                || user_face_phonenumber.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR MESSAGE");
            alert.setHeaderText("XXX");
            alert.setContentText("Vui lòng điền đầy đủ các trường dữ liệu");
            alert.showAndWait();
        } else if (!checkdata.isVietnameseAlphabet(user_face_fullname.getText())) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR MESSAGE");
            alert.setHeaderText("XXX");
            alert.setContentText("Vui lòng chỉ nhập Họ và tên là chữ cái");
            alert.showAndWait();
        } else if (user_face_username.getText().length() < 4 || user_face_username.getText().length() > 20) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR MESSAGE");
            alert.setHeaderText("XXX");
            alert.setContentText("Vui lòng nhập Tên đăng nhập từ 6 đến 20 ký tự");
            alert.showAndWait();
        } else if (!user_face_password.getText().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$")) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR MESSAGE");
            alert.setHeaderText("XXX");
            alert.setContentText("Mật khẩu phải chứa ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt");
            alert.showAndWait();
        } else if (!checkdata.containsNonNumericCharacters(user_face_phonenumber.getText())) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR MESSAGE");
            alert.setHeaderText("XXX");
            alert.setContentText("Vui lòng nhập Số điện thoại chỉ có chữ số");
            alert.showAndWait();
        } else if (user_face_phonenumber.getText().length() < 6 || user_face_phonenumber.getText().length() > 13) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR MESSAGE");
            alert.setHeaderText("XXX");
            alert.setContentText("Vui lòng nhập Số điện thoại từ 6 đến 13 chữ số");
            alert.showAndWait();
        } else {
            // Kiểm tra ID người dùng trong cơ sở dữ liệu
            String checkData = "SELECT id FROM users WHERE id = '" + user_face_id.getText() + "'";
            statement = connect.createStatement();
            result = statement.executeQuery(checkData);

            if (result.next()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR MESSAGE");
                alert.setHeaderText("XXX");
                alert.setContentText("Mã người dùng: " + user_face_id.getText() + " đã tồn tại!");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, checkdata.capitalizeFirstLetter(user_face_fullname.getText()));
                prepare.setString(2, user_face_username.getText());
                prepare.setString(3, user_face_password.getText());
                prepare.setString(4, user_face_phonenumber.getText());
                prepare.setString(5, user_face_id.getText());

                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFORMATION MESSAGE");
                alert.setHeaderText("XXX");
                alert.setContentText("Thêm người dùng thành công!");
                alert.showAndWait();

                addUsersShowListData();
                addUsersReset();
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    public ObservableList<userData> addUsersListData() {
        ObservableList<userData> userList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users";
        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            userData userD;

            while (result.next()) {
                userD = new userData(result.getString("fullname"),
                        result.getString("username"),
                        result.getString("password"),
                        result.getString("phonenumber"),
                        result.getInt("id"));
                userList.add(userD);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return userList;
    }
//
//    //
   private ObservableList<userData> addUsersList;
//
  
    public void addUsersShowListData() {
        addUsersList = addUsersListData();
        user_col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        user_col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        user_col_fullname.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        //      user_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        user_col_phonenumber.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        user_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));

        customer_tableView1.setItems(addUsersList);
    }
//
   
  
    public void userSelect() {
        userData userD = customer_tableView1.getSelectionModel().getSelectedItem();
        int num = customer_tableView1.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            return;
        } else {
            user_face_fullname.setText(userD.getFullname());
            user_face_username.setText(userD.getUsername());
            user_face_password.setText(userD.getPassword());
            user_face_phonenumber.setText(userD.getPhonenumber());
            user_face_id.setText(String.valueOf(userD.getId()));
        }
    }
//    //
//
 
   public void addUsersUpdate() {
    String sql = "UPDATE users SET fullname = ?, username = ?, password = ?, phonenumber = ? WHERE id = ?";
    connect = database.connectDb();

    try {
        Alert alert;
        if (user_face_username.getText().isEmpty()
                || user_face_password.getText().isEmpty()
                || user_face_fullname.getText().isEmpty()
                || user_face_phonenumber.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR MESSAGE");
            alert.setHeaderText("XXX");
            alert.setContentText("Vui lòng điền đầy đủ các trường dữ liệu");
            alert.showAndWait();
        } else if (!checkdata.isVietnameseAlphabet(user_face_fullname.getText())) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR MESSAGE");
            alert.setHeaderText("XXX");
            alert.setContentText("Vui lòng chỉ nhập Họ và tên là chữ cái");
            alert.showAndWait();
        } else if (!checkdata.containsNonNumericCharacters(user_face_phonenumber.getText())) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR MESSAGE");
            alert.setHeaderText("XXX");
            alert.setContentText("Vui lòng nhập Số điện thoại chỉ có chữ số");
            alert.showAndWait();
        } else if (user_face_phonenumber.getText().length() < 6 || user_face_phonenumber.getText().length() > 13) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR MESSAGE");
            alert.setHeaderText("XXX");
            alert.setContentText("Vui lòng nhập Số điện thoại từ 6 đến 13 chữ số");
            alert.showAndWait();
        } else {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("CONFIRMATION MESSAGE");
            alert.setHeaderText(null);
            alert.setContentText("BẠN CÓ MUỐN CẬP NHẬT NGƯỜI DÙNG CÓ ID: " + user_face_id.getText() + "?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, checkdata.capitalizeFirstLetter(user_face_fullname.getText()));
                prepare.setString(2, user_face_username.getText());
                prepare.setString(3, user_face_password.getText());
                prepare.setString(4, user_face_phonenumber.getText());
                prepare.setString(5, user_face_id.getText());

                prepare.executeUpdate();

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("INFORMATION MESSAGE");
                alert.setHeaderText("XXX");
                alert.setContentText("Cập nhật thành công!");
                alert.showAndWait();

                addUsersShowListData();
                addUsersReset();
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

//
//    
//    //DELETE
  
    public void addUsersDelete() {
        String sql = "DELETE FROM users WHERE username = '" + user_face_username.getText() + "'";

        connect = database.connectDb();
        try {
            Alert alert;
            if (user_face_username.getText().isEmpty()
                    || user_face_password.getText().isEmpty()
                    || user_face_fullname.getText().isEmpty()
                    || user_face_phonenumber.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR MESSAGE");
                alert.setHeaderText("XXXXX-ORDER");
                alert.setContentText("FILL ALL BANK FIELDS");
                alert.showAndWait();

            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("CONFIRMATION MESSAGE");
                alert.setHeaderText(null);
                alert.setContentText("ARE YOU WANT TO DELETE PRODUCT ID: " + user_face_username.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("INFORMATION MESSAGE");
                    alert.setHeaderText("XXX");
                    alert.setContentText("SUCCESSFLLY DELETE");
                    alert.showAndWait();

                    addUsersShowListData();

                    addUsersReset();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
    
    public void addUsersReset() {
        user_face_username.setText("");
        user_face_fullname.setText("");
        user_face_password.setText("");
        user_face_phonenumber.setText("");
        user_face_id.setText("");
    }

     
    public void userSearch() {
        FilteredList<userData> filter = new FilteredList<>(addUsersList, e -> true);

        user_search.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(predicateUserData -> {
                if (newValue == null) {
                    return false;
                }

                String searchKey = newValue.toLowerCase();
                if (predicateUserData.getFullname().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<userData> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(customer_tableView1.comparatorProperty());
        customer_tableView1.setItems(sortList);

    }
       
        @Override
    public void initialize(URL location, ResourceBundle resources) {
        addUsersShowListData();

      
    }
}
