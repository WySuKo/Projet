package bpo2.echecs.jeu;

/***
 * Définit un contrat pour la création de joueurs
 */
public interface IFabriqueJoueur {
    /***
     * Fabrique le premier joueur (le joueur blanc)
     * @return le joueur blanc
     */
    IJoueur fabriquerPremierJoueur();

    /***
     * Fabrique le second joueur (le joueur noir)
     * @return le joueur noir
     */
    IJoueur fabriquerSecondJoueur();
}
