package com.example.beautyclub.viewmodel

import com.example.beautyclub.data.local.entity.TransactionEntity

data class TransactionUiState(
    val transactions: List<TransactionEntity> = emptyList(),
    val isSuccess: Boolean = false
)