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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeViewModel(
    private val memberRepository: MemberRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _member = MutableStateFlow<MemberEntity?>(null)
    val member: StateFlow<MemberEntity?> = _member

    private val _transactions = MutableStateFlow<List<TransactionEntity>>(emptyList())
    val transactions: StateFlow<List<TransactionEntity>> = _transactions

    fun loadData(memberId: Int) {
        viewModelScope.launch {
            _member.value = memberRepository.getMember(memberId)

            transactionRepository.getTransactions(memberId).collectLatest { list ->
                _transactions.value = list
                // ← Auto-refresh member setiap ada perubahan transaksi
                val currentId = _member.value?.id ?: memberId
                _member.value = memberRepository.getMember(currentId)
            }
        }
    }

    fun refreshMember() {
        viewModelScope.launch {
            val currentId = _member.value?.id ?: return@launch
            _member.value = memberRepository.getMember(currentId)
        }
    }


    fun redeemReward(member: MemberEntity, rewardName: String, pointCost: Int) {
        viewModelScope.launch {
            val updatedMember = member.copy(points = member.points - pointCost)
            memberRepository.updateMember(updatedMember)
            _member.value = updatedMember

            val date = SimpleDateFormat("dd MMM yyyy • HH:mm", Locale.getDefault())
                .format(Date())
            transactionRepository.addRedemption(
                memberId  = member.id,
                rewardName = rewardName,
                pointCost  = pointCost,
                date       = date
            )
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