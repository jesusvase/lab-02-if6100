package ucr.ac.lab02B98295.register.jpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ucr.ac.lab02B98295.register.jpa.Entities.userEntity;

import java.util.List;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<userEntity,UUID> {

    @Query("SELECT COUNT(u) = 0 FROM userEntity u WHERE u.alias = :alias AND u.room_id = :roomId")
    boolean isAliasUniqueInRoom(@Param("alias") String alias, @Param("roomId") UUID roomId);

    @Query("SELECT u.alias FROM userEntity u WHERE u.room_id = :roomId")
    List<String> findAliasesInRoom(@Param("roomId") UUID roomId);

    @Query("SELECT u FROM userEntity u WHERE u.alias = :alias")
    userEntity findByAlias(@Param("alias") String alias);

    @Query("SELECT u FROM userEntity u WHERE u.room_id = :room_id")
    List<userEntity> findByRoomId(@Param("room_id") UUID room_id);


}
