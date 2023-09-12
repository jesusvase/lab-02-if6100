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
public class UserJoinHandler {

    static String name;
    @Autowired
    private RoomRepository repository;

    @Autowired
    private UserRepository repository2;

    public record Command(
            String identifier,
            String alias
    ) {
        public List<String> toEntities(RoomRepository repository, UserRepository repository2) {
            List<String> savedUserAliases = new ArrayList<>();

            // Validar que los campos no sean nulos o vacíos
            if (identifier() == null || identifier().isEmpty() ||
                    alias() == null || alias().isEmpty()) {
                return savedUserAliases;
            }

            // Buscar la sala por identificador utilizando el repositorio inyectado
            roomEntity room = repository.findByIdentifier(identifier());

            if (room == null) {
                return savedUserAliases;
            }

            // Verificar si el alias es único en la sala utilizando el repositorio2 inyectado
            boolean isAliasUnique = repository2.isAliasUniqueInRoom(alias(), room.getId());

            if (!isAliasUnique) {
                return savedUserAliases;
            }

            name = room.getName();

            userEntity user = new userEntity();
            user.setId(UUID.randomUUID());
            user.setAlias(alias());
            user.setRoom_id(room.getId());

            // Guardar el usuario en la base de datos
            repository2.save(user);

            savedUserAliases.addAll(repository2.findAliasesInRoom(room.getId()));

            return savedUserAliases;
        }
    }

    public String handle(Command command) {
        List<String> savedUserAliases = command.toEntities(repository, repository2);

        if (savedUserAliases.isEmpty()) {
            return null;
        }
        String result = "id =" + command.identifier() +"\nname ="+ name +"\nUsers = "+ savedUserAliases;

        return String.join(",", result);
    }
}

