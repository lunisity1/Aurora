package dev.lunisity.aurora.json.creator;

import dev.lunisity.aurora.json.entity.StorageEntity;

import java.util.UUID;

public interface InstanceCreator<V extends StorageEntity> {

    V create(final UUID key);

    boolean canCreate(final UUID uuid);

    boolean canUnload(final V entity);

}