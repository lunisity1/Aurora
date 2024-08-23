package dev.lunisity.aurora.json.manager;

import dev.lunisity.aurora.json.cache.StorageCache;
import dev.lunisity.aurora.json.creator.InstanceCreator;
import dev.lunisity.aurora.json.entity.StorageEntity;
import dev.lunisity.aurora.json.source.DataSource;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface StorageManager<V extends StorageEntity> {

    V read(final UUID key);

    void write(final V entity);

    void invalidate(final UUID key);

    boolean contains(final UUID key);

    boolean isCaching();

    boolean canCreateInstances();

    void setCache(final StorageCache<V> cache);

    void setCreator(final InstanceCreator<V> creator);

    InstanceCreator<V> getCreator();

    DataSource<V> getSource();

    @Nullable
    StorageCache<V> getCache();

    void save();

}
