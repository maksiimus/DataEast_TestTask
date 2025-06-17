package com.example.dataeast.model

data class CalculatorState(
    val expression: String = "",
    val result: String = "",
    val history: List<Pair<String, String>> = emptyList()
)
