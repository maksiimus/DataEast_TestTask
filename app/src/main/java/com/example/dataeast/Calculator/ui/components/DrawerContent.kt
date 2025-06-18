package com.example.dataeast.Calculator.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.dataeast.Calculator.model.DrawerItem
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.unit.dp

@Composable
fun DrawerContent(
    items : List<DrawerItem>,
    OnItemClick : (DrawerItem) -> Unit
) {
    Column {
        Text("Меню",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )
        Divider()
        items.forEach { item ->
            NavigationDrawerItem(
                label = { Text(item.title)},
                selected = false,
                onClick = { OnItemClick (item) },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}