package dk.tbsalling.ais.rest.controllers;

import dk.tbsalling.ais.rest.services.NmeaStringService;
import dk.tbsalling.aismessages.ais.messages.AISMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
public class AisMessagesController {

    private final static String LINE_SEPARATOR = System.getProperty("line.separator");

    private final NmeaStringService nmeaStringService;

    public AisMessagesController(NmeaStringService nmeaStringService) {
        this.nmeaStringService = nmeaStringService;
    }

    @PostMapping("/decode")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public List<AISMessage> decode(@RequestBody String body) {
        return nmeaStringService.decode(List.of(body.split(LINE_SEPARATOR)));
    }

}
