package bpo2.echecs.jeu;

import bpo2.echecs.exceptions.CaseInvalideException;
import bpo2.echecs.exceptions.PieceNonDeplacableException;

import java.util.ArrayList;

/***
 * Représente un plateau d'échecs
 */
public class Plateau {
    private final IPiece[][] plateau; //Tableau à deux dimensions qui permet d'accéder facilement aux pièces (interne à la classe)
    public static final int TAILLE = 8;

    private final ArrayList<IPiece> piecesBlanches;
    private final ArrayList<IPiece> piecesNoires;

    /***
     * Crée un nouveau plateau.
     */
    public Plateau() {
        this.plateau = new IPiece [TAILLE][TAILLE];
        piecesBlanches = new ArrayList<>();
        piecesNoires = new ArrayList<>();
    }

    /***
     * Renvoie la liste des pièces blanches du plateau
     * @return la liste des pièces blanches du plateau
     */
    public ArrayList<IPiece> getPiecesBlanches() {
        return piecesBlanches;
    }


    /***
     * Renvoie la liste des pièces noires du plateau
     * @return la liste des pièces noires du plateau
     */
    public ArrayList<IPiece> getPiecesNoires() {
        return piecesNoires;
    }

    /***
     * Teste la validité d'une case
     * @param c la case à tester
     * @return true si la case est valide, false sinon
     */
    public boolean caseValide(Case c){
        return c.getX() >= 0 && c.getX() < TAILLE && c.getY() >= 0 && c.getY() < TAILLE;
    }

    /***
     * Effectue une copie profonde du plateau
     * @return une copie profonde du plateau
     */
    public Plateau copieProfonde(){
        Plateau plateauCopie = new Plateau();
        for(IPiece[] pieces : plateau)
            for(IPiece piece : pieces)
                if(piece != null)
                    plateauCopie.ajouterPiece(piece.copieProfonde());
        return plateauCopie;
    }

    /***
     * Recherche les pièces qui menacent (<=> qui peuvent prendre) une autre pièce
     * @param pieceMenacee la pièce dont on souhaite connaître les menaces
     * @return une liste contenant les pièces menacantes
     */
    public ArrayList<IPiece> piecesMenacantes(IPiece pieceMenacee){
        ArrayList<IPiece> pieces = new ArrayList<>();
        //On recherche les pièces menacantes seulement parmi les pièces qui ne sont pas de la même couleur
        ArrayList<IPiece> piecesEnnemies = pieceMenacee.getCouleur() == Couleur.BLANC ? piecesNoires : piecesBlanches;

        for(IPiece piece : piecesEnnemies)
            if(piece.deplacable(this, pieceMenacee.getPosition()))
                pieces.add(piece);

        return pieces;
    }

    /***
     * Ajoute une pièce sur le plateau
     * @param piece la pièce à ajouter
     */
    public void ajouterPiece(IPiece piece) {
        Case destination = piece.getPosition();

        //Si une pièce existe déjà sur la case cible, on la retire
        if(getPiece(destination) != null)
            retirerPiece(destination);

        //On ajoute la pièce dans le tableau
        plateau[destination.getX()][destination.getY()] = piece;

        //Et on ajoute la pièce dans la liste correspondant à sa couleur
        if(piece.getCouleur() == Couleur.BLANC)
            piecesBlanches.add(piece);
        else
            piecesNoires.add(piece);
    }

    /***
     * Retire une pièce du plateau
     * @param source la position de la pièce à retirer
     */
    private void retirerPiece(Case source){
        IPiece piece = plateau[source.getX()][source.getY()];
        //On commence par retirer la pièce de la liste de sa couleur
        if(piece.getCouleur() == Couleur.BLANC)
            piecesBlanches.remove(piece);
        else
            piecesNoires.remove(piece);

        //Puis on la retire du tableau 2d
        plateau[source.getX()][source.getY()] = null;
    }

