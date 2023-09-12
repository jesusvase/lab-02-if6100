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

        public String toEntities(RoomRepository roomRepository, MessageRepository messageRepository,UserRepository userRepository) {
            List<String> messagesList = new ArrayList<>();

            if (identifier() == null || identifier().isEmpty()) {
                return null;
            }

            roomEntity room = roomRepository.findByIdentifier(identifier);
            if (room == null) {
                return null;
            }

            List<messageEntity> messages = messageRepository.findAll();

            for (messageEntity message : messages) {
                Optional<userEntity> userOptional = userRepository.findById(message.getUser_id());
                String alias = userOptional.map(userEntity::getAlias).orElse("Usuario no encontrado");
                String message2 = message.getMessage();
                String createdOn = message.getFecha().toString();

                String formattedMessage = (" Alias :"+alias+"\n Message: "+message2+"\n createdOn: "+createdOn+"\n");
                messagesList.add(formattedMessage);
            }

            return "Id: " +room.getIdentifier()+"\nName :"+room.getName() +"\nMessage:\n"+messagesList;
        }


    }


    public String handle(Command command) {
        String result;
        result= command.toEntities(roomRepository,messageRepository,userRepository);

        return result; // Puedes devolver directamente el resultado
    }
}

