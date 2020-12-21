# Custom acquisition type

You can create your own acquisition type, with these advantages:

- The acquisition type will appear among all other official types from manufacturers, in Wiclax UI
- It will be possible to specify what the communication protocol looks like, to the extent that anything not specified
  will be handled like documented for the generic acquisition
- It will be easy to deploy on your workstations, or for your customers

## Create a new acquisition type

1. Create a directory `Ressources` in the Wiclax installation path, if it does not exist yet
2. Create a directory `Acquisitions` in the `Ressources` directory
3. Create a new file with the extension `.chip-acquisition.xml` in the `Acquisitions` directory - for
   example: `mysystem.chip-acquisition.xml`
4. Edit the file with the options.

Example file:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Acquisition
        name="My system"
        defaultTCPPort="9854"
        inCommandEndChars="\r"
        passingDataMask="C,YYYYMMDDhhmmssccc,@"
        passingDataSeparator=","
        isWithHeartbeat="1"
        heartbeatValue="*">
    <icon>
        ...
    </icon>
</Acquisition>
```

## Acquisition type options

### name

Mandatory. Text used as a caption for the acquisition type in the UI, and also an id.

NOTE: In case the value is changed, the eventually existing acquisitions of this type will no longer be recognized as
belonging to it.

### passingDataSeparator

Optional. Separator char or string used to interpret the fields of a passing data string sent by the device. If not set,
then the mask for passing data will be interpreted with fixed length fields. These fixed length fields can be set
in [passingDataMask](#passingdatamask).

### passingDataMask

A string defining how passing data sent by the device must be interpreted by the acquisition. Mandatory fields to
include are the chip id and the timestamp (can be a date and a time field if not a single value).

| Character     | Meaning                                          |
| ------------- | ------------------------------------------------ |
| M             | month                                            |
| D             | day                                              |
| Y             | year                                             |
| h             | hours                                            |
| m             | minutes                                          |
| s             | seconds                                          |
| c             | thousands of seconds                             |
| u             | Unix timestamp (number of seconds since 1/1/1970)|
| C             | chip id                                          |
| L             | passing #                                        |
| @             | source id/reader id                              |
| NOT SUPPORTED | battery level                                    |
| NOT SUPPORTED | rewind                                           |

Examples:

| passingDataMask                    | passingDataSeparator | Explanation                                                                                          |
| --------------------------------   | -------------------- | ---------------------------------------------------------------------------------------------------- |
| `C,YYYYMMDDhhmmssccc,@`            | `,`                  | field chip id of variable length, followed by timestamp field and source id field of variable length |
| `CCCCCCCCCCC YYYYMMDDhhmmssccc @@` | {empty}              | chip id on exactly 11 chars, then timestamp starting at pos 13 and source id on chars 31 and 32      |

### inCommandEndChars

Chars used to indicate the end of a command when sent by the device. Not used, the protocol must use either \r or \n as
command separator.

### outCommandEndChars

Optional. Chars used to indicate the end of a command when sent by Wiclax. Defaults to `\r` if not set.

### commandsForInitialization

Optional. Set of text commands to send when initiating a connection with the device. The commands must include any end
char if necessary.

### getClockCommand

Optional. Command to send to query the clock value of the device.

Defaults to `CLOCK`.

### setClockCommand

Optional. Command to send to initialize the device clock. The command includes a standard time mask.

Defaults to `CLOCK YYYY/MM/DD hh:mm:ss`

### rewindCommand

Optional. Command to send to ask the device all the passings it has in memory.

Defaults to `REWIND`.

### startReadCommand

Optional. Command to send to ask the device to enter in tag reading mode.

Defaults to `STARTREAD`.

### stopReadCommand

Optional. Command to send to ask the device to exit from tag reading mode.

Defaults to `STOPREAD`.

### icon tag

Optional. Xml child node of the document root. Used to get a picture for the acquisition type on the UI. If not set, the
picture will default to the generic acquisition logo. Value must be the base64 encoding of a png image file, size 32x32
pixels.
