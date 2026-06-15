package com.example.beautyclub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.beautyclub.data.local.entity.MemberEntity
import com.example.beautyclub.data.local.entity.TransactionEntity
import com.example.beautyclub.data.repository.MemberRepository
import com.example.beautyclub.data.repository.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val memberRepository: MemberRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _member = MutableStateFlow<MemberEntity?>(null)
    val member: StateFlow<MemberEntity?> = _member

    private val _transactions = MutableStateFlow<List<TransactionEntity>>(emptyList())
    val transactions: StateFlow<List<TransactionEntity>> = _transactions

    // Dipanggil dari NavGraph setelah login sukses, pass memberId
    fun loadData(memberId: Int) {
        viewModelScope.launch {
            // Load member sekali
            _member.value = memberRepository.getMember(memberId)

            // Observe transactions secara realtime (Flow)
            transactionRepository.getTransactions(memberId).collectLatest { list ->
                _transactions.value = list
            }
        }
    }
}

class HomeViewModelFactory(
    private val memberRepository: MemberRepository,
    private val transactionRepository: TransactionRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(memberRepository, transactionRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}