# Release process

Install [gnupg](https://gnupg.org/) to sign the files.

## Create gpg key

Create a new key with the command below:

```shell
gpg --full-generate-key
```

Use key size of 4096 bits.

## Create legacy secring file

To create the `secring.gpg` file use:

```powershell
gpg --export-secret-keys -o "$env:APPDATA\gnupg\secring.gpg"
```

```shell
gpg --export-secret-keys -o ~/.gnupg/secring.gpg
```

## Finding the keyId

Use the command below:

```shell
gpg --list-keys --keyid-format SHORT
```

The output will contain something like this:

```text
pub   rsa4096/{keyid} [some date] ...
```

You will need the 8 character long keyId from here

## Running the publish

Fill in the variables in [gradle properties](gradle.properties) and run:

```shell
./gradlew publish
```
