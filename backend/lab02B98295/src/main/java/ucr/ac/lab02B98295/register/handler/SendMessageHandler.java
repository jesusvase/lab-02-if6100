package ucr.ac.lab02B98295.register.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public record Command(
            String identifier,
            String alias,
            String message
    ) {
        public String execute(RoomRepository roomRepository, UserRepository userRepository, MessageRepository messageRepository) {
            if (identifier() == null || identifier().isEmpty() ||
                    alias() == null || alias().isEmpty() ||
                    message() == null || message().isEmpty()) {
                return null;
            }

            roomEntity room = roomRepository.findByIdentifier(identifier());

            if (room == null) {
                return null;
            }

            boolean aliasExistsInRoom = userRepository.isAliasUniqueInRoom(alias(), room.getId());

            if (aliasExistsInRoom) {
                return null;
            }

            userEntity user = userRepository.findByAlias(alias());

            if (user == null) {
                return null;
            }

            messageEntity newMessage = new messageEntity();
            newMessage.setId(UUID.randomUUID());
            newMessage.setMessage(message());
            newMessage.setDate_(LocalDateTime.now());
            newMessage.setUser_id(user.getId());

            messageRepository.save(newMessage);

            MessageInfo messageInfo = new MessageInfo(newMessage.getId().toString(), newMessage.getDate_().toString(), newMessage.getMessage());

            try {
                return objectMapper.writeValueAsString(messageInfo);
            } catch (JsonProcessingException e) {
                return null;
            }
        }
    }

    public String handle(Command command) {
        String result = command.execute(roomRepository, userRepository, messageRepository);

        return result;
    }

    public record MessageInfo(
            String id,
            String createdOn,
            String message
    ) {
    }
}

