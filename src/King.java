import java.lang.reflect.Array;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import org.omg.PortableInterceptor.INACTIVE;

public class King extends Piece {

    private ArrayList<Path> paths, newPaths, toRemove = new ArrayList<>();

    public King(Image img, int direction, Player player) {
        super(img, direction, player);
        paths = new ArrayList<Path>();
        newPaths = new ArrayList<Path>();
        toRemove = new ArrayList<Path>();

    }

    //Setter Methods

    public void addPath(Path path){
        newPaths.add(path);
    }
    public void removePath(Path path){
        toRemove.remove(path);
    }


    @Override
    void pull() {
        ArrayList<Pane> playground = getPlayer().getGame().getPlayground();
        int start = playground.indexOf(this.getParent());
        int[] directions = {-9, 11, 9, -11};
        getOptions().clear();

        for(int d : directions){
            for (int i = 1; start + i * d < 100 && start + i * d >= 0; i++) {
                ArrayList<Integer> pos = new ArrayList<>();
                int target = start + (i * d);

                if ((start % 10 == 0 && (d == 9 || d == -11)) || (start % 10 == 9 && (d == -9 || d == 11)) || !playground.get(start + i * d).getChildren().isEmpty()){
                    break;
                }

                if(target % 10 == 0 || target % 10 == 9){
                    pos.add(start);
                    pos.add(target);
                    getOptions().add(pos);
                    break;
                }

                if(playground.get(start + i * d).getChildren().isEmpty()){
                    pos.add(start);
                    pos.add(target);
                    getOptions().add(pos);
                }
            }
        }
        super.getPlayer().getGame().setStyleH1(start);
        super.setOnMouseClick();
    }

    @Override
    boolean jump(ArrayList<Integer> start) {

        Path path = new Path(start, this);
        ArrayList<ArrayList> toRemove = new ArrayList<>();
        paths.add(path);
        boolean more = true;
        getOptions().clear();

        while(more){
            more = nextStep();
        }

        for (Path p : paths){
            if(p.getPath().size() > 1) {
                getOptions().add(p.getPath());
            }
        }

        //Removes all duplicate Arraylists
        for(int i = 0; i < getOptions().size(); i++){
            for(int j = i + 1; j < getOptions().size(); j++){
                if(getOptions().get(i) != getOptions().get(j) && getOptions().get(i).equals(getOptions().get(j))){
                    toRemove.add(getOptions().get(j));
                }
            }
        }

        for(ArrayList list : toRemove){
            getOptions().remove(list);
        }

        return false;
    }


    public boolean nextStep(){
        boolean more = false;
        for(Path p : paths){
            if(p.jumpPossible()){
                more = true;

            }
        }
        transferList(more);
        return more;
    }


    public void transferList(boolean more){
        if(more) {
            paths.clear();
            paths.addAll(newPaths);
            newPaths.clear();
        }
        else{
            newPaths.clear();
        }
    }
}
