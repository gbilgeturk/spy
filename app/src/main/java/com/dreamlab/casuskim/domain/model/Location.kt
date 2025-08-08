package com.dreamlab.casuskim.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val name: String,
    val roles: List<String>
)