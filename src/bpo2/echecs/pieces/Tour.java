package bpo2.echecs.pieces;

import bpo2.echecs.jeu.Case;
import bpo2.echecs.jeu.Couleur;
import bpo2.echecs.jeu.IPiece;
import bpo2.echecs.jeu.Plateau;

import java.util.ArrayList;

/***
 * Implémentation d'une tour
 */
public class Tour extends Piece {
    /***
     * Crée la tour
     * @param couleur la couleur de la tour
     * @param caseDepart la case de départ de la tour
     */
    public Tour(Couleur couleur, Case caseDepart) {
        super(couleur,'t', caseDepart);
    }

    /***
     * Vérifie que la pièce est déplacable sur une case d'un plateau
     * @param plateau le plateau sur lequel s'effectue le déplacement
     * @param caseArrivee la case sur laquelle on teste le déplacement
     * @return true si la pièce est déplacable, false sinon
     */
    @Override
    public boolean deplacable(Plateau plateau, Case caseArrivee) {
        //On vérifie que la case sur laquelle on souhaite se déplacer est présente dans les déplacements possibles
        return deplacementsPossibles(plateau).contains(caseArrivee);
    }

    /***
     * Renvoie la liste des déplacements possibles de la pièce sur un plateau
     * @param plateau le plateau sur lequel on cherche les déplacements
     * @return la liste des déplacements possibles de la pièce sur un plateau
     */
    @Override
    public ArrayList<Case> deplacementsPossibles(Plateau plateau) {
        ArrayList<Case> deplacements = new ArrayList<>();
        int positionX = getPosition().getX();
        int positionY = getPosition().getY();

        int i = positionX + 1;
        while(i < Plateau.TAILLE){
            Case caseActuelle = new Case(i, positionY);
            if(!super.deplacable(plateau, caseActuelle))
                break;
            deplacements.add(caseActuelle);
            if(plateau.caseOccupee(caseActuelle) && plateau.getPiece(caseActuelle).getCouleur() != getCouleur())
                break;
            i++;
        }
        i = positionX - 1;
        while(i >= 0){
            Case caseActuelle = new Case(i, positionY);
            if(!super.deplacable(plateau, caseActuelle))
                break;
            deplacements.add(caseActuelle);
            if(plateau.caseOccupee(caseActuelle) && plateau.getPiece(caseActuelle).getCouleur() != getCouleur())
                break;
            i--;
        }
        int j = positionY + 1;
        while(j < Plateau.TAILLE){
            Case caseActuelle = new Case(positionX, j);
            if(!super.deplacable(plateau, caseActuelle))
                break;
            deplacements.add(caseActuelle);
            if(plateau.caseOccupee(caseActuelle) && plateau.getPiece(caseActuelle).getCouleur() != getCouleur())
            break;
            j++;
        }
        j = positionY - 1;
        while(j >= 0){
            Case caseActuelle = new Case(positionX, j);
            if(!super.deplacable(plateau, caseActuelle))
                break;
            deplacements.add(caseActuelle);
            if(plateau.caseOccupee(caseActuelle) && plateau.getPiece(caseActuelle).getCouleur() != getCouleur())
                break;
            j--;
        }

        return deplacements;
    }

    /***
     * Effectue une copie profonde de la pièce
     * @return la copie profonde de la pièce
     */
    @Override
    public IPiece copieProfonde(){
        return new Tour(getCouleur(), getPosition());
    }
}
