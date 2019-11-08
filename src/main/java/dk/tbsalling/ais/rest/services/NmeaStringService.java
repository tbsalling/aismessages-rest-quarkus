package dk.tbsalling.ais.rest.services;

import dk.tbsalling.aismessages.ais.messages.AISMessage;
import dk.tbsalling.aismessages.nmea.NMEAMessageHandler;
import dk.tbsalling.aismessages.nmea.messages.NMEAMessage;

import javax.enterprise.context.RequestScoped;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class NmeaStringService {

    public List<AISMessage> decode(List<String> nmeaMessagesAsStrings) {
        List<AISMessage> aisMessages = new ArrayList<>();

        NMEAMessageHandler nmeaMessageHandler = new NMEAMessageHandler("SRC1", aisMessage -> aisMessages.add(aisMessage));

        nmeaMessagesAsStrings.stream().map(s -> NMEAMessage.fromString(s)).forEach(nmea -> nmeaMessageHandler.accept(nmea));

        return aisMessages;
    }

}
