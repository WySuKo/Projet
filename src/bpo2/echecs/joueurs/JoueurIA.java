package bpo2.echecs.joueurs;

import bpo2.echecs.exceptions.CaseInvalideException;
import bpo2.echecs.exceptions.PieceNonDeplacableException;
import bpo2.echecs.jeu.Case;
import bpo2.echecs.jeu.Couleur;
import bpo2.echecs.jeu.IPiece;
import bpo2.echecs.jeu.Plateau;

import java.util.ArrayList;
import java.util.Random;

/***
 * Implémentation d'un joueur contrôlé par une IA
 */
public class JoueurIA extends Joueur {
    private Random random; //générateur de nombres pour générer des coups aléatoires

    /***
     * Crée une IA
     * @param couleur couleur du joueur
     */
    public JoueurIA(Couleur couleur){
        super(couleur);
        random = new Random();
    }

    /***
     * Affiche une chaîne sur la sortie standard
     * @param s la chaîne à afficher
     * @return s
     */
    private String afficherEtRetourner(String s){
        System.out.println("> " + s);
        return s;
    }

    /***
     * Permet à l'IA de jouer un coup concernant son roi si ce dernier est en échec
     * @param plateau le plateau contenant les pièces
     * @param pieces les pièces appartenant au joueur
     * @return le coup à jouer, sous forme de texte
     */
    private String jouerCoupSiEchec(Plateau plateau, ArrayList<IPiece> pieces){
        //Si le roi est en échec, on essaye de le déplacer pour qu'il ne le soit plus
        IPiece roi = plateau.getRoi(getCouleur());
        ArrayList<Case> deplacements = roi.deplacementsPossibles(plateau);

        Case positionRoi = roi.getPosition();
        for(Case c : deplacements){
            try{
                if(!plateau.echecApresDeplacement(getCouleur(), positionRoi, c)){
                    return afficherEtRetourner(positionRoi.toString() + c.toString());
                }

            }catch (Exception e){
                ;
            }
        }
        //Si on a pas pu déplacer le roi, on cherche à prendre la pièce qui le menace s'il n'y en a qu'une
        ArrayList<IPiece> piecesProblematiques = plateau.piecesMenacantes(roi);
        if(piecesProblematiques.size() == 1){
            for(IPiece piece : pieces){
                if(piece.deplacable(plateau, piecesProblematiques.get(0).getPosition())){
                    return afficherEtRetourner(piece + piecesProblematiques.get(0).getPosition().toString());
                }
            }
        }

        //On cherche parmi toutes les possibilités à sacrifier une pièce
        for (IPiece piece : pieces)
            for(Case c : piece.deplacementsPossibles(plateau)) {
                try {
                    if(piece.deplacable(plateau, c) && !plateau.echecApresDeplacement(getCouleur(), piece.getPosition(), c))
                        return piece.toString() + c.toString();
                } catch (CaseInvalideException | PieceNonDeplacableException e) {
                }
            }

        //Normalement, on arrive jamais ici
        return afficherEtRetourner("abandon");
    }

    /***
     * Renvoie le coup que le joueur joue, sous forme de chaîne de caractères
     * @param plateau le plateau contenant les pièces du joueur
     * @return le coup joué
     */
    @Override
    public String obtenirCoup(Plateau plateau) {
        ArrayList<IPiece> pieces = getCouleur() == Couleur.BLANC ? plateau.getPiecesBlanches() : plateau.getPiecesNoires();

        if(plateau.echec(getCouleur())){
            return jouerCoupSiEchec(plateau, pieces);
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
