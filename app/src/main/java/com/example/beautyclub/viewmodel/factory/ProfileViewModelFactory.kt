package com.example.beautyclub.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.beautyclub.data.repository.MemberRepository
import com.example.beautyclub.viewmodel.ProfileViewModel

class ProfileViewModelFactory(
    private val memberRepository: MemberRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (
            modelClass.isAssignableFrom(
                ProfileViewModel::class.java
            )
        ) {

            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(
                memberRepository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}