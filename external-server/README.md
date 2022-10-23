# External server

Trivial demo external server providing the threat types along with other useful information.

Project is written in Kotlin and built for Java 17 using Gradle.

## Build the server

Run using [gw](https://github.com/gdubw/gng):

```shell
gw clean build
```

## Run the server

The server will be by default available at [http://localhost:8080/](http://localhost:8080/).

### From built artifact

```shell
java -jar build/libs/external-server.jar
```

### From Gradle

Run using [gw](https://github.com/gdubw/gng):

```shell
gw clean bootRun
```
