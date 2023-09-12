package ucr.ac.lab02B98295.register.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ucr.ac.lab02B98295.register.jpa.Entities.messageEntity;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<messageEntity, UUID> {

    //List<messageEntity> findByUserId(UUID user_id);

    @Query("SELECT m FROM messageEntity m WHERE m.user_id = :user_id")
    List<messageEntity> findByUserId(@Param("user_id") UUID user_id);


}
