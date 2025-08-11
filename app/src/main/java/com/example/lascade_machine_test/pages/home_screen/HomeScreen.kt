package com.example.lascade_machine_test.pages.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
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
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = { CustomBottomSheet() },
        sheetPeekHeight = 190.dp, // Optimized height to fit content perfectly
        modifier = Modifier.fillMaxSize(),
        contentColor = androidx.compose.ui.graphics.Color.Transparent,
        backgroundColor = androidx.compose.ui.graphics.Color.Transparent
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