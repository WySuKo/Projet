package Pieces;

public class Main {
    public static void main(String[] args) {
        Roi roi = new Roi(PieceCouleur.BLANC);
        Plateau plateau = new Plateau();

        plateau.ajoutPiece(roi,2,2);
        System.out.println(plateau);
    }
}
