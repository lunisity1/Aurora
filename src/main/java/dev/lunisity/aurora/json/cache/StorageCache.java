package dev.lunisity.aurora.json.cache;

import dev.lunisity.aurora.json.entity.StorageEntity;

import java.util.Map;
import java.util.UUID;

public interface StorageCache<V extends StorageEntity> {

    V read(final UUID key);

    void write(final V entity);

    void invalidate(final UUID key);

    boolean contains(final UUID key);

    Map<UUID, V> getAsMap();

}
