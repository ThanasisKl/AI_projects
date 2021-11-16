package n_queens_problem;

public class Main {
    public static void main(String[] args){
        Genetic g = new Genetic();
        long start = System.currentTimeMillis();
        Chromosome chromosome = g.geneticAlgorithm(100,0.8,100000,10);
        long end = System.currentTimeMillis();
        chromosome.showChessboard();
        System.out.println("\nTotal time of searching: " + (double)(end - start) / 1000 + " sec.");
    }
}
