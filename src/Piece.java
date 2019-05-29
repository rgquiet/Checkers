import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

abstract class Piece extends ImageView {

    private Player player;
    private ArrayList<ArrayList> options;

    public Piece(Image img, Player player) {
        super(img);
        this.player = player;
        options = new ArrayList<>();
    }

    //Abstract-Methods
    abstract void pull(int startPos);
    abstract boolean jump(ArrayList<Integer> start, Image piece);

    //Getter-Methods
    public Player getPlayer() { return player; }
    public ArrayList<ArrayList> getOptions () { return options; }

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
