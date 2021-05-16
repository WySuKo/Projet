package Pieces;

public abstract class Piece {
    private final char caractere;
    private final Couleur couleur;
    private int x, y;

    public Piece(Couleur couleur, char caractere, int x, int y){
        this.x = x;
        this.y = y;
        this.couleur = couleur;
        this.caractere = couleur == Couleur.BLANC? Character.toUpperCase(caractere):caractere;
    }

    public final int getX() {
        return x;
    }

    public final int getY() {
        return y;
    }

    public final void setX(int x) {
        this.x = x;
    }

    public final void setY(int y) {
        this.y = y;
    }

    public final char getCaractere(){
        return caractere;
    }

    public final Couleur getCouleur() {
        return couleur;
    }

    public boolean deplacable(int x, int y, Plateau plateau){
        if(x > Plateau.TAILLE - 1 || x < 0 || y > Plateau.TAILLE - 1 || y < 0)
            return false;

        if(getX() == x && getY() == y)
            return false;

        if(plateau.getPlateau()[x][y] != null && plateau.getPlateau()[x][y].getCouleur() == getCouleur())
            return false;

        return true;
    }
}


