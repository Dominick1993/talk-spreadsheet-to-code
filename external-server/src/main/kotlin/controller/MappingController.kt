package dev.labuda.server.controller

import dev.labuda.server.Utils.loadResourceText
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/")
class MappingController {

    @GetMapping(produces = [APPLICATION_JSON_VALUE])
    suspend fun getMapping(): String = loadResourceText("/threatTypes.json")

}
