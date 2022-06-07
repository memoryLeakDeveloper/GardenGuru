package com.example.gardenguru.core

abstract class Base {

    interface DbObject

    interface CloudObject

    interface DataObject

    interface UiObject

    interface CloudMapper<C: CloudObject, D: DataObject>{
        fun map(cloud: C): D
    }

    interface DataMapper<C: CloudObject, D: DataObject>{
        fun map(data: D): C
    }
}