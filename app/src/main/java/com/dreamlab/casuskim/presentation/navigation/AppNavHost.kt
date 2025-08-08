package com.dreamlab.casuskim.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dreamlab.casuskim.presentation.CivilianVotingScreen
import com.dreamlab.casuskim.presentation.GameScreen
import com.dreamlab.casuskim.presentation.GameViewModel
import com.dreamlab.casuskim.presentation.PlayerEntryScreen
import com.dreamlab.casuskim.presentation.ResultScreen
import com.dreamlab.casuskim.presentation.RoleRevealScreen
import com.dreamlab.casuskim.presentation.SettingsScreen
import com.dreamlab.casuskim.presentation.SpyGuessScreen
import com.dreamlab.casuskim.presentation.onboarding.OnboardingScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String,
    viewModel: GameViewModel,
    onOnboardingFinished: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                navController = navController,
                onFinish = {
                    onOnboardingFinished.invoke()
                    navController.navigate(Screen.PlayerEntry.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.PlayerEntry.route) {
            val context = LocalContext.current
            PlayerEntryScreen(
                navController = navController,
                viewModel = viewModel,
                onStart = { names ->
                    viewModel.setPlayerNames(names)
                    viewModel.assignRoles(context)
                    navController.navigate(Screen.RoleReveal.route)
                }
            )
        }

        composable(Screen.RoleReveal.route) {
            var currentIndex by rememberSaveable { mutableStateOf(0) }
            RoleRevealScreen(
                players = viewModel.players,
                currentIndex = currentIndex,
                onNext = { currentIndex = it },
                onFinish = { navController.navigate(Screen.Game.route) }
            )
        }

        composable(Screen.Game.route) {
            GameScreen(
                viewModel = viewModel,
                onStartVoting = { navController.navigate(Screen.CivilianVoting.route)},
                onSpyWantsToAnswer = {     navController.navigate(Screen.SpyGuess.route)
                }
            )
        }

        composable(Screen.CivilianVoting.route) {
            CivilianVotingScreen(
                players = viewModel.players,
                onSpyFound = {
                    // Sivil kazandı
                    viewModel.setSpyGuessResult(false)
                    navController.navigate(Screen.Result.route)
                },
                onSpyNotFound = {
                    // Casus tahmine geçiyor
                    navController.navigate(Screen.SpyGuess.route)
                }
            )
        }

        composable(Screen.SpyGuess.route) {
            val actualLocation = viewModel.players.firstOrNull { !it.isSpy }?.roleLocation ?: ""
            SpyGuessScreen(
                allLocations = viewModel.getAllLocations(LocalContext.current),
                actualLocation = actualLocation,
                onSpyWins = {
                    viewModel.setSpyGuessResult(true)
                    navController.navigate(Screen.Result.route)
                },
                onSpyLoses = {
                    viewModel.setSpyGuessResult(false)
                    navController.navigate(Screen.Result.route)
                }
            )
        }

        composable(Screen.Result.route) {
            ResultScreen(
                players = viewModel.players,
                spyGuessedRight = viewModel.spyGuessedRight,
                onNewGame = {
                    viewModel.resetGame()
                    navController.navigate(Screen.PlayerEntry.route) {
                        popUpTo(Screen.PlayerEntry.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}