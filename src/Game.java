/**
 * https://www.dame100-korbach.de/spielregeln/
 * https://www.tutorialspoint.com/javafx/javafx_application.htm
 * https://stackoverflow.com/questions/32892646/adding-borders-to-gridpane-javafx
 */

import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Game extends Application {
    @Override
    public void start(Stage stage) {
        int dimension = 10;
        int sizeWindow = 300;
        ArrayList<Pane> elements = new ArrayList<Pane>();

        GridPane grid = new GridPane();
        grid.getStyleClass().add("game-grid");

        for (int i = 0; i < dimension; i++) {
            ColumnConstraints column = new ColumnConstraints();
            RowConstraints row = new RowConstraints();

            column.setPercentWidth(100/dimension);
            row.setPercentHeight(100/dimension);

            grid.getColumnConstraints().add(column);
            grid.getRowConstraints().add(row);
        }

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                Pane pane = new Pane();
                pane.getStyleClass().add("game-grid-cell");

                if ((i & 1) == 0) {
                    if ((j & 1) == 0) { pane.getStyleClass().add("white"); }
                    else { pane.getStyleClass().add("black"); }
                } else {
                    if ((j & 1) == 0) { pane.getStyleClass().add("black"); }
                    else { pane.getStyleClass().add("white"); }
                }

                if (i == 0) { pane.getStyleClass().add("first-row"); }
                if (j == 0) { pane.getStyleClass().add("first-column"); }

                elements.add(pane);
                grid.add(pane, j, i);
            }
        }

        /* wip: Set Image to a specific pane*/
        Image imgWhite = new Image(getClass().getResourceAsStream("/white.png"));
        ImageView white = new ImageView(imgWhite);
        white.setFitHeight(sizeWindow/dimension-2);
        white.setFitWidth(sizeWindow/dimension-2);

        Image imgBlack = new Image(getClass().getResourceAsStream("/black.png"));
        ImageView black = new ImageView(imgBlack);
        black.setFitHeight(sizeWindow/dimension-2);
        black.setFitWidth(sizeWindow/dimension-2);

        elements.get(1).getChildren().add(white);
        elements.get(2).getChildren().add(black);
        //elements.get(1).getChildren().remove(icon);

        white.setOnMouseClicked((MouseEvent e) -> {
            System.out.println("click"); //change functionality
        });

        Scene scene = new Scene(grid, sizeWindow, sizeWindow);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            white.setFitWidth((int)(double)newVal/dimension-2);
            black.setFitWidth((int)(double)newVal/dimension-2);
        });

        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            white.setFitHeight((int)(double)newVal/dimension-2);
            black.setFitHeight((int)(double)newVal/dimension-2);
        });

        stage.setTitle("Checkers 100");
        stage.setScene(scene);
        stage.show();
        System.out.println("this is just a git test");
        System.out.println("this is another a git test");
    }

    public static void main(final String[] args) {
        launch();
    }

}
