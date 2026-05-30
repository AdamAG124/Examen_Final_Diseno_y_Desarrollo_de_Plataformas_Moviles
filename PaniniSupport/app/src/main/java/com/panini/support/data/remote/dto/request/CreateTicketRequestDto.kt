package com.panini.support.data.remote.dto.request

import com.google.gson.annotations.SerializedName

data class CreateTicketRequestDto(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("priority") val priority: String,
    @SerializedName("provider") val provider: String,
    @SerializedName("category") val category: String
)