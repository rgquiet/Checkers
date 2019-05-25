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

    public void generateRandomMove(){
        ArrayList<Piece> posPieces = new ArrayList<>();

        if(game.getBlackPlayer().checkOptions() > 0){
            game.getBlackPlayer().checkPulls();
        }
        for(Piece p: game.getBlackPlayer().getPieces()){
            if(p.getOptions().size() > 0){
                posPieces.add(p);
            }
        }
        Random randomPiece = new Random();
        Random randomMove = new Random();

        int rp = randomPiece.nextInt(posPieces.size());
        int rm = randomMove.nextInt(posPieces.get(rp).getOptions().size());
        ImageView checker = posPieces.get(rp);
        Pane pane = playground.get(playground.indexOf(checker));

        game.animateMove(checker, pane, posPieces.get(rp).getOptions().get(rm), 0 );
    }

}
