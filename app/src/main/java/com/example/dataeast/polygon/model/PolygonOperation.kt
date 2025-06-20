package com.example.dataeast.polygon.model

enum class PolygonOperation(val label: String){
    UNION("Объединение"),
    INTERSECTION("Пересечение"),
    DIFFERENCE("Разность");

    override fun toString(): String = label
}