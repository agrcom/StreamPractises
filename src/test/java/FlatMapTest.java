import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class FlatMapTest {

    @Test
    public void flatMapPrintAll() {
        List<Foo> foos = buildTestCollection();

        foos.stream()
                .flatMap(f -> f.bars.stream())
                .forEach(bar -> System.out.println(bar.name));
    }

    @Test
    public void buildAndListObjectInOneStream(){
        IntStream.range(1,500)
                .mapToObj(i -> new Foo("foo"+i))
                .peek(f ->
                        IntStream
                                .range(1, 500)
                                .forEach(i -> f.bars.add(new Bar("bar "+i + " <- "+f.name ))))
                .flatMap(f -> f.bars.stream())
                .forEach(bar -> System.out.println(bar.name));
    }

    private List<Foo> buildTestCollection() {
        List<Foo> foos = new ArrayList<>();

        IntStream
                .range(1, 6)
                .forEach(i -> foos.add(new Foo("foo" + 1)));

        foos.forEach(f ->
                IntStream
                        .range(1, 6)
                        .forEach(i -> f.bars.add(new Bar("bar "+i + " <- "+f.name ))));
        return foos;
    }
}
