package com.panini.support.data.remote

import com.panini.support.data.remote.dto.request.CreateTicketRequestDto
import com.panini.support.data.remote.dto.request.LoginRequestDto
import com.panini.support.data.remote.dto.response.LoginResponseDto
import com.panini.support.data.remote.dto.response.TicketResponseDto
import com.panini.support.data.remote.dto.request.UpdatePriorityRequestDto
import com.panini.support.data.remote.dto.request.UpdateStatusRequestDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface TicketApi {

    @POST("auth/login")
    suspend fun login(@Body body: LoginRequestDto): LoginResponseDto

    @GET("tickets")
    suspend fun getTickets(): List<TicketResponseDto>

    @GET("tickets/{id}")
    suspend fun getTicket(@Path("id") id: String): TicketResponseDto

    @POST("tickets")
    suspend fun createTicket(@Body body: CreateTicketRequestDto): TicketResponseDto

    @PATCH("tickets/{id}/status")
    suspend fun updateStatus(@Path("id") id: String, @Body body: UpdateStatusRequestDto): TicketResponseDto

    @PATCH("tickets/{id}/priority")
    suspend fun updatePriority(@Path("id") id: String, @Body body: UpdatePriorityRequestDto): TicketResponseDto
}