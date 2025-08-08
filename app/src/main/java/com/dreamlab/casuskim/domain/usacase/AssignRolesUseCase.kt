package com.dreamlab.casuskim.domain.usacase

import com.dreamlab.casuskim.domain.model.Location
import com.dreamlab.casuskim.domain.model.Player


class AssignRolesUseCase {

    fun invoke(players: List<Player>, location: Location): List<Player> {
        if (players.size < 3) throw IllegalArgumentException("En az 3 oyuncu gerekli")

        val shuffledPlayers = players.shuffled()
        val spyIndex = (0 until players.size).random()

        val assignedPlayers = mutableListOf<Player>()
        var roleIndex = 0

        for ((index, player) in shuffledPlayers.withIndex()) {
            val role = if (index == spyIndex) {
                "Casus"
            } else {
                location.roles[roleIndex++ % location.roles.size]
            }

            // 📌 Burada roleLocation atanıyor
            val assignedPlayer = player.copy(
                role = role,
                roleLocation = if (role == "Casus") "" else location.name
            )

            assignedPlayers.add(assignedPlayer)
        }

        return assignedPlayers
    }
}