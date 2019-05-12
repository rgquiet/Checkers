import java.util.ArrayList;
import javafx.scene.image.Image;

public class King extends Piece {

    public King(Image img, int direction, Player player) {
        super(img, direction, player);
    }

    @Override
    void pull() {
        //wip..
    }

    @Override
    boolean jump(ArrayList<Integer> start, Image king) {
        ArrayList<ArrayList> posMoves = new ArrayList<ArrayList>();
        // start = int of field on Playground
        ArrayList<Integer> steps = new ArrayList<Integer>();
        steps.add(start.get(0));

        //Check Northeast
        checkNorthEast(steps, steps.get(steps.size() - 1));
        //Check Southeast

        //Check Southwest

        //Check Northwest


        return false;
    }

    boolean checkNorthEast(ArrayList steps, Integer start){
        for(int i = 1; i < 10; i++){
            if(!getPlayer().getGame().getPlayground().get(start + i * 11).getChildren().isEmpty() && getPlayer().getGame().getPlayground().get(start + (i + 1) * 11).getChildren().isEmpty()){
                Piece target = (Piece)getPlayer().getGame().getPlayground().get(start + i * 11).getChildren();
                if(target.getPlayer() != getPlayer()){
                    steps.add(getPlayer().getGame().getPlayground().indexOf(getPlayer().getGame().getPlayground().get(start + (i + 1) * 11)));
                    checkNorthEast(steps,(int)steps.get(steps.size() - 1));
                    return true;
                }
                else{
                    break;
                }
            }
        }
        return false;
    }

}
