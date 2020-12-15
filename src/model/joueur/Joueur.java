package model.joueur;

import model.Coup;
import model.Partie;

public abstract class Joueur {

    private int number;
    private boolean isIA;

    public Joueur(int number) {
        this.number = number;
        this.isIA = false;
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

    public abstract Coup determinerCoup(Partie game);

}



