package kmp.redux.features.space.repository

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kmp.redux.features.space.entity.PeopleInSpace
import kmp.redux.network.HttpClientFactory
import kmp.redux.network.Response
import kmp.redux.network.request

internal class SpaceRepository(
        private val httpClient: HttpClient = HttpClientFactory().create()
) {

    suspend fun getPeopleInSpace(): Response<PeopleInSpace> = request {
        httpClient.get("astros.json").body() as PeopleInSpace
    }
}