    /***
     * Déplace une pièce d'une case à une autre (méthode utilitaire)
     * @param piece la pièce à déplacer
     * @param caseArrivee la case d'arrivée de la pièce
     */
    private void deplacerPiece(IPiece piece, Case caseArrivee) {
        Case caseDepart = piece.getPosition();

        //On retire la pièce de sa position de départ, sans pour autant la retirer de la liste correspondant à sa couleur
        plateau[caseDepart.getX()][caseDepart.getY()] = null;

        //s'il y a une pièce sur la case d'arrivée, on la retire
        if(caseOccupee(caseArrivee))
            retirerPiece(caseArrivee);

        plateau[caseArrivee.getX()][caseArrivee.getY()] = piece;
        piece.deplacer(caseArrivee);
    }

    /***
     * Déplace une pièce d'une case à une autre (méthode publique)
     * @param caseDepart la position de la pièce à déplacer
     * @param caseArrivee la position sur laquelle déplacer la pièce
     * @throws CaseInvalideException si la case de départ ou la case d'arrivée est invalide
     * @throws PieceNonDeplacableException si la pièce ne peut pas être déplacée
     */
    public void deplacer(Case caseDepart, Case caseArrivee) throws CaseInvalideException, PieceNonDeplacableException {
        IPiece pieceADeplacer = getPiece(caseDepart);
        if(pieceADeplacer == null){
            throw new CaseInvalideException("Erreur lors du déplacement d'une pièce: la pièce à déplacer n'existe pas !");
        }

        if(!pieceADeplacer.deplacable(this, caseArrivee))
            throw new PieceNonDeplacableException("Erreur lors du déplacement d'une pièce: la pièce n'est pas déplacable !");

        deplacerPiece(pieceADeplacer, caseArrivee);
    }

    /***
     * Récupère une pièce grâce à sa position
     * @param source la position de la pièce à récupérer
     * @return
     */
    public IPiece getPiece(Case source) {
        if(!caseValide(source)) return null;
        return plateau[source.getX()][source.getY()];
    }

    /***
     * Vérifie si une case est occupée par une pièce
     * @param source la case à tester
     * @return true si la case est occupée, false sinon
     */
    public boolean caseOccupee(Case source) {
        return plateau[source.getX()][source.getY()] != null;
    }

    /***
     * Renvoie le roi d'une couleur précisée
     * @param couleurPiece la couleur du roi que l'on cherche
     * @return le roi recherché
     */
    public IPiece getRoi(Couleur couleurPiece){
        ArrayList<IPiece> listeAChercher = couleurPiece == Couleur.BLANC ? piecesBlanches : piecesNoires;
        for(IPiece piece : listeAChercher)
            if(piece.estCritique())
                return piece;
        return null;
    }

    /***
     * Vérifie qu'un roi ne soit pas en échec après un déplacement. Pour cela, on effectue une copie du plateau, on effectue le mouvement et on vérifie si le roi est en échec ou non.
     * @param couleurRoiATester la couleur du roi dont on souhaite vérifier l'éched
     * @param caseDepart la case de départ de la pièce à déplacer
     * @param caseDestination la case sur laquelle déplacer la pièce
     * @return true si le roi est en échec après le mouvement, false sinon
     * @throws CaseInvalideException si une des cases du déplacement est invalide
     * @throws PieceNonDeplacableException si la pièce n'est pas déplacable
     */
    public boolean echecApresDeplacement(Couleur couleurRoiATester, Case caseDepart, Case caseDestination) throws CaseInvalideException, PieceNonDeplacableException{
        Plateau plateauCopie = this.copieProfonde(); //On effectue une copie profonde du plateau pour ne pas modifier l'original

        try {
            plateauCopie.deplacer(caseDepart, caseDestination); //On effectue le déplacement
        }catch (CaseInvalideException | PieceNonDeplacableException e){
            throw e;
        }

        return plateauCopie.echec(couleurRoiATester); //et on vérifie que le plateau soit en échec ou non
    }

    /***
     * Vérifie si le roi d'une couleur est en échec au roi ou non
     * @param couleur la couleur du roi à tester
     * @return
     */
    public boolean echec(Couleur couleur) {
        IPiece roi = getRoi(couleur);

        //Si une pièce adverse peut prendre le roi, alors il est en échec
        ArrayList<IPiece> piecesATester = couleur == Couleur.BLANC? piecesNoires : piecesBlanches;
        for(IPiece piece : piecesATester)
            if(piece.deplacable(this, roi.getPosition()))
                return true;
        return false;
    }

