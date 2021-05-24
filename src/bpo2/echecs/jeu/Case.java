package bpo2.echecs.jeu;

public class Case {
    private final int x;
    private final int y;
    private final static String lettres = "abcdefgh";

    public Case(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Case(String coup){ //TODO exception
        this(-Integer.parseInt(String.valueOf(coup.charAt(1))) + Plateau.TAILLE,
                lettres.indexOf(coup.charAt(0)));
    }

    public String toString(){ //TODO vérifier chaine
        return lettres.charAt(y) + Integer.toString(Plateau.TAILLE - x);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    private boolean identique(Case autre){
        return x == autre.x && y == autre.y;
    }

    @Override
    public boolean equals(Object autre){
        if (this == autre)
            return true;
        if(autre == null)
            return false;
        if(getClass() != autre.getClass())
            return false;
        return identique((Case)autre);
    }
}
