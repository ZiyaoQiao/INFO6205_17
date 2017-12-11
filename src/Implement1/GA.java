package Implement1;

import org.apache.log4j.Logger;

import java.util.*;

public class GA {


    public static final int populationSize = 2000;
    public static final double generation = 100;
    public static final double selfRate = 0.9;
    //public static final double[] mapXList = {17 , 2 , 3, 4, 5, 6, 7, 22, 9, 19, 11, 12, 13, 14, 15, 16};
    //public static final double[] mapYList = {17 , 2, 3, 4, 5, 6, 7, 22, 9,19,11,12,13,14,15,16};
    //public static final double[] mapXList = {60, 180, 80, 140, 20, 100, 200, 140, 40, 100, 180, 60, 120, 180, 20, 100, 200, 20, 60, 160};
    //public static final double[] mapYList = {200, 200, 180, 180, 160, 160, 160, 140, 120, 120, 100, 80, 80, 60, 40, 40, 40, 20, 20, 20};
    public static final double[] mapXList = {1 , 3.6 , 2.76 , 7.2 , 23.1 , 12.6 , 16.4 , 5.2 , 8.2 , 14.2 , 8.4 , 5.6 , 7.5 , 2 ,   6 ,   8};
    public static final double[] mapYList = {1 , 5.3 , 4.8 , 3.7 ,  6 ,    7.23 ,  9 ,   3.5 , 9.4 , 2.4 ,  6.4 , 8 ,   3.6 , 2.9 , 8.4 , 4.7};

    final static Logger logger = Logger.getLogger(GA.class);

    List<Person> list;
    ArrayList<ArrayList<Double>> map;

    public GA(){
        map = new ArrayList<>();
        for(int i = 0 ; i<Person.DNASize; i++){
            ArrayList<Double> tmp = new ArrayList<>();
            tmp.add(mapXList[i]);
            tmp.add(mapYList[i]);
            map.add(tmp);
        }
        list = new ArrayList<>();
        for(int i = 0 ; i<populationSize ; i++){
            list.add(new Person());
        }
    }

    public double fitness(Person person){
        if(person.distance == 0) {
            double distance = 0;
            int j = person.translateSingleDNA(person.DNA.get(0));
            int pre = 0;
            for (int i = 1; i < person.DNASize; i++) {
                pre = j;
                j = person.translateSingleDNA(person.DNA.get(i));
                distance += Math.sqrt(Math.pow(map.get(j).get(0) - map.get(pre).get(0), 2) + Math.pow(map.get(j).get(1) - map.get(pre).get(1), 2));
            }
            person.distance = distance;
        }
        return person.distance;
    }

    public void sort(){
        for(int i = 0; i<list.size(); i++) {
            fitness(list.get(i));
        }
        Collections.sort(list);
    }

    public void select(){
        sort();
        int end = (int)(list.size()*0.6);
        if(end > 4*populationSize)
            end = end/2;
        list = list.subList(0, end);
    }

    public void evolve(){
        select();
        Random rand = new Random();
        int size = list.size();
        for(int i = 0; i < size; i++){
            double pos = Math.random();
            int j = rand.nextInt(size);
            list.add(new Person(list.get(i),list.get(j)));

            if(pos > selfRate){
                list.add(new Person(list.get(i)));
            }
        }
        sort();
    }


    public void logging(int generation, GA gsp){
        if(logger.isInfoEnabled()){
            logger.info("This is generation " + generation);
            ArrayList<Integer> integers = selectMin().translate();
            StringBuffer points = new StringBuffer();
            StringBuffer locations = new StringBuffer();

            for(Integer i : integers){
                points.append(i + ";");
                locations.append(mapXList[i] + "," + mapYList[i] + " ; ");
            }
            logger.info("The best of this generation points: " + points);
            logger.info("The best of this generation locations: " + locations);
            logger.info("The best of this generation distance: " + gsp.list.get(0).distance);

            ArrayList<Integer> integers2 = selectMax().translate();
            StringBuffer points2 = new StringBuffer();
            StringBuffer locations2 = new StringBuffer();
            for(Integer i : integers2){
                points2.append(i + ";");
                locations2.append(mapXList[i] + "," + mapYList[i] + " ; ");
            }
            logger.info("The worst of this generation points: " + points2);
            logger.info("The worst of this generation locations: " + locations2);
            logger.info("The worst of this generation distance: " + gsp.list.get(list.size()-1).distance);
        }
    }

    public Person selectMin(){
        return list.get(0);
    }
    public Person selectMax(){
        return list.get(list.size()-1);
    }

    public static void main(String[] args){
        GA tsp = new GA();
        for(int i = 0; i < generation; i++) {
            tsp.evolve();
            tsp.logging(i , tsp);
        }
    }
}
