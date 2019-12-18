package sample;

public abstract class Joueur {

    private int number;
    private boolean isIA;

    public Joueur(int number, boolean isIA) {
        this.number = number;
        this.isIA = isIA;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isIA() {
        return isIA;
    }

    public void setIA(boolean IA) {
        isIA = IA;
    }

    public abstract void jouer(Coup move, Partie game);

}

class JoueurHumain extends Joueur {

    public JoueurHumain(int number, boolean isIA) {
        super(number, isIA);
        this.setIA(false);
    }

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

}

class JoueurIA extends Joueur {

    public JoueurIA(int number, boolean isIA) {
        super(number, isIA);
        this.setIA(true);
    }

    public void jouer(Coup move, Partie game){
        // TODO
    }

    public Coup déterminerCoup(Partie game){
        return new Coup(new Coord(0,0),new Coord(0,0));
    }

}