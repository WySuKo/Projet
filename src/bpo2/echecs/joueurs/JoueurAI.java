package bpo2.echecs.joueurs;

import bpo2.echecs.jeu.Case;
import bpo2.echecs.jeu.Couleur;
import bpo2.echecs.jeu.IPiece;
import bpo2.echecs.jeu.Plateau;

import java.util.ArrayList;
import java.util.Random;

public class JoueurAI extends Joueur {
    private Random random;

    public JoueurAI(Couleur couleurPieces){
        super(couleurPieces);
        random = new Random();
    }

    private String afficherEtRetourner(String s){
        System.out.println("> " + s);
        return s;
    }

    @Override
    public String obtenirCoup(Plateau plateau) {
        ArrayList<IPiece> pieces = getCouleurJoueur() == Couleur.BLANC ? plateau.getPiecesBlanches() : plateau.getPiecesNoires();

        if(plateau.echec(getCouleurJoueur())){
            //Si le roi est en échec, on essaye de le déplacer pour qu'il ne le soit plus
            IPiece roi = plateau.getRoi(getCouleurJoueur());
            ArrayList<Case> deplacements = roi.deplacementsPossibles(plateau);

            Case positionRoi = roi.getPosition();
            for(Case c : deplacements){
                try{
                    if(!plateau.echecApresDeplacement(getCouleurJoueur(), positionRoi, c)){
                        return afficherEtRetourner(positionRoi.toString() + c.toString());
                    }

                }catch (Exception e){
                    ;
                }

            }
            //Si on n'a pas pu déplacer le roi, on cherche à prendre la pièce qui le menace s'il n'y en a qu'une
            ArrayList<IPiece> piecesProblematiques = plateau.piecesMenacantes(roi);
            if(piecesProblematiques.size() == 1){
                for(IPiece piece : pieces){
                    if(piece.deplacable(plateau, piecesProblematiques.get(0).getPosition())){
                        return afficherEtRetourner(piece + piecesProblematiques.get(0).getPosition().toString());
                    }
                }
            }
            //On ne sait plus quoi faire: on abandonne


            return afficherEtRetourner("abandon");
        }

        int indexPiece = random.nextInt(pieces.size());
        IPiece pieceADeplacer = pieces.get(indexPiece);
        ArrayList<Case> deplacement = pieceADeplacer.deplacementsPossibles(plateau);

        int i = 0;
        while(deplacement.isEmpty()){
            pieceADeplacer = pieces.get(random.nextInt(pieces.size()));
            deplacement = pieceADeplacer.deplacementsPossibles(plateau);

            i++;
            if(i == 10){
                return afficherEtRetourner("abandon"); //On a vraiment pas de chance: on ne trouve pas de bon mouvement donc on abandonne
            }
        }

        int indexDeplacement = random.nextInt(deplacement.size());

        Case source = pieceADeplacer.getPosition();
        Case destination = deplacement.get(indexDeplacement);

        return afficherEtRetourner(source.toString() + destination.toString());
    }
}
