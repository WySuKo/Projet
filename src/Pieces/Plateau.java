package Pieces;

public class Plateau {
    private Piece plateau [][];
    public static final char[] lettres = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    private static final int taille = 8;

    public Plateau(){
        this.plateau = new Piece [taille][taille];
    }

    public void ajoutPiece(Piece piece,int x,int y){
        plateau[x][y] =  piece;
    }



    private String genererLigne(){
        return "   " +
                "--- ".repeat(8);
    }

    public String toString(){
        StringBuilder aff = new StringBuilder();
        aff.append("    ");
        for(int i = 0; i < 8; i++)
            aff.append(lettres[i]).append("   ");
        aff.append("\n");

        for(int i = 0; i < 8; i++){
            aff.append(genererLigne()).append("\n");
            aff.append(8 - i);
            for(int j = 0; j < 8; j++){
                aff.append(" | ").append(plateau[i][j] != null? plateau[i][j].getCaractere() : " ");
            }
            aff.append(" | ").append(8-i).append("\n");
        }
        aff.append(genererLigne());
        aff.append("\n    ");

        for(int i = 0; i < 8; i++)
            aff.append(lettres[i]).append("   ");
        aff.append("\n");

        return aff.toString();
    }
}
