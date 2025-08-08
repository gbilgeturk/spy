package com.dreamlab.casuskim.presentation.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object PlayerEntry : Screen("playerEntry")
    object RoleReveal : Screen("roleReveal")
    object Game : Screen("game")
    object Voting : Screen("voting")
    object Result : Screen("result")
    object Settings : Screen("settings")
}