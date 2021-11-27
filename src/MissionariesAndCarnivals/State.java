package MissionariesAndCarnivals;

import java.util.ArrayList;

public class State implements Comparable<State> {
    private int totalPeople;
    private int cannibalsLeft;
    private int missionariesLeft;
    private int cannibalsRight;
    private int missionariesRight;
    private position boatPosition;
    private State father = null;
    private int heuristicScore;
    private int boatCapacity;
    private int cost;

    public State(int N,  int M) {         //constructor
        this.totalPeople = 2*N;
        this.cannibalsLeft = N;
        this.missionariesLeft = N;
        this.cannibalsRight = 0;
        this.missionariesRight = 0;
        this.boatCapacity = M;
        this.boatPosition = position.LEFT;
        this.cost = 0;
    }


    public State(State state){       //copy constructor
        this.cannibalsLeft = state.getCannibalsLeft();
        this.cannibalsRight = state.getCannibalsRight();
        this.missionariesLeft = state.getMissionariesLeft();
        this.missionariesRight = state.getMissionariesRight();
        this.boatPosition = state.getBoatPos();
        this.boatCapacity = state.getBoatCapacity();
        this.totalPeople = state.getTotalPeople();
        this.cost = state.getCost();
    }

    public boolean isFinal(){   //checks if a state is final
        return (this.cannibalsLeft == 0 && this.missionariesLeft == 0);
    }

    public boolean isValid(){   //checks if a state is valid
        return ((this.cannibalsLeft<= this.missionariesLeft || this.missionariesLeft ==0) && (this.cannibalsRight<=this.missionariesRight || this.missionariesRight ==0) );
    }

    public boolean isBoatValid(int cannibals,int missionaries){   //checks if a boat is valid
        return((cannibals + missionaries <= boatCapacity) && (!(cannibals>missionaries && missionaries !=0)) && (!(cannibals ==0 && missionaries ==0))) ;
    }

    public boolean moveBoat(int cannibals, int missionaries , position boatPos){     //moves boat from one side to another
        if(!isBoatValid(cannibals,missionaries))return false;
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
                        child.IncreaseScore();
                        child.calculateHeuristicScore();
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
                        child.IncreaseScore();
                        child.calculateHeuristicScore();
                        child.setFather(this);
                        children.add(child);
                    }
                }else{
                    for (int j = 0; j <= missionariesRight; j++) {
                        State child = new State(this);
                        if(child.moveBoat(i, j, this.boatPosition)){
                            child.IncreaseScore();
                            child.calculateHeuristicScore();
                            child.setFather(this);
                            children.add(child);
                        }
                    }
                }
            }
        }
        return children;
    }


    void showState(){      //prints a state
        for(int i=0; i<cannibalsLeft; i++){
            System.out.print("C" );
        }
        System.out.print(" ");
        for(int i=0; i<missionariesLeft; i++){
            System.out.print("M");
        }
        System.out.print(" \uD83C\uDF0A \uD83C\uDF0A \uD83C\uDF0A "); //river
        for(int i=0; i<cannibalsRight; i++){
            System.out.print("C");
        }
        System.out.print(" ");
        for(int i=0; i<missionariesRight; i++){
            System.out.print("M");
        }
        System.out.println("\n-------------------------------------");
    }

    private void calculateHeuristicScore(){
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

    public int getCannibalsLeft() {
        return cannibalsLeft;
    }

    public int getMissionariesLeft() {
        return missionariesLeft;
    }

    public int getCannibalsRight() {
        return cannibalsRight;
    }

    public int getMissionariesRight() {
        return missionariesRight;
    }

    public position getBoatPos() {
        return boatPosition;
    }

    public int getBoatCapacity() {
        return boatCapacity;
    }

    State getFather() {
        return this.father;
    }

    void setFather(State father)
    {
        this.father = father;
    }

    public int getTotalPeople() {
        return totalPeople;
    }


    @Override
    public int hashCode() {
        return 7^cannibalsLeft + 5^missionariesLeft;
    }

    @Override
    public boolean equals(Object obj) {
        if(this.cannibalsLeft == ((State)obj).cannibalsLeft && this.missionariesLeft == ((State) obj).missionariesLeft && this.boatPosition == ((State) obj).boatPosition) {
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int compareTo(State s)
    {
        return Double.compare(this.heuristicScore, s.heuristicScore);
    }

    public void IncreaseScore(){
        cost++;
    }
}
