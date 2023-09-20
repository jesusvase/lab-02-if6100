package ucr.ac.lab02B98295.register.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucr.ac.lab02B98295.register.handler.GetMessageHandler;
import ucr.ac.lab02B98295.register.handler.RoomInsertHandler;
import ucr.ac.lab02B98295.register.handler.SendMessageHandler;
import ucr.ac.lab02B98295.register.handler.UserJoinHandler;

@RestController
public class InsertRoomController {

    @Autowired
    RoomInsertHandler roomInsertHandler;
    @Autowired
    UserJoinHandler userJoinHandler;
    @Autowired
    SendMessageHandler sendMessageHandler;
    @Autowired
    GetMessageHandler getMessageHandler;



    @PostMapping("/room/create")
    public ResponseEntity<String> register(@RequestBody RoomInsertRequest payload){
        String identifier = roomInsertHandler.handle(new RoomInsertHandler.Command(
                payload.name(),
                payload.createdBy()
        ));

        if (identifier != null) {
            return ResponseEntity.ok(identifier);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
    }

    @PostMapping("/room/join")
    public ResponseEntity<String> joinRoom(@RequestBody UserJoinRequest payload) {
        try {
            String result = userJoinHandler.handle(new UserJoinHandler.Command(
                    payload.identifier(),
                    payload.alias()
            ));

            if (result != null) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
            }
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
        }
    }

    @PostMapping("/room/message")
    public ResponseEntity<String> sendMessage(@RequestBody SendMessageRequest payload) {
        String result = sendMessageHandler.handle(new SendMessageHandler.Command(
                payload.identifier(),
                payload.alias(),
                payload.message()
        ));

        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
    }

    @GetMapping("/room/message")
        public ResponseEntity GetMessage(@RequestBody GetMessage payload){
        String result = getMessageHandler.handle(new GetMessageHandler.Command(
                payload.identifier()
        ));
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
    }
    }

record RoomInsertRequest(
        String name,
        String createdBy
){}

record UserJoinRequest(
        String identifier,
        String alias
){}
record SendMessageRequest(
        String identifier,
        String alias,
        String message
){}
record GetMessage(
        String identifier
){}





