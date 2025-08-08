package com.dreamlab.casuskim.presentation

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dreamlab.casuskim.data.datasource.LocationProvider
import com.dreamlab.casuskim.data.local.SettingsDataStore
import com.dreamlab.casuskim.domain.model.Location
import com.dreamlab.casuskim.domain.model.Player
import com.dreamlab.casuskim.domain.repository.GameRepository
import com.dreamlab.casuskim.domain.repository.GameRepositoryImpl
import com.dreamlab.casuskim.domain.usacase.AssignRolesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.random.Random


class GameViewModel(
    private val dataStore: SettingsDataStore // DataStore sınıfımız
) : ViewModel() {

    private val _players = mutableStateListOf<Player>()
    val players: List<Player> get() = _players

    // Voting sonucunu Result ekranına taşımak için
    var spyGuessedRight by mutableStateOf(false)
        private set

    // Tur süresi (varsayılan 10 dakika = 600 saniye)
    var roundTimeSeconds by mutableStateOf(600)
        private set


    init {
        // DataStore’dan süreyi yükle
        viewModelScope.launch {
            dataStore.getRoundTimeSeconds().collect { seconds ->
                roundTimeSeconds = seconds
            }
        }
    }

    fun setRoundTime(seconds: Int) {
        viewModelScope.launch {
            dataStore.saveRoundTimeSeconds(seconds)
        }
    }

    fun setSpyGuessResult(value: Boolean) {
        spyGuessedRight = value
    }

    fun resetGame() {
        _players.clear()
        spyGuessedRight = false
    }

    fun setPlayerNames(names: List<String>) {
        _players.clear()
        _players.addAll(
            names.filter { it.isNotBlank() }.map { Player(name = it) }
        )
    }

    fun assignRoles(context: Context) {
        if (_players.isEmpty()) return
        val location = LocationProvider.getRandomLocation(context)
        val spyIndex = (0 until _players.size).random()

        _players.forEachIndexed { index, p ->
            _players[index] = p.copy(
                isSpy = index == spyIndex,
                roleLocation = if (index == spyIndex) "" else location.name,
                role = if (index == spyIndex) "" else
                    (location.roles.getOrNull(index) ?: "Vatandaş")
            )
        }
    }

    // AppNavHost'tan çağrılabilsin diye
    fun getAllLocations(context: Context): List<Location> =
        LocationProvider.getAllLocations(context)

    // Tur süresini güncelle
    fun updateRoundTime(seconds: Int) {
        roundTimeSeconds = seconds
    }
}

class GameViewModelFactory(
    private val dataStoreManager: SettingsDataStore
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameViewModel(dataStoreManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}