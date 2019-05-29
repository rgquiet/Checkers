import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Player {

    private int biggest;
    private Game game;
    private ArrayList<Piece> pieces;
    private HashMap<Piece, Integer> possiblePieces;
    private final Image checkerImg, kingImg;

    public Player(Game game, Scene scene, Image checkerImg, Image kingImg, int direction, int start) {
        biggest = 0;
        possiblePieces = new HashMap<>();
        this.checkerImg = checkerImg;
        this.kingImg = kingImg;
        this.game = game;

        //Create 20 pieces for current Player at the right start position
        pieces = new ArrayList<>();
        for (int i = 20; start < start+i; start += 2) {
            //New Row
            if (pieces.size() % 5 == 0) {
                //Even
                if (pieces.size()/5 % 2 == 0) { start += 1; }
                //Odd
                else { start -= 1; }
            }
            Checker checker = new Checker(checkerImg, this, direction);
            checker.setFitHeight(game.getSizeWindow()/game.getDimension()-2);
            checker.setFitWidth(game.getSizeWindow()/game.getDimension()-2);

            game.getPlayground().get(start).getChildren().add(checker);
            pieces.add(checker);
            i--;
        }

        //Change Image-Size if Window changed
        scene.widthProperty().addListener((obs, oldVal, newVal) -> pieces.forEach(n -> n.setFitWidth((int)(double)newVal/game.getDimension()-2)));
        scene.heightProperty().addListener((obs, oldVal, newVal) -> pieces.forEach(n -> n.setFitHeight((int)(double)newVal/game.getDimension()-2)));
    }

    public Player(Game game, Scene scene, Image checkerImg, Image kingImg, int direction, ArrayList<Integer> testing) {
        biggest = 0;
        possiblePieces = new HashMap<>();
        this.checkerImg = checkerImg;
        this.kingImg = kingImg;
        this.game = game;

        //Create pieces for current Player (just for testing)
        pieces = new ArrayList<>();
        testing.forEach(n -> {
            Checker checker = new Checker(checkerImg, this, direction);
            checker.setFitHeight(game.getSizeWindow()/game.getDimension()-2);
            checker.setFitWidth(game.getSizeWindow()/game.getDimension()-2);

            game.getPlayground().get(n).getChildren().add(checker);
            pieces.add(checker);
        });

        //Change Image-Size if Window changed
        scene.widthProperty().addListener((obs, oldVal, newVal) -> pieces.forEach(n -> n.setFitWidth((int)(double)newVal/game.getDimension()-2)));
        scene.heightProperty().addListener((obs, oldVal, newVal) -> pieces.forEach(n -> n.setFitHeight((int)(double)newVal/game.getDimension()-2)));
    }

    //Getter-Methods
    public Game getGame() { return game; }
    public ArrayList<Piece> getPieces() { return pieces; }
    public HashMap<Piece, Integer> getPossiblePieces() { return possiblePieces; }

    //Setter-Methods
    public void setKing(Checker checker) {
        King king = new King(kingImg, this);
        king.setFitHeight(game.getSizeWindow()/game.getDimension()-2);
        king.setFitWidth(game.getSizeWindow()/game.getDimension()-2);

        int i = game.getPlayground().indexOf(checker.getParent());
        game.getPlayground().get(i).getChildren().remove(checker);
        game.getPlayground().get(i).getChildren().add(king);
        pieces.remove(checker);
        pieces.add(king);
    }

    public void checkOptions() {
        for (Piece n: pieces) {
            ArrayList<Integer> start = new ArrayList<>();
            start.add(game.getPlayground().indexOf(n.getParent()));

            //Fills the ArrayList options for each piece
            //wip: Refactoring (checkerImg by King!)
            if (n.jump(start, kingImg)) {
                boolean repeat = true;
                while (repeat) {
                    repeat = false;
                    ArrayList<ArrayList> pos = new ArrayList<>(n.getOptions());
                    for (ArrayList p: pos) {
                        if(n.jump(p, kingImg)) { repeat = true; }
                    }
                }
                if (n.getOptions().get(0).size() > biggest) { biggest = n.getOptions().get(0).size(); }
                possiblePieces.put(n, n.getOptions().get(0).size());
            }
        }

        //Holds only the pieces, which do the most kills in this turn
        if (possiblePieces.size() > 0) {
            possiblePieces.entrySet().removeIf(e -> {
                if (e.getValue() < biggest) {
                    e.getKey().getOptions().clear();
                    return true;
                } else {
                    game.setStyleH1((int)e.getKey().getOptions().get(0).get(0));
                    e.getKey().setOnMouseClick();
                    return false;
                }
            });
        } else {
            //Nobody can make a kill
            for (Piece n: pieces) {
                n.pull(game.getPlayground().indexOf(n.getParent()));
                if (!n.getOptions().isEmpty()) {
                    possiblePieces.put(n, n.getOptions().get(0).size());
                    game.setStyleH1((int)n.getOptions().get(0).get(0));
                    n.setOnMouseClick();
                }
            }
        }

        biggest = 0;
    }

}
