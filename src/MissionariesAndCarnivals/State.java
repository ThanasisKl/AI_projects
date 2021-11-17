package MissionariesAndCarnivals;

import java.util.ArrayList;

public class State implements Comparable<State> {
    private int totalPeople;
    private int cannibalsLeft;
    private int missionariesLeft;
    private int cannibalsRight;
    private int missionariesRight;
    private position boatPosition; //true if its on the left, false if its on the right
    private State father = null;
    private int heuristicScore;
    private int boatCapacity;
    private int cost;   //tree depth

    public State(int N,  int M) {
        this.totalPeople = 2*N;
        this.cannibalsLeft = N;
        this.missionariesLeft = N;
        this.cannibalsRight = 0;
        this.missionariesRight = 0;
        this.boatCapacity = M;
        this.boatPosition = position.LEFT;
        this.cost = 0;
    }


    public State(State oldState){
        this.setCannibalsLeft(oldState.getCannibalsLeft());
        this.setCannibalsRight(oldState.getCannibalsRight());
        this.setMissionariesLeft(oldState.getMissionariesLeft());
        this.setMissionariesRight(oldState.getMissionariesRight());
        this.setBoatPos(oldState.getBoatPos());
        this.setBoatCapacity(oldState.getBoatCapacity());
        this.setTotalPeople(oldState.getTotalPeople());
        this.setCost(oldState.getCost());
    }

    /*
    checks if the state is final.By final, we want 0 cannibals and 0 missionaries on the left side
     */
    public boolean isFinal(){
        return (this.cannibalsLeft == 0 && this.missionariesLeft == 0);
    }

    /*
    checks is the boat maneuver about to happen is valid(we dont want more canibals on the boat than missionaries)
     */

    /*
    checks if a state is valid
     */
    public boolean isValid(){return ((this.cannibalsLeft<= this.missionariesLeft || this.missionariesLeft ==0) && (this.cannibalsRight<=this.missionariesRight || this.missionariesRight ==0) );}
    /*
     move the boat to the right side of the river
     */




    public boolean moveBoat(int cannibals, int missionaries , position boatPos){
        if(cannibals ==0 && missionaries ==0) return false;
        if(cannibals + missionaries > boatCapacity)return false;
        if(cannibals>missionaries && missionaries !=0) return false;
        if(boatPos.equals(position.LEFT)){
            this.missionariesLeft -= missionaries;
            this.cannibalsLeft -= cannibals;
            this.missionariesRight += missionaries;
            this.cannibalsRight += cannibals;
            boatPosition = position.RIGHT;
        }else{
            this.missionariesRight -= missionaries;
            this.cannibalsRight -= cannibals;
            this.missionariesLeft += missionaries;
            this.cannibalsLeft += cannibals;
            boatPosition = position.LEFT;
        }
        if(!isValid()){
            return false;
        }
        return true;
    }


    public ArrayList<State> getChildren(){

        ArrayList<State> children = new ArrayList<>();

        if(boatPosition.equals(position.LEFT)){
            for(int i=0; i<= cannibalsLeft ; i++) {
                for (int j = 0; j <= missionariesLeft; j++) {
                    State child = new State(this);
                    if(child.moveBoat(i, j, this.boatPosition)){
                        child.addScore();
                        child.countRemainingPeople();
                        child.setFather(this);
                        children.add(child);
                    }
                }
            }
        }else{
            for(int i=0; i<= cannibalsRight ; i++) {
                if(missionariesRight == 0){
                    State child = new State(this);
                    if(child.moveBoat(i, 0, this.boatPosition)){
                        child.addScore();
                        child.countRemainingPeople();
                        child.setFather(this);
                        children.add(child);
                    }
                }else{
                    for (int j = 0; j <= missionariesRight; j++) {
                        State child = new State(this);
                        if(child.moveBoat(i, j, this.boatPosition)){
                            child.addScore();
                            child.countRemainingPeople();
                            child.setFather(this);
                            children.add(child);
                        }
                    }
                }

            }
        }
        return children;
    }


    void print(){
        System.out.println("-------------------------------------");

        for(int i=0; i<cannibalsLeft; i++){
            System.out.print("C");
        }

        System.out.print(" ");

        for(int i=0; i<missionariesLeft; i++){
            System.out.print("M");
        }

        System.out.print(" ~~~~~~ ");


        for(int i=0; i<cannibalsRight; i++){
            System.out.print("C");
        }

        System.out.print(" ");

        for(int i=0; i<missionariesRight; i++){
            System.out.print("M");
        }
        System.out.print("\n");
        System.out.println("-------------------------------------");
    }

    private void countRemainingPeople(){
        int peopleLeft = missionariesLeft + cannibalsLeft;
        if(boatPosition.equals(position.RIGHT)){
            heuristicScore = 2*peopleLeft;
        }else if(boatPosition.equals(position.LEFT)==true && peopleLeft==1){
            heuristicScore = 1;
        }else if(boatPosition.equals(position.LEFT) && peopleLeft>1){
            heuristicScore = 2*peopleLeft - 3;
        }else if(peopleLeft == 0){
            heuristicScore = 0;
        }
        heuristicScore += cost;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCannibalsLeft() {
        return cannibalsLeft;
    }

    public void setCannibalsLeft(int cannibalsLeft) {
        this.cannibalsLeft = cannibalsLeft;
    }

    public int getMissionariesLeft() {
        return missionariesLeft;
    }

    public void setMissionariesLeft(int missionariesLeft) {
        this.missionariesLeft = missionariesLeft;
    }

    public int getCannibalsRight() {
        return cannibalsRight;
    }

    public void setCannibalsRight(int cannibalsRight) {
        this.cannibalsRight = cannibalsRight;
    }

    public int getMissionariesRight() {
        return missionariesRight;
    }

    public void setMissionariesRight(int missionariesRight) {
        this.missionariesRight = missionariesRight;
    }

    public position getBoatPos() {
        return boatPosition;
    }

    public void setBoatPos(position boatPos) {
        this.boatPosition = boatPos;
    }


    public int getBoatCapacity() { return boatCapacity; }

    public void setBoatCapacity(int boatCapacity) { this.boatCapacity = boatCapacity; }

    State getFather()
    {
        return this.father;
    }

    void setFather(State father)
    {
        this.father = father;
    }

    public int getTotalPeople() {
        return totalPeople;
    }

    public void setTotalPeople(int totalPeople) {
        this.totalPeople = totalPeople;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this.cannibalsLeft != ((State)obj).cannibalsLeft
                || this.missionariesLeft != ((State) obj).missionariesLeft
                || this.boatPosition != ((State) obj).boatPosition) {return false;}

        return true;
    }

    @Override
    public int hashCode()
    {
        return 2^cannibalsLeft + 5^ missionariesLeft;
    }

    @Override
    public int compareTo(State s)
    {
        return Double.compare(this.heuristicScore, s.heuristicScore); // compare based on the heuristic score.
    }

    public void addScore(){
        cost++;
    }



}
