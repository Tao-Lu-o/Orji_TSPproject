package Main;
// Essentially copied from stackoverflow.com/questions/2670982/using-pairs-or-2-tuples-in-java

public class Coordinate<X,Y>{
    public final X x;
    public final Y y;
    public Coordinate(X x, Y y){
        this.x = x;
        this.y = y;
    }
}
