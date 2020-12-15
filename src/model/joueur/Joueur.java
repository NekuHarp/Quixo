package model.joueur;

import model.Coup;
import model.Partie;

public abstract class Joueur {

    private int number;
    private boolean isIA;

    Joueur(int number) {
        this.number = number;
        this.isIA = false;
    }

    public int getNumber() {
        return number;
    }

    public boolean isIA() {
        return isIA;
    }

    void setIA(boolean IA) {
        isIA = IA;
    }

    public abstract void jouer(Coup move, Partie game);

    public abstract Coup determinerCoup(Partie game);

}



