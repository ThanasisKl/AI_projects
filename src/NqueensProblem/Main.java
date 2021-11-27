package NqueensProblem;

public class Main {
    public static void main(String[] args){
        Genetic g = new Genetic();
        long start = System.currentTimeMillis();  //calculates time
                         //runs genetic algorithm (population size, mutation probabiity, max-loops, N)
        Chromosome chromosome = g.geneticAlgorithm(300,0.08,100000,8); //runs genetic algorithm (population size)
        long end = System.currentTimeMillis();
        chromosome.showChessboard();
        System.out.println("\nTotal time of searching: " + (double)(end - start) / 1000 + " sec."); //prints time
    }
}
