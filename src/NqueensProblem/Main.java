package NqueensProblem;

public class Main {
    public static void main(String[] args){
        Genetic g = new Genetic();
        long start = System.currentTimeMillis();
        Chromosome chromosome = g.geneticAlgorithm(300,0.08,100000,8);
        long end = System.currentTimeMillis();
        chromosome.showChessboard();
        System.out.println("\nTotal time of searching: " + (double)(end - start) / 1000 + " sec.");
    }
}
