package bpo2.echecs.jeu;

/***
 * Définit un contrat pour l'implémentation d'un joueur
 */
public interface IJoueur {

    /***
     * Renvoie le coup que le joueur joue, sous forme de chaîne de caractères
     * @param plateau le plateau contenant les pièces du joueur
     * @return le coup joué
     */
    String obtenirCoup(Plateau plateau);
}
