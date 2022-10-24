# Generator

This demo project generates the static mapping of threats from API and spreadsheet into Kotlin code.

Project is written in Kotlin and built for Java 17 using Gradle.

## Build the generator

Run using [gw](https://github.com/gdubw/gng):

```shell
gw clean build
```

## Run the generator

Simply run `main()` function located in [App.kt](src/main/kotlin/App.kt) in your IDE and the generated code will be
dumped to a `../generated-demo/src/main/kotlin/` location by default.
