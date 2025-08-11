package com.example.lascade_machine_test.components.bottom_sheet_components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AnimatedBottomSheetContent(
    isMenuOpen: Boolean,
    defaultContent: @Composable () -> Unit,
    routeSettingsContent: @Composable () -> Unit
) {
    // Show default content when menu is not open
    AnimatedVisibility(
        visible = !isMenuOpen,
        enter = slideInHorizontally(
            animationSpec = tween(durationMillis = 300),
            initialOffsetX = { it }
        ),
        exit = slideOutHorizontally(
            animationSpec = tween(durationMillis = 300),
            targetOffsetX = { -it }
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            defaultContent()
        }
    }

    // Show route settings content when menu is open
    AnimatedVisibility(
        visible = isMenuOpen,
        enter = slideInHorizontally(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow
            ),
            initialOffsetX = { it }
        ),
        exit = slideOutHorizontally(
            animationSpec = tween(durationMillis = 300),
            targetOffsetX = { it }
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            routeSettingsContent()
        }
    }
}