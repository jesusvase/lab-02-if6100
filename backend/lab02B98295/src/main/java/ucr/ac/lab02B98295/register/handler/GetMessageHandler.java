package ucr.ac.lab02B98295.register.handler;

import com.sun.nio.sctp.MessageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ucr.ac.lab02B98295.register.jpa.Entities.messageEntity;
import ucr.ac.lab02B98295.register.jpa.Entities.roomEntity;
import ucr.ac.lab02B98295.register.jpa.Entities.userEntity;
import ucr.ac.lab02B98295.register.jpa.repositories.MessageRepository;
import ucr.ac.lab02B98295.register.jpa.repositories.RoomRepository;
import ucr.ac.lab02B98295.register.jpa.repositories.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class GetMessageHandler {


        String alias;
        @Autowired
        private RoomRepository roomRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private MessageRepository messageRepository;

    public record Command(
            String identifier
    ) {

        public List<String> toEntities(RoomRepository roomRepository, MessageRepository messageRepository,UserRepository userRepository) {
            List<String> messagesList = new ArrayList<>();

            // Verificar si el identificador de la sala es válido
            roomEntity room = roomRepository.findByIdentifier(identifier);
            if (room == null) {
                return null; // Retorna null si el identificador de la sala no existe
            }

            // Obtener todos los mensajes de la sala en el orden en que están
            List<messageEntity> messages = messageRepository.findAll();

            // Recorrer los mensajes y agregarlos a la lista de resultados
            for (messageEntity message : messages) {
                Optional<userEntity> userOptional = userRepository.findById(message.getUser_id());
                String alias = userOptional.map(userEntity::getAlias).orElse("Usuario no encontrado");
                //String alias = message.getUser_id().toString(); // Puedes usar el user_id como alias, o el campo adecuado
                String mensaje = message.getMessage();
                String createdOn = message.getFecha().toString(); // Supongamos que la fecha está en un formato adecuado

                String formattedMessage = String.format("Alias: %s\nMensaje: %s\nFecha y hora: %s", alias, mensaje, createdOn);
                messagesList.add(formattedMessage);
            }

            return messagesList;
        }


    }


    public List<String> handle(Command command) {
        List<String> result = new ArrayList<>();
        result= command.toEntities(roomRepository,messageRepository,userRepository);

        return result; // Puedes devolver directamente el resultado
    }
}

