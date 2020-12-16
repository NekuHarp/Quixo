package model.joueur;

import model.Coup;

import java.util.ArrayList;

public class NodeIA {

    // Comme précisé dans le rapport, cette classe n'est pas utilisée dans le programme car elle aurait dû servir pour le Min-Max.

    private int depth;
    private boolean win;
    private ArrayList<Coup> couplist = new ArrayList<Coup>();
    private Coup lastmove;

    public NodeIA(int depth, boolean win, ArrayList<Coup> couplist, Coup lastmove) {
        this.depth = depth;
        this.win = win;
        this.couplist = couplist;
        this.lastmove = lastmove;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public boolean getWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public ArrayList<Coup> getCouplist() {
        return couplist;
    }

    public void setCouplist(ArrayList<Coup> couplist) {
        this.couplist = couplist;
    }

    public Coup getLastmove() {
        return lastmove;
    }

    public void setLastmove(Coup lastmove) {
        this.lastmove = lastmove;
    }
}
