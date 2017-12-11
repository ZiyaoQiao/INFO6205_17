package Implement1;

import org.junit.Test;
import java.util.HashSet;
import static org.junit.Assert.*;


public class GATest {
    @Test public void test0() {
        final Person person = new Person();
        HashSet<String> dna = new HashSet<>();
        for(String i: person.DNA){
            dna.add(i);
        }
        assertEquals(Person.DNASize, dna.size());
    }
    @Test public void test1() {
        final Person father = new Person();
        final Person mother = new Person();
        final Person child = new Person(father, mother);
        HashSet<String> dna = new HashSet<>();
        for(String i: child.DNA){
            dna.add(i);
        }
        assertEquals(Person.DNASize, dna.size());
    }
    @Test public void test2() {
        final Person father = new Person();
        final Person child = new Person(father);
        HashSet<String> dna = new HashSet<>();
        for(String i: child.DNA){
            dna.add(i);
        }
        assertEquals(Person.DNASize, dna.size());
    }
    @Test public void test3() {
        final Person father = new Person();
        final Person mother = new Person();
        final Person child = new Person(father, mother);
        assert(!child.DNA.toString().equals(father.DNA.toString()));
        assert(!child.DNA.toString().equals(mother.DNA.toString()));
    }

    @Test public void test4() {
        final GA gsp = new GA();
        gsp.evolve();
        assert(gsp.list.get(0).distance <= gsp.list.get(gsp.list.size()-1).distance);
    }

    @Test public void test5(){
        final GA gsp = new GA();
        gsp.sort();
        Person person1 = gsp.list.get(0);
        gsp.evolve();
        Person person2 = gsp.list.get(0);
        assert(person2.distance <= person1.distance);
    }

    @Test public void test6(){
        final GA gsp = new GA();
        gsp.evolve();
        int size = gsp.list.size();
        gsp.select();
        int afterSize = gsp.list.size();
        assert(size > afterSize);
        assert(gsp.list.get(0).distance <= gsp.list.get(gsp.list.size()-1).distance);
    }

    @Test public void test7(){
        final GA gsp = new GA();
        gsp.evolve();
        Person person = gsp.selectMin();
        for(Person p : gsp.list){
            assert(p.distance >= person.distance);
        }
    }

    @Test public void test8(){
        final Person person = new Person();
        int i = 100;
        while(i-- > 0){
            person.mutate();
            HashSet<Integer> set = new HashSet<>();
            for(String str:person.DNA){
                set.add(person.translateSingleDNA(str));
            }
            assertEquals(person.DNASize, set.size());
        }
    }
}