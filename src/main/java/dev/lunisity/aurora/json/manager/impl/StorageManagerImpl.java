package dev.lunisity.aurora.json.manager.impl;

import dev.lunisity.aurora.json.cache.StorageCache;
import dev.lunisity.aurora.json.creator.InstanceCreator;
import dev.lunisity.aurora.json.entity.StorageEntity;
import dev.lunisity.aurora.json.manager.StorageManager;
import dev.lunisity.aurora.json.source.DataSource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class StorageManagerImpl<V extends StorageEntity> implements StorageManager<V> {

    private final DataSource<V> source;
    private StorageCache<V> cache;
    private InstanceCreator<V> creator;

    @Override
    public V read(final UUID key) {

        if (this.cache != null) {
            final V value = this.cache.read(key);

            if (value != null) {
                return value;
            }
        }

        V value = this.source.read(key);

        if (value == null) {

            if (this.creator != null && this.creator.canCreate(key)) {
                value = this.creator.create(key);

                if (this.cache != null) {
                    this.cache.write(value);
                }
            }
        }
        return value;
    }

    @Override
    public void write(final V entity) {

        if (this.cache != null) {
            this.cache.write(entity);
        } else {
            this.source.write(entity);
        }
    }

    @Override
    public void invalidate(final UUID key) {

        if (this.cache != null) {
            this.cache.invalidate(key);
        }
        this.source.invalidate(key);
    }

    @Override
    public boolean contains(final UUID key) {

        if (this.cache != null) {

            if (this.cache.contains(key)) {
                return true;
            } else {
                return this.cache.contains(key);
            }
        } else {
            return this.source.contains(key);
        }
    }

    @Override
    public boolean isCaching() {
        return this.cache != null;
    }

    @Override
    public boolean canCreateInstances() {
        return false;
    }


    @Override
    public void save() {

        if (this.cache == null) {
            throw new UnsupportedOperationException("Attempted to save a StorageManager's null cache.");
        }

        for (final V entity : this.cache.getAsMap().values()) {

            if (!entity.isModified()) {
                continue;
            }

            if (this.creator != null && this.creator.canUnload(entity)) {
                this.source.write(entity);
                continue;
            }

            this.source.write(entity);
            entity.setModified(false);
        }
    }
}
