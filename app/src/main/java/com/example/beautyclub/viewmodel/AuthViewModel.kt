package com.example.beautyclub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.beautyclub.data.local.entity.MemberEntity
import com.example.beautyclub.data.repository.MemberRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ── State untuk UI ────────────────────────────────────────────────
sealed class AuthState {
    data object Idle : AuthState()
    data object Loading : AuthState()
    data object Success : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel(
    private val memberRepository: MemberRepository
) : ViewModel() {

    // State login / register
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    // Data member yang sedang login — dipakai HomeScreen
    private val _loggedInMember = MutableStateFlow<MemberEntity?>(null)
    val loggedInMember: StateFlow<MemberEntity?> = _loggedInMember

    // ── Login ─────────────────────────────────────────────────────
    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _authState.value = AuthState.Error("Email dan password tidak boleh kosong")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val member = memberRepository.login(email.trim(), password)
            if (member != null) {
                _loggedInMember.value = member
                _authState.value = AuthState.Success
            } else {
                _authState.value = AuthState.Error("Email atau password salah")
            }
        }
    }

    // ── Register ──────────────────────────────────────────────────
    fun register(
        name: String,
        email: String,
        phone: String,
        password: String
    ) {
        if (name.isBlank() || email.isBlank() || phone.isBlank() || password.isBlank()) {
            _authState.value = AuthState.Error("Semua kolom harus diisi")
            return
        }
        if (password.length < 6) {
            _authState.value = AuthState.Error("Password minimal 6 karakter")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val newMember = MemberEntity(
                name     = name.trim(),
                email    = email.trim(),
                phone    = phone.trim(),
                password = password,
                points   = 0
            )
            memberRepository.addMember(newMember)
            _authState.value = AuthState.Success
        }
    }

    // ── Reset state (supaya tidak re-trigger navigasi) ────────────
    fun resetState() {
        _authState.value = AuthState.Idle
    }

    // ── Logout ────────────────────────────────────────────────────
    fun logout() {
        _loggedInMember.value = null
        _authState.value = AuthState.Idle
    }
}

// ── Factory — wajib karena ViewModel punya constructor parameter ──
class AuthViewModelFactory(
    private val memberRepository: MemberRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(memberRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}