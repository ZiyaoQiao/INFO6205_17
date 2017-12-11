package Implement2;

import java.util.Arrays;
import java.util.Random;

public class MyPerson implements Comparable{
    int size;
    double[] weight;
    double distance;

    public MyPerson() {
        size = Parameters.SIZE;
        weight = new double[size];
        distance = 0;
    }

    public MyPerson(MyPerson mother, MyPerson father) {
        this();
        this.weight = mother.crossOver(father).getWeight();
        updateDistance();
    }

    public double[] getWeight() {
        return weight;
    }

    public void generateRandomWeight() {
        Random r = new Random();
        for (int i = 1; i < size; i++) {
            while (true) {
                int index = r.nextInt(size);
                if (weight[index] == 0) {
                    weight[index] = i;
                    break;
                }
            }
        }
        updateDistance();
        reorder();
    }

    public void reorder() {
        int first = -1;
        int last = -1;
        for (int i = 0; i < size; i++) {
            if (weight[i] == 0) {
                first = i;
                break;
            }
        }

        for (int i = 0; i < size; i++) {
            if (weight[i] == size - 1) {
                last = i;
                break;
            }
        }

        if (first > last) {
            reverse();
        }
    }

    public void reverse() {
        double[] newWeight = new double[size];
        for (int i = 0; i < size; i++) {
            newWeight[i] = size - 1 - weight[i];
        }
        weight = newWeight;
    }

    public int[] getPath() {
        int[] path = new int[size];
        DataPair[] dataPairs = new DataPair[size];
        for (int i = 0; i < size; i++) {
            dataPairs[i] = new DataPair(weight[i], i);
        }

        Arrays.sort(dataPairs);

        for (int i = 0; i < size; i++) {
            path[i] = dataPairs[i].getIndex();
        }
        return path;
    }

    public StringBuilder printPath() {
        int[] path = getPath();
        StringBuilder sb = new StringBuilder("Path = ");

        for (int i = 0; i < size; i++) {
            sb.append(path[i] + " ");
        }
        return sb;
    }

    @Override
    public int compareTo(Object o) {
        if (distance > ((MyPerson)o).distance) {
            return 1;
        } else if (distance < ((MyPerson)o).distance) {
            return -1;
        } return 0;
    }

    public double updateDistance() {
        distance = 0;
        int[] path = getPath();
        for (int i = 0; i < Parameters.SIZE - 1; i++) {
            double dx = Parameters.X[path[i + 1]] - Parameters.X[path[i]];
            double dy = Parameters.Y[path[i + 1]] - Parameters.Y[path[i]];
            distance += Math.sqrt(dx * dx + dy * dy);
        }
        return distance;
    }

    public class DataPair implements Comparable {
        double weight;
        int index;

        public double getWeight() {
            return weight;
        }

        public int getIndex() {
            return index;
        }

        @Override
        public int compareTo(Object anotherDataPair) {
            double result = this.weight - ((DataPair) anotherDataPair).getWeight();
            if (result > 0) {
                return 1;
            } else if (result < 0) {
                return -1;
            } else {
                return 0;
            }
        }

        public DataPair(double weight, int index) {
            this.weight = weight;
            this.index = index;
        }
    }

    public MyPerson crossOver(MyPerson father) {
        mutation();
        father.mutation();

        MyPerson child = new MyPerson();
        DataPair[] dataPairs = new DataPair[size];
        for (int i = 0; i < size; i++) {
            dataPairs[i] = new DataPair((getWeight()[i] * 1.01 + father.getWeight()[i] * 1.0001) / 2, i);
        }

        Arrays.sort(dataPairs);

        for (int i = 0; i  < size; i++) {
            child.getWeight()[dataPairs[i].getIndex()] = i;
        }
        child.reorder();
        child.updateDistance();

        return child;
    }

    public void mutation() {
        Random r = new Random();
        if (r.nextDouble() < Parameters.MUTATE) {
            int source = r.nextInt(size);
            int target = -1;
            while (true) {
                target = r.nextInt(size);
                if (source != target) {
                    break;
                }
            }
            double temp = weight[source];
            weight[source] = weight[target];
            weight[target] = temp;
        }
        updateDistance();
    }

    public double getDistance() {
        return distance;
    }
}
