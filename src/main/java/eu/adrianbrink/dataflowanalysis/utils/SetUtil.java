package eu.adrianbrink.dataflowanalysis.utils;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by antreas on 3/7/17.
 */
public class SetUtil {

    public static<T> Set<T> filter(Set<T> set, Predicate<T> test) {
        return set.stream().filter(test).collect(Collectors.toSet());
    }
}
