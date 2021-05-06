package Pieces;

public class Piece {
    private char caractere;
    private PieceCouleur couleur;

    public Piece(PieceCouleur couleur, char caractere){
        this.couleur = couleur;
        this.caractere = couleur == PieceCouleur.BLANC?Character.toUpperCase(caractere):caractere;
    }
    public char getCaractere(){
        return caractere;
    }
}


