package ftoop_checkers_guedel;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

abstract class Piece extends ImageView {

    private Image buddy;
    private Player player;
    private ArrayList<ArrayList> options;

    public Piece(Image img, Image buddy, Player player) {
        super(img);
        this.buddy = buddy;
        this.player = player;
        options = new ArrayList<>();
    }

    //Abstract-Methods
    abstract void pull(int startPos);
    abstract boolean jump(ArrayList<Integer> start);

    //Getter-Methods
    public Image getBuddy() { return buddy; }
    public Player getPlayer() { return player; }
    public List<ArrayList> getOptions () { return options; }

    //Setter-Methods
    public void setOnMouseClick() {
        setOnMouseClicked((MouseEvent e) -> {
            player.getGame().setSelected(this);
            player.getGame().clearStyleH2();
            options.forEach(n -> player.getGame().setStyleH2((int)n.get(n.size()-1)));
        });
    }

    public void removeOnMouseClick() {
        setOnMouseClicked(null);
    }

    public void removeSmallerOptions() {
        //Keep only the biggest List
        int biggest = 0;
        for (int i = 0; i < getOptions().size(); i++) {
            if (biggest < getOptions().get(i).size()) { biggest = getOptions().get(i).size(); }
        }
        for (Iterator<ArrayList> it = getOptions().iterator(); it.hasNext();) {
            if (it.next().size() < biggest) { it.remove(); }
        }
    }

    public boolean up(int pos) {
        return pos < getPlayer().getGame().getDimension();
    }

    public boolean down(int pos) {
        int dimension = getPlayer().getGame().getDimension();
        return pos >= dimension*dimension - dimension;
    }

    public boolean left(int pos) {
        return pos % getPlayer().getGame().getDimension() == 0;
    }

    public boolean right(int pos) {
        return (pos+1) % getPlayer().getGame().getDimension() == 0;
    }

}
