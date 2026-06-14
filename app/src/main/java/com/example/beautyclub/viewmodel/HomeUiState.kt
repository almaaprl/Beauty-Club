package com.example.beautyclub.viewmodel

import com.example.beautyclub.data.local.entity.MemberEntity
import com.example.beautyclub.data.local.entity.TransactionEntity

data class HomeUiState(
    val member: MemberEntity? = null
)