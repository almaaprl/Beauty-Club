package com.example.beautyclub.navigation

sealed class Screen(val route: String) {

    data object Splash : Screen("splash")

    data object Login : Screen("login")

    data object Register : Screen("register")

    data object Home : Screen("home")

    data object MyCard : Screen("my_card")

    data object Reward : Screen("reward")

    data object Transaction : Screen("transaction")

    data object AddTransaction : Screen("add_transaction")

    data object TransactionSuccess : Screen("transaction_success")

    data object Profile : Screen("profile")
}