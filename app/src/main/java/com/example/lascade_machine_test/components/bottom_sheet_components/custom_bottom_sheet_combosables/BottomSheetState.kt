package com.example.lascade_machine_test.components.bottom_sheet_components.custom_bottom_sheet_combosables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.lascade_machine_test.R

@Stable
class BottomSheetState {
    var currentPage by mutableStateOf(0)
    var isAnimating by mutableStateOf(false)
    var routeName by mutableStateOf("")
    var selectedRouteIndex by mutableStateOf(0)
    var selectedMapStyle by mutableStateOf(0)
    var searchQuery by mutableStateOf("")
    var dragOffset by mutableStateOf(0f)

    val animationOffset = Animatable(0f)
    val animationAlpha = Animatable(1f)
    val animationScale = Animatable(1f)

    // Sample data
    val savedRoutes = listOf(
        "Kochi - Los Angeles",
        "Dubai trip",
        "Kochi - Los Angeles"
    )

    private val locations = listOf(
        "New York" to "New York, United States",
        "London" to "London, United Kingdom",
        "Heathrow LHR" to "London, United Kingdom",
        "Stansted STN" to "London, United Kingdom",
        "Paris CDG" to "Paris, France",
        "Tokyo" to "Tokyo, Japan",
        "Dubai DXB" to "Dubai, United Arab Emirates",
        "Singapore SIN" to "Singapore",
        "Mumbai BOM" to "Mumbai, India",
        "Delhi DEL" to "Delhi, India"
    )

    val mapStyles = listOf(
        "Classic" to R.drawable.map1,
        "Blueprint" to R.drawable.map2,
        "Night" to R.drawable.map4
    )

    fun getFilteredLocations(): List<Pair<String, String>> {
        return if (searchQuery.isEmpty()) {
            locations
        } else {
            locations.filter {
                it.first.contains(searchQuery, ignoreCase = true) ||
                        it.second.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    fun updateDrag(dragAmount: Float) {
        if (dragAmount > 0) {
            dragOffset += dragAmount * 0.4f
            dragOffset = dragOffset.coerceAtMost(400f)
        }
    }

    fun resetDrag() {
        dragOffset = 0f
    }

    suspend fun performSmoothPageTransition(
        newPage: Int,
        animationScope: CoroutineScope,
        lazyListState: LazyListState,
        onComplete: (Int) -> Unit
    ) {
        if (isAnimating) return
        isAnimating = true

        val direction = if (newPage > currentPage) -1f else 1f
        val animationDuration = 100

        //Fade out and slide current content
        animationScope.launch {
            animationAlpha.animateTo(
                targetValue = 0.3f,
                animationSpec = tween(durationMillis = animationDuration / 3)
            )
        }

        animationScope.launch {
            animationScale.animateTo(
                targetValue = 0.95f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
        }

        animationOffset.animateTo(
            targetValue = direction * 50f,
            animationSpec = tween(durationMillis = animationDuration / 3)
        )

        // Change page
        lazyListState.scrollToItem(newPage)
        currentPage = newPage
        onComplete(newPage)

        // Slide in from opposite direction
        animationOffset.snapTo(-direction * 50f)

        // Animate to final position
        animationScope.launch {
            animationOffset.animateTo(
                targetValue = 0f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
        }

        animationScope.launch {
            animationAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = animationDuration / 2)
            )
        }

        animationScale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
        )

        delay(100)
        isAnimating = false
    }

    fun navigateToPage(
        page: Int,
        animationScope: CoroutineScope,
        lazyListState: LazyListState,
        onPageChanged: (Int) -> Unit
    ) {
        if (!isAnimating) {
            animationScope.launch {
                performSmoothPageTransition(page, animationScope, lazyListState, onPageChanged)
            }
        }
    }
}

@Composable
fun rememberBottomSheetState(): BottomSheetState {
    return remember { BottomSheetState() }
}