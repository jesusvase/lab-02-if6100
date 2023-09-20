package ucr.ac.lab02B98295.register.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.nio.sctp.MessageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ucr.ac.lab02B98295.register.jpa.Entities.messageEntity;
import ucr.ac.lab02B98295.register.jpa.Entities.roomEntity;
import ucr.ac.lab02B98295.register.jpa.Entities.userEntity;
import ucr.ac.lab02B98295.register.jpa.repositories.MessageRepository;
import ucr.ac.lab02B98295.register.jpa.repositories.RoomRepository;
import ucr.ac.lab02B98295.register.jpa.repositories.UserRepository;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Component
public class GetMessageHandler {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public record Command(
            String identifier
    ) {
        public String toEntities(RoomRepository roomRepository, UserRepository userRepository, MessageRepository messageRepository) {
            if (identifier() == null || identifier().isEmpty()) {
                return null;
            }

            roomEntity room = roomRepository.findByIdentifier(identifier);
            if (room == null) {
                return null;
            }

            List<userEntity> usersInRoom = userRepository.findByRoomId(room.getId());

            List<MessageInfo> messagesList = new ArrayList<>();

            for (userEntity user : usersInRoom) {
                List<messageEntity> messages = messageRepository.findByUserId(user.getId());

                messages.sort(Comparator.comparing(messageEntity::getDate_));

                for (messageEntity message : messages) {
                    String alias = user.getAlias();
                    String messageText = message.getMessage();
                    String createdOn = message.getDate_().format(DateTimeFormatter.ISO_DATE_TIME);

                    MessageInfo formattedMessage = new MessageInfo(alias, messageText, createdOn);

                    messagesList.add(formattedMessage);
                }
            }

            RoomInfo roomInfo = new RoomInfo(room.getIdentifier(), room.getName(), messagesList);

            try {
                return objectMapper.writeValueAsString(roomInfo);
            } catch (JsonProcessingException e) {
                return null;
            }
        }

    }

    public String handle(Command command) {
        String result = command.toEntities(roomRepository, userRepository, messageRepository);

        return result;
    }

    public record RoomInfo(
            String id,
            String name,
            List<MessageInfo> messages
    ) {
    }

    public record MessageInfo(
            String alias,
            String message,
            String createdOn
    ) {
    }
}