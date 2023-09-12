package ucr.ac.lab02B98295.register.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ucr.ac.lab02B98295.register.jpa.Entities.roomEntity;
import ucr.ac.lab02B98295.register.jpa.Entities.userEntity;
import ucr.ac.lab02B98295.register.jpa.repositories.RoomRepository;
import ucr.ac.lab02B98295.register.jpa.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class RoomInsertHandler {

    @Autowired
    RoomRepository repository;
    @Autowired
    UserRepository repository2;

    public record Command(
            String name,
            String createdBy
    ) {
        public List<Object> toEntities() {
            roomEntity room = new roomEntity();
            room.setId(UUID.randomUUID());
            room.setName(name());
            room.setIdentifier(UUID.randomUUID().toString());

            userEntity user = new userEntity();
            user.setId(UUID.randomUUID());
            user.setAlias(createdBy());
            user.setRoom_id(room.getId());

            List<Object> entities = new ArrayList<>();
            entities.add(room);
            entities.add(user);

            return entities;
        }
    }


    public String handle(Command command) {
        if (command.name() == null || command.name().isEmpty() ||
                command.createdBy() == null || command.createdBy().isEmpty()) {
            return null;
        }

        roomEntity room = new roomEntity();
        room.setId(UUID.randomUUID());
        room.setName(command.name());
        room.setIdentifier(UUID.randomUUID().toString());

        userEntity user = new userEntity();
        user.setId(UUID.randomUUID());
        user.setAlias(command.createdBy());
        user.setRoom_id(room.getId());

        repository.save(room);
        repository2.save(user);

        return room.getIdentifier();
    }

}
