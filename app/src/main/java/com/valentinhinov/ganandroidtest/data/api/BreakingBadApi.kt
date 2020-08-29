package com.valentinhinov.ganandroidtest.data.api

import com.valentinhinov.ganandroidtest.data.models.SeriesCharacter
import retrofit2.http.GET

interface BreakingBadApi {

    @GET("characters")
    suspend fun getAllCharacters(): List<SeriesCharacter>
}