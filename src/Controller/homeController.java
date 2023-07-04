package Controller;

import alert.alertMessage;
import database.database;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Admin
 */
public class homeController implements Initializable {

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

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    //------------------------------------------------------------------------------------

    //
    alertMessage alert = new alertMessage();

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
