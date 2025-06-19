package com.example.dataeast.Geocoder.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dataeast.Geocoder.ui.components.*
import com.example.dataeast.Geocoder.viewmodel.GeocoderViewModel

@Composable
fun GeocoderScreen(padding: PaddingValues) {
    val history = remember { mutableStateListOf<String>() }

    val viewModel: GeocoderViewModel = viewModel()
    val point by viewModel.point.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)
    ) {
        var address by remember { mutableStateOf("") }

        AddressInputField(
            address = address,
            onAddressChange = {
                address = it
                viewModel.onAddressChange(it)
            },
            onGeocoderClick = {
                if (address.isNotBlank()) {
                    history.add(0, address)
                    viewModel.geocodeAddress()
                }
            }
        )


        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            MapViewContainer(context = LocalContext.current, point = point)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("История запросов: ", style = MaterialTheme.typography.titleMedium)

        AddressHistoryList(
            history = history,
            onHistoryItemClick = { selectedAddress ->
                address = selectedAddress
                viewModel.onAddressChange(selectedAddress)
            }
        )

    }
}
