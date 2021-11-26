package MissionariesAndCarnivals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class SpaceSearcher {
    private ArrayList<State> frontier;
    private HashSet<State> closedSet;


    SpaceSearcher() {
        this.frontier = new ArrayList<>();
        this.closedSet = new HashSet<>();
    }


    State AstarAlgorithm(State initialState)
    {
        if(initialState.isFinal()) return initialState;
        this.frontier.add(initialState);
        while(this.frontier.size() > 0){
            State currentState = this.frontier.remove(0);

            if(currentState.isFinal()) return currentState;
            if (!this.closedSet.contains(currentState)){
                this.closedSet.add(currentState);
                this.frontier.addAll(currentState.getChildren());
            }
            Collections.sort(this.frontier); //sort based on heuristicScore

        }
        return null;
    }
}
