package ucr.ac.lab02B98295.register.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class UserJoinHandler {

    static String name;
    @Autowired
    private RoomRepository repository;

    @Autowired
    private UserRepository repository2;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public record Command(
            String identifier,
            String alias
    ) {
        public List<String> toEntities(RoomRepository repository, UserRepository repository2) {
            List<String> savedUserAliases = new ArrayList<>();

            if (identifier() == null || identifier().isEmpty() ||
                    alias() == null || alias().isEmpty()) {
                return savedUserAliases;
            }

            roomEntity room = repository.findByIdentifier(identifier());

            if (room == null) {
                return savedUserAliases;
            }

            boolean isAliasUnique = repository2.isAliasUniqueInRoom(alias(), room.getId());

            if (!isAliasUnique) {
                return savedUserAliases;
            }

            name = room.getName();

            userEntity user = new userEntity();
            user.setId(UUID.randomUUID());
            user.setAlias(alias());
            user.setRoom_id(room.getId());

            repository2.save(user);

            savedUserAliases.addAll(repository2.findAliasesInRoom(room.getId()));

            return savedUserAliases;
        }
    }

    public String handle(Command command) throws JsonProcessingException {
        List<String> savedUserAliases = command.toEntities(repository, repository2);

        if (savedUserAliases.isEmpty()) {
            return null;
        }

        RoomInfo roomInfo = new RoomInfo(command.identifier(), name, savedUserAliases);
        return objectMapper.writeValueAsString(roomInfo);
    }

    public record RoomInfo(
            String id,
            String name,
            List<String> users
    ) {
    }
}

