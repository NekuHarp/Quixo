package sample;

public class Coup {

    private Coord initialPos;
    private Coord finalPos;

    public Coup(Coord initialPos, Coord finalPos) {
        this.initialPos = initialPos;
        this.finalPos = finalPos;
    }

    public Coord getInitialPos() {
        return initialPos;
    }

    public void setInitialPos(Coord initialPos) {
        this.initialPos = initialPos;
    }

    public Coord getFinalPos() {
        return finalPos;
    }

    public void setFinalPos(Coord finalPos) {
        this.finalPos = finalPos;
    }
}
