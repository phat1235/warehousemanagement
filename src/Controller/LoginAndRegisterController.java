package Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import alert.alertMessage;
import database.*;
import database.getData;

/**
 *
 * @author WINDOWS 10
 */


public class LoginAndRegisterController implements Initializable {

    @FXML
    private AnchorPane login_form;

    @FXML
    private TextField login_username;

    @FXML
    private TextField signup_fullname;

    @FXML
    private PasswordField login_password;

    @FXML
    private TextField login_showPassword;

    @FXML
    private CheckBox login_selectShowPassword;

    @FXML
    private Button login_btn;

    @FXML
    private Button login_createAccount;

    @FXML
    private AnchorPane signup_form;

    @FXML
    private TextField signup_username;

    @FXML
    private PasswordField signup_password;

    @FXML
    private PasswordField signup_cPassword;

    @FXML
    private Button signup_loginAccount;

//TOOLS
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private ResultSet rs;
    private double x = 0, y = 0;
    
    alertMessage alert = new alertMessage();
    //
    public void login() {
        

        // CHECK IF ALL OR SOME OF THE FIELDS ARE EMPTY
        if (login_username.getText().isEmpty() || login_password.getText().isEmpty()) {
            alert.errorMessage("Incorrect Username/Password");
        } else {
            String selectData = "SELECT * FROM users WHERE "
                    + "username = ? and password = ?"; // users IS OUR TABLE NAME

            connect = database.connectDb();

            if (login_selectShowPassword.isSelected()) {
                login_password.setText(login_showPassword.getText());
            } else {
                login_showPassword.setText(login_password.getText());
            }

            try {
                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, login_username.getText());
                prepare.setString(2, login_password.getText());

                result = prepare.executeQuery();

                String username = login_username.getText();

                if (result.next()) {
                    getData.username = login_username.getText();
                    if ("admin".equals(username)) {
                        // ONCE ALL DATA THAT USERS INSERT ARE CORRECT, THEN WE WILL PROCEED TO OUR MAIN FORM

                        alert.successMessage("Successfully Login - admin!");
                        // TO LINK THE MAIN FORM
                        Parent root = FXMLLoader.load(getClass().getResource("/View/AfterLoginHome.fxml"));

                        Stage stage = new Stage();
                        Scene scene = new Scene(root);

                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.setResizable(false);
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
                        stage.setScene(scene);
                        // TO SHOW OUR MAIN FORM
                        stage.show();

                        // TO HIDE WINDOW OF LOGIN FORM
                        login_btn.getScene().getWindow().hide();
                    } else {
                        alert.successMessage("BẠN KHÔNG PHẢI LÀ ADMIN - CHƯA CÓ TÍNH NĂNG CHO BẠN!");
                        // TO LINK THE MAIN FORM

                        // TO HIDE WINDOW OF LOGIN FORM
                        //  login_btn.getScene().getWindow().hide();
                    }

                } else {
                    // ELSE, THEN ERROR MESSAGE WILL APPEAR
                    alert.errorMessage("Incorrect Username/Password");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    // Tích vào ô để hiện mật khẩu

    public void showPassword() {

        if (login_selectShowPassword.isSelected()) {
            login_showPassword.setText(login_password.getText());
            login_showPassword.setVisible(true);
            login_password.setVisible(false);
        } else {
            login_password.setText(login_showPassword.getText());
            login_showPassword.setVisible(false);
            login_password.setVisible(true);
        }

    }

    //  Clas Đăng Ký user
    //   Bắt các lỗi như : 
    //       + Không điền đầy đủ các trường dữ liệu.
    //       + Mật khẩu ngắn hơn 8 kí tự.
    //       + Mật khẩu nhập lại không khớp.
    //       + Tài khoản đã được đăng ký.
    public void register() {
      

        if (signup_username.getText().isEmpty()
                || signup_password.getText().isEmpty() || signup_cPassword.getText().isEmpty()) {
            alert.errorMessage("All fields are necessary to be filled");
        } else if (!signup_password.getText().equals(signup_cPassword.getText())) {
            alert.errorMessage("Password does not match");
        } else if (signup_password.getText().length() < 8) {
            alert.errorMessage("Invalid Password, at least 8 characters needed");
        } else {
            String checkUsername = "SELECT * FROM users WHERE username = ?";
            String insertData = "INSERT INTO users (fullname, username, password, phonenumber) VALUES (?, ?, ?, '')";
            Connection connection = null;
            PreparedStatement checkUsernameStatement = null;
            PreparedStatement insertDataStatement = null;

            try {
                connection = database.connectDb();
                checkUsernameStatement = connection.prepareStatement(checkUsername);
                insertDataStatement = connection.prepareStatement(insertData);

                // Kiểm tra xem username đã tồn tại chưa
                checkUsernameStatement.setString(1, signup_username.getText());
                ResultSet result = checkUsernameStatement.executeQuery();

                if (result.next()) {
                    alert.errorMessage(signup_username.getText() + " is already taken");
                } else {

                    insertDataStatement.setString(1, signup_fullname.getText());
                    insertDataStatement.setString(2, signup_username.getText());
                    insertDataStatement.setString(3, signup_password.getText());
                    insertDataStatement.executeUpdate();

                    alert.successMessage("Registered Successfully!");

                    registerClearFields();

                    signup_form.setVisible(false);
                    login_form.setVisible(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Đóng kết nối và các đối tượng PreparedStatement
                if (insertDataStatement != null) {
                    try {
                        insertDataStatement.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (checkUsernameStatement != null) {
                    try {
                        checkUsernameStatement.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // Xóa các trường dữ liệu sau một hành động thành công nào đó( thêm, sửa, xóa)
    public void registerClearFields() {
        signup_fullname.setText("");
        signup_username.setText("");
        signup_password.setText("");
        signup_cPassword.setText("");

    }

    // Chuyển cảnh
    // + Mặc định là giao diện đăng nhập
    // + Nếu nhấn vào Button ---- Register ---- thì sẽ ẩn giao diện đăng nhập, set cho giao diện đăng kí hiện lên... 
    // || vì giao diện đăng nhâp và đăng kí cùng tạo trên 1 cái stackpane
    // + Nếu muốn quay lại giao diện đăng nhập  thì nhấn vào Button ------- Login Account
    public void switchForm(ActionEvent event) {
        if (event.getSource() == signup_loginAccount) {        // THE REGISTRATION FORM WILL BE VISIBLE
            signup_form.setVisible(false);
            login_form.setVisible(true);
        } else if (event.getSource() == login_createAccount) { // THE LOGIN FORM WILL BE VISIBLE
            signup_form.setVisible(true);
            login_form.setVisible(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
