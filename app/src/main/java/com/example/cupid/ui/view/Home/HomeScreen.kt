package com.example.cupid.ui.view.Home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.cupid.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.cupid.view_model.UserViewModel
import com.example.cupid.view_model.UserVmFactory

// ------------------- DATA -------------------
data class Person(
    val name: String,
    val age: Int,
    val profession: String,
    val imageResIds: List<Int>
)

enum class SwipeDirection { Left, Right }

// ------------------- SWIPEABLE CARD -------------------
@Composable
fun SwipeableCard(
    imageUrl: String,
    name: String,
    offsetX: Animatable<Float, AnimationVector1D>,
    rotation: Animatable<Float, AnimationVector1D>,
    onTap: (Offset, Float) -> Unit,
    onSwipe: (SwipeDirection) -> Unit
) {
    val density = LocalDensity.current
    val widthPx = with(density) { 300.dp.toPx() }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .size(300.dp, 400.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.LightGray)
            .graphicsLayer {
                translationX = offsetX.value
                rotationZ = rotation.value
                scaleX = 1f - (kotlin.math.abs(offsetX.value) / 2000f)
                scaleY = 1f - (kotlin.math.abs(offsetX.value) / 2000f)
            }
            .pointerInput(Unit) {
                detectTapGestures { tapOffset ->
                    onTap(tapOffset, widthPx)
                }
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        when {
                            offsetX.value > 100f -> onSwipe(SwipeDirection.Right)
                            offsetX.value < -100f -> onSwipe(SwipeDirection.Left)
                            else -> {
                                scope.launch {
                                    offsetX.animateTo(0f, tween(300))
                                    rotation.animateTo(0f, tween(300))
                                }
                            }
                        }
                    }
                ) { change, dragAmount ->
                    change.consume()
                    scope.launch {
                        val newOffset = offsetX.value + dragAmount.x
                        offsetX.snapTo(newOffset)
                        rotation.snapTo((newOffset / 300f) * 10f)
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Text(
            text = name,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
        )
    }
}

// ------------------- ICON BUTTON -------------------
@Composable
fun CircleIconButton(
    icon: ImageVector,
    tint: Color,
    size: Dp,
    onClick: () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val containerColor by animateColorAsState(
        targetValue = if (pressed) tint else Color.White,
        label = "containerColorAnim"
    )
    val iconColor by animateColorAsState(
        targetValue = if (pressed) Color.White else tint,
        label = "iconColorAnim"
    )

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(containerColor)
            .clickable {
                pressed = true
                onClick()
                scope.launch {
                    delay(200)
                    pressed = false
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(size * 0.5f)
        )
    }
}

// ------------------- SAMPLE PEOPLE -------------------
val people = listOf(
    Person(
        name = "Jessica Parker",
        age = 23,
        profession = "Professional Model",
        imageResIds = listOf(
            R.drawable.person1_1,
            R.drawable.person1_2,
            R.drawable.person1_3,
            R.drawable.person1_4,
            R.drawable.person1_5
        )
    ),
    Person(
        name = "Michael Johnson",
        age = 28,
        profession = "Software Engineer",
        imageResIds = listOf(
            R.drawable.person2_1,
            R.drawable.person2_2,
            R.drawable.person2_3,
            R.drawable.person2_4,
            R.drawable.person2_5
        )
    )
)

// ------------------- HOME SCREEN -------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, paddingValues: PaddingValues) {
    val vm: UserViewModel = viewModel(factory = UserVmFactory())
    val users by vm.users.collectAsState()

    LaunchedEffect(Unit) { vm.load() }

    if (users.isEmpty()) {
        Box(Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
            Text("Loading user...")
        }
        return
    }

    var userIndex by remember { mutableStateOf(0) }
    var photoIndex by remember { mutableStateOf(0) }

    val u = users[userIndex % users.size]
    val photos = if (u.photos.isNotEmpty()) u.photos else listOf()
    val currentUrl = photos[photoIndex % photos.size].photo_url

    val offsetX = remember { Animatable(0f) }
    val rotation = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize().padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        SwipeableCard(
            imageUrl = currentUrl,
            name = u.username,
            offsetX = offsetX,
            rotation = rotation,
            onTap = { tapOffset, width ->
                if (photos.isNotEmpty()) {
                    photoIndex = if (tapOffset.x < width/2) {
                        (photoIndex - 1 + photos.size) % photos.size
                    } else {
                        (photoIndex + 1) % photos.size
                    }
                }
            },
            onSwipe = { direction ->
                val isLeft = direction == SwipeDirection.Left
                scope.launch {
                    simulateSwipe(isLeft, offsetX, rotation) {
                        userIndex++
                        photoIndex = 0
                    }
                }
            }
        )

        Row(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleIconButton(icon = Icons.Default.Close, tint = Color.Red, size = 60.dp) {
                scope.launch { simulateSwipe(true, offsetX, rotation) {
                    userIndex++; photoIndex = 0
                } }
            }
            CircleIconButton(icon = Icons.Default.Favorite, tint = Color.Green, size = 80.dp) {
                val lat = u.location_lat ?: 0.0
                val lng = u.location_lng ?: 0.0
                navController.navigate("map?name=${u.username}&lat=$lat&lng=$lng")
            }
            CircleIconButton(icon = Icons.Default.Star, tint = Color.Yellow, size = 60.dp) {
                scope.launch { simulateSwipe(false, offsetX, rotation) {
                    userIndex++; photoIndex = 0
                } }
            }
        }
    }
}


// ------------------- SWIPE SIMULATION -------------------
suspend fun simulateSwipe(
    isLeft: Boolean,
    offsetX: Animatable<Float, AnimationVector1D>,
    rotation: Animatable<Float, AnimationVector1D>,
    onFinish: () -> Unit
) {
    val endOffset = if (isLeft) -2000f else 2000f
    val endRotation = if (isLeft) -30f else 30f

    offsetX.animateTo(endOffset, animationSpec = tween(400))
    rotation.animateTo(endRotation, animationSpec = tween(400))
    onFinish()
    offsetX.snapTo(0f)
    rotation.snapTo(0f)
}
