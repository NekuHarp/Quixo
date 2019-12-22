package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

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

class JoueurHumain extends Joueur {

    public JoueurHumain(int number) {
        super(number);
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

    public Coup determinerCoup(Partie game){
        return null;
    }

}

class JoueurIA extends Joueur {

    int maxdepth = 1;

    public JoueurIA(int number) {
        super(number);
        this.setIA(true);
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

    public Coup determinerCoup(Partie game){

        return randomIA(game);
        /*ArrayList<Coup> couplist = new ArrayList<Coup>();
        ArrayList<ArrayList<Coup>> listcouplist = new ArrayList<ArrayList<Coup>>();
        Collections.sort(listcouplist, new Comparator<ArrayList<Coup>>() {
            @Override
            public int compare(ArrayList<Coup> a, ArrayList<Coup> b) {
                if(a.size()>b.size()){
                    return b.size();
                } else {
                    return a.size();
                }
            }
        });
        if(getNumber()==1){
            for (Coord n : game.allSelectablePieces(game.getJoueur1())) {
                int i = n.getX();
                int j = n.getY();
                for (Coup m : game.allMoves(new Coord(i, j), this)) {
                    couplist.add(m);
                    listcouplist.add((playIA(game, new NodeIA(1, false, couplist,m)).getCouplist()));
                    couplist.remove(m);
                }
            }
            for (ArrayList<Coup> n : listcouplist) {
                if (n.size() < 5) {
                    return n.get(0);
                }
            }
            return listcouplist.get(0).get(0);
        } else {
            for (Coord n : game.allSelectablePieces(game.getJoueur2())) {
                int i = n.getX();
                int j = n.getY();
                for (Coup m : game.allMoves(new Coord(i, j), this)) {
                    couplist.add(m);
                    listcouplist.add((playIA(game, new NodeIA(1, false, couplist,m)).getCouplist()));
                    listcouplist.add(couplist);
                    couplist.remove(m);
                }
            }
            for (ArrayList<Coup> n : listcouplist) {
                if ((n.size() < 5)&&(n.size()!=0)) {
                    return n.get(0);
                }
            }
            return listcouplist.get(0).get(0);
        }*/
    }

    private NodeIA playIA(Partie game, NodeIA node) {

        ArrayList<ArrayList<Coup>> listcouplist = new ArrayList<ArrayList<Coup>>();
        //double[][] grille = game.getGrille();
        double[][] grille = new double[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grille[i][j] = game.getGrille()[i][j];
            }
        }
        int depth = node.getDepth();
        ArrayList<Coup> couplist = node.getCouplist();
        Coup lastmove = node.getLastmove();
        int x = lastmove.getInitialPos().getX();
        int y = lastmove.getInitialPos().getY();
        int a = lastmove.getFinalPos().getX();
        int b = lastmove.getFinalPos().getY();

        if (x == a) {
            if (y > b) {
                for (int i = y; i > b; i--) {
                    grille[x][i] = grille[x][i - 1];
                }
            } else {
                for (int i = y; i < b; i++) {
                    grille[x][i] = grille[x][i + 1];
                }
            }
        } else {
            if (x > a) {
                for (int i = x; i > a; i--) {
                    grille[i][y] = grille[i - 1][y];
                }
            } else {
                for (int i = x; i < a; i++) {
                    grille[i][y] = grille[i + 1][y];
                }
            }
        }
        grille[a][b] = this.getNumber();

        if(game.checkwin()==getNumber()){
            return new NodeIA(node.getDepth(),true,node.getCouplist(),node.getLastmove());
        } else if(depth==maxdepth) {
            return new NodeIA(node.getDepth(),false,node.getCouplist(),node.getLastmove());
        } else {
            if(getNumber()==1){
                for (Coord n : game.allSelectablePieces(game.getJoueur1())) {
                    int i = n.getX();
                    int j = n.getY();
                    for (Coup m : game.allMoves(new Coord(i, j), this)) {
                        couplist.add(m);
                        if((playIA(game, new NodeIA(depth+1, false, couplist,m)).getWin())||(playIA(game, new NodeIA(depth+1, false, couplist,m)).getDepth()==maxdepth)){
                            listcouplist.add((playIA(game, new NodeIA(depth+1, false, couplist,m)).getCouplist()));
                        }
                        couplist.remove(m);
                    }
                }
                Collections.sort(listcouplist, new Comparator<ArrayList<Coup>>() {
                    @Override
                    public int compare(ArrayList<Coup> a, ArrayList<Coup> b) {
                        if(a.size()>b.size()){
                            return b.size();
                        } else {
                            return a.size();
                        }
                    }
                });
                for (ArrayList<Coup> n : listcouplist) {
                    if ((n.size() < 5)&&(n.size()!=0)) {
                        return new NodeIA(n.size(),false,n,n.get(n.size()-1));
                    }
                }
                return new NodeIA(5,false,listcouplist.get(0),listcouplist.get(0).get(4));
            } else {
                for (Coord n : game.allSelectablePieces(game.getJoueur2())) {
                    int i = n.getX();
                    int j = n.getY();
                    for (Coup m : game.allMoves(new Coord(i, j), this)) {
                        couplist.add(m);
                        if((playIA(game, new NodeIA(depth+1, false, couplist,m)).getWin())||(playIA(game, new NodeIA(depth+1, false, couplist,m)).getDepth()==maxdepth)){
                            listcouplist.add((playIA(game, new NodeIA(depth+1, false, couplist,m)).getCouplist()));
                        }
                        couplist.remove(m);
                    }
                }
                Collections.sort(listcouplist, new Comparator<ArrayList<Coup>>() {
                    @Override
                    public int compare(ArrayList<Coup> a, ArrayList<Coup> b) {
                        if(a.size()>b.size()){
                            return b.size();
                        } else {
                            return a.size();
                        }
                    }
                });
                for (ArrayList<Coup> n : listcouplist) {
                    if ((n.size() < 5)&&(n.size()!=0)) {
                        return new NodeIA(n.size(),false,n,n.get(n.size()-1));
                    }
                }
                return new NodeIA(5,false,listcouplist.get(0),listcouplist.get(0).get(0));
            }
        }
    }

    private Coup randomIA(Partie game){
        Random random = new Random();
        double[][] grille = new double[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grille[i][j] = game.getGrille()[i][j];
            }
        }
        ArrayList<Coup> couplist = new ArrayList<Coup>();
        if(getNumber()==1){
            for (Coord n : game.allSelectablePieces(game.getJoueur1())) {
                int i = n.getX();
                int j = n.getY();
                for (Coup m : game.allMoves(new Coord(i, j), this)) {
                    couplist.add(m);
                }
            }
            int randomInteger = random.nextInt(couplist.size());
            return couplist.get(randomInteger);
        } else {
            for (Coord n : game.allSelectablePieces(game.getJoueur2())) {
                int i = n.getX();
                int j = n.getY();
                for (Coup m : game.allMoves(new Coord(i, j), this)) {
                    couplist.add(m);
                }
            }
            int randomInteger = random.nextInt(couplist.size());
            return couplist.get(randomInteger);
        }
    }

}