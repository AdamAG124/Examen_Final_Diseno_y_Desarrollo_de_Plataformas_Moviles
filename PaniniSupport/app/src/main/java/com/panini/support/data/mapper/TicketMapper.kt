package com.panini.support.data.mapper

import com.panini.support.data.remote.dto.request.CreateTicketRequestDto
import com.panini.support.data.remote.dto.response.TicketResponseDto
import com.panini.support.domain.model.Priority
import com.panini.support.domain.model.Ticket
import com.panini.support.domain.model.TicketCategory
import com.panini.support.domain.model.TicketStatus

fun TicketResponseDto.toDomain(): Ticket = Ticket(
    id = id,
    title = title,
    description = description,
    priority = Priority.fromApi(priority),
    status = TicketStatus.fromApi(status),
    provider = provider,
    category = TicketCategory.fromApi(category),
    createdAt = createdAt
)

fun buildCreateRequest(
    title: String,
    description: String,
    priority: Priority,
    provider: String,
    category: TicketCategory
): CreateTicketRequestDto = CreateTicketRequestDto(
    title = title,
    description = description,
    priority = priority.name,
    provider = provider,
    category = category.name
)