package MissionariesAndCarnivals;

import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args){
        State initialState = new State(3, 2);
        int crossingsAllowed = 40;
        SpaceSearcher searcher = new SpaceSearcher();
        long start = System.currentTimeMillis();       //calculate time
        State terminal_state = searcher.AstarAlgorithm(initialState);
        long end = System.currentTimeMillis();
        if(terminal_state == null) {
            System.out.println("Can not find solution.");
        }else{
            State state = terminal_state;
            ArrayList<State> path2solution = new ArrayList<>();
            path2solution.add(terminal_state);
            int crossings = 0;
            while(state.getFather() != null) { // back-tracking
                crossings++;
                path2solution.add(state.getFather());
                state = state.getFather();
            }
            Collections.reverse(path2solution);   //reverse arraylist
            int i = 1;
            if (crossings <= crossingsAllowed){
                for(State st: path2solution)         // print the path from start to finish
                {
                    System.out.println(i+")");
                    i++;
                    st.showState();
                }
                System.out.println("\nSearch time:" + (double)(end - start) / 1000 + " sec."); //print time
            }else {
                System.out.println("Failed: Crossings limit exceeded ---- hint: more crossings needed");

            }
        }
    }
}
