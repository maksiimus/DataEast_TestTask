package com.example.dataeast.Calculator.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.dataeast.Calculator.model.DrawerItem
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.unit.dp

@Composable
fun DrawerContent(items: List<DrawerItem>, onItemClick: (DrawerItem) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        items.forEach { item ->
            Text(
                text = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .clickable() { onItemClick(item) },
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}