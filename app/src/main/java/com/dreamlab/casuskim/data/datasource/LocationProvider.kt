package com.dreamlab.casuskim.data.datasource

import android.content.Context
import com.dreamlab.casuskim.domain.model.Location
import kotlinx.serialization.json.Json

object LocationProvider {

    private var cachedLocations: List<Location>? = null

    fun getAllLocations(context: Context): List<Location> {
        if (cachedLocations != null) return cachedLocations!!

        val json = context.assets.open("locations.json")
            .bufferedReader()
            .use { it.readText() }

        val parsed = Json.decodeFromString<List<Location>>(json)
        cachedLocations = parsed
        return parsed
    }

    fun getRandomLocation(context: Context): Location {
        return getAllLocations(context).random()
    }
}