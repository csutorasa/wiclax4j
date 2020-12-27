# Smoke testing

This is a smoke test which tests:

- custom Acquisition type generation
- heartbeat messages
- get clock time
- set clock time
- read start
- read end
- race start
- rewind

## Setup

- Create the `Ressources\Acquisitions\smoketest.chip-acquisition.xml` file with the content
  from [AcquisitionTypeGenerator](AcquisitionTypeGenerator.groovy) inside the Wiclax installation
- Open Wiclax
- Create or open an existing event
- Open `Acquisitions` menu (from the menu or F9 hotkey)
- Add a `Smoke test` acquisition
- Enter `127.0.0.1` IP address or `localhost` url
- Set rewind time to today from 0:00 to 23:59
- Open Connection Spy
- Remove `@Ignore` annotation from [IntegrationSmokeTest](IntegrationSmokeTest.groovy) `"smoke test"` method

## Running the test

- Start test from [IntegrationSmokeTest](IntegrationSmokeTest.groovy) `"smoke test"` method from either:
    - Your IDE
    - Shell
      ```shell
      ./gradlew test --tests com.github.csutorasa.wiclax.smoke.IntegrationSmokeTest
      ```  
- Connect to the Wiclax server
- Get clock
- Set clock
    - PC time
- Get clock
- Start reading
- Stop reading
- Open rewind screen
- Disconnect from the Wiclax server

## Validate

Test should pass.

Rewind window should have 2 acquisitions.

Connection spy should show something similar to this:

```text
>> HELLO

>> CLOCK

 << CLOCK 27-12-2020 17:34:16

>> CLOCK 27-12-2020 17:34:20

 << *

 << CLOCKOK

>> CLOCK

 << CLOCK 27-12-2020 17:34:24

>> STARTREAD

 << READOK

 << 123;27-12-2020 17:30:08.392;301;;;0

 << 123;27-12-2020 17:30:08.403;302;;;0

>> STOPREAD

 << READOK

 << RACESTART 17:30:09.065

>> REWIND 27-12-2020 00:00:00 27-12-2020 17:07:00

 << 123;27-12-2020 17:30:09.958;301;;;1

 << 123;27-12-2020 17:30:09.958;301;;;1
```
