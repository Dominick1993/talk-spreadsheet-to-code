package dev.labuda.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
class ServerApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder()
        .sources(ServerApplication::class.java)
        .run(*args)
}
