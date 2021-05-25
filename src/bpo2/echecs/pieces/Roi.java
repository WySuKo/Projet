package bpo2.echecs.pieces;

import bpo2.echecs.jeu.Case;
import bpo2.echecs.jeu.Couleur;
import bpo2.echecs.jeu.IPiece;
import bpo2.echecs.jeu.Plateau;

import java.util.ArrayList;

/***
 * Implémentation d'un roi
 */
public class Roi extends Piece {
    /***
     * Crée le roi
     * @param couleur la couleur du roi
     * @param caseDepart la case de départ du roi
     */
    public Roi(Couleur couleur, Case caseDepart) {
        super(couleur,'r', caseDepart);
    }

    /***
     * Vérifie que la pièce est déplacable sur une case d'un plateau
     * @param plateau le plateau sur lequel s'effectue le déplacement
     * @param caseArrivee la case sur laquelle on teste le déplacement
     * @return true si la pièce est déplacable, false sinon
     */
    @Override
    public boolean deplacable(Plateau plateau, Case caseArrivee) {
        return deplacementsPossibles(plateau).contains(caseArrivee);
    }

    /***
     * Effectue une copie profonde de la pièce
     * @return la copie profonde de la pièce
     */
    @Override
    public IPiece copieProfonde() {
        return new Roi(getCouleur(), getPosition());
    }

    /***
     * Teste si la pièce est critique (<=> roi)
     * @return true si la pièce est critique, false sinon
     */
    @Override
    public boolean estCritique() {
        return true;
    }

    /***
     * Renvoie la liste des déplacements possibles de la pièce sur un plateau
     * @param plateau le plateau sur lequel on cherche les déplacements
     * @return la liste des déplacements possibles de la pièce sur un plateau
     */
    @Override
    public ArrayList<Case> deplacementsPossibles(Plateau plateau){
        ArrayList<Case> deplacements = new ArrayList<>();
        int positionX = getPosition().getX();
        int positionY = getPosition().getY();

        //On ajoute toutes les cases entourant le roi, même celles qui sont invalides
        deplacements.add(new Case(positionX + 1, positionY));
        deplacements.add(new Case(positionX - 1, positionY));
        deplacements.add(new Case(positionX, positionY + 1));
        deplacements.add(new Case(positionX, positionY - 1));
        deplacements.add(new Case(positionX + 1, positionY + 1));
        deplacements.add(new Case(positionX + 1, positionY -1));
        deplacements.add(new Case(positionX - 1, positionY+1));
        deplacements.add(new Case(positionX - 1, positionY-1));

        //On retire de la liste les cases sur lesquelles on ne peut pas se déplacer
        //Utiliser removeIf permet à la fois de simplifier le code grâce à la lambda, mais aussi d'éviter l'erreur d'utiliser remove dans une boucle
        deplacements.removeIf(c -> !super.deplacable(plateau, c));

        return deplacements;
    }
}
