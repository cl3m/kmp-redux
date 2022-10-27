package kmp.redux.network

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

const val BACKEND_LINK = "api.open-notify.org"

class HttpClientFactory {

    fun create() = HttpClient {
        defaultRequest {
            url {
                host = BACKEND_LINK
                protocol = URLProtocol.HTTP
            }
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useArrayPolymorphism = true
            })
        }
        Logging {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
    }
}
