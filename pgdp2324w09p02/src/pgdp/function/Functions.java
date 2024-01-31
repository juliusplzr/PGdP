package pgdp.function;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Functions {
    public static <A,B> List<B> map(List<A> toMap,Function<A,B> function){
        var list = new ArrayList<B>();

        for (var e : toMap) {
            list.add(function.apply(e));
        }

        return list;
    }
    public static List<Integer> square(List<Integer> toSquare){
        return map(toSquare, i -> i * i);
    }
    public static <A> List<String> toString(List<A> toString){
        return map(toString, Object::toString);
    }
    public static <A> List<A> filter(List<A> toFilter, Predicate<A> filter){
       var list = new ArrayList<A>();

       for (var e : toFilter) {
           if (filter.test(e)) {
               list.add(e);
           }
       }

       return list;
    }

    public static <A> List<A> filterAny(List<A> toFilter, Predicate<A> filter1, Predicate<A> filter2){
        return filter(toFilter, filter1.or(filter2));
    }

    public static List<Integer> multiple2or7(List<Integer>numbers){
        Predicate<Integer> div2 = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer % 2 == 0;
            }
        };

        Predicate<Integer> div7 = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer % 7 == 0;
            }
        };

        return filterAny(numbers, div2, div7);
    }
}