    /***
     * Vérifie si le roi d'une couleur est en échec et mat
     * @param couleur la couleur du roi à tester
     * @return true si le roi est en échec et mat, false sinon
     */
    public boolean echecEtMat(Couleur couleur) {
        //On récupère le roi que l'on souhaite tester
        IPiece roi = getRoi(couleur);

        ArrayList<IPiece> piecesAdverses = couleur == Couleur.BLANC? piecesNoires : piecesBlanches;
        ArrayList<IPiece> piecesAlliees = couleur == Couleur.BLANC? piecesBlanches : piecesNoires;

        //on récupère la liste des pièces menacant le roi
        ArrayList<IPiece> piecesDeplacablesCaseActuelle = piecesMenacantes(roi);
        if(piecesDeplacablesCaseActuelle.isEmpty()) //aucune pièce ne menace le roi, il n'est pas en échec
            return false;
        //Une seule pièce menace le roi, on vérifie qu'elle puisse être prise
        if(piecesDeplacablesCaseActuelle.size() == 1){
            Case positionPieceEnnemie = piecesDeplacablesCaseActuelle.get(0).getPosition();
            for(IPiece piece : piecesAlliees){ //on inclut le roi dans la recherche car le roi peut manger son agresseur s'il est à côté
                try {
                    if(piece.deplacable(this, positionPieceEnnemie) && !echecApresDeplacement(couleur, piece.getPosition(), positionPieceEnnemie)){
                        //la seule pièce qui menace le roi peut être mangée, le roi n'est pas en échec et mat
                        return false;
                    }
                } catch (CaseInvalideException | PieceNonDeplacableException e){;} //Pas de traitement des exceptions car tout est déjà vérifié
            }
        }
        //on ne peut pas manger la pièce menacante, on va donc essayer de bouger le roi sur une case sécurisée
        ArrayList<Case> deplacementsRoi = roi.deplacementsPossibles(this);
        for(Case c : deplacementsRoi){
            try {
                if(!echecApresDeplacement(couleur, roi.getPosition(), c)) //si le roi n'est pas en échec après le déplacement, il n'y a pas échec et mat
                    return false;
            } catch (CaseInvalideException | PieceNonDeplacableException e) {}
        }

        //on ne peut pas déplacer le Roi sur une autre case: on va donc chercher parmi tous les coups des pièces alliées au roi, si l'un d'entre eux bloque l'échec
        for(IPiece piece : piecesAlliees){
            if(piece == roi) continue; //on ne peut pas déplacer le roi donc on ne le prend pas en compte
            for(Case c : piece.deplacementsPossibles(this)) {
                try {
                    //On trouve un déplacement qui ne provoque pas d'échec après le mouvement => pas d'échec et mat
                    if(!echecApresDeplacement(couleur, piece.getPosition(), c))
                        return false;
                } catch (CaseInvalideException | PieceNonDeplacableException e) {}
            }
        }
        //On a trouvé aucune solution, le roi est donc en échec et mat
        return true;
    }

    /* Génération de la représentation du plateau */

    /***
     * Génère une ligne
     * @return la ligne générée
     */
    private String genererLigne(){
        return "   " + "--- --- --- --- --- --- --- ---\n";
    }

    /***
     * Renvoie le plateau sous forme de chaîne de caractère
     * @return le plateau sous forme de chaîne de caractère
     */
    public String toString(){
        StringBuilder aff = new StringBuilder();
        aff.append("    a   b   c   d   e   f   g   h\n");

        for(int i = 0; i < TAILLE; i++){
            aff.append(genererLigne());
            aff.append(TAILLE - i);
            for(int j = 0; j < TAILLE; j++){
                aff.append(" | ").append(plateau[i][j] != null? plateau[i][j].getRepresentation() : " ");
            }
            aff.append(" | ").append(TAILLE-i).append("\n");
        }
        aff.append(genererLigne());
        aff.append("    a   b   c   d   e   f   g   h\n");

        return aff.toString();
    }
}
