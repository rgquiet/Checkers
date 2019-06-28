package ftoop_checkers_guedel;

import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ComputerRandom implements Runnable{

    private List<Pane> playground = new ArrayList<>();
    private Game game;
    private ImageView checker;

    public ComputerRandom(Game game){
        this.game = game;
        playground = game.getPlayground();
    }

    @Override
    public void run() {
        generateRandomMove();
    }

    public void generateRandomMove(){

        Random randomPiece = new Random();
        Random randomMove = new Random();


        // Wählt einen Zufälligen Stein aus

        int rp = randomPiece.nextInt(game.getH1().size());

        checker = (ImageView) playground.get(game.getH1().get(rp)).getChildren().get(0);
        Piece piece = (Piece) playground.get(game.getH1().get(rp)).getChildren().get(0);

        // Wählt einen Zufälligen möglichen Spielzug aus

        int rm = randomMove.nextInt(piece.getOptions().size());

        game.setStyleH3((int) piece.getOptions().get(rm).get(0), (int) piece.getOptions().get(rm).get(piece.getOptions().get(rm).size() - 1));
        game.setSteps((int)piece.getOptions().get(rm).get(0));
        game.setSelected(piece);

        try {
            game.clearStyleH1();
            game.clearStyleH3();
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Ein Fehler ist aufgetreten");
        }
        catch (Exception e){
            System.out.println(e);
        }
        game.moveAnimation(checker);

    }

}

