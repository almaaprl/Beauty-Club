package com.example.beautyclub.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.beautyclub.data.local.BeautyClubDatabase
import com.example.beautyclub.data.repository.MemberRepository
import com.example.beautyclub.data.repository.TransactionRepository
import com.example.beautyclub.ui.auth.LoginScreen
import com.example.beautyclub.ui.auth.RegisterScreen
import com.example.beautyclub.ui.auth.SplashScreen
import com.example.beautyclub.ui.home.HomeScreen
import com.example.beautyclub.ui.profile.ProfileScreen
import com.example.beautyclub.viewmodel.AuthViewModel
import com.example.beautyclub.viewmodel.AuthViewModelFactory
import com.example.beautyclub.viewmodel.HomeViewModel
import com.example.beautyclub.viewmodel.HomeViewModelFactory
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.beautyclub.ui.home.MyCardScreen
import com.example.beautyclub.ui.home.RewardScreen
import com.example.beautyclub.ui.transaction.AddTransactionScreen
import com.example.beautyclub.ui.transaction.TransactionScreen
import com.example.beautyclub.ui.transaction.TransactionSuccessScreen
import com.example.beautyclub.viewmodel.ProfileViewModel
import com.example.beautyclub.viewmodel.TransactionViewModel
import com.example.beautyclub.viewmodel.TransactionViewModelFactory
import com.example.beautyclub.viewmodel.factory.ProfileViewModelFactory

@Composable
fun NavGraph() {

    val navController = rememberNavController()
    val context       = LocalContext.current
    val db            = BeautyClubDatabase.getDatabase(context)

    val memberRepository      = MemberRepository(db.memberDao())
    val transactionRepository = TransactionRepository(db.transactionDao(), db.memberDao())

    // Shared AuthViewModel
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(memberRepository)
    )

    // Shared HomeViewModel
    val homeViewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(memberRepository, transactionRepository)
    )

    // Saat login sukses → load data home sesuai memberId dari DB
    val loggedInMember by authViewModel.loggedInMember.collectAsState()
    LaunchedEffect(loggedInMember) {
        loggedInMember?.let { member ->
            homeViewModel.loadData(member.id)
        }
    }

    val transactionViewModel: TransactionViewModel = viewModel(
        factory = TransactionViewModelFactory(
            transactionRepository,
            memberRepository
        )
    )

    val profileViewModel: ProfileViewModel =
        viewModel(
            factory = ProfileViewModelFactory(
                memberRepository
            )
        )

    NavHost(
        navController    = navController,
        startDestination = Screen.Splash.route
    ) {

        composable(Screen.Splash.route) {
            SplashScreen(navController)
            // PENTING: di SplashScreen.kt, pastikan navigate ke Login seperti ini:
            // navController.navigate(Screen.Login.route) {
            //     popUpTo(Screen.Splash.route) { inclusive = true }
            // }
            // Agar tombol back dari Login tidak balik ke Splash
        }

        composable(Screen.Login.route) {
            LoginScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                homeViewModel = homeViewModel
            )
        }

        composable(Screen.Transaction.route) {

            val memberId = loggedInMember?.id ?: 0

            TransactionScreen(
                navController = navController,
                memberId = memberId,
                transactionViewModel = transactionViewModel
            )
        }

        composable(
            route = Screen.AddTransaction.route,
            arguments = listOf(
                navArgument("memberId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val memberId =
                backStackEntry.arguments?.getInt("memberId") ?: 0

            AddTransactionScreen(
                navController = navController,
                memberId = memberId,
                transactionViewModel = transactionViewModel,
                homeViewModel        = homeViewModel
            )
        }

        composable(
            route = Screen.TransactionSuccess.route,
            arguments = listOf(
                navArgument("pointEarned") {
                    type = NavType.IntType
                },
                navArgument("totalPoints") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val pointEarned =
                backStackEntry.arguments?.getInt("pointEarned") ?: 0

            val totalPoints =
                backStackEntry.arguments?.getInt("totalPoints") ?: 0

            TransactionSuccessScreen(
                navController = navController,
                pointEarned = pointEarned,
                totalPoints = totalPoints,
                transactionViewModel = transactionViewModel
            )
        }

        composable(Screen.MyCard.route) {
            MyCardScreen(
                navController = navController,
                homeViewModel = homeViewModel
            )
        }

        composable(Screen.Reward.route) {
            RewardScreen(
                navController = navController,
                homeViewModel = homeViewModel
            )
        }

        composable(Screen.Profile.route) {

            val memberId =
                loggedInMember?.id ?: 0

            LaunchedEffect(memberId) {
                profileViewModel.loadMember(memberId)
            }

            ProfileScreen(
                navController = navController,
                profileViewModel = profileViewModel,
                authViewModel = authViewModel
            )
        }
    }
}