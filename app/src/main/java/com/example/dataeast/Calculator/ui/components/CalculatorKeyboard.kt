package com.example.dataeast.Calculator.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CalculatorKeyboard(onSymbolClick: (String) -> Unit) {
    val buttons = listOf(
        listOf("7", "8", "9", "/"),
        listOf("4", "5", "6", "*"),
        listOf("1", "2", "3", "-"),
        listOf("0", "(", ")", "+"),
        listOf("C", "=", "")
    )

    Column {
        for (row in buttons) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (label in row) {
                    if (label.isNotEmpty()) {
                        CalculatorButton(
                            label = label,
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp)
                        ) {
                            onSymbolClick(label)
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
