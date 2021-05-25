package bpo2.echecs.jeu;

/***
 * Représente une case
 */
public class Case {
    private final int x;
    private final int y;
    private final static String lettres = "abcdefgh"; //les lettres qui peuvent représenter une case

    /**
     * Crée une case
     * @param x la coordonnée x de la case
     * @param y la coordonnée y de la case
     */
    public Case(int x, int y){
        this.x = x;
        this.y = y;
    }

    /***
     * Crée une case à partir de sa représentation sous forme de String
     * @param coup le coup que l'on souhaite transformer en case (de la forme a1)
     */
    public Case(String coup){
        this(-Integer.parseInt(String.valueOf(coup.charAt(1))) + Plateau.TAILLE,
                lettres.indexOf(coup.charAt(0)));
    }

    /***
     * Transforme une case en une chaîne la représentant
     * @return une chaîne de la forme a1 représentant la case
     */
    public String toString(){
        //Valide lors de l'appel
        return lettres.charAt(y) + Integer.toString(Plateau.TAILLE - x);
    }

    /***
     * Renvoie la coordonnée x de la case
     * @return la coordonnée x de la case
     */
    public int getX() {
        return x;
    }

    /***
     * Renvoie la coordonnée y de la case
     * @return la coordonnée y de la case
     */
    public int getY() {
        return y;
    }

    /***
     * Vérifie que deux cases sont égales (les x sont égaux et les y le sont aussi)
     * @param autre la case à comparen
     * @return true si les cases sont identiques, false sinon
     */
    @Override
    public boolean equals(Object autre){
        if (this == autre)
            return true;
        if(autre == null)
            return false;
        if(getClass() != autre.getClass())
            return false;
        //Deux cases sont identiques si elles ont les mêmes coordonnées
        return x == ((Case)autre).x && y == ((Case)autre).y;
    }
}
