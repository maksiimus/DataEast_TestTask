package com.example.dataeast.Calculator.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.dataeast.Calculator.model.CalculatorState
import com.example.dataeast.Calculator.util.ExpressionEvaluator

class CalculatorViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CalculatorState())
    val uiState: StateFlow<CalculatorState> = _uiState

    fun onSymbolEntered(symbol: String) {
        when (symbol) {
            "=" -> calculateResult()
            "C" -> clear()
            else -> _uiState.value = _uiState.value.copy(
                expression = _uiState.value.expression + symbol
            )
        }
    }

    private fun clear() {
        _uiState.value = _uiState.value.copy(
            expression = "",
            result = ""
        )
    }

    private fun calculateResult() {
        val expr = _uiState.value.expression
        val result = try {
            ExpressionEvaluator.evaluate(expr).toString()
        } catch (e: Exception) {
            "Ошибка"
        }

        _uiState.value = _uiState.value.copy(
            result = result,
            history = listOf(Pair(expr, result)) + _uiState.value.history
        )
    }
}
