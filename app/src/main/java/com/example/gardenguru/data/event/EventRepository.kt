package com.example.gardenguru.data.event

interface EventRepository {

    fun completeEvent()

    class Base: EventRepository{
        override fun completeEvent() {
            TODO("Not yet implemented")
        }

    }

}