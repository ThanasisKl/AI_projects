package n_queens_problem;
import java.util.Random;

public class Chromosome implements  Comparable<Chromosome>{
    private int [] genes;
    private int score;

    public Chromosome(int N){
        this.genes = new int[N];
        Random r = new Random();
        for(int i=0; i<N;i++){
            this.genes[i] = r.nextInt(N);
        }
        this.score = findScore();
    }

    public Chromosome(int [] genes,int N){
        this.genes = new int[N];
        for(int i=0; i<N;i++){
            this.genes[i] = genes[i];
        }
        this.score = findScore();
    }

    public int[] getGenes() {
        return genes;
    }

    public void setGenes(int[] genes) {
        this.genes = genes;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }



    private int findScore() {
        int not_threats = 0;
        for(int i=0;i<this.genes.length;i++){
            for(int j=i+1; j<this.genes.length;j++){ //
                if((this.genes[i] != this.genes[j]) && (Math.abs(i-j) != Math.abs(this.genes[i] - this.genes[j]))){
                    not_threats++;
                }
            }
        }
        return not_threats;
    }

    public void mutate(int N){
        Random r = new Random();
        this.genes[r.nextInt(N)] = r.nextInt(N);
        this.score = findScore();
    }

    public void showChessboard(){

        System.out.print("Best Chromosome: ");
        for(int i=0; i<genes.length;i++){
            System.out.print(this.genes[i]);
            System.out.print(" ");
        }
        System.out.print(" with score: ");
        System.out.println(this.score);

        System.out.println("Visual Result:\n");

        for(int i=0; i<genes.length; i++){
            for (int j=0; j<genes.length; j++){
                if (genes[j] == i){
                    System.out.print("|Q");
                }else{
                    System.out.print("| ");
                }
            }
            System.out.println("|");
        }
    }

    @Override
    public boolean equals(Object obj){
        for(int i=0; i<this.genes.length; i++){
            if(this.genes[i] != ((Chromosome)obj).genes[i]){
                return false;
            }
        }
        return true;
    }

    @Override
    public int compareTo(Chromosome chromosome){
        return this.score - chromosome.score;
    }
}
