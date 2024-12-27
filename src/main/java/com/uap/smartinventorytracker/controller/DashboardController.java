package com.uap.smartinventorytracker.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;

import com.uap.smartinventorytracker.model.ProductType;
import com.uap.smartinventorytracker.repository.ProductRepository;
import com.uap.smartinventorytracker.repository.ProductTransactionRepository;
import com.uap.smartinventorytracker.repository.ProductTypeRepository;

public class DashboardController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private Label barangKeluar;

    @FXML
    private Label barangMasuk;

    @FXML
    private AnchorPane dashboard_fxml;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private PieChart pieChart;

    @FXML
    private Label totalProduk;
    
    private final ProductRepository productRepo = new ProductRepository();
    private final ProductTransactionRepository productTransactionRepo = new ProductTransactionRepository();
    private final ProductTypeRepository productTypeRepo = new ProductTypeRepository();

    @FXML
    public void initialize() {
        // Format number
        NumberFormat numberFormat = NumberFormat.getInstance();
        totalProduk.setText(numberFormat.format(productRepo.getTotalProductQuantity()));
        barangMasuk.setText(numberFormat.format(productTransactionRepo.getTotalQuantityByTransactionType("ADD")));
        barangKeluar.setText(numberFormat.format(productTransactionRepo.getTotalQuantityByTransactionType("REMOVE")));

        pieChart.setData(generatePieChartData());

        populateBarChart();

        populateLineChart();
    }

    private ObservableList<PieChart.Data> generatePieChartData() {
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        List<ProductType> productTypes;
		try {
			productTypes = productTypeRepo.getAllProductTypes();
		    for (ProductType type : productTypes) {
	            int productCount = productRepo.getProductCountByType(type.getId());
	            pieData.add(new PieChart.Data(type.getName(), productCount));
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return pieData;
    }

    private void populateBarChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Barang Masuk Minggu Ini");
        
        // Fetch daily incoming product data for the week (example for Monday to Friday)
        series.getData().add(new XYChart.Data<>("Senin", productTransactionRepo.getQuantityByDayOfWeek("ADD", "Monday")));
        series.getData().add(new XYChart.Data<>("Selasa", productTransactionRepo.getQuantityByDayOfWeek("ADD", "Tuesday")));
        series.getData().add(new XYChart.Data<>("Rabu", productTransactionRepo.getQuantityByDayOfWeek("ADD", "Wednesday")));
        series.getData().add(new XYChart.Data<>("Kamis", productTransactionRepo.getQuantityByDayOfWeek("ADD", "Thursday")));
        series.getData().add(new XYChart.Data<>("Jumat", productTransactionRepo.getQuantityByDayOfWeek("ADD", "Friday")));
        
        barChart.getData().add(series);
    }

    private void populateLineChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Barang Keluar Bulan Ini");

        // Fetch weekly outgoing product data for the month
        series.getData().add(new XYChart.Data<>("Week 1", productTransactionRepo.getQuantityByWeek("REMOVE", 1)));
        series.getData().add(new XYChart.Data<>("Week 2", productTransactionRepo.getQuantityByWeek("REMOVE", 2)));
        series.getData().add(new XYChart.Data<>("Week 3", productTransactionRepo.getQuantityByWeek("REMOVE", 3)));
        series.getData().add(new XYChart.Data<>("Week 4", productTransactionRepo.getQuantityByWeek("REMOVE", 4)));

        lineChart.getData().add(series);
    }
}
