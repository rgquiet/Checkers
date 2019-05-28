import java.util.ArrayList;
import javafx.util.Duration;
import javafx.geometry.Bounds;
import javafx.animation.PathTransition;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.scene.input.MouseEvent;

public class Game {

    private final int dimension, sizeWindow;
    private boolean white;
    private int counter;
    private Scene scene;
    private ChangeListener onHover;
    private Piece selected;
    private Player blackPlayer, whitePlayer;
    private ArrayList<Integer> h1, h2, h3, steps;
    private ArrayList<Pane> playground;

    public Game(Stage stage) {
        white = true;
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
                //wip: Refactoring (looks like shit)
                //Check if mouse hover over the marked field
                String selectedName = ov.toString().split("bean: ")[1].split("style")[0];
                selectedName = selectedName.substring(0, selectedName.length()-1);
                for (ArrayList<Integer> n: selected.getOptions()) {
                    String fieldName = playground.get(n.get(n.size()-1)).toString().split("style")[0];
                    fieldName = fieldName.substring(0, fieldName.length()-1);
                    if(fieldName.equals(selectedName)) {
                        if ((boolean)ov.getValue()) {
                            //Hover over
                            for(int i = 0; i < n.size(); i++) {
                                if(i != 0) { setStyleH3(n.get(i-1), n.get(i)); }
                            }
                            playground.get(n.get(n.size()-1)).setOnMouseClicked((MouseEvent e) -> {
                                //Execute move animation to the target field
                                steps = new ArrayList<>(h3);
                                steps.add(0, playground.indexOf(selected.getParent()));
                                moveAnimation(selected);

                                //Reset all Style tags
                                clearStyleH1();
                                clearStyleH2();
                                clearStyleH3();
                            });
                        } else {
                            //Hover away
                            clearStyleH3();
                        }
                    }
                }
            }
        };
    }

    //Getter-Methods
    public int getDimension() { return dimension; }
    public int getSizeWindow() { return sizeWindow; }
    public Player getBlackPlayer() { return blackPlayer; }
    public Player getWhitePlayer() { return whitePlayer; }
    public ArrayList<Pane> getPlayground() { return playground; }

    //Setter-Methods
    public void setSelected(Piece piece) { selected = piece; }
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
        selected.getPlayer().getPossiblePieces().forEach((k, v) -> {
            k.removeOnMouseClick();
            k.getOptions().clear();
        });
        selected.getPlayer().getPossiblePieces().clear();
        h1.forEach(n -> playground.get(n).getStyleClass().remove("h1"));
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
        playground.get(h3.get(h3.size()-1)).setOnMouseClicked(null);
        h3.forEach(n -> playground.get(n).getStyleClass().remove("h3"));
        h3.clear();
    }

    public void createPlayers(ArrayList<Integer> blackPos, ArrayList<Integer> whitePos) {
        //Define all Images for the Game-Pieces
        Image blackChecker = new Image(getClass().getResourceAsStream("blackChecker.png"));
        Image blackKing = new Image(getClass().getResourceAsStream("blackKing.png"));
        Image whiteChecker = new Image(getClass().getResourceAsStream("whiteChecker.png"));
        Image whiteKing = new Image(getClass().getResourceAsStream("whiteKing.png"));

        //Create Players
        if (blackPos.size() == 0 && whitePos.size() == 0) {
            blackPlayer = new Player(this, scene, blackChecker, blackKing, 1, 0);
            whitePlayer = new Player(this, scene, whiteChecker, whiteKing, -1, 60);
        } else {
            //For JUnit Tests
            blackPlayer = new Player(this, scene, blackChecker, blackKing, 1, blackPos);
            whitePlayer = new Player(this, scene, whiteChecker, whiteKing, -1, whitePos);
        }
    }

    public void nextTurn(){
        if (white) {
            whitePlayer.checkOptions();
        } else {
            blackPlayer.checkOptions();
        }
        white = !white;
    }

    private void moveAnimation(ImageView piece) {
        piece.getParent().toFront();
        PathTransition transition = new PathTransition();
        transition.setDuration(Duration.seconds(0.5));
        transition.setNode(piece);

        counter = 0;
        transition.setOnFinished(actionEvent -> {
            if (counter > 0) {
                //Kill the enemy's piece at the current position
                Pane field = playground.get(steps.get(counter));
                if (!field.getChildren().isEmpty()) {
                    Player enemy;
                    if (white) { enemy = whitePlayer; }
                    else { enemy = blackPlayer; }
                    enemy.getPieces().remove(enemy.getPieces().indexOf(field.getChildren().get(0)));
                    field.getChildren().clear();
                }
            }
            counter++;
            if (counter < steps.size()) {
                //Sets next animation step and execute
                transition.setPath(getNewLine(piece,
                        playground.get(steps.get(counter-1)),
                        playground.get(steps.get(counter))));
                transition.play();
            } else {
                //Animation done: Add piece to new pane
                playground.get(steps.get(0)).getChildren().clear();
                piece.setTranslateX(0);
                piece.setTranslateY(0);
                int endPos = steps.get(steps.size()-1);
                playground.get(endPos).getChildren().add(piece);

                if (selected instanceof Checker) {
                    Checker checker = (Checker)selected;
                    if (checker.otherside(endPos, dimension)) {
                        //Checker becomes King
                        selected.getPlayer().setKing(checker);
                    }
                }

                selected = null;
                nextTurn();
            }
        });
        transition.play();
    }

    public Line getNewLine(ImageView piece, Pane from, Pane to) {
        Bounds start = piece.getParent().localToParent(piece.getBoundsInLocal());
        Bounds source = from.getBoundsInParent();
        Bounds target = to.getBoundsInParent();

        double width = start.getWidth();
        double height = start.getHeight();

        Line line = new Line();
        line.setStartX(source.getMinX() - start.getMinX() + width / 2);
        line.setStartY(source.getMinY() - start.getMinY() + height / 2);
        line.setEndX(target.getMinX() - start.getMinX() + width / 2);
        line.setEndY(target.getMinY() - start.getMinY() + height / 2);

        return line;
    }

}
