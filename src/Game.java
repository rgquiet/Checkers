import java.util.ArrayList;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.input.MouseEvent;

public class Game {

    private final int dimension, sizeWindow;
    private Player blackPlayer, whitePlayer;
    private Checker selected;
    private ArrayList<Integer> h1, h2, h3;
    private ArrayList<Pane> playground;
    private Scene scene;
    private ChangeListener onHover;

    public Game(Stage stage) {
        dimension = 10;
        sizeWindow = 500;
        selected = null;
        h1 = new ArrayList<>();
        h2 = new ArrayList<>();
        h3 = new ArrayList<>();
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

        //Initialize Change Listener
        onHover = new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object oldValue, Object newValue) {
                //Check if mouse hover over the marked field
                String fieldName = null;
                String selectedName = ov.toString().split("bean: ")[1].split("style")[0];
                selectedName = selectedName.substring(0, selectedName.length()-1);
                for (ArrayList<Integer> n: selected.getOptions()) {
                    fieldName = playground.get((int)n.get(n.size()-1)).toString().split("style")[0];
                    fieldName = fieldName.substring(0, fieldName.length()-1);
                    if(fieldName.equals(selectedName)) {
                        if ((boolean)ov.getValue()) {
                            //Hover over
                            for(int i = 0; i < n.size(); i++) {
                                if(i != 0) { setStyleH3(n.get(i-1), n.get(i)); }
                            }
                            playground.get(n.get(n.size()-1)).setOnMouseClicked((MouseEvent e) -> {
                                //wip: Insert code for animation here

                                //wip: Reset everything Routine
                                clearStyleH1();
                            });
                        } else {
                            //Hover away
                            playground.get(n.get(n.size()-1)).setOnMouseClicked(null);
                            clearStyleH3();
                        }
                    }
                }
            }
        };
    }

    //Getter-Methods
    public Player getBlackPlayer() { return blackPlayer; }
    public Player getWhitePlayer() { return whitePlayer; }
    public ArrayList<Pane> getPlayground() { return playground; }
    public int getDimension() { return dimension; }
    public int getSizeWindow() { return sizeWindow; }

    //Setter-Methods
    public void setSelected(Checker piece) { selected = piece; }
    public void setStyleH1(Integer field) {
        //Set style tag h1
        playground.get(field).getStyleClass().add("h1");
        h1.add(field);
    }
    public void setStyleH2(Integer field) {
        //Set style tag h2
        playground.get(field).getStyleClass().add("h2");
        playground.get(field).hoverProperty().addListener(onHover);
        h2.add(field);
    }
    public void setStyleH3(Integer from, Integer to) {
        //Save direction between int "from" -> "to"
        Integer i = 0;
        if(from < to) {
            if ((to - from) % (dimension - 1) == 0) { i = dimension - 1; }
            else if ((to - from) % (dimension + 1) == 0) { i = dimension + 1; }
        } else {
            if ((from - to) % (dimension - 1) == 0) { i = -dimension + 1; }
            else if ((from - to) % (dimension + 1) == 0) { i = -dimension - 1; }
        }

        //Set style tag h3
        while (from != to && i != 0) {
            from = from + i;
            playground.get(from).getStyleClass().add("h3");
            h3.add(from);
        }
    }

    public void clearStyleH1() {
        h1.forEach(n -> {
            playground.get(n).getStyleClass().remove("h1");
            //wip...
            Checker piece = (Checker)playground.get(n).getChildren();
            piece.removeOnMouseClick();
        });
        h1.clear();
    }

    public void clearStyleH2() {
        h2.forEach(n -> {
            playground.get(n).getStyleClass().remove("h2");
            playground.get(n).hoverProperty().removeListener(onHover);
        });
        h2.clear();
    }

    public void clearStyleH3() {
        h3.forEach(n -> playground.get(n).getStyleClass().remove("h3"));
        h3.clear();
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

}
