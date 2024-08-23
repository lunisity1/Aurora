package dev.lunisity.aurora.json.source.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dev.lunisity.aurora.json.source.FileStore;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

@Getter
public class JsonFileStore<V> implements FileStore<V> {

    private final List<V> elements;

    private final Class<V> clazz;
    private final File file;
    private final Gson gson;

    @SneakyThrows
    public JsonFileStore(final Class<V> clazz, final File file, final Gson gson) {
        this.clazz = clazz;
        this.file = file;

        this.gson = gson;

        if (!this.file.exists()) {
            this.elements = new ArrayList<>();
            return;
        }
        final FileReader fileReader = new FileReader(this.file);
        final Type type = TypeToken.getParameterized(List.class,  clazz).getType();
        this.elements = this.gson.fromJson(fileReader, type);
    }

    public JsonFileStore(final Class<V> clazz, final File folder, final String name, final Gson gson) {
        this(clazz, new File(folder, name + ".json"), gson);
    }

    @Override
    public V element() {
        final int index = ThreadLocalRandom.current().nextInt(this.elements.size());
        return this.elements.get(index);
    }

    @Override
    public V element(final int index) {
        return this.elements.get(index);
    }

    @Override
    public void insert(final V element) {
        this.elements.add(element);
    }

    @Override
    public void delete(final V element) {
        this.elements.remove(element);
    }

    @Override
    public boolean contains(final V element) {
        return this.elements.contains(element);
    }

    @Override
    public void removeIf(final Predicate<V> predicate) {
        this.elements.removeIf(predicate);
    }

    @Override
    public boolean anyMatch(final Predicate<V> predicate) {
        return this.elements.stream().anyMatch(predicate);
    }

    @Override
    public V find(Predicate<V> predicate) {
        return this.elements.stream().filter(predicate).findFirst().orElse(null);
    }

    @Override
    public int size() {
        return this.elements.size();
    }

    @SneakyThrows
    @Override
    public void save() {
        final FileWriter fileWriter = new FileWriter(this.file);
        this.gson.toJson(this.elements, fileWriter);
        fileWriter.flush();
    }

    @NotNull
    @Override
    public Iterator<V> iterator() {
        return this.elements.iterator();
    }
}
