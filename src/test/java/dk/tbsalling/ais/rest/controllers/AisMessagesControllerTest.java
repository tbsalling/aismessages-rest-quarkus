package dk.tbsalling.ais.rest.controllers;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class AisMessagesControllerTest {

    @Test
    public void testDecodeEndpoint() {
        given()
                .when()
                .body("!AIVDM,1,1,,A,18UG;P0012G?Uq4EdHa=c;7@051@,0*53")
                .post("/decode")
                .then()
                .statusCode(200)
                .body("$.size()", is(1),
                        "[0].navigationStatus", is("UnderwayUsingEngine"),
                        "[0].rateOfTurn", is(0),
                        "[0].speedOverGround", is(6.6f),
                        "[0].courseOverGround", is(350.0f),
                        "[0].trueHeading", is(355),
                        "[0].valid", is(true));
    }

}