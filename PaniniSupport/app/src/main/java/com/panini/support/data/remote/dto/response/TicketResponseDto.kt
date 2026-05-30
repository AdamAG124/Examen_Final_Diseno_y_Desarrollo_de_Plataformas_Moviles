package com.panini.support.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class TicketResponseDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("priority") val priority: String,
    @SerializedName("status") val status: String,
    @SerializedName("provider") val provider: String,
    @SerializedName("category") val category: String,
    @SerializedName("createdAt") val createdAt: String
)