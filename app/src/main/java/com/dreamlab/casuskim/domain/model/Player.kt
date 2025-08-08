package com.dreamlab.casuskim.domain.model

data class Player(
    val name: String,
    val role: String = "",
    val roleLocation: String = "",// Rol atanırken set edilir
    val isSpy: Boolean = false

)