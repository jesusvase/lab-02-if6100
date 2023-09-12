package ucr.ac.lab02B98295.register.jpa.Entities;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "rooms")
public class roomEntity {

    @Id
    private UUID id;

    @Column()
    private String name;

    @Column()
    private String identifier;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        roomEntity that = (roomEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(identifier, that.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, identifier);
    }
}
