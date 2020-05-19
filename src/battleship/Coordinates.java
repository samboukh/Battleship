package battleship;

public class Coordinates {
    private int x;
    private int y;
    
    public Coordinates(int c, int l) {
            x = c;
            y = l;
            
    }
    public int getValueColumn( int c) { /// prévoir une boucle avant l'appel de la methode 
        return x;
    }
    public int getValueLine( int l) { /// prévoir une boucle avant l'appel de la methode 
        return y;
    }
    /*public static void setValueCoordinate(int c, int l) {
        coordinate[0] = c;
        coordinate[1] = l;
    }*/
}