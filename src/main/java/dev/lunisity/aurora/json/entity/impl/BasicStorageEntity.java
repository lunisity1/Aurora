package dev.lunisity.aurora.json.entity.impl;

import dev.lunisity.aurora.json.entity.StorageEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class BasicStorageEntity implements StorageEntity {

    private UUID key;
    private boolean modified;

    public BasicStorageEntity(final UUID key) {
        this.key = key;
    }
}
