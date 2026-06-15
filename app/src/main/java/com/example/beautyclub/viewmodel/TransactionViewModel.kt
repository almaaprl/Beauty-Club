package com.example.beautyclub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.beautyclub.data.local.entity.TransactionEntity
import com.example.beautyclub.data.repository.MemberRepository
import com.example.beautyclub.data.repository.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed class AddTransactionState {
    data object Idle : AddTransactionState()
    data object Loading : AddTransactionState()
    data class Success(val pointEarned: Int, val totalPoints: Int) : AddTransactionState()
    data class Error(val message: String) : AddTransactionState()
}

class TransactionViewModel(
    private val transactionRepository: TransactionRepository,
    private val memberRepository: MemberRepository
) : ViewModel() {

    private val _transactions = MutableStateFlow<List<TransactionEntity>>(emptyList())
    val transactions: StateFlow<List<TransactionEntity>> = _transactions

    private val _addState = MutableStateFlow<AddTransactionState>(AddTransactionState.Idle)
    val addState: StateFlow<AddTransactionState> = _addState

    // Load semua transaksi milik member (realtime Flow)
    fun loadTransactions(memberId: Int) {
        viewModelScope.launch {
            transactionRepository.getTransactions(memberId).collectLatest { list ->
                _transactions.value = list
            }
        }
    }

    // Tambah transaksi baru
    fun addTransaction(memberId: Int, treatmentName: String, amount: Double) {
        if (treatmentName.isBlank()) {
            _addState.value = AddTransactionState.Error("Pilih treatment terlebih dahulu")
            return
        }
        if (amount <= 0) {
            _addState.value = AddTransactionState.Error("Nominal tidak valid")
            return
        }

        viewModelScope.launch {
            _addState.value = AddTransactionState.Loading

            val date = SimpleDateFormat("dd MMM yyyy • HH:mm", Locale.getDefault())
                .format(Date())

            val pointEarned = (amount / 10_000).toInt()

            transactionRepository.addTransaction(
                memberId      = memberId,
                treatmentName = treatmentName,
                amount        = amount,
                date          = date
            )

            // Ambil total poin terbaru setelah transaksi
            val updatedMember = memberRepository.getMember(memberId)
            val totalPoints   = updatedMember?.points ?: 0

            _addState.value = AddTransactionState.Success(
                pointEarned = pointEarned,
                totalPoints = totalPoints
            )
        }
    }

    fun resetAddState() {
        _addState.value = AddTransactionState.Idle
    }
}

class TransactionViewModelFactory(
    private val transactionRepository: TransactionRepository,
    private val memberRepository: MemberRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransactionViewModel(transactionRepository, memberRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}