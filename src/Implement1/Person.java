package Implement1;

import java.util.ArrayList;
import java.util.Random;

public class Person implements Comparable{
    public static final int DNASize = 16;
    public static final double mutateRate = 0.2;

    public static final String[] DNAList = {"00000","00001","00010","00011","00100","00101","00110","00111","01000","01001","01010","01011","01100","01101","01110","01111","10000","10001","10010","10011","10100","10101","10110","10111","11000","11001","11010","11011","11100","11101","11110","11111"};
    ArrayList<String> DNA;
    double distance = 0;

    public Person(){
        DNA = new ArrayList<>(DNASize);
        Random rand = new Random();
        int x = 0;
        for(int i  = 0; i < DNASize ; i++){
            do{
                x = rand.nextInt(DNASize);
            }while(DNA.contains(DNAList[x]));
            DNA.add(DNAList[x]);
        }
    }

    public Person(Person father, Person mother){
        crossover(father,mother,this);
        mutate();
    }

    public Person(Person self){
        this.DNA = self.DNA;
        mutate();
    }

    public ArrayList<Integer> translate(){
        ArrayList<Integer> integers = new ArrayList<>();
        for(String i: DNA){
            integers.add(Integer.parseInt(i, 2));
        }
        return integers;
    }

    public int translateSingleDNA(String dna){
        return Integer.parseInt(dna, 2);
    }

    public void crossover(Person father, Person mother, Person child){
        child.DNA = new ArrayList<>();
        for(int i = 0; i<DNASize; i++){
            if(i%2 == 0)
                child.DNA.add(father.DNA.get(i));
            else
                child.DNA.add("empty");
        }
        int i = 0;
        int j = 1;
        while(child.DNA.contains("empty")){
            if(i < DNASize && !(child.DNA.contains(mother.DNA.get(i))) && j < DNASize){
                child.DNA.set(j,mother.DNA.get(i));
                j += 2;
                i++;
            }else{
                i++;
            }
        }
    }

    public void mutate(){
        double poss = Math.random();
        if(poss< mutateRate){
            Random rand = new Random();
            int i, j;
            do{
                i = rand.nextInt(DNASize);
                j = rand.nextInt(DNASize);
            }while(i == j);
            String tmp = this.DNA.get(j);
            this.DNA.set(j,this.DNA.get(i));
            this.DNA.set(i,tmp);
        }
    }

    @Override
    public int compareTo(Object o) {
        if(this.distance > ((Person)o).distance)
            return 1;
        else if(this.distance == ((Person)o).distance)
            return 0;
        return -1;
    }
}