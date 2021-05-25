package bpo2.echecs.pieces;

import bpo2.echecs.jeu.Case;
import bpo2.echecs.jeu.Couleur;
import bpo2.echecs.jeu.IFabriquePiece;
import bpo2.echecs.jeu.IPiece;

import java.util.ArrayList;

/***
 * Implémentation d'une fabrique de pièces, utilisée pour la création d'une partie
 */
public class FabriquePiece implements IFabriquePiece {
    private ArrayList<IPiece> pieces;

    /***
     * Crée la fabrique de pièces
     */
    public FabriquePiece(){
        pieces = new ArrayList<>();

        pieces.add(new Tour(Couleur.BLANC, new Case(2, 2)));
        pieces.add(new Cavalier(Couleur.BLANC, new Case(5, 7)));
        pieces.add(new Roi(Couleur.NOIR, new Case(1, 6)));
        pieces.add(new Tour(Couleur.NOIR, new Case(3, 3)));
        pieces.add(new Roi(Couleur.BLANC, new Case(4, 1)));
        pieces.add(new Cavalier(Couleur.NOIR, new Case(1, 2)));
    }

    /***
     * Renvoie les pièces à ajouter
     * @return la liste des pièces à ajouter
     */
    @Override
    public ArrayList<IPiece> fabriquerPieces(){
        return pieces;
    }
}
