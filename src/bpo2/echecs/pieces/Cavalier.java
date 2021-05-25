package bpo2.echecs.pieces;

import bpo2.echecs.jeu.Case;
import bpo2.echecs.jeu.Couleur;
import bpo2.echecs.jeu.IPiece;
import bpo2.echecs.jeu.Plateau;

import java.util.ArrayList;

/***
 * Implémentation d'un cavalier
 */
public class Cavalier extends Piece {
    /***
     * Crée un cavalier
     * @param couleur la couleur du cavalier
     * @param caseDepart la case de départ du cavalier
     */
    public Cavalier(Couleur couleur, Case caseDepart) {
        super(couleur, 'c', caseDepart);
    }

    /***
     * Vérifie que la pièce est déplacable sur une case d'un plateau
     * @param plateau le plateau sur lequel s'effectue le déplacement
     * @param caseArrivee la case sur laquelle on teste le déplacement
     * @return true si la pièce est déplacable, false sinon
     */
    @Override
    public boolean deplacable(Plateau plateau, Case caseArrivee){
        if(!super.deplacable(plateau, caseArrivee))
            return false;

        int xDifference = caseArrivee.getX() - getPosition().getX();
        int yDifference = caseArrivee.getY() - getPosition().getY();

        return (Math.abs(xDifference) == 2 && Math.abs(yDifference) == 1) || (Math.abs(xDifference) == 1 && Math.abs(yDifference) == 2);
    }

    /***
     * Effectue une copie profonde de la pièce
     * @return la copie profonde de la pièce
     */
    @Override
    public IPiece copieProfonde(){
        return new Cavalier(getCouleur(), getPosition());
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

        //On ajoute toutes les cases sur lesquelles le cavalier peut se déplacer, même celles qui sont invalides
        deplacements.add(new Case(positionX + 2, positionY + 1));
        deplacements.add(new Case(positionX + 2, positionY - 1));
        deplacements.add(new Case(positionX + 1, positionY + 2));
        deplacements.add(new Case(positionX + 1, positionY - 2));
        deplacements.add(new Case(positionX - 1, positionY + 2));
        deplacements.add(new Case(positionX - 1, positionY - 2));
        deplacements.add(new Case(positionX - 2, positionY + 1));
        deplacements.add(new Case(positionX - 2, positionY - 1));

        //On retire de la liste les cases sur lesquelles on ne peut pas se déplacer
        //Utiliser removeIf permet à la fois de simplifier le code grâce à la lambda, mais aussi d'éviter l'erreur d'utiliser remove dans une boucle
        deplacements.removeIf(c -> !plateau.caseValide(c) || !deplacable(plateau, c));

        return deplacements;
    }
}
