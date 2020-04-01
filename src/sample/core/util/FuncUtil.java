package sample.core.util;

import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class FuncUtil {
    public static UnaryOperator<Integer> inc(Integer d) { return (i) -> i + d; }

    public static Predicate<Integer> isGreaterThan(Integer d) { return (i) -> i < d; }

    public static Predicate<Integer> lessThan(Integer d) { return (i) -> i < d; }

    public static <T> Predicate<T> pred(Predicate<T> t) { return t; }
}
