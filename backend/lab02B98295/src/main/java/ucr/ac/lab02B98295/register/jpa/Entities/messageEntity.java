package ucr.ac.lab02B98295.register.jpa.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "messages")
public class messageEntity {

    @Id
    private UUID id;

    @Column()
    private String message;

    @Column()
    private LocalDateTime fecha;

    @Column()
    private UUID user_id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        messageEntity that = (messageEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(message, that.message) && Objects.equals(fecha, that.fecha) && Objects.equals(user_id, that.user_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, fecha, user_id);
    }
}
