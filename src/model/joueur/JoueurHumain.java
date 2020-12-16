package model.joueur;

import model.Coup;
import model.Partie;

public class JoueurHumain extends Joueur {

    public JoueurHumain(int number) {
        super(number);
        this.setIA(false);
    }

    // Fonction de jeu.
    public void jouer(Coup move, Partie game){

        int x = move.getInitialPos().getX();
        int y = move.getInitialPos().getY();
        int a = move.getFinalPos().getX();
        int b = move.getFinalPos().getY();
        double[][] grille = game.getGrille();

        if(x==a){
            if(y>b){
                for(int i = y; i>b; i--){
                    grille[x][i]=grille[x][i-1];
                }
            } else {
                for(int i = y; i<b; i++){
                    grille[x][i]=grille[x][i+1];
                }
            }
        } else {
            if(x>a){
                for(int i = x; i>a; i--){
                    grille[i][y]=grille[i-1][y];
                }
            } else {
                for(int i = x; i<a; i++){
                    grille[i][y]=grille[i+1][y];
                }
            }
        }
        grille[a][b]=this.getNumber();
        game.setGrille(grille);

    }

    // Fonction inutile en tant que joueur humain, mais il est nécessaire de la définir quand-même.
    public Coup determinerCoup(Partie game){
        return null;
    }

}