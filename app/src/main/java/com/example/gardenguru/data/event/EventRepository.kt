package com.example.gardenguru.data.event

import com.example.gardenguru.domain.repository.EventRepository

interface EventRepositoryImpl : EventRepository {

    override fun completeEvent()

}