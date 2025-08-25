package com.example.cupid.ui.view.Profile

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.PolyUtil
import com.google.maps.android.compose.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

// ===================== Directions API =====================
suspend fun getDirections(
    origin: LatLng,
    destination: LatLng,
    apiKey: String
): Pair<List<LatLng>, String>? {
    val url = "https://maps.googleapis.com/maps/api/directions/json" +
            "?origin=${origin.latitude},${origin.longitude}" +
            "&destination=${destination.latitude},${destination.longitude}" +
            "&key=$apiKey"

    return try {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        val body = response.body?.string() ?: return null

        val json = JSONObject(body)
        val routes = json.optJSONArray("routes") ?: return null
        if (routes.length() == 0) return null

        val firstRoute = routes.getJSONObject(0)
        val overviewPolyline = firstRoute
            .getJSONObject("overview_polyline")
            .getString("points")

        val legs = firstRoute.getJSONArray("legs")
        val distanceText = legs.getJSONObject(0)
            .getJSONObject("distance")
            .getString("text")

        val path: List<LatLng> = PolyUtil.decode(overviewPolyline)
        path to distanceText
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

// ===================== Map Screen =====================
@SuppressLint("MissingPermission")
@Composable
fun GoogleMapScreen(
    userName: String,
    destination: LatLng
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val fused = remember { LocationServices.getFusedLocationProviderClient(context) }

    // Quyền vị trí
    var hasLocationPerm by remember { mutableStateOf(false) }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { grants ->
        hasLocationPerm = (grants[Manifest.permission.ACCESS_FINE_LOCATION] == true
                || grants[Manifest.permission.ACCESS_COARSE_LOCATION] == true)
    }

    LaunchedEffect(Unit) {
        val fine = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarse = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
        hasLocationPerm = (fine == PackageManager.PERMISSION_GRANTED ||
                coarse == PackageManager.PERMISSION_GRANTED)
        if (!hasLocationPerm) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    // Trạng thái UI
    var myLatLng by remember { mutableStateOf<LatLng?>(null) }
    var routePoints by remember { mutableStateOf<List<LatLng>>(emptyList()) }
    var distanceText by remember { mutableStateOf<String?>(null) }

    // Lấy API key từ manifest meta-data
    val apiKey by remember {
        mutableStateOf(
            try {
                val ai = context.packageManager.getApplicationInfo(
                    context.packageName, PackageManager.GET_META_DATA
                )
                ai.metaData.getString("com.google.android.geo.API_KEY") ?: ""
            } catch (_: Exception) { "" }
        )
    }

    // Map state
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(destination, 12f)
    }
    val mapProperties by remember(hasLocationPerm) {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = hasLocationPerm
            )
        )
    }
    val uiSettings by remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = false)) }

    // Marker states (fix lỗi "Creating state object during composition")
    val destinationMarkerState = remember { MarkerState(destination) }
    var myMarkerState by remember { mutableStateOf<MarkerState?>(null) }

    // Lấy vị trí hiện tại (last known)
    LaunchedEffect(hasLocationPerm) {
        if (!hasLocationPerm) return@LaunchedEffect
        fused.lastLocation.addOnSuccessListener { loc ->
            if (loc != null) {
                myLatLng = LatLng(loc.latitude, loc.longitude)
                myMarkerState = MarkerState(myLatLng!!)

                // Fit 2 điểm vào khung hình (fix nullable myLatLng)
                scope.launch {
                    myLatLng?.let { current ->
                        val bounds = LatLngBounds.builder()
                            .include(current)
                            .include(destination)
                            .build()
                        cameraPositionState.animate(
                            update = CameraUpdateFactory.newLatLngBounds(bounds, 120)
                        )
                    }
                }

                // Gọi Directions API để lấy đường đi + khoảng cách
                scope.launch {
                    val result = withContext(Dispatchers.IO) {
                        if (apiKey.isNotBlank()) getDirections(myLatLng!!, destination, apiKey)
                        else null
                    }
                    result?.let { (polyline, dist) ->
                        routePoints = polyline
                        distanceText = dist
                    }
                }
            } else {
                // fallback nếu null (ví dụ chưa có last location)
                myLatLng = LatLng(10.762622, 106.660172) // HCM
                myMarkerState = MarkerState(myLatLng!!)
            }
        }
    }

    // UI Map
    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Box(Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                properties = mapProperties,
                uiSettings = uiSettings,
                cameraPositionState = cameraPositionState
            ) {
                // Marker người kia (fixed với remember)
                Marker(
                    state = destinationMarkerState,
                    title = userName,
                    snippet = "Người bạn thích"
                )
                // Marker của bạn (chỉ tạo khi có vị trí)
                myMarkerState?.let {
                    Marker(
                        state = it,
                        title = "Bạn",
                        snippet = "Vị trí hiện tại"
                    )
                }
                // Vẽ đường đi
                if (routePoints.isNotEmpty()) {
                    Polyline(points = routePoints)
                }
            }

            // Overlay khoảng cách
            val text = when {
                distanceText != null -> "Khoảng cách theo đường đi: ${distanceText}"
                myLatLng != null -> {
                    val res = android.location.Location("").apply {
                        latitude = myLatLng!!.latitude
                        longitude = myLatLng!!.longitude
                    }
                    val dst = android.location.Location("").apply {
                        latitude = destination.latitude
                        longitude = destination.longitude
                    }
                    val km = res.distanceTo(dst) / 1000f
                    "Khoảng cách ước tính: %.2f km".format(km)
                }
                else -> "Đang xác định vị trí của bạn…"
            }

            Text(
                text = text,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(12.dp)
                    .background(Color.White.copy(alpha = 0.9f))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                color = Color.Black
            )
        }
    }
}
