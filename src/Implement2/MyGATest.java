package Implement2;

import org.junit.Test;
import static org.junit.Assert.*;

public class MyGATest {
    @Test
    public void TestMyPersonGenerateRandomWeight() {
        final MyPerson person = new MyPerson();
        person.generateRandomWeight();
        assertNotEquals(0, person.getDistance());
    }

    @Test
    public void TestMyPersonReorder() {
        MyPerson mother  = new MyPerson();
        mother.generateRandomWeight();
        MyPerson father = new MyPerson();
        father.generateRandomWeight();
        assertNotEquals(0, mother.crossOver(father).getDistance());
    }

    @Test
    public void TestMyPersonMutation() {
        MyPerson person = new MyPerson();
        double distance = person.getDistance();
        for (int i = 0; i < 10000; i++) {
            person.mutation();
        }
        assertNotEquals(distance, person.getDistance());
    }

    @Test
    public void TestMyGAReproduce() {
        MyGA myGA = new MyGA();
        double distance = myGA.remain[0].getDistance();
        myGA.reproduce();
        assertNotEquals(distance, myGA.remain[0].getDistance());
    }
}
