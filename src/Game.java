import java.util.ArrayList;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
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
import javafx.util.Duration;

public class Game {

    private final int dimension, sizeWindow;
    private Scene scene;
    private ChangeListener onHover;
    private Piece selected;
    private Player blackPlayer, whitePlayer;
    private ArrayList<Integer> h1, h2, h3;
    private ArrayList<Pane> playground;

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
                                //wip: Insert code for animation here
                                //to move = selected
                                //target = playground.get(n.get(n.size()-1))
                                ArrayList steps = new ArrayList<>();

                                //Überprüft welche Liste das Ziel enthaltet
                                for(int i = 0 ; i < selected.getOptions().size(); i++){
                                    if(playground.get(n.get(n.size()-1)) == playground.get((int)selected.getOptions().get(i).get(selected.getOptions().get(i).size() - 1))){
                                        for(int j = 1; j < selected.getOptions().get(i).size(); j++){
                                            steps.add(selected.getOptions().get(i).get(j));
                                        }
                                    }
                                }

                                animateMove(selected, playground.get((int)steps.get(0)), steps, 0);


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


    void animateMove (ImageView checker, Pane pane, ArrayList steps, int i){

        //Gets X and Y Values of the pane and Checker

        Bounds boundsInScene = checker.getParent().localToParent(checker.getBoundsInLocal());
        Bounds targetInScene = pane.getBoundsInParent();


        //Defines the Path for the Animation and Executes it

        Line line = new Line(
                (boundsInScene.getMaxX() - boundsInScene.getMinX()) / 2,
                (targetInScene.getMaxY() - targetInScene.getMinY()) / 2,
                (targetInScene.getMaxX() - ((targetInScene.getMaxX() - targetInScene.getMinX()) / 2)) - (boundsInScene.getMaxX() - ((boundsInScene.getMaxX() - boundsInScene.getMinX()) / 2)) + ((targetInScene.getMaxX() - targetInScene.getMinX()) / 2),
                (targetInScene.getMaxY() - ((targetInScene.getMaxY() - targetInScene.getMinY()) / 2)) - (boundsInScene.getMaxY() - ((boundsInScene.getMaxY() - boundsInScene.getMinY()) / 2)) + ((targetInScene.getMaxY() - targetInScene.getMinY()) / 2));
        PathTransition transition = new PathTransition();
        checker.getParent().toFront();
        transition.setNode(checker);
        transition.setDuration(Duration.seconds(1));
        transition.setPath(line);

        // Starts once Animation is done
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(selected instanceof Checker){
                    int removeChecker = 999;
                    //Überprüft ob Start kleiner als Ziel ist
                    if(playground.indexOf(selected.getParent()) < playground.indexOf(pane)){
                        for(int i = 1; i <= 9; i++) {
                            if((playground.indexOf(selected.getParent()) + (11 * i) ==  playground.indexOf(pane))){
                                removeChecker = playground.indexOf(pane) - 11;
                            }
                        }
                        if(removeChecker == 999){
                            removeChecker = playground.indexOf(pane) - 9;
                        }
                    }
                    else {
                        for(int i = 1; i <= 9; i++) {
                            if((playground.indexOf(selected.getParent()) - (11 * i) ==  playground.indexOf(pane))){
                                removeChecker = playground.indexOf(pane) + 11;
                            }
                        }
                        if(removeChecker == 999){
                            removeChecker = playground.indexOf(pane) + 9;
                        }
                    }
                    playground.get(removeChecker).getChildren().removeAll(playground.get(removeChecker).getChildren());


                    Image blackChecker = new Image(getClass().getResourceAsStream("blackChecker.png"));
                    Checker checker = new Checker(blackChecker, 1, blackPlayer);
                    checker.setFitHeight(getSizeWindow()/getDimension()-2);
                    checker.setFitWidth(getSizeWindow()/getDimension()-2);
                    ((Pane)selected.getParent()).getChildren().remove(selected);
                    pane.getChildren().add(checker);
                    blackPlayer.removePiece(selected);
                    blackPlayer.addPiece(checker);
                    selected = checker;

                }
                else if(selected instanceof King){

                }

                checkForNextStep(selected, steps, i);
            }
        });

        transition.play();
    }

    public void checkForNextStep(ImageView checker, ArrayList steps, int i){
        if(steps.get(i) != steps.get(steps.size() - 1)){
            i++;
            animateMove(checker, playground.get((int)steps.get(i)), steps, i);
        }
    }
}
