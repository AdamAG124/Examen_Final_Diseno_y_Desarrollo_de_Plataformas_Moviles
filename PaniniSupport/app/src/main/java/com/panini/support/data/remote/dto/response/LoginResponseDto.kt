package com.panini.support.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    @SerializedName("token") val token: String,
    @SerializedName("userName") val userName: String
)