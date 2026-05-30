package com.panini.support.data.remote.dto.request

import com.google.gson.annotations.SerializedName

data class UpdateStatusRequestDto(
    @SerializedName("status") val status: String
)

data class UpdatePriorityRequestDto(
    @SerializedName("priority") val priority: String
)