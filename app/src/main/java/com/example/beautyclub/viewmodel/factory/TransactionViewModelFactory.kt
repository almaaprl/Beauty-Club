package com.example.beautyclub.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.beautyclub.data.repository.TransactionRepository
import com.example.beautyclub.viewmodel.TransactionViewModel

//class TransactionViewModelFactory(
//    private val transactionRepository: TransactionRepository
//) : ViewModelProvider.Factory {
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(
//        modelClass: Class<T>
//    ): T {
//
//        if (
//            modelClass.isAssignableFrom(
//                TransactionViewModel::class.java
//            )
//        ) {
//
//            return TransactionViewModel(
//                transactionRepository
//            ) as T
//        }
//
//        throw IllegalArgumentException(
//            "Unknown ViewModel class"
//        )
//    }
//}