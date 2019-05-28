import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.Random;


public class ComputerRandom implements Runnable{

    private ArrayList<Pane> playground = new ArrayList<>();
    private Game game;

    public ComputerRandom(Game game){
        this.game = game;
        playground = game.getPlayground();
    }

    @Override
    public void run() {

    }

    public void generateRandomMove(ArrayList<ArrayList> moves){
        ArrayList<Piece> posPieces = new ArrayList<>();

        if(game.getBlackPlayer().checkOptions() > 0){
            game.getBlackPlayer().checkPulls();
        }

        Random randomPiece = new Random();
        Random randomMove = new Random();

        int rp = randomPiece.nextInt(moves.size());
        ImageView checker = (ImageView) playground.get((int)moves.get(rp).get(0)).getChildren();
        Pane pane = playground.get(playground.indexOf(checker));

        game.animateMove(checker, pane, moves.get(rp), 0 );
    }

}

