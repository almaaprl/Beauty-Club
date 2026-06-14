package com.example.beautyclub.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.beautyclub.data.repository.MemberRepository
import com.example.beautyclub.viewmodel.AuthViewModel

class AuthViewModelFactory(
    private val memberRepository: MemberRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (
            modelClass.isAssignableFrom(
                AuthViewModel::class.java
            )
        ) {

            return AuthViewModel(
                memberRepository
            ) as T
        }

        throw IllegalArgumentException()
    }
}