package dev.lunisity.aurora.json.entity;

import java.util.UUID;

public interface StorageEntity {

    UUID getKey();

    void setKey(final UUID uuid);

    boolean isModified();

    void setModified(final boolean modified);

}
