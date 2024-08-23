package dev.lunisity.aurora.json.source.impl;

import com.google.gson.Gson;
import dev.lunisity.aurora.json.entity.StorageEntity;
import dev.lunisity.aurora.json.source.DataSource;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.UUID;

@AllArgsConstructor
public class FileDataSource<V extends StorageEntity> implements DataSource<V> {

    private final File folder;
    private final Class<V> clazz;

    private final Gson gson;

    @Override
    public void invalidate(final UUID uuid) {

        final File file = new File(this.folder, uuid + ".json");

        if (!file.exists()) {
            return;
        }
        file.delete();
    }

    @SneakyThrows @Override
    public V read(final UUID key) {

        final File file = new File(this.folder, key + ".json");

        if (!file.exists()) {
            return null;
        }

        try (final FileReader fileReader = new FileReader(file)) {

            final V value = this.gson.fromJson(fileReader, this.clazz);
            value.setKey(key);

            fileReader.close();
            return value;
        }
    }

    @SneakyThrows
    @Override
    public void write(final V entity) {

        final File file = new File(this.folder, entity.getKey() + ".json");

        try (final FileWriter fileWriter = new FileWriter(file)) {
            this.gson.toJson(entity, fileWriter);
            fileWriter.flush();
        }
    }

    @Override
    public boolean contains(final UUID key) {
        final File file = new File(this.folder, key + ".json");
        return file.exists();
    }
}
