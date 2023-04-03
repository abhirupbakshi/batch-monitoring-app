package example.app.entities;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <h1>Interface FileDatabaseCompatible</h1>
 * This interface is to make sure an entity class is compatible with the FileDatabase class
 */
public interface FileDatabaseCompatible {
    LocalDateTime getCreationDate();
    UUID getId();
}
