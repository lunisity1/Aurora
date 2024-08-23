package dev.lunisity.aurora.json.source;

import dev.lunisity.aurora.json.entity.StorageEntity;

import java.util.UUID;

public interface DataSource<V extends StorageEntity> {

    V read(final UUID key);

    void write(final V entity);

    void invalidate(final UUID key);

    boolean contains(final UUID key);

}
