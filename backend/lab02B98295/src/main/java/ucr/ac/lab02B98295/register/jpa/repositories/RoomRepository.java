package ucr.ac.lab02B98295.register.jpa.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ucr.ac.lab02B98295.register.jpa.Entities.roomEntity;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface RoomRepository extends JpaRepository<roomEntity, UUID>{

    roomEntity findByIdentifier(String identifier);

}
