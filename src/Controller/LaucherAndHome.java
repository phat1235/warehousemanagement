package Controller;

import alert.alertMessage;
import database.database;
import database.getData;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class LaucherAndHome implements Initializable {

    @FXML
    private AnchorPane main_pane;
    @FXML
    private AnchorPane main_form1;
    @FXML
    private AnchorPane home_form;
    @FXML
    private Label home_AvalableProduct;
    @FXML
    private AreaChart<?, ?> home_IncomeChart;
    @FXML
    private Label home_NumberOrder;
    @FXML
    private BarChart<?, ?> home_OrderChart;
    @FXML
    private Label home_TotalIncome;
    @FXML
    private ComboBox<?> revenue_income;
    @FXML
    private ComboBox<?> revenue_order;
    @FXML
    private Label username;
    @FXML
    private Button logout_Btn;

    
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
   
    //------------------------------------------------------------------------------------
    alertMessage alert1 = new alertMessage();

    public void displayUsername() {
        username.setText(getData.username);
    }

    public void minimize() {
        Stage stage = (Stage) main_form1.getScene().getWindow();
        stage.setIconified(true);

    }

    public void close() {
        System.exit(0);
    }
    private double x = 0;
    private double y = 0;

    public void logout() {
        try {// LINK LOGIN DE CHUYEN TOI
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CONFIRMATION MESSAGE");
            alert.setHeaderText(null);
            alert.setContentText("ARE YOU WANT TO LOGOUT?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                // AN LOGIN DI KHI THANH CONG
                logout_Btn.getScene().getWindow().hide();
                //LINK LOGIN
                Parent root = FXMLLoader.load(getClass().getResource("/View/LoginAndRegister.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                // NHAP MOUSE VAO VA DI CHUYEN GIAO DIEN  X0Y
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
                stage.show();
            } else {
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loaderSecond(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/home.fxml"));
        home_form.setVisible(false);
        main_pane.getChildren().setAll(pane);
    }

    public void loaderSecond1(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/product.fxml"));
        home_form.setVisible(false);
        main_pane.getChildren().setAll(pane);
    }

    public void loaderSecond2(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/customer.fxml"));
        home_form.setVisible(false);
        main_pane.getChildren().setAll(pane);
    }

    public void loaderSecond3(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/employee.fxml"));
        home_form.setVisible(false);
        main_pane.getChildren().setAll(pane);
    }

    public void loaderSecond4(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/supplier.fxml"));
        home_form.setVisible(false);
        main_pane.getChildren().setAll(pane);
    }

    public void loaderSecond5(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/order.fxml"));
        home_form.setVisible(false);
        main_pane.getChildren().setAll(pane);
    }

    public void loaderSecond6(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/user.fxml"));
        home_form.setVisible(false);
        main_pane.getChildren().setAll(pane);
    }

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homeOrderChart();
        homeIncomeChart();
        honeDisplayAvalProduct();
        
        
    }
}
