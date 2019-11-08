package dk.tbsalling.ais.rest.controllers;

import dk.tbsalling.ais.rest.services.NmeaStringService;
import dk.tbsalling.aismessages.ais.messages.AISMessage;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/decode")
public class AisMessagesController {

    private final static String LINE_SEPARATOR = System.getProperty("line.separator");

    private final NmeaStringService nmeaStringService;

    public AisMessagesController(NmeaStringService nmeaStringService) {
        this.nmeaStringService = nmeaStringService;
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public List<AISMessage> decode(String body) {
        return nmeaStringService.decode(List.of(body.split(LINE_SEPARATOR)));
    }

}
