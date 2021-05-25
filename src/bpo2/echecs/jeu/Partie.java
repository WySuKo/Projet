package bpo2.echecs.jeu;

import bpo2.echecs.exceptions.CaseInvalideException;
import bpo2.echecs.exceptions.CreationPlateauInvalideException;
import bpo2.echecs.exceptions.FormatCoupInvalideException;
import bpo2.echecs.exceptions.PieceNonDeplacableException;

import java.util.regex.Pattern;

/***
 * Représente une partie d'échecs
 */
public class Partie {
    private final Plateau plateau;
    private final IJoueur joueurBlanc;
    private final IJoueur joueurNoir;
    private IJoueur joueurActif;

    private boolean abandon; //pour vérifier si la partie a été abandonnée
    private static Pattern regexFormatCoup = Pattern.compile("([a-h][1-8]){2}"); //cette regex correspond au format a1b1 qu'un coup doit avoir
    private boolean propositionNulle; //est ce qu'une proposition de nulle a été émise?
    private boolean propositionNulleAcceptee; //est-ce que la proposition de nulle a été acceptée?
    private boolean duelDeRois; //est ce qu'il ne reste que des rois?
    private int nombreTours; //nombre de tours qui se sont effectués

    /***
     * Crée une nouvelle partie
     * @param fabriqueJoueur la fabrique qui permet de créer les joueurs
     * @param fabriquePiece la fabrique qui permet de créer les pièces
     * @throws CreationPlateauInvalideException s'il y a plus de deux rois, ou qu'une pièce est invalide
     */
    public Partie(IFabriqueJoueur fabriqueJoueur, IFabriquePiece fabriquePiece) throws CreationPlateauInvalideException {
        joueurBlanc = fabriqueJoueur.fabriquerPremierJoueur();
        joueurNoir = fabriqueJoueur.fabriquerSecondJoueur();
        joueurActif = joueurBlanc;
        plateau = new Plateau();
        abandon = false;
        propositionNulle = false;
        propositionNulleAcceptee = false;
        duelDeRois = false;
        nombreTours = 0;

        fabriquerPieces(fabriquePiece);
    }

    /***
     * Crée les pièces sur la plateau
     * @param fabriquePiece la fabrique qui permet de créer les pièces
     * @throws CreationPlateauInvalideException s'il y a plus de deux rois, ou qu'une pièce est invalide
     */
    private void fabriquerPieces(IFabriquePiece fabriquePiece) throws CreationPlateauInvalideException {
        boolean roiBlancAjoute = false;
        boolean roiNoirAjoute = false;

        for(IPiece piece : fabriquePiece.fabriquerPieces()){
            if(piece == null) throw new CreationPlateauInvalideException("Tentative d'ajout d'une pièce inexistante !");
            if(!plateau.caseValide(piece.getPosition())) throw new CreationPlateauInvalideException("Ajout d'une pièce dans une case inexistante !");

            if(piece.estCritique()){
                if(piece.getCouleur() == Couleur.BLANC){
                    if(roiBlancAjoute)
                        throw new CreationPlateauInvalideException("Un roi blanc existe déjà !");
                    else
                        roiBlancAjoute = true;
                }
                else{
                    if(roiNoirAjoute)
                        throw new CreationPlateauInvalideException("Un roi noir existe déjà !");
                    else
                        roiNoirAjoute = true;
                }
            }
            plateau.ajouterPiece(piece);
        }
        //S'il manque un roi, la partie ne peut pas être lancée
        if(!roiNoirAjoute || !roiBlancAjoute){
            throw new CreationPlateauInvalideException("Il manque un Roi dans l'une des deux équipes !");
        }
    }

    /***
     * Renvoie le plateau sur lequel se déroule la partie
     * @return le plateau
     */
    public Plateau getPlateau() {
        return plateau;
    }

    /***
     * Permet de savoir si un joueur a abandonné la partie
     * @return true si un joueur a abandonné, sinon false
     */
    public boolean abandonnee() {
        return abandon;
    }

