package ftoop_checkers_guedel;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
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
            Checker checker = new Checker(checkerImg, kingImg, this, direction);
            checker.setFitWidth(game.getWidthWindow()/game.getDimension()-2);
            checker.setFitHeight(game.getHeightWindow()/game.getDimension()-2);

            game.getPlayground().get(start).getChildren().add(checker);
            pieces.add(checker);
            i--;
        }

        //Change Image-Size if Window changed
        scene.widthProperty().addListener((obs, oldVal, newVal) -> pieces.forEach(n -> n.setFitWidth((int)(double)newVal/game.getDimension()-2)));
        scene.heightProperty().addListener((obs, oldVal, newVal) -> pieces.forEach(n -> n.setFitHeight((int)(double)newVal/game.getDimension()-2)));
    }

    public Player(Game game, Scene scene, Image checkerImg, Image kingImg, int direction, List<Integer> testing) {
        biggest = 0;
        possiblePieces = new HashMap<>();
        this.checkerImg = checkerImg;
        this.kingImg = kingImg;
        this.game = game;

        //Create pieces for current Player (just for testing)
        pieces = new ArrayList<>();
        testing.forEach(n -> {
            Checker checker = new Checker(checkerImg, kingImg, this, direction);
            checker.setFitWidth(game.getWidthWindow()/game.getDimension()-2);
            checker.setFitHeight(game.getHeightWindow()/game.getDimension()-2);

            game.getPlayground().get(n).getChildren().add(checker);
            pieces.add(checker);
        });

        //Change Image-Size if Window changed
        scene.widthProperty().addListener((obs, oldVal, newVal) -> pieces.forEach(n -> n.setFitWidth((int)(double)newVal/game.getDimension()-2)));
        scene.heightProperty().addListener((obs, oldVal, newVal) -> pieces.forEach(n -> n.setFitHeight((int)(double)newVal/game.getDimension()-2)));
    }

    //Getter-Methods
    public Game getGame() { return game; }
    public List<Piece> getPieces() { return pieces; }
    public Map<Piece, Integer> getPossiblePieces() { return possiblePieces; }

    //Setter-Methods
    public void setKing(Checker checker) {
        King king = new King(kingImg, checkerImg, this);
        king.setFitWidth(game.getWidthWindow()/game.getDimension()-2);
        king.setFitHeight(game.getHeightWindow()/game.getDimension()-2);

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
            if (n.jump(start)) {
                boolean repeat = true;
                while (repeat) {
                    repeat = false;
                    ArrayList<ArrayList> pos = new ArrayList<>(n.getOptions());
                    for (ArrayList p: pos) {
                        if(n.jump(p)) { repeat = true; }
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

        if(possiblePieces.size() == 0){
            if (game.getWhite()){
                game.showWinner("Schwarz");
            }
            else{
                game.showWinner("Weiss");
            }
        }

        biggest = 0;
    }

}
