package com.example.lascade_machine_test.components.bottom_sheet_components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.lascade_machine_test.components.bottom_sheet_components.custom_bottom_sheet_combosables.pages.AddLocationsPage
import com.example.lascade_machine_test.components.bottom_sheet_components.custom_bottom_sheet_combosables.pages.DefaultContentPage
import com.example.lascade_machine_test.components.bottom_sheet_components.custom_bottom_sheet_combosables.pages.DeleteConfirmationPage
import com.example.lascade_machine_test.components.bottom_sheet_components.custom_bottom_sheet_combosables.pages.LoadRoutePage
import com.example.lascade_machine_test.components.bottom_sheet_components.custom_bottom_sheet_combosables.pages.MapStylePage
import com.example.lascade_machine_test.components.bottom_sheet_components.custom_bottom_sheet_combosables.pages.RouteSettingsPage
import com.example.lascade_machine_test.components.bottom_sheet_components.custom_bottom_sheet_combosables.pages.SaveRoutePage
import com.example.lascade_machine_test.components.bottom_sheet_components.custom_bottom_sheet_combosables.pages.SaveSuccessPage
import com.example.lascade_machine_test.components.bottom_sheet_components.custom_bottom_sheet_combosables.rememberBottomSheetState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun CustomBottomSheet(
    onPageChanged: (Int) -> Unit = {},
    externalPageChange: Int = 0
) {
    val bottomSheetState = rememberBottomSheetState()
    val animationScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    LaunchedEffect(externalPageChange) {
        if (externalPageChange != bottomSheetState.currentPage && externalPageChange >= 0) {
            bottomSheetState.performSmoothPageTransition(
                externalPageChange,
                animationScope,
                lazyListState
            ) { newPage ->
                onPageChanged(newPage)
            }
        }
    }

    LaunchedEffect(bottomSheetState.currentPage) {
        onPageChanged(bottomSheetState.currentPage)

        if (bottomSheetState.currentPage == 4) {
            delay(2000)
            bottomSheetState.performSmoothPageTransition(
                0,
                animationScope,
                lazyListState
            ) { newPage ->
                onPageChanged(newPage)
            }
        }
    }

    val dynamicOffset = if (bottomSheetState.currentPage == 6 && bottomSheetState.dragOffset > 0) {
        (bottomSheetState.dragOffset * 0.6f).coerceAtMost(300f)
    } else 0f

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)

            .then(
                if (bottomSheetState.currentPage == 6) {
                    Modifier.pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { bottomSheetState.resetDrag() },
                            onDragEnd = {
                                if (bottomSheetState.dragOffset > 150) {
                                    animationScope.launch {
                                        bottomSheetState.performSmoothPageTransition(
                                            0,
                                            animationScope,
                                            lazyListState
                                        ) { newPage ->
                                            onPageChanged(newPage)
                                        }
                                    }
                                }
                                bottomSheetState.resetDrag()
                            }
                        ) { change, dragAmount ->
                            bottomSheetState.updateDrag(dragAmount.y)
                        }
                    }
                } else Modifier
            )
    ) {

        DragHandle()

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer(
                    translationX = bottomSheetState.animationOffset.value,
                    alpha = bottomSheetState.animationAlpha.value,
                    scaleX = bottomSheetState.animationScale.value,
                    scaleY = bottomSheetState.animationScale.value
                )
        ) {
            LazyRow(
                state = lazyListState,
                modifier = Modifier.fillMaxWidth(),
                userScrollEnabled = false,
                horizontalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                // All pages
                items(8) { pageIndex ->
                    when (pageIndex) {
                        // DEFAULT CONTENT PAGE - INDEX 0
                        0 -> DefaultContentPage(
                            screenWidth = screenWidth,
                            onMenuClick = {
                                bottomSheetState.navigateToPage(1, animationScope, lazyListState) { onPageChanged(it) }
                            },
                            onAddClick = {
                                bottomSheetState.navigateToPage(6, animationScope, lazyListState) { onPageChanged(it) }
                            }
                        )
                        // ROUTE SETTINGS PAGE - INDEX 1
                        1 -> RouteSettingsPage(
                            screenWidth = screenWidth,
                            onCloseClick = {
                                bottomSheetState.navigateToPage(0, animationScope, lazyListState) { onPageChanged(it) }
                            },
                            onDeleteClick = {
                                bottomSheetState.navigateToPage(2, animationScope, lazyListState) { onPageChanged(it) }
                            },
                            onSaveClick = {
                                bottomSheetState.navigateToPage(3, animationScope, lazyListState) { onPageChanged(it) }
                            },
                            onLoadClick = {
                                bottomSheetState.navigateToPage(5, animationScope, lazyListState) { onPageChanged(it) }
                            }
                        )
                        // DELETE CONFIRMATION PAGE - INDEX 2
                        2 -> DeleteConfirmationPage(
                            screenWidth = screenWidth,
                            onCloseClick = {
                                bottomSheetState.navigateToPage(1, animationScope, lazyListState) { onPageChanged(it) }
                            },
                            onDeleteClick = {
                                bottomSheetState.navigateToPage(0, animationScope, lazyListState) { onPageChanged(it) }
                            }
                        )
                        // SAVE ROUTE PAGE - INDEX 3
                        3 -> SaveRoutePage(
                            screenWidth = screenWidth,
                            routeName = bottomSheetState.routeName,
                            onRouteNameChange = { bottomSheetState.routeName = it },
                            onBackClick = {
                                bottomSheetState.navigateToPage(1, animationScope, lazyListState) { onPageChanged(it) }
                            },
                            onSaveClick = {
                                if (bottomSheetState.routeName.isNotEmpty()) {
                                    bottomSheetState.navigateToPage(4, animationScope, lazyListState) { onPageChanged(it) }
                                }
                            }
                        )
                        // SAVE SUCCESS PAGE - INDEX 4
                        4 -> SaveSuccessPage(screenWidth = screenWidth)
                        // LOAD ROUTE PAGE - INDEX 5
                        5 -> LoadRoutePage(
                            screenWidth = screenWidth,
                            savedRoutes = bottomSheetState.savedRoutes,
                            selectedRouteIndex = bottomSheetState.selectedRouteIndex,
                            onRouteSelect = { bottomSheetState.selectedRouteIndex = it },
                            onBackClick = {
                                bottomSheetState.navigateToPage(1, animationScope, lazyListState) { onPageChanged(it) }
                            },
                            onLoadClick = {
                                bottomSheetState.navigateToPage(1, animationScope, lazyListState) { onPageChanged(it) }
                            }
                        )
                        // ADD LOCATIONS PAGE - INDEX 6
                        6 -> AddLocationsPage(
                            screenWidth = screenWidth,
                            searchQuery = bottomSheetState.searchQuery,
                            onSearchQueryChange = { bottomSheetState.searchQuery = it },
                            filteredLocations = bottomSheetState.getFilteredLocations(),
                            onLocationSelect = {
                                bottomSheetState.navigateToPage(0, animationScope, lazyListState) { onPageChanged(it) }
                            },
                            onCloseClick = {
                                bottomSheetState.navigateToPage(0, animationScope, lazyListState) { onPageChanged(it) }
                            }
                        )
                        // MAP STYLE PAGE - INDEX 7
                        7 -> MapStylePage(
                            screenWidth = screenWidth,
                            mapStyles = bottomSheetState.mapStyles,
                            selectedMapStyle = bottomSheetState.selectedMapStyle,
                            onMapStyleSelect = { bottomSheetState.selectedMapStyle = it },
                            onCloseClick = {
                                bottomSheetState.navigateToPage(0, animationScope, lazyListState) { onPageChanged(it) }
                            },
                            onApplyClick = {
                                bottomSheetState.navigateToPage(0, animationScope, lazyListState) { onPageChanged(it) }
                            }
                        )
                    }
                }
            }
        }
    }
}
//DRAG HANDLE
@Composable
fun DragHandle() {
    Box(
        modifier = Modifier
            .width(57.dp)
            .height(8.dp)

            .background(
                Color(0xFF35383F),
                shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
            )
    )
}