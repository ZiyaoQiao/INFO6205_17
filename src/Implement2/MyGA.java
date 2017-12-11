package Implement2;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;

public class MyGA {
    public int size;
    public MyPerson[] remain;
    public MyPerson[] population;

    private final static Logger logger = Logger.getLogger(MyGA.class);

    public MyGA() {
        size = Parameters.REMAIN;
        remain = new MyPerson[size];
        population = new MyPerson[size * (size + 1)];
        initialize();
    }

    public void initialize() {
        for (int i = 0; i < size; i++) {
            remain[i] = new MyPerson();
            remain[i].generateRandomWeight();
        }
    }

    public void reproduce() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                population[i * size + j] = new MyPerson(remain[i], remain[j]);
            }
        }

        for (int i = 0; i < size; i++) {
            population[size * size + i] = remain[i];
        }
        cull();
    }

    public void cull() {
        Arrays.sort(population);
        for (int i = 0; i < size; i++) {
            remain[i] = population[i];
        }
    }

    public void logging(int generation){
        if(logger.isInfoEnabled()){
            logger.info("************* Generation " + generation + " *************");
            logger.info("The best of this generation : " + remain[0].printPath());
            logger.info("Distance = " + remain[0].getDistance());
            logger.info("The worst of this generation : " + remain[size - 1].printPath());
            logger.info("Distance = " + remain[size - 1].getDistance());
        }
    }

    public static void main(String[] args) {
        MyGA myGA = new MyGA();
        for (int i = 0; i < Parameters.GENERATION; i++) {
            myGA.reproduce();
            myGA.logging(i);


// switch for early termination:
//            if (Arrays.equals(remain[0].getWeight(), remain[size - 1].getWeight())) {
//                return;
//            }
        }
    }


}
