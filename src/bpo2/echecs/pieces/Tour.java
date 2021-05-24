package bpo2.echecs.pieces;

import bpo2.echecs.jeu.Case;
import bpo2.echecs.jeu.CouleurPiece;
import bpo2.echecs.jeu.IPiece;
import bpo2.echecs.jeu.Plateau;

import java.util.ArrayList;

public class Tour extends Piece {
    public Tour(CouleurPiece couleur, Case caseDepart) {
        super(couleur,'t', caseDepart);
    }

    @Override
    public boolean deplacable(Plateau plateau, Case caseArrivee) {
        //On vérifie que la case sur laquelle on souhaite se déplacer est présente dans les déplacements possibles
        return deplacementsPossibles(plateau).contains(caseArrivee);
    }

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
            i++;
        }
        i = positionX - 1;
        while(i >= 0){
            Case caseActuelle = new Case(i, positionY);
            if(!super.deplacable(plateau, caseActuelle))
                break;
            deplacements.add(caseActuelle);
            i--;
        }
        int j = positionY + 1;
        while(j < Plateau.TAILLE){
            Case caseActuelle = new Case(positionX, j);
            if(!super.deplacable(plateau, caseActuelle))
                break;
            deplacements.add(caseActuelle);
            j++;
        }
        j = positionY - 1;
        while(j >= 0){
            Case caseActuelle = new Case(positionX, j);
            if(!super.deplacable(plateau, caseActuelle))
                break;
            deplacements.add(caseActuelle);
            j--;
        }

        return deplacements;
    }



    @Override
    public IPiece copieProfonde(){
        return new Tour(getCouleur(), getPosition());
    }
}
