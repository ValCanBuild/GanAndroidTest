package com.valentinhinov.ganandroidtest.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
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
) : Parcelable