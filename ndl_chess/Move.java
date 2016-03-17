package ndl_chess;
/**
 *
 * @author newdeviceslab_2
 */
public class Move {

    private Point from;
    private Point to;

    public Move(Point from, Point to) {
        this.from = from;
        this.to = to;
    }

    public Point getTo() {
        return to;
    }

    public Point getFrom() {
        return from;
    }
    
    @Override
    public String toString() {
        return from + " to " + to;
    }
    
    public Move reverseMove() {
        return new Move (to, from);
    }

}
