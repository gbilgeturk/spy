package com.dreamlab.casuskim.domain.repository

import android.content.Context
import com.dreamlab.casuskim.data.datasource.LocationProvider
import com.dreamlab.casuskim.domain.model.Location


interface GameRepository {
    fun getLocations(): List<Location>
    fun getRandomLocation(): Location
}


class GameRepositoryImpl(private val context: Context) : GameRepository {
    override fun getLocations(): List<Location> {
        return LocationProvider.getAllLocations(context)
    }

    override fun getRandomLocation(): Location {
        return LocationProvider.getRandomLocation(context)
    }
}