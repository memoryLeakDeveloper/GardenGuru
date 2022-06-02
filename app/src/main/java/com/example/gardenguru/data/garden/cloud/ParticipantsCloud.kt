package com.example.gardenguru.data.garden.cloud

import com.example.gardenguru.core.Base

data class ParticipantsCloud(
    val id: String,
    val user: String,
    val role: String
): Base.CloudObject