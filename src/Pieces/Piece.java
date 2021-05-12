package Pieces;

public abstract class Piece {
    private char caractere;
    private Couleur couleur;
    private int x, y;

    public Piece(Couleur couleur, char caractere, int x, int y){
        this.x = x;
        this.y = y;
        this.couleur = couleur;
        this.caractere = couleur == Couleur.BLANC? Character.toUpperCase(caractere):caractere;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public char getCaractere(){
        return caractere;
    }

    public Couleur getCouleur() {
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
    //Comentaire test
}


