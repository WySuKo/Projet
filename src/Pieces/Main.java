package Pieces;

public class Main {
    public static void main(String[] args) {
        Tour tour_blanche = new Tour(PieceCouleur.BLANC, 4, 4);
        Tour tour_blanche_2 = new Tour(PieceCouleur.BLANC, 4, 1);
        Tour tour_noire = new Tour(PieceCouleur.NOIRE, 4, 6);
        Tour tour_noire_2 = new Tour(PieceCouleur.NOIRE, 1, 4);

        Plateau plateau = new Plateau();

        plateau.ajouterPiece(tour_blanche);
        plateau.ajouterPiece(tour_blanche_2);
        plateau.ajouterPiece(tour_noire);
        plateau.ajouterPiece(tour_noire_2);

        plateau.deplacerPiece(tour_blanche, 2, 1);
        System.out.println(plateau);

        plateau.deplacerPiece(tour_blanche, 4, 1);
        System.out.println(plateau);

        plateau.deplacerPiece(tour_blanche, 6, 4);
        System.out.println(plateau);

        plateau.deplacerPiece(tour_blanche,0, 4);
        System.out.println(plateau);

        plateau.deplacerPiece(tour_blanche ,1, 4);
        System.out.println(plateau);
    }
}
