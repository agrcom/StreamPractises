import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ReduceTest {

    @Test
    public void compareElements() {
        List<Person> persons = makePersonsCollection();

        persons.stream()
                .reduce((p1, p2) -> p1.getAge() > p2.getAge() ? p1 : p2)
                .ifPresent(person -> System.out.println(person.getName() + " " + person.getAge()));
    }

    @Test
    public void joinToOneObject() {
        List<Person> persons = makePersonsCollection();

        Person result =
                persons
                        .stream()
                        .reduce(new Person(0, ""), (p1, p2) -> {
                            p1.setAge(p1.getAge() + p2.getAge());
                            p1.setName(p1.getName() + p2.getName());
                            return p1;
                        });

        System.out.format("name=%s; age=%s", result.getName(), result.getAge());
    }

    @Test
    public void accumulatorReduce() {
        List<Person> persons = makePersonsCollection();

        /*Integer ageSum = persons.stream()
                .reduce(0, (sum, person) -> sum += person.getAge(), (sum1, sum2) -> sum1 + sum2);*/

        Integer ageSum =
                persons.stream()
                        .reduce(0, (sum, person) -> {
                                    System.out.println(String.format("accumulator: name: %s, age: %s\n", person.getName(), person.getAge()));
                                    return sum += person.getAge();
                                },
                                (sum1, sum2) ->
                                {
                                    System.out.println(String.format("combinator: sum1:%s, sum2:%s\n", sum1, sum2));
                                    return sum1 + sum2;
                                }
                                );

        System.out.println(ageSum);
    }


    @Test
    public void accumulatorReduceParallelStream() {
        List<Person> persons = makePersonsCollection();

        Integer ageSum =
                persons.parallelStream()
                        .reduce(0, (sum, person) -> {
                                    System.out.println(String.format("accumulator: name: %s, age: %s", person.getName(), person.getAge()));
                                    return sum += person.getAge();
                                },
                                (sum1, sum2) ->
                                {
                                    System.out.println(String.format("combinator: sum1:%s, sum2:%s", sum1, sum2));
                                    return sum1 + sum2;
                                }
                        );

        System.out.println(ageSum);
    }

    private List<Person> makePersonsCollection() {
        List<Person> result = new ArrayList<>();
        result.add(new Person(35, "Jen"));
        result.add(new Person(30, "Mos"));
        result.add(new Person(33, "Roy"));
        result.add(new Person(38, "Boss"));

        return result;
    }
}
