package com.valentinhinov.ganandroidtest.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeriesCharacter(
    @SerialName("char_id")
    val id: Int,
    val name: String,
    @SerialName("occupation")
    val occupations: List<String>,
    @SerialName("img")
    val imgUrl: String,
    val status: String,
    @SerialName("appearance")
    val seasonAppearances: List<Int>,
    val nickname: String,
    val portrayed: String
)