    /***
     * Permet de savoir si une proposition de nulle a été acceptée
     * @return true si la proposition a été acceptée, false sinon
     */
    public boolean propositionNulleAcceptee() {
        return propositionNulleAcceptee;
    }

    /***
     * Renvoie le joueur actif
     * @return le joueur actif
     */
    public IJoueur getJoueurActif() {
        return joueurActif;
    }

    /***
     * Renvoie le joueur blanc
     * @return le joueur blanc
     */
    public IJoueur getJoueurBlanc() {
        return joueurBlanc;
    }

    /***
     * Permet de savoir si les seules pièces restantes sur le plateau sont des rois
     * @return true s'il ne reste que des rois, false sinon
     */
    public boolean duelDeRois() {
        return duelDeRois;
    }

    /***
     * Appellé à chaque tour, vérifie qu'il reste que des rois
     * @return true s'il ne reste que des rois, false sinon
     */
    private boolean roiContreRoi(){
        //Les seules pièces restantes sont forcément les rois, sinon la partie serait déjà finie
        duelDeRois = plateau.getPiecesNoires().size() == 1 && plateau.getPiecesBlanches().size() == 1;
        return duelDeRois;
    }

    /***
     * @brief Renvoie le nombre de tours qui se sont écoulés
     * @return le nombre de tours
     */
    public int getNombreTours() {
        return nombreTours;
    }

    /***
     * Vérifie que la partie peut continuer
     * @return true si la partie peut continuer, false sinon
     */
    public boolean peutContinuer(){
        //La partie peut continuer s'il n'y a pas d'abandon, pas d'échec et Mat, pas de proposition de nulle acceptée et qu'il y a eu moins de 300 tours joués
        return !abandon && !getPlateau().echecEtMat(joueurActif == joueurBlanc ? Couleur.BLANC : Couleur.NOIR) && !roiContreRoi() && !propositionNulleAcceptee && nombreTours != 300;
    }

    /***
     * @brief Récupère un coup de la part du joueur actif et essaye d'effectuer ce coup
     * @throws FormatCoupInvalideException Si le format du coup (ex a1b1) est invalide
     * @throws PieceNonDeplacableException Si la pièce précisée ne peut pas être déplacée
     * @throws CaseInvalideException Si la case de départ ou d'arrivée est invalide
     */
    public void jouerCoup() throws FormatCoupInvalideException, PieceNonDeplacableException, CaseInvalideException {
        String coup = joueurActif.obtenirCoup(plateau);

        if(coup.equals("abandon")){
            abandon = true;
            return; //quitter la fonction ici permet de ne pas changer le joueur actif et donc de l'afficher dans le message
        }
        if(coup.equals("nulle")){
            if(propositionNulle)
                propositionNulleAcceptee = true;
            else
                propositionNulle = true;

            //une proposition de nulle ne compte pas comme un tour, donc on n'incrémente pas le compteur de tours
            joueurActif = joueurActif == joueurBlanc ? joueurNoir : joueurBlanc;
            return;
        }

        propositionNulle = false;
        propositionNulleAcceptee = false;

        //On teste le format du coup grâce à la regex
        if(!regexFormatCoup.matcher(coup).matches()){
            throw new FormatCoupInvalideException("Le coup que vous avez entré est invalide ! Veuillez entrer un coup de format a1b1.");
        }

        //La case de départ correspond aux deux premiers caractères, et la case d'arrivée aux deux derniers
        Case caseDepart = new Case(coup.substring(0, 2));
        Case caseArrivee = new Case(coup.substring(2, 4));

        //pour pas que le joueur se mette en échec lui-même après un déplacement
        if(plateau.echecApresDeplacement(plateau.getPiece(caseDepart).getCouleur(), caseDepart, caseArrivee))
            throw new PieceNonDeplacableException("Le coup que vous avez entré est invalide ! Déplacer cette pièce mettrait votre Roi en échec.");

        try {
            plateau.deplacer(caseDepart, caseArrivee);
        }catch (CaseInvalideException e){
            throw e;
        }

        //on échange les joueurs
        nombreTours++;
        joueurActif = joueurActif == joueurBlanc ? joueurNoir : joueurBlanc;
    }
}
