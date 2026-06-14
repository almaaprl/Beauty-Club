package com.example.beautyclub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beautyclub.data.repository.MemberRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val memberRepository: MemberRepository
) : ViewModel() {

    private val _loginSuccess =
        MutableStateFlow(false)

    val loginSuccess: StateFlow<Boolean> =
        _loginSuccess

    fun login(
        email: String,
        password: String
    ) {

        viewModelScope.launch {

            val member =
                memberRepository.login(
                    email,
                    password
                )

            _loginSuccess.value =
                member != null
        }
    }
}