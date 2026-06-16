package com.example.beautyclub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beautyclub.data.local.entity.MemberEntity
import com.example.beautyclub.data.repository.MemberRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val memberRepository: MemberRepository
) : ViewModel() {

    private val _member =
        MutableStateFlow<MemberEntity?>(null)

    val member: StateFlow<MemberEntity?> = _member

    fun loadMember(memberId: Int) {
        viewModelScope.launch {
            _member.value =
                memberRepository.getMember(memberId)
        }
    }

    fun updateProfile(
        member: MemberEntity
    ) {
        viewModelScope.launch {
            memberRepository.updateMember(member)
            _member.value = member
        }
    }
}

