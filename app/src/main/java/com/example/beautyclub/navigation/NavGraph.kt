package com.example.beautyclub.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.beautyclub.ui.auth.LoginScreen
import com.example.beautyclub.ui.home.HomeScreen
import com.example.beautyclub.ui.auth.RegisterScreen
import com.example.beautyclub.ui.auth.SplashScreen

//import com.example.beautyclub.ui.home.MyCardScreen
//import com.example.beautyclub.ui.home.RewardScreen
//
//import com.example.beautyclub.ui.transaction.TransactionScreen
//import com.example.beautyclub.ui.transaction.AddTransactionScreen
//import com.example.beautyclub.ui.transaction.TransactionSuccessScreen

import com.example.beautyclub.ui.profile.ProfileScreen

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(navController)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }
//
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
//
//        composable(Screen.MyCard.route) {
//            MyCardScreen(navController)
//        }
//
//        composable(Screen.Reward.route) {
//            RewardScreen(navController)
//        }
//
//        composable(Screen.Transaction.route) {
//            TransactionScreen(navController)
//        }
//
//        composable(Screen.AddTransaction.route) {
//            AddTransactionScreen(navController)
//        }
//
//        composable(Screen.TransactionSuccess.route) {
//            TransactionSuccessScreen(navController)
//        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }
    }
}