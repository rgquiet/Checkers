import java.lang.reflect.Array;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import org.omg.PortableInterceptor.INACTIVE;

public class King extends Piece {

    ArrayList<Pane> playground = getPlayer().getGame().getPlayground();

    public King(Image img, int direction, Player player) {
        super(img, direction, player);
    }

    @Override
    void pull() {
        //wip..
    }

    @Override
    boolean jump(ArrayList<Integer> start, Image king) {

        int directions[] = {-9, 11, 9, -11};



        for(int d : directions){
            if(movePossible(start.get(0),d)){
                ArrayList<Integer> pos = new ArrayList<>();
                pos.add(start.get(0));
                pos.add(start.get(0) + d * 2);
                getOptions().add(pos);
                for (int sd : directions){
                    if(d == sd * -1){

                    }
                }
            }
        }

        // start = int of field on Playground
        ArrayList<Integer> steps = new ArrayList<Integer>();
        steps.add(start.get(0));
        getOptions().add(steps);

        //Check Northeast

        //Check Southeast

        //Check Southwest

        //Check Northwest


        return false;
    }


    // Hilfsmethoden, sind untereinander voneinander abhänging


    //überprüft ob das springen in eine Richtung möglich ist

    boolean movePossible(int start, int direction){

        for(int i = 1; i < 10; i++){
            if(!playground.get(start + i * direction).getChildren().isEmpty() && playground.get(start + (i + 1) * 11).getChildren().isEmpty()){
                Piece target = (Piece)playground.get(start + i * direction).getChildren();
                if(target.getPlayer() != getPlayer()){
                    return true;
                }
                else{
                    break;
                }
            }
        }
        return false;
    }


    //Returned einen List mit allen möglichen landungsfeldern

    ArrayList getPossibleFields(int start, int direction){
        ArrayList<Integer> possible = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            int posTarget = playground.indexOf(playground.get(start + (i + 1) * direction));
            if(!playground.get(start + i * direction).getChildren().isEmpty() && posTarget > 0 && posTarget < 99){
                possible.add(playground.indexOf(playground.get(start + (i + 1) * direction)));
            }
        }
        return possible;
    }


    ArrayList generateList(ArrayList<Integer> possible, ArrayList<Integer> steps){

    }

    ArrayList getKilledCheckers(ArrayList<Integer> steps){
        ArrayList<Integer> killed = new ArrayList<>();
        for(int i = 0; i < steps.size() - 1; i++){
            int start = steps.get(i);
            int target = steps.get(i + 1);
            int direction;

            //Findet die Richtung des Sprungs
            if (start > target) {
                if(start % 11 == target % 11){
                    direction = 11;
                }
                else {
                    direction = 9;
                }
            }
            else {
                if(start % 11 == target % 11){
                    direction = -11;
                }
                else
                    direction = -9;
            }

                //Searches for slain Checker and add it to list
                for(int j = 0; j < 10; j++){
                    if(!playground.get(start + i * direction).getChildren().isEmpty()){
                        killed.add(start + i * direction);
                        break;
                    }
                }
            }
        return killed;
        }



}
