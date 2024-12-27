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

import java.text.NumberFormat;

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

    // Contoh data dummy (ini akan diganti dengan data dari database)
    private int totalBarang = 150; // Total barang di inventaris
    private int barangMasukHariIni = 25; // Barang masuk hari ini
    private int barangKeluarHariIni = 20; // Barang keluar hari ini

    @FXML
    public void initialize() {
        NumberFormat numberFormat = NumberFormat.getInstance();
        totalProduk.setText(numberFormat.format(totalBarang));
        barangMasuk.setText(numberFormat.format(barangMasukHariIni));
        barangKeluar.setText(numberFormat.format(barangKeluarHariIni));

        pieChart.setData(generatePieChartData());

        populateBarChart();

        populateLineChart();
    }

    private ObservableList<PieChart.Data> generatePieChartData() {
        return FXCollections.observableArrayList(
            new PieChart.Data("Makanan", 50),
            new PieChart.Data("Minuman", 30),
            new PieChart.Data("Sembako", 70)
        );
    }

    private void populateBarChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Barang Masuk Minggu Ini");
        series.getData().add(new XYChart.Data<>("Senin", 10));
        series.getData().add(new XYChart.Data<>("Selasa", 15));
        series.getData().add(new XYChart.Data<>("Rabu", 20));
        series.getData().add(new XYChart.Data<>("Kamis", 25));
        series.getData().add(new XYChart.Data<>("Jumat", 30));
        barChart.getData().add(series);
    }

    private void populateLineChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Barang Keluar Bulan Ini");
        series.getData().add(new XYChart.Data<>("Week 1", 50));
        series.getData().add(new XYChart.Data<>("Week 2", 70));
        series.getData().add(new XYChart.Data<>("Week 3", 60));
        series.getData().add(new XYChart.Data<>("Week 4", 80));
        lineChart.getData().add(series);
    }
}
