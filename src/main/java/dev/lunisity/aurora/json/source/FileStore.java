package dev.lunisity.aurora.json.source;

import java.io.File;
import java.util.List;
import java.util.function.Predicate;

public interface FileStore<V> extends TypeAware<V>, Iterable<V> {

    List<V> getElements();

    V element();

    V element(final int index);

    void insert(final V element);

    void delete(final V element);

    boolean contains(final V element);

    void removeIf(final Predicate<V> predicate);

    boolean anyMatch(final Predicate<V> predicate);

    V find(final Predicate<V> predicate);

    int size();

    File getFile();

    void save();

}
