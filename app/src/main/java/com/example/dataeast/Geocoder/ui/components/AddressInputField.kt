package com.example.dataeast.Geocoder.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddressInputField(
    address: String,
    onAddressChange: (String) -> Unit,
    onGeocoderClick: () -> Unit
) {
    OutlinedTextField(
        value = address,
        onValueChange = onAddressChange,
        label = {
            Text(
                "Адрес",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )

    Spacer(modifier = Modifier.height(8.dp))
    Button(
        onClick = onGeocoderClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Геокодировать")
    }
}
