import java.util.ArrayList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;

public class Game {

    private boolean turn;
    private final int dimension, sizeWindow;
    private final Player blackPlayer, whitePlayer;
    private ArrayList<Pane> playground;

    public Game(Stage stage) {
        turn = true;
        dimension = 10;
        sizeWindow = 500;
        playground = new ArrayList<>();

        GridPane grid = new GridPane();
        grid.getStyleClass().add("game-grid");

        //Set responsive Grid-Layout for the Playground Window
        for (int i = 0; i < dimension; i++) {
            ColumnConstraints column = new ColumnConstraints();
            RowConstraints row = new RowConstraints();

            column.setPercentWidth(100/dimension);
            row.setPercentHeight(100/dimension);

            grid.getColumnConstraints().add(column);
            grid.getRowConstraints().add(row);
        }

        //Create Playground (for each Field a new Pane)
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                Pane pane = new Pane();
                pane.getStyleClass().add("game-grid-cell");

                //Set tags for Styling-Sheet
                if ((i & 1) == 0) {
                    if ((j & 1) == 0) { pane.getStyleClass().add("white"); }
                    else { pane.getStyleClass().add("black"); }
                } else {
                    if ((j & 1) == 0) { pane.getStyleClass().add("black"); }
                    else { pane.getStyleClass().add("white"); }
                }
                if (i == 0) { pane.getStyleClass().add("first-row"); }
                if (j == 0) { pane.getStyleClass().add("first-column"); }

                //Save all Pane in the Playground List
                playground.add(pane);
                grid.add(pane, j, i);
            }
        }

        //Add Styling-Sheet to the main Window
        Scene scene = new Scene(grid, sizeWindow, sizeWindow);
        scene.getStylesheets().add(getClass().getResource("playground.css").toExternalForm());

        //Define all Images for the Game-Pieces
        Image white = new Image(getClass().getResourceAsStream("white.png"));
        Image black = new Image(getClass().getResourceAsStream("black.png"));
        //wip: King Images

        //Create Players
        blackPlayer = new Player(this, black, 0, 1);
        whitePlayer = new Player(this, white, 60, -1);

        //Change Image-Size if Window changed
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            blackPlayer.getPieces().forEach(n -> n.setFitWidth((int)(double)newVal/dimension-2));
            whitePlayer.getPieces().forEach(n -> n.setFitWidth((int)(double)newVal/dimension-2));
        });
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            blackPlayer.getPieces().forEach(n -> n.setFitHeight((int)(double)newVal/dimension-2));
            whitePlayer.getPieces().forEach(n -> n.setFitHeight((int)(double)newVal/dimension-2));
        });

        stage.setScene(scene);
    }

    //Getter-Methods
    public ArrayList<Pane> getPlayground() { return playground; }
    public int getDimension() { return dimension; }
    public int getSizeWindow() { return sizeWindow; }

    //wip..
    public void nextTurn() {
        //True: Turn Black / False: Turn White
        if (turn) {
            blackPlayer.pull();
            turn = false;
        } else {
            whitePlayer.pull();
            turn = true;
        }

    }

}
