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
            "⌫" -> backspace()
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

    private fun backspace(){
        val expr = _uiState.value.expression
        if (expr.isNotEmpty()){
            _uiState.value = _uiState.value.copy(
                expression = expr.dropLast(1)
            )
        }
    }

    private fun calculateResult() {
        val expr = _uiState.value.expression

        if(expr.isBlank()){
            _uiState.value = _uiState.value.copy(result = "Введите выражение")
            return
        }

        if(!areParenthesesBalanced(expr)){
            _uiState.value = _uiState.value.copy(result = "Скобки не сбалансированы")
            return
        }

        if(hasConsecutiveOperators(expr)){
            _uiState.value = _uiState.value.copy(result = "Неправильное выражение")
            return
        }

        val result = try {
            val exprValue = ExpressionEvaluator.evaluate(expr)
            if (exprValue.isInfinite() || exprValue.isNaN()){
                "Разделить на ноль нельзя"
            }
            else {
                formatResult(exprValue.toString())
            }
        } catch (e: Exception) {
            "Ошибка"
        }

        _uiState.value = _uiState.value.copy(
            expression = result.toString(),
            result = result,
            history = listOf(Pair(expr, result)) + _uiState.value.history
        )
    }

    private fun areParenthesesBalanced(expression: String): Boolean {
        var balance = 0
        for (ch in expression){
            when (ch) {
                '(' -> balance++
                ')' -> balance--
            }
            if (balance < 0) return false
        }
        return balance == 0
    }

    private fun hasConsecutiveOperators(expression: String): Boolean {
        val operators = listOf('+', '-', '*', '/')
        for (i in 1 until expression.length) {
            if (expression[i] in operators && expression[i - 1] in operators) {
                return true
            }
        }
        return false
    }

    fun formatResult(result: String): String {
        return result.toDoubleOrNull()?.let {
            if (it % 1.0 == 0.0) it.toInt().toString() else it.toString()
        } ?: result
    }
}
