package MissionariesAndCarnivals;

public class State  {
    private int cannibalsLeft;
    private int cannibalsRight;
    private int missionnariesLeft;
    private int missionnariesRight;
    private int cannibalsOnBoat;
    private int missionariesOnBoat;
    private position boatPosition;
    private State father = null;

    public State(int cannibalsLeft, int cannibalsRight, int missionnariesLeft, int missionnariesRight, position boatPosition) {
        this.cannibalsLeft = cannibalsLeft;
        this.cannibalsRight = cannibalsRight;
        this.missionnariesLeft = missionnariesLeft;
        this.missionnariesRight = missionnariesRight;
        this.boatPosition = boatPosition;
        this.missionariesOnBoat = 0;
        this.cannibalsOnBoat = 0;
    }

    public int getCannibalsLeft() {
        return cannibalsLeft;
    }

    public void setCannibalsLeft(int cannibalsLeft) {
        this.cannibalsLeft = cannibalsLeft;
    }

    public int getCannibalsRight() {
        return cannibalsRight;
    }

    public void setCannibalsRight(int cannibalsRight) {
        this.cannibalsRight = cannibalsRight;
    }

    public int getMissionnariesLeft() {
        return missionnariesLeft;
    }

    public void setMissionnariesLeft(int missionnariesLeft) {
        this.missionnariesLeft = missionnariesLeft;
    }

    public int getMissionnariesRight() {
        return missionnariesRight;
    }

    public void setMissionnariesRight(int missionnariesRight) {
        this.missionnariesRight = missionnariesRight;
    }

    public position getBoatPosition() {
        return boatPosition;
    }

    public void setBoatPosition(position boatPosition) {
        this.boatPosition = boatPosition;
    }

    public State getFather() {
        return father;
    }

    public void setFather(State father) {
        this.father = father;
    }

    public boolean isFinal(){
        return (this.cannibalsLeft == 0 && this.missionnariesLeft == 0);
    }

    public boolean isValidBoatCapacity(){
        return (this.cannibalsOnBoat <= this.missionariesOnBoat);
    }

    public boolean isValid(){
        return ((cannibalsLeft <= missionnariesLeft) && (cannibalsRight <= missionnariesRight));
    }



}
