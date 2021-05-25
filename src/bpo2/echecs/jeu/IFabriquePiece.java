package bpo2.echecs.jeu;

import java.util.ArrayList;

/**
 * Définit un contrat pour la création de pièces
 */
public interface IFabriquePiece {
    /***
     * Renvoie les pièces à ajouter
     * @return la liste des pièces à ajouter
     */
    ArrayList<IPiece> fabriquerPieces();
}
