package com.example.dataeast.Geocoder.viewmodel

import androidx.lifecycle.ViewModel
import com.yandex.mapkit.geometry.BoundingBox
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.search.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GeocoderViewModel : ViewModel() {

    private val _point = MutableStateFlow(Point(55.75, 37.61))
    val point: StateFlow<Point> = _point

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> = _address

    private val searchManager: SearchManager =
        SearchFactory.getInstance().createSearchManager(SearchManagerType.ONLINE)

    fun onAddressChange(newAddress: String) {
        _address.value = newAddress
    }

    fun geocodeAddress() {
        val query = _address.value
        if (query.isBlank()) return

        val boundingBox = BoundingBox(
            Point(55.6, 37.3),
            Point(55.9, 37.8)
        )
        val geometry = Geometry.fromBoundingBox(boundingBox)

        searchManager.submit(
            query,
            geometry,
            SearchOptions(),
            object : Session.SearchListener {
                override fun onSearchResponse(response: Response) {
                    val result = response.collection.children
                        .firstOrNull()?.obj?.geometry?.firstOrNull()?.point
                    result?.let { _point.value = it }
                }

                override fun onSearchError(error: com.yandex.runtime.Error) {
                }
            }
        )
    }
}
