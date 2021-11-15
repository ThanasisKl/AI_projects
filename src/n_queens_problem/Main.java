package n_queens_problem;

public class Main {
    public static void main(String[] args){
        Genetic g = new Genetic();
        Chromosome chromosome = g.geneticAlgorithm(100,0.8,10000,9);
        chromosome.print();
    }
}
