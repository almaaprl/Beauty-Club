package com.example.beautyclub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beautyclub.data.repository.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(TransactionUiState())

    val uiState: StateFlow<TransactionUiState> =
        _uiState.asStateFlow()

    fun loadTransactions(memberId: Int) {

        viewModelScope.launch {

            transactionRepository
                .getTransactions(memberId)
                .collectLatest { transactions ->

                    _uiState.value =
                        _uiState.value.copy(
                            transactions = transactions
                        )
                }
        }
    }

    fun addTransaction(
        memberId: Int,
        treatmentName: String,
        amount: Double,
        date: String
    ) {

        viewModelScope.launch {

            transactionRepository.addTransaction(
                memberId = memberId,
                treatmentName = treatmentName,
                amount = amount,
                date = date
            )

            _uiState.value =
                _uiState.value.copy(
                    isSuccess = true
                )
        }
    }

    fun resetSuccessState() {

        _uiState.value =
            _uiState.value.copy(
                isSuccess = false
            )
    }
}