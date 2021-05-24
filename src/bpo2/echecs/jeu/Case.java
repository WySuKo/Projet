package bpo2.echecs.jeu;

public class Case {
    private final int x;
    private final int y;
    private final static String lettres = "abcdefgh";

    public Case(int x, int y){
        this.x = x;
        this.y = y;
    }

    //pas besoin d'exception ici, les seuls appels sont faits quand le format du coup est vérifié
    public Case(String coup){
        this(-Integer.parseInt(String.valueOf(coup.charAt(1))) + Plateau.TAILLE,
                lettres.indexOf(coup.charAt(0)));
    }

    public String toString(){
        //Valide lors de l'appel
        return lettres.charAt(y) + Integer.toString(Plateau.TAILLE - x);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object autre){
        if (this == autre)
            return true;
        if(autre == null)
            return false;
        if(getClass() != autre.getClass())
            return false;
        return x == ((Case)autre).x && y == ((Case)autre).y;
    }
}
