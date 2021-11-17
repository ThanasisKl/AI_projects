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

    State BFS(State initialState) {
        if (initialState.isFinal()) return initialState;
        // step 1: put initial state in the frontier.
        this.frontier.add(initialState);
        // step 2: check for empty frontier.
        while (this.frontier.size() > 0) {
            // step 3: get the first node out of the frontier.
            State currentState = this.frontier.remove(0);
            // step 4: if final state, return.
            if (currentState.isFinal()) {
                return currentState;
            }
            // step 5: if the node is not in the closed set, put the children at the END of the frontier (queue).
            // else go to step 2.
            if (!this.closedSet.contains(currentState)) {
                this.closedSet.add(currentState);
                this.frontier.addAll(currentState.getChildren());
            }
        }

        return null;
    }

    State AstarAlgorithm(State initialState)
    {
        if(initialState.isFinal()) return initialState;
        // step 1: put initial state in the frontier.
        this.frontier.add(initialState);
        // step 2: check for empty frontier.
        while(this.frontier.size() > 0)
        {
            // step 3: get the first node out of the frontier.
            State currentState = this.frontier.remove(0);
            // step 4: if final state, return.
            if(currentState.isFinal()) return currentState;
            // step 5: put the children at the frontier
            if (!this.closedSet.contains(currentState)) {
                this.closedSet.add(currentState);
                this.frontier.addAll(currentState.getChildren());
            }
            // step 6: sort the frontier based on the heuristic score to get best as first
            Collections.sort(this.frontier);

        }
        return null;
    }
}
