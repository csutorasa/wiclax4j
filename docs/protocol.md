# Generic acquisition protocol

## Tag report

The device sends to the software some data corresponding to a live passing.

There are 6 datapoint which you can send, and separate them by `;` and end it with a carriage return (``) character.

1. the chip/bib identifier – mandatory
2. date and time, format dd-mm-yyyy hh:mm:ss.ccc – mandatory
3. loop/antenna/device id – optional, blank if not set – used by the software to target different splits for passages
   coming from a single device, based on this information
4. lap sequential number for the device – optional, blank if not set
5. transponder battery level – optional, blank if not set – value in percent. For example `65` for 65%
6. rewind flag – 0 if live split, 1 if spit sent from a rewind request

Example:

```text
01539;22-09-2011 02:41:30.620;0A;559;;0
```


Message class: [AcquisitionMessage](../src/main/java/com/github/csutorasa/wiclax/message/AcquisitionMessage.java)

## Heartbeat

The device can send data on a regular basis to inform about its status and let know that the connection is still alive.

Message to Wiclax data format: `*`

Message class: [HeartBeatMessage](../src/main/java/com/github/csutorasa/wiclax/message/HeartBeatMessage.java)

## Gun start

The device sends a command to Wiclax to push a start time. This start time will be integrated in the active event as a
race start (the race being prompted on screen in case of a multiple races event).

Message to Wiclax:

```text
RACESTART HH:mm:ss,ccc
```

Where HH:mm:ss,ccc is the time to integrate.

Example:

```text
RACESTART 23:59:59,999
```

Message class: [RaceStartMessage](../src/main/java/com/github/csutorasa/wiclax/message/RaceStartMessage.java)

## Get time

The software sends a command to know the device current time.

Request from Wiclax:

```text
CLOCK
```

Response to Wiclax:

```text
CLOCK dd-MM-yyyy HH:mm:ss
```

Request class: [GetClockRequest](../src/main/java/com/github/csutorasa/wiclax/request/GetClockRequest.java)

Response class: [ClockResponse](../src/main/java/com/github/csutorasa/wiclax/response/ClockResponse.java)

## Set time

The software sends a command to initialize the device time.

Request from Wiclax:

```text
CLOCK dd-MM-yyyy HH:mm:ss
```

Response to Wiclax with an acknowledgement:

```text
CLOCKOK
```

Request class: [SetClockRequest](../src/main/java/com/github/csutorasa/wiclax/request/SetClockRequest.java)

Response class: [ClockOkResponse](../src/main/java/com/github/csutorasa/wiclax/response/ClockOkResponse.java)

## Start and stop reading

The software sends a command to start or stop the device reading mode.

Requests from Wiclax:

```text
STARTREAD
```

or

```text
STOPREAD
```

If ok, response to Wiclax is an acknowledgement in both cases:

```text
READOK
```

Start request class: [StartReadRequest](../src/main/java/com/github/csutorasa/wiclax/request/StartReadRequest.java)

Stop request class: [StopReadRequest](../src/main/java/com/github/csutorasa/wiclax/request/StopReadRequest.java)

Response class: [ReadOkResponse](../src/main/java/com/github/csutorasa/wiclax/response/ReadOkResponse.java)

## Rewind

The software sends a command to get a list of registered splits, corresponding to the requested period.

Request from Wiclax:

```text
REWIND dd-MM-yyyy HH:mm:ss dd-MM-yyyy HH:mm:ss
```

Where the 2 timestamps are the bounds for the splits to send. The device sends all requested splits with the same format
as for the tag reads. Only difference will be the rewind flag is set to 1. The software may expect live splits sent
while a rewind reception is in progress.

Request class: [RewindRequest](../src/main/java/com/github/csutorasa/wiclax/request/RewindRequest.java)
