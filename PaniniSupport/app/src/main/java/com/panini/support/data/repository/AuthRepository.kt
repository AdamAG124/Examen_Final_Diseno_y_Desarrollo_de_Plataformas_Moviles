package com.panini.support.data.repository

import kotlinx.coroutines.delay

class AuthRepository : IAuthRepository {

    override suspend fun login(email: String, password: String): Result<String> {
        delay(500)
        return if (email.contains("@") && password.length >= 4) {
            Result.success("mock-token-123")
        } else {
            Result.failure(IllegalArgumentException("Credenciales inválidas. Verifique correo y contraseña."))
        }
    }
}