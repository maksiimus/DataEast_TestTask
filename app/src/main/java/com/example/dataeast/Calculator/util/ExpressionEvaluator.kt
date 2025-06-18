package com.example.dataeast.Calculator.util

import java.util.Stack

object ExpressionEvaluator {

    fun evaluate(expression: String): Double {
        val tokens = toRPN(expression)
        val stack = Stack<Double>()

        for (token in tokens) {
            when {
                token.toDoubleOrNull() != null -> stack.push(token.toDouble())
                token in listOf("+", "-", "*", "/") -> {
                    val b = stack.pop()
                    val a = stack.pop()
                    val result = when (token) {
                        "+" -> a + b
                        "-" -> a - b
                        "*" -> a * b
                        "/" -> a / b
                        else -> throw IllegalArgumentException("Неизвестная операция: $token")
                    }
                    stack.push(result)
                }
            }
        }

        return stack.pop()
    }

    // Преобразование выражения в обратную польскую нотацию (Shunting Yard Algorithm)
    private fun toRPN(expression: String): List<String> {
        val output = mutableListOf<String>()
        val operators = Stack<Char>()
        var number = ""

        val precedence = mapOf('+' to 1, '-' to 1, '*' to 2, '/' to 2)

        for (char in expression.filterNot { it.isWhitespace() }) {
            when {
                char.isDigit() || char == '.' -> number += char
                char in precedence.keys -> {
                    if (number.isNotEmpty()) {
                        output.add(number)
                        number = ""
                    }
                    while (operators.isNotEmpty() &&
                        (precedence[operators.peek()] ?: 0) >= (precedence[char] ?: 0)) {
                        output.add(operators.pop().toString())
                    }
                    operators.push(char)
                }
                char == '(' -> operators.push(char)
                char == ')' -> {
                    if (number.isNotEmpty()) {
                        output.add(number)
                        number = ""
                    }
                    while (operators.peek() != '(') {
                        output.add(operators.pop().toString())
                    }
                    operators.pop() // Удаляем '('
                }
            }
        }

        if (number.isNotEmpty()) output.add(number)

        while (operators.isNotEmpty()) {
            output.add(operators.pop().toString())
        }

        return output
    }
}
