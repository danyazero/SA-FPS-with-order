package com.zero.safpswithorder;

import com.zero.safpswithorder.service.FPSService;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.math.BigDecimal;

public class HelloController {
    @FXML private GridPane grid_result;
    @FXML private Label profit_result;
    @FXML private Label drive_result;
    @FXML private Label machine_result;
    @FXML private VBox result_pane;
    @FXML private TextField e_field;
    @FXML private TextField n_field;
    @FXML private TextField k_field;
    @FXML private TextField vv_field;
    @FXML private TextField vn_field;
    @FXML private TextField d_field;
    @FXML private TextField tcp_field;
    @FXML private TextField lambda_field;
    @FXML
    protected void onHelloButtonClick() {
        int k = Integer.parseInt(k_field.getText());
        double lambda = Double.parseDouble(lambda_field.getText());
        double tcp = Double.parseDouble(tcp_field.getText());
        double d = Double.parseDouble(d_field.getText());
        double vv = Double.parseDouble(vv_field.getText());
        double vn = Double.parseDouble(vn_field.getText());
        int e = Integer.parseInt(e_field.getText());
        int n = Integer.parseInt(n_field.getText());

        FPSService fps = new FPSService(e, n);
        BigDecimal[][] matrix = fps.calculate(lambda, tcp, d, vv, vn, k);
        result_pane.setVisible(true);
        renderMatrix(matrix);
        profit_result.setText(fps.getProfit().toString());
        drive_result.setText(String.valueOf(fps.getDrives()));
        machine_result.setText(String.valueOf(fps.getMachines()));
    }

    private void renderMatrix(BigDecimal[][] matrix) {
        grid_result.getChildren().clear();
        for (int i = 1; i < matrix[0].length; i++) {
            Label label = new Label(""+i);
            label.setPadding(new Insets(5, 5, 5, 5));
            grid_result.add(label, 0, i);
        }
        for (int i = 1; i < matrix.length; i++) {
            Label num = new Label(""+i);
            num.setPadding(new Insets(5, 5, 5, 5));
            grid_result.add(num, i, 0);
            for (int j = 1; j < matrix[0].length; j++) {
                Label label = new Label(String.valueOf(matrix[i][j]));
                label.setPadding(new Insets(5, 5, 5, 5));
                grid_result.add(label, i, j);
            }
        }
    }

}