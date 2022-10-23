package dev.labuda.server

object Utils {

    fun loadResourceText(resourcePath: String) = checkNotNull(javaClass.getResource(resourcePath)) {
        "The resource on path='$resourcePath' could not be read correctly."
    }.readText()
}
