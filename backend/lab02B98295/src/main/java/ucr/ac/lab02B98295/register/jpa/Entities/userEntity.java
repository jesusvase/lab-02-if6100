package ucr.ac.lab02B98295.register.jpa.Entities;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "users")
public class userEntity {

    @Id
    private UUID id;

    @Column
    private String alias;

    @Column
    private UUID room_id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public UUID getRoom_id() {
        return room_id;
    }

    public void setRoom_id(UUID room_id) {
        this.room_id = room_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        userEntity that = (userEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(alias, that.alias) && Objects.equals(room_id, that.room_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, alias, room_id);
    }
}
