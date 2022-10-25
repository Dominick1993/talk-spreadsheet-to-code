# Turning spreadsheets into code

Sample project to accompany my lightning talk about turning external API providing some sort of mapping and our custom
mapping in a spreadsheet into a single static code resource in Kotlin.

## 1. Running local server

Before we are able to combine the "external" static mapping with our spreadsheets we have to run the local server
providing those resources.

Following command will run the server on a default `8080` port on `localhost` address:
```shell
./gradlew :external-server:bootRun
```

## 2. Run the generator

The easiest way would be to run the `main()` function from generator's [App.kt](generator/src/main/kotlin/App.kt).
It will by default process the spreadsheet saved in repository root named `turningSpreadsheetsIntoCode.xlsx` and store
the output in the `src` folder of the `generated-demo` project.

## 3. Observe!
