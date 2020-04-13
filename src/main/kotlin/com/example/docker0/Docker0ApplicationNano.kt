package com.example.docker0

import fi.iki.elonen.NanoHTTPD
import java.io.IOException

class Docker0ApplicationNano : NanoHTTPD(port) {

    init {
        start(SOCKET_READ_TIMEOUT, false)
        println("Up and running on port $port")
    }

    override fun serve(
        uri: String,
        method: Method,
        headers: MutableMap<String, String>,
        parms: MutableMap<String, String>,
        files: MutableMap<String, String>
    ): Response {
        return if (uri == healthUrl) {
            val okStatus = "{\"status\":\"OK\"}"
            newFixedLengthResponse(okStatus)
        } else {
            newFixedLengthResponse(Response.Status.NOT_FOUND, MIME_PLAINTEXT, "Page not found")
        }
    }

    companion object {
        private const val port = 8000
        private const val healthUrl = "/health"
    }

}

fun main() {
    try {
        Docker0ApplicationNano()
    } catch (e: IOException) {
        print("Couldn't start server")
    }
}

