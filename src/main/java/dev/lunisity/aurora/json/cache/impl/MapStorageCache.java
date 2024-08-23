package dev.lunisity.aurora.json.cache.impl;

import dev.lunisity.aurora.json.cache.StorageCache;
import dev.lunisity.aurora.json.entity.StorageEntity;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MapStorageCache<V extends StorageEntity> implements StorageCache<V> {

    private final Map<UUID, V> values = new ConcurrentHashMap<>();

    @Override
    public V read(final UUID key) {
        return this.values.get(key);
    }

    @Override
    public void write(final V entity) {
        this.values.put(entity.getKey(), entity);
    }

    @Override
    public void invalidate(final UUID key) {
        this.values.remove(key);
    }

    @Override
    public boolean contains(final UUID key) {
        return this.values.containsKey(key);
    }

    @Override
    public Map<UUID, V> getAsMap() {
        return this.values;
    }
}
