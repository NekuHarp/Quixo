package sample;

import java.util.ArrayList;

public class Partie {

    private double[][] grille =
            {
                    { 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 0 },
            };

    private Joueur joueur1;
    private Joueur joueur2;

    public Partie(Joueur joueur1, Joueur joueur2) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
    }

    public double[][] getGrille() {
        return grille;
    }

    public void setGrille(double[][] grille) {
        this.grille = grille;
    }

    public Joueur getJoueur1() {
        return joueur1;
    }

    public void setJoueur1(Joueur joueur1) {
        this.joueur1 = joueur1;
    }

    public Joueur getJoueur2() {
        return joueur2;
    }

    public void setJoueur2(Joueur joueur2) {
        this.joueur2 = joueur2;
    }

    public ArrayList<Coord> allSelectablePieces(Joueur player){

        ArrayList<Coord> listresult = new ArrayList<Coord>();
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                if((i==0)||(i==4)||(j==0)||(j==4)){
                    if((grille[i][j]==0)||(grille[i][j]==player.getNumber())){
                        listresult.add(new Coord(i,j));
                    }
                }
            }
        }

        return listresult;
    }

    public ArrayList<Coup> allMoves(Coord initpos, Joueur player){

        ArrayList<Coup> listresult = new ArrayList<Coup>();
        int a = initpos.getX();
        int b = initpos.getY();
        if((a==0)||(a==4)){
            if((b==0)||(b==4)) {
                listresult.add(new Coup(initpos, new Coord(a, 4 - b)));
                listresult.add(new Coup(initpos, new Coord(4 - a, b)));
            } else {
                listresult.add(new Coup(initpos, new Coord(a, 0)));
                listresult.add(new Coup(initpos, new Coord(4-a,b)));
                listresult.add(new Coup(initpos, new Coord(a, 4)));
            }
        } else {
            listresult.add(new Coup(initpos, new Coord(0, b)));
            listresult.add(new Coup(initpos, new Coord(a,4-b)));
            listresult.add(new Coup(initpos, new Coord(4, b)));
        }

        return listresult;
    }

    public boolean verifCoup(Coup move, Joueur player){

        boolean result = false;
        int x = move.getInitialPos().getX();
        int y = move.getInitialPos().getY();
        int a = move.getFinalPos().getX();
        int b = move.getFinalPos().getY();

        if(grille[x][y]==player.getNumber()){
            if(((a==x)&&(y!=b))||((b==y)&&(x!=a))){

            }
        }

        return result;

    }

}
