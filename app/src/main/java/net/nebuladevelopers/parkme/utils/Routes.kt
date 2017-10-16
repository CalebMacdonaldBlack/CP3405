package net.nebuladevelopers.parkme.utils

import android.graphics.Color
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions

/**
 * Created by calebmacdonaldblack on 7/10/17.
 */
object Routes {

    val buildingsHashmap: HashMap<String, LatLng> = hashMapOf(
            "17" to LatLng(-19.329356, 146.759716),
            "14" to LatLng(-19.331430, 146.758528),
            "34" to LatLng(-19.329240, 146.759004),
            "18" to LatLng(-19.328543, 146.758219),
            "2" to LatLng(-19.326477, 146.757709)
    )

    val routeHashmap: HashMap<String, PolylineOptions> = hashMapOf(
            "17-14" to PolylineOptions().add(
                    buildingsHashmap["17"],
                    LatLng(-19.329254, 146.758755),
                    buildingsHashmap["14"]),
            "17-34" to PolylineOptions().add(
                    buildingsHashmap["17"],
                    buildingsHashmap["34"]),
            "14-34" to PolylineOptions().add(
                    buildingsHashmap["14"],
                    LatLng(-19.329254, 146.758755),
                    buildingsHashmap["34"]),
            "18-17" to PolylineOptions().add(
                    buildingsHashmap["18"],
                    LatLng(-19.328794, 146.758179),
                    LatLng(-19.328971, 146.759162),
                    buildingsHashmap["17"]),
            "18-14" to PolylineOptions().add(
                    buildingsHashmap["18"],
                    LatLng(-19.329254, 146.758755),
                    buildingsHashmap["14"]),
            "18-34" to PolylineOptions().add(
                    buildingsHashmap["18"],
                    LatLng(-19.328794, 146.758179),
                    buildingsHashmap["34"]),
            "17-2" to PolylineOptions().add(
                    buildingsHashmap["2"],
                    LatLng(-19.327743, 146.757240),
                    LatLng(-19.327965, 146.757526),
                    LatLng(-19.328315, 146.758337),
                    buildingsHashmap["18"],
                    LatLng(-19.328794, 146.758179),
                    LatLng(-19.328971, 146.759162),
                    buildingsHashmap["17"]),
            "2-18" to PolylineOptions().add(
                    buildingsHashmap["2"],
                    LatLng(-19.327743, 146.757240),
                    LatLng(-19.327965, 146.757526),
                    LatLng(-19.328315, 146.758337),
                    buildingsHashmap["18"]),
            "2-34" to PolylineOptions().add(
                    buildingsHashmap["2"],
                    LatLng(-19.327743, 146.757240),
                    LatLng(-19.327965, 146.757526),
                    LatLng(-19.328315, 146.758337),
                    buildingsHashmap["18"],
                    LatLng(-19.328794, 146.758179),
                    buildingsHashmap["34"]),
            "2-14" to PolylineOptions().add(
                    buildingsHashmap["2"],
                    LatLng(-19.327743, 146.757240),
                    LatLng(-19.327965, 146.757526),
                    LatLng(-19.328315, 146.758337),
                    buildingsHashmap["18"],
                    LatLng(-19.328794, 146.758179),
                    buildingsHashmap["14"])
    )

    fun addRouteToMap(mMap: GoogleMap, from: String, to: String) {
        if (routeHashmap.containsKey("$from-$to")) {
            mMap.addPolyline(routeHashmap.get("$from-$to")!!.color(Color.RED).width(5f))
        } else {
            mMap.addPolyline(routeHashmap.get("$to-$from")!!.color(Color.RED).width(5f))
        }
    }
}