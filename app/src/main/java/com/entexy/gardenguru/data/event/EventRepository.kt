package com.entexy.gardenguru.data.event

import com.entexy.gardenguru.domain.repository.EventRepository

interface EventRepositoryImpl : EventRepository {

    override fun completeEvent()

}