package com.zero.safpswithorder;

import com.zero.safpswithorder.service.FPSService;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

public class HelloController {
    public TableView<Double[]> result_table;
    public Text result_text;
    public GridPane grid_result;
    public Label profit_result;
    public Label drive_result;
    public Label machine_result;
    @FXML
    private TextField k_field;
    @FXML
    private TextField vv_field;
    @FXML
    private TextField vn_field;
    @FXML
    private TextField d_field;
    @FXML
    private TextField tcp_field;
    @FXML
    private TextField lambda_field;

    @FXML
    protected void onHelloButtonClick() {
        int k = Integer.parseInt(k_field.getText());
        double lambda = Double.parseDouble(lambda_field.getText());
        double tcp = Double.parseDouble(tcp_field.getText());
        double d = Double.parseDouble(d_field.getText());
        double vv = Double.parseDouble(vv_field.getText());
        double vn = Double.parseDouble(vn_field.getText());

        FPSService fps = new FPSService(k);
        BigDecimal[][] matrix = fps.calculate(lambda, tcp, d, vv, vn);
        renderMatrix(matrix);
        profit_result.setText(fps.getProfit().toString());
        drive_result.setText(String.valueOf(fps.getDrives()));
        machine_result.setText(String.valueOf(fps.getMachines()));
    }



    private void renderMatrix(BigDecimal[][] matrix) {
        for (int i = 1; i < matrix.length; i++) {
            Label label = new Label(""+i);
            label.setPadding(new Insets(5, 5, 5, 5));
            grid_result.add(label, 0, i);
        }
        for (int i = 1; i < matrix.length; i++) {
            Label num = new Label(""+i);
            num.setPadding(new Insets(5, 5, 5, 5));
            grid_result.add(num, i, 0);
            for (int j = 1; j < matrix.length; j++) {
                Label label = new Label(String.valueOf(matrix[i][j]));
                label.setPadding(new Insets(5, 5, 5, 5));
                grid_result.add(label, i, j);
            }
        }
    }

}

//substract() віднімання
//multiply() множення
//divide() ділення
//add() додавання