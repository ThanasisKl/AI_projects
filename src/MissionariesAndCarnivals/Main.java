package MissionariesAndCarnivals;

import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args){
        State initialState = new State(3, 2);
        SpaceSearcher searcher = new SpaceSearcher();
        long start = System.currentTimeMillis();
        State terminalState = searcher.AstarAlgorithm(initialState);
        long end = System.currentTimeMillis();
        if(terminalState == null) System.out.println("Could not find a solution.");
        else
        {
            State temp = terminalState;
            ArrayList<State> path = new ArrayList<>();
            path.add(terminalState);
            while(temp.getFather() != null)
            {
                path.add(temp.getFather());
                temp = temp.getFather();
            }
            Collections.reverse(path);
            int i = 1;
            for(State item: path)
            {
                System.out.println(i+")");
                i++;
                item.print();
            }
            System.out.println();
            System.out.println("Search time:" + (double)(end - start) / 1000 + " sec.");
        }
    }
}
