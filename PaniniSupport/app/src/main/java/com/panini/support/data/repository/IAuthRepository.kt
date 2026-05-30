package com.panini.support.data.repository

interface IAuthRepository {
    suspend fun login(email: String, password: String): Result<String>
}