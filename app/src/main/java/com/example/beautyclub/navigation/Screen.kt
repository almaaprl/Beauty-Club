package com.example.beautyclub.navigation

sealed class Screen(val route: String) {

    data object Splash : Screen("splash")

    data object Login : Screen("login")

    data object Register : Screen("register")

    data object Home : Screen("home")

    data object MyCard : Screen("my_card")

    data object Reward : Screen("reward")

    data object Transaction : Screen("transaction")

    data object AddTransaction : Screen("add_transaction/{memberId}") {
        fun createRoute(memberId: Int) =
            "add_transaction/$memberId"
    }

    data object TransactionSuccess :
        Screen("transaction_success?pointEarned={pointEarned}&totalPoints={totalPoints}") {

        fun createRoute(
            pointEarned: Int,
            totalPoints: Int
        ) =
            "transaction_success?pointEarned=$pointEarned&totalPoints=$totalPoints"
    }
    data object Profile : Screen("profile")
}