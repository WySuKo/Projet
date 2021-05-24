package bpo2.echecs.joueurs;

import bpo2.echecs.jeu.Case;
import bpo2.echecs.jeu.CouleurPiece;
import bpo2.echecs.jeu.IPiece;
import bpo2.echecs.jeu.Plateau;

import java.util.ArrayList;
import java.util.Random;

public class JoueurAI extends Joueur {
    private Random random;

    public JoueurAI(CouleurPiece couleurPieces){
        super(couleurPieces);
        random = new Random();
    }

    @Override
    public String obtenirCoup(Plateau plateau) {
        if(plateau.echec(getCouleurJoueur())){
            //Si le roi est en échec, on essaye de le déplacer pour qu'il ne le soit plus
            IPiece roi = plateau.getRoi(getCouleurJoueur()); //TODO exception
            ArrayList<Case> deplacements = roi.deplacementsPossibles(plateau);

            Case positionRoi = roi.getPosition();
            for(Case c : deplacements){
                if(!plateau.echecApresDeplacement(getCouleurJoueur(), positionRoi, c))
                    return positionRoi.toString() + c.toString();
            }

            //Si on n'a pas pu déplacer le roi, on cherche à prendre la pièce qui le menace s'il n'y en a qu'une
        }
        //TODO retry si la piece n'a pas de déplacement disponible, abandon si trop d'échecs
        ArrayList<IPiece> pieces = getCouleurJoueur() == CouleurPiece.BLANC ? plateau.getPiecesBlanches() : plateau.getPiecesNoires();
        int indexPiece = random.nextInt(pieces.size());
        IPiece pieceADeplacer = pieces.get(indexPiece);
        ArrayList<Case> deplacement = pieceADeplacer.deplacementsPossibles(plateau);
        int indexDeplacement = random.nextInt(deplacement.size());

        Case source = pieceADeplacer.getPosition();
        Case destination = deplacement.get(indexDeplacement);

        return source.toString() + destination.toString();
    }
}
