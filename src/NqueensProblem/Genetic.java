package NqueensProblem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Genetic {
    private ArrayList<Chromosome> population = null;
    private ArrayList<Integer> probabilityArray = null;

    public Genetic(){}

    public Chromosome geneticAlgorithm(int populationSize, double mutationProbability, int loops, int N){
        int solutionScore = findSolutionScore(N);
        this.population = new ArrayList<Chromosome>();
        for(int i=0; i<populationSize; i++){
            this.population.add(new Chromosome(N));
        }
        this.updateProbabilityArray();

        Random r = new Random();
        for(int j=0; j<loops;j++){
            ArrayList<Chromosome> newPopulation = new ArrayList<Chromosome>();
            for (int i=0; i<populationSize; i++){
                int parent1Index = this.probabilityArray.get(r.nextInt(this.probabilityArray.size()));
                Chromosome parent1 = this.population.get(parent1Index);
                int parent2Index;
                do{
                    parent2Index = this.probabilityArray.get(r.nextInt(this.probabilityArray.size()));
                }while(parent2Index != parent1Index);

                Chromosome parent2 = this.population.get(parent2Index);

                Chromosome child = this.reproduce(parent1,parent2,N);

                if(r.nextDouble() < mutationProbability){
                    child.mutate(N);
                }

                newPopulation.add(child);
            }
            this.population = new ArrayList<Chromosome>(newPopulation);

            Collections.sort(this.population,Collections.reverseOrder());


            if(this.population.get(0).getScore() == solutionScore){
                System.out.println("Needed " + j + " loops....");
                return this.population.get(0);
            }else{
                this.updateProbabilityArray();
            }
        }

        System.out.println("Needed " + loops + " loops...");
        return this.population.get(0);
//        return test();
    }

    private int findSolutionScore(int N) {
        int sum = 0;
        for(int i=0;i<N;i++){
            sum += i;
        }
        System.out.println("Solution score: "+sum);
        return sum;
    }

    private void updateProbabilityArray() {
        this.probabilityArray = new ArrayList<Integer>();
        for (int i=0; i<this.population.size();i++){
            for(int j = 0; j<this.population.get(i).getScore(); j++){
                probabilityArray.add(i);
            }
        }
    }

    private Chromosome reproduce(Chromosome x, Chromosome y, int N) {
        Random r = new Random();
        int breakIndex = r.nextInt(N-1) + 1;
        int[] childGenes =  new int[N];

        for(int i=0; i<breakIndex;i++){
            childGenes[i] = x.getGenes()[i];
        }
        for(int i=breakIndex; i<childGenes.length;i++){
            childGenes[i] = y.getGenes()[i];
        }
        return new Chromosome(childGenes,N);
    }
}
