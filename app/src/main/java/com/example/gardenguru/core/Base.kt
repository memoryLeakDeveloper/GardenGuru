package com.example.gardenguru.core

abstract class Base {

    interface DbObject

    interface CloudObject

    interface DataObject

    interface UiObject

    interface Mapper<C: CloudObject, D: DataObject>{
        fun map(cloud: C): D
    }
}