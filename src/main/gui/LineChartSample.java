package main.gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
 
 
public class LineChartSample extends Application {
 
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override public void start(Stage stage) {
    	final LineChart lineChart = new LineChart(
    	        new NumberAxis(), new NumberAxis(),
    	        FXCollections.observableArrayList(
    	            new XYChart.Series(
    	                "My portfolio",
    	                FXCollections.observableArrayList(
    	                    plot(23, 14, 15, 24, 34, 36, 22, 45, 43, 17, 29, 25)
    	                )
    	            )
    	        )
    	    );
    	
    	    lineChart.setTitle("Stock Monitoring, 2013");
    	 
    	    stage.setScene(new Scene(lineChart, 500, 400));
    	    stage.show();
    }
    
    /** @return plotted y values for monotonically increasing integer x values, starting from x=1 */
    @SuppressWarnings({"unchecked", "rawtypes"})
	public ObservableList<XYChart.Data<Integer, Integer>> plot(int... y) {
      final ObservableList<XYChart.Data<Integer, Integer>> dataset = FXCollections.observableArrayList();
      int i = 0;
      while (i < y.length) {
		final XYChart.Data<Integer, Integer> data = new XYChart.Data(i + 1, y[i]);
        data.setNode(
            new HoveredThresholdNode(
                (i == 0) ? 0 : y[i-1],
                y[i]
            )
        );
   
        dataset.add(data);
        i++;
      }
   
      return dataset;
    }
   
    /** a node which displays a value on hover, but is otherwise empty */
    class HoveredThresholdNode extends StackPane {
      HoveredThresholdNode(int priorValue, int value) {
        setPrefSize(15, 15);
   
        final Label label = createDataThresholdLabel(priorValue, value);
   
        setOnMouseEntered(new EventHandler<MouseEvent>() {
          public void handle(MouseEvent mouseEvent) {
            getChildren().setAll(label);
            toFront();
          }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {
          public void handle(MouseEvent mouseEvent) {
            getChildren().clear();
          }
        });
      }
   
      private Label createDataThresholdLabel(int priorValue, int value) {
        final Label label = new Label(value + "");
        label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
        label.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
   
        if (priorValue == 0) {
          label.setTextFill(Color.DARKGRAY);
        } else if (value > priorValue) {
          label.setTextFill(Color.FORESTGREEN);
        } else {
          label.setTextFill(Color.FIREBRICK);
        }
   
        label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        return label;
      }
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}