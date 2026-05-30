package com.panini.support.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panini.support.core.result.UiState
import com.panini.support.data.repository.IAuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: IAuthRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<UiState<Unit>?>(null)
    val loginState: StateFlow<UiState<Unit>?> = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = UiState.Loading
            authRepository.login(email, password)
                .onSuccess { _loginState.value = UiState.Success(Unit) }
                .onFailure { _loginState.value = UiState.Error(it.message ?: "Error de autenticación") }
        }
    }

    fun consumeState() { _loginState.value = null }
}