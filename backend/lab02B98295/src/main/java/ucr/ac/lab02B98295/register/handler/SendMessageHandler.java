package ucr.ac.lab02B98295.register.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ucr.ac.lab02B98295.register.jpa.Entities.messageEntity;
import ucr.ac.lab02B98295.register.jpa.Entities.roomEntity;
import ucr.ac.lab02B98295.register.jpa.Entities.userEntity;
import ucr.ac.lab02B98295.register.jpa.repositories.MessageRepository;
import ucr.ac.lab02B98295.register.jpa.repositories.RoomRepository;
import ucr.ac.lab02B98295.register.jpa.repositories.UserRepository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class SendMessageHandler {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    public record Command(
            String identifier,
            String alias,
            String message
    ) {
        public String execute(RoomRepository roomRepository, UserRepository userRepository, MessageRepository messageRepository) {
            // Validar que los campos no sean nulos o vacíos
            if (identifier() == null || identifier().isEmpty() ||
                    alias() == null || alias().isEmpty() ||
                    message() == null || message().isEmpty()) {
                return null;
            }

            // Buscar la sala por identificador
            roomEntity room = roomRepository.findByIdentifier(identifier());

            if (room == null) {
                return null;
            }

            // Verificar si el alias existe en la sala
            boolean aliasExistsInRoom = userRepository.isAliasUniqueInRoom(alias(), room.getId());

            if (aliasExistsInRoom) {
                return null;
            }

            // Buscar el usuario por alias
            userEntity user = userRepository.findByAlias(alias());

            if (user == null) {
                return null; // Manejar el caso en el que el usuario no se encuentra en la base de datos
            }

            // Crear y guardar el mensaje en la base de datos de mensajes
            messageEntity newMessage = new messageEntity();
            newMessage.setId(UUID.randomUUID());
            newMessage.setMessage(message());
            newMessage.setDate_(LocalDateTime.now());
            newMessage.setUser_id(user.getId());

            // Guardar el mensaje en la base de datos
            messageRepository.save(newMessage);

            // Retornar solo el mensaje
            return "Id: "+newMessage.getId()+"\nCreatedON: "+newMessage.getDate_()+"\nmesage: "+newMessage.getMessage();
        }
    }
    public String handle(Command command) {
        String result = command.execute(roomRepository, userRepository,messageRepository);

        return result; // Puedes devolver directamente el resultado
    }
}

