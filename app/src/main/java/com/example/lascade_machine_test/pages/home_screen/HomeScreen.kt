package com.example.lascade_machine_test.pages.home_screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lascade_machine_test.R
import com.example.lascade_machine_test.components.bottom_sheet_components.CustomBottomSheet
import com.example.lascade_machine_test.components.home_screen_components.CustomAppBar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen() {
    var currentPage by remember { mutableStateOf(0) }

    // Define heights for different pages
    val pageHeights = mapOf(
        0 to 190.dp, // Default page - original height
        1 to 186.dp, // Route settings page - same height as default
        2 to 256.dp, // Delete confirmation page - needs space for confirmation text
        3 to 290.dp, // Save route page - needs space for text input and save button
        4 to 215.dp, // Save success page - needs space for success message
        5 to 430.dp, // Load route page - needs space for route list and load button
        6 to 750.dp  // Add locations page - needs space for search and location list
    )

    val bottomSheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )

    // Animate height changes with same duration as content animation
    val animatedHeight by animateDpAsState(
        targetValue = pageHeights[currentPage] ?: 186.dp,
        animationSpec = tween(
            durationMillis = 500,
            delayMillis = 0
        ),
        label = "BottomSheetHeight"
    )

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            CustomBottomSheet(
                onPageChanged = { newPage ->
                    currentPage = newPage
                }
            )
        },
        sheetPeekHeight = animatedHeight,
        modifier = Modifier.fillMaxSize(),
        sheetBackgroundColor = androidx.compose.ui.graphics.Color.Transparent,
        sheetElevation = 0.dp, // Remove elevation to eliminate shadow
        backgroundColor = androidx.compose.ui.graphics.Color.Transparent // Make scaffold background transparent
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Full screen map
            Image(
                painter = painterResource(id = R.drawable.map),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            CustomAppBar()
        }
    }
}