![Build status](https://travis-ci.org/tbsalling/aismessages.svg?branch=master)

# Introduction
This project exposes a web-service to decode AIS messages. If you are new to AIS you can read a short introduction to it on [my blog](https://tbsalling.dk/2018/09/01/an-introduction-to-ais/). 

The actual decoding is based on the [AISmessages](https://github.com/tbsalling/aismessages) library.

The web service decodes one or more messages like this:

```
!AIVDM,1,1,,A,18UG;P0012G?Uq4EdHa=c;7@051@,0*53
!AIVDM,2,1,0,B,539S:k40000000c3G04PPh63<00000000080000o1PVG2uGD:00000000000,0*34
!AIVDM,2,2,0,B,00000000000,2*27
```

into easily interpretable JSON representation like this:

```
[
  {
    "navigationStatus": "UnderwayUsingEngine",
    "rateOfTurn": 0,
    "speedOverGround": 6.6,
    "positionAccuracy": false,
    "latitude": 37.912167,
    "longitude": -122.42299,
    "courseOverGround": 350.0,
    "trueHeading": 355,
    "second": 40,
    "specialManeuverIndicator": "NotAvailable",
    "raimFlag": false,
    "communicationState": {
      "syncState": "UTCDirect",
      "slotTimeout": 1,
      "numberOfReceivedStations": null,
      "slotNumber": null,
      "utcHour": 8,
      "utcMinute": 20,
      "slotOffset": null
    },
    "messageType": "PositionReportClassAScheduled",
    "transponderClass": "A",
    "rawSpeedOverGround": 66,
    "rawLatitude": 22747300,
    "rawLongitude": -73453790,
    "rawCourseOverGround": 3500,
    "valid": true
  }
] 
```

# Building and running

## Production mode
To build and run the service from scratch in production mode:

```bash
$ git clone https://github.com/tbsalling/aismessages-rest-quarkus.git
...
$ cd aismessages-rest-quarkus/
$ ./mvnw package quarkus:build
...
$ java -jar target/aismessages-rest-awslambda-quarkus-1.0.0-SNAPSHOT-runner.jar 
2019-11-07 21:22:53,764 INFO  [io.quarkus] (main) aismessages-rest-awslambda-quarkus 1.0.0-SNAPSHOT (running on Quarkus 1.0.0.CR1) started in 0.647s. Listening on: http://0.0.0.0:8080
2019-11-07 21:22:53,771 INFO  [io.quarkus] (main) Profile prod activated. 
2019-11-07 21:22:53,771 INFO  [io.quarkus] (main) Installed features: [cdi, resteasy, resteasy-jackson, smallrye-openapi, spring-di, spring-web]
```

Note the sub-second startup time (0.646s in the example)! 

## Development mode
To build and run the service from scratch in development mode:

```bash
$ git clone https://github.com/tbsalling/aismessages-rest-quarkus.git
...
$ cd aismessages-rest-quarkus/
$ $ ./mvnw package quarkus:dev
...
2019-11-07 13:36:08,907 INFO  [io.quarkus] (main) Quarkus 1.0.0.CR1 started in 1.099s. Listening on: http://0.0.0.0:8080
...
```

# OpenAPI
To get the built-in OpenAPI specification of the service visit http://localhost:8080/openapi.

# Calling the service
With the service started - in production or development mode - you can decode NMEA-armoured AIS messages like this:

```bash
$ curl -d $'!AIVDM,1,1,,A,18UG;P0012G?Uq4EdHa=c;7@051@,0*53\n' -H "Content-Type: text/plain" -H "Accept: application/json" -X POST http://localhost:8080/decode
```

To get a compactly formatted JSON-representation of the AIS data like this:

```json
[{"nmeaMessages":[{"rawMessage":"!AIVDM,1,1,,A,18UG;P0012G?Uq4EdHa=c;7@051@,0*53","valid":true,"sequenceNumber":null,"radioChannelCode":"A","checksum":83,"numberOfFragments":1,"fragmentNumber":1,"messageType":"AIVDM","encodedPayload":"18UG;P0012G?Uq4EdHa=c;7@051@","fillBits":0}],"metadata":{"source":"SRC1","received":1573130490.503415000,"decoderVersion":"3.0.0","category":"AIS"},"repeatIndicator":0,"sourceMmsi":{"mmsi":576048000},"navigationStatus":"UnderwayUsingEngine","rateOfTurn":0,"speedOverGround":6.6,"positionAccuracy":false,"latitude":37.912167,"longitude":-122.42299,"courseOverGround":350.0,"trueHeading":355,"second":40,"specialManeuverIndicator":"NotAvailable","raimFlag":false,"communicationState":{"syncState":"UTCDirect","slotTimeout":1,"numberOfReceivedStations":null,"slotNumber":null,"utcHour":8,"utcMinute":20,"slotOffset":null},"messageType":"PositionReportClassAScheduled","transponderClass":"A","rawSpeedOverGround":66,"rawLatitude":22747300,"rawLongitude":-73453790,"rawCourseOverGround":3500,"valid":true}] 
```
 
You also post multiple lines.

# Technology
The service is built using Java using the [Quarkus](https://quarkus.io/) stack. It therefore has fast startup-time and low memory requirements. This makes it very suitable for cloud deployment; e.g. in a Kubernetes cluster or as a lambda function.

# Cloud deployment
TBD

