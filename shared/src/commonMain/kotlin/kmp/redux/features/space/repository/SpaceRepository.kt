package kmp.redux.features.space.repository

import io.ktor.client.*
import io.ktor.client.request.*
import kmp.redux.features.space.entity.PeopleInSpace
import kmp.redux.network.HttpClientFactory
import kmp.redux.network.request

internal class SpaceRepository(
        private val httpClient: HttpClient = HttpClientFactory().create()
) {

    suspend fun getPeopleInSpace() = request {
        httpClient.get<PeopleInSpace>(path = "astros.json")
    }
}
