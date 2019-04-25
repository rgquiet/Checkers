import java.util.ArrayList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;

public class Game {

    private final int dimension, sizeWindow;
    private Player blackPlayer, whitePlayer;
    private ArrayList<Pane> playground;
    private Scene scene;

    public Game(Stage stage) {
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
        scene = new Scene(grid, sizeWindow, sizeWindow);
        scene.getStylesheets().add(getClass().getResource("playground.css").toExternalForm());
        stage.setScene(scene);
    }

    public void createPlayers(ArrayList<Integer> blackPos, ArrayList<Integer> whitePos) {
        //Define all Images for the Game-Pieces
        Image black = new Image(getClass().getResourceAsStream("black.png"));
        Image blackKing = new Image(getClass().getResourceAsStream("blackKing.png"));
        Image white = new Image(getClass().getResourceAsStream("white.png"));
        Image whiteKing = new Image(getClass().getResourceAsStream("whiteKing.png"));

        //Create Players
        if (blackPos.size() == 0 && whitePos.size() == 0) {
            blackPlayer = new Player(black, blackKing, this, 1, 0);
            whitePlayer = new Player(white, whiteKing, this, -1, 60);
        } else {
            //For JUnit Tests
            blackPlayer = new Player(black, blackKing, this, 1, blackPos);
            whitePlayer = new Player(white, whiteKing, this, -1, whitePos);
        }

        //Change Image-Size if Window changed
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            blackPlayer.getPieces().forEach(n -> n.setFitWidth((int)(double)newVal/dimension-2));
            whitePlayer.getPieces().forEach(n -> n.setFitWidth((int)(double)newVal/dimension-2));
        });
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            blackPlayer.getPieces().forEach(n -> n.setFitHeight((int)(double)newVal/dimension-2));
            whitePlayer.getPieces().forEach(n -> n.setFitHeight((int)(double)newVal/dimension-2));
        });
    }

    //Getter-Methods
    public Player getBlackPlayer() { return blackPlayer; }
    public Player getWhitePlayer() { return whitePlayer; }
    public ArrayList<Pane> getPlayground() { return playground; }
    public int getDimension() { return dimension; }
    public int getSizeWindow() { return sizeWindow; }

}
