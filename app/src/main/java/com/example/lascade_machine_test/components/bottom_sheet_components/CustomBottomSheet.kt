package com.example.lascade_machine_test.components.bottom_sheet_components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.example.lascade_machine_test.R
import kotlinx.coroutines.launch

@Composable
fun CustomBottomSheet(
    onPageChanged: (Int) -> Unit = {},
    externalPageChange: Int = 0
) {
    var currentPage by remember { mutableStateOf(0) }
    var targetPage by remember { mutableStateOf(0) }
    var isAnimating by remember { mutableStateOf(false) }

    // Animation progress for smooth bounce effect
    var animationProgress by remember { mutableStateOf(0f) }

    // Smooth animated progress with bounce easing
    val animatedProgress by animateFloatAsState(
        targetValue = animationProgress,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium,
            visibilityThreshold = 0.01f
        ),
        label = "PageBounceAnimation"
    )

    // Create coroutine scope for smooth animation sequences
    val animationScope = rememberCoroutineScope()

    var routeName by remember { mutableStateOf("") }
    var selectedRouteIndex by remember { mutableStateOf(0) }
    var selectedMapStyle by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    var dragOffset by remember { mutableStateOf(0f) }
    val lazyListState = rememberLazyListState()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    // Function to perform smooth page transition with bounce
    suspend fun performPageTransition(newPage: Int, oldPage: Int, onComplete: (Int) -> Unit) {
        if (isAnimating) return

        isAnimating = true

        val direction = if (newPage > oldPage) 1f else -1f

        // Phase 1: Quick bounce out
        animationProgress = direction * 0.3f
        delay(150)

        // Phase 2: Navigate to new page
        lazyListState.scrollToItem(newPage)
        onComplete(newPage)

        // Phase 3: Bounce in from opposite direction
        animationProgress = -direction * 0.5f
        delay(100)

        // Phase 4: Settle to center
        animationProgress = 0f
        delay(300)

        isAnimating = false
    }

    // Handle external page changes (from app bar buttons)
    LaunchedEffect(externalPageChange) {
        if (externalPageChange != currentPage && externalPageChange != 0) {
            targetPage = externalPageChange
            performPageTransition(externalPageChange, currentPage) { newPage ->
                currentPage = newPage
            }
        }
    }

    // Sample routes data
    val savedRoutes = listOf(
        "Kochi - Los Angeles",
        "Dubai trip",
        "Kochi - Los Angeles"
    )

    // Sample locations data for search
    val locations = listOf(
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

    // Map styles data
    val mapStyles = listOf(
        "Classic" to R.drawable.map1,
        "Blueprint" to R.drawable.map2,
        "Night" to R.drawable.map4
    )

    // Filter locations based on search query
    val filteredLocations = if (searchQuery.isEmpty()) {
        locations
    } else {
        locations.filter {
            it.first.contains(searchQuery, ignoreCase = true) ||
                    it.second.contains(searchQuery, ignoreCase = true)
        }
    }

    // Enhanced page change handling
    LaunchedEffect(currentPage) {
        onPageChanged(currentPage)

        // Auto-dismiss success page with smooth transition
        if (currentPage == 4) {
            delay(2000)
            performPageTransition(0, currentPage) { newPage ->
                currentPage = newPage
            }
        }
    }

    // Calculate dynamic offset for drag gesture on add locations page
    val dynamicOffset = if (currentPage == 6 && dragOffset > 0) {
        (dragOffset * 0.6f).coerceAtMost(300f)
    } else 0f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = dynamicOffset.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .then(
                    if (currentPage == 6) {
                        Modifier.pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { dragOffset = 0f },
                                onDragEnd = {
                                    if (dragOffset > 150) {
                                        animationScope.launch {
                                            performPageTransition(0, currentPage) { newPage ->
                                                currentPage = newPage
                                            }
                                        }
                                    }
                                    dragOffset = 0f
                                }
                            ) { change, dragAmount ->
                                if (dragAmount.y > 0) {
                                    dragOffset += dragAmount.y * 0.4f
                                    dragOffset = dragOffset.coerceAtMost(400f)
                                }
                            }
                        }
                    } else Modifier
                )
        ) {
            // Drag handle
            Box(
                modifier = Modifier
                    .width(57.dp)
                    .height(8.dp)
                    .background(
                        Color(0xFF35383F),
                        shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                    )
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Content container with bounce animation applied to individual items
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(x = (animatedProgress * screenWidth.value * 0.2f).dp)
            ) {
                LazyRow(
                    state = lazyListState,
                    modifier = Modifier.fillMaxWidth(),
                    userScrollEnabled = false,
                    horizontalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    // Page 0: Default content
                    item {
                        DefaultContentPage(
                            screenWidth = screenWidth,
                            onMenuClick = {
                                animationScope.launch {
                                    performPageTransition(1, currentPage) { newPage ->
                                        currentPage = newPage
                                    }
                                }
                            },
                            onAddClick = {
                                animationScope.launch {
                                    performPageTransition(6, currentPage) { newPage ->
                                        currentPage = newPage
                                    }
                                }
                            }
                        )
                    }

                    // Page 1: Route settings
                    item {
                        RouteSettingsPage(
                            screenWidth = screenWidth,
                            onCloseClick = {
                                animationScope.launch {
                                    performPageTransition(0, currentPage) { newPage ->
                                        currentPage = newPage
                                    }
                                }
                            },
                            onDeleteClick = {
                                animationScope.launch {
                                    performPageTransition(2, currentPage) { newPage ->
                                        currentPage = newPage
                                    }
                                }
                            },
                            onSaveClick = {
                                animationScope.launch {
                                    performPageTransition(3, currentPage) { newPage ->
                                        currentPage = newPage
                                    }
                                }
                            },
                            onLoadClick = {
                                animationScope.launch {
                                    performPageTransition(5, currentPage) { newPage ->
                                        currentPage = newPage
                                    }
                                }
                            }
                        )
                    }

                    // Page 2: Delete confirmation
                    item {
                        DeleteConfirmationPage(
                            screenWidth = screenWidth,
                            onCloseClick = {
                                animationScope.launch {
                                    performPageTransition(1, currentPage) { newPage ->
                                        currentPage = newPage
                                    }
                                }
                            },
                            onDeleteClick = {
                                animationScope.launch {
                                    performPageTransition(0, currentPage) { newPage ->
                                        currentPage = newPage
                                    }
                                }
                            }
                        )
                    }

                    // Page 3: Save route
                    item {
                        SaveRoutePage(
                            screenWidth = screenWidth,
                            routeName = routeName,
                            onRouteNameChange = { routeName = it },
                            onBackClick = {
                                animationScope.launch {
                                    performPageTransition(1, currentPage) { newPage ->
                                        currentPage = newPage
                                    }
                                }
                            },
                            onSaveClick = {
                                if (routeName.isNotEmpty()) {
                                    animationScope.launch {
                                        performPageTransition(4, currentPage) { newPage ->
                                            currentPage = newPage
                                        }
                                    }
                                }
                            }
                        )
                    }

                    // Page 4: Save success
                    item {
                        SaveSuccessPage(screenWidth = screenWidth)
                    }

                    // Page 5: Load route
                    item {
                        LoadRoutePage(
                            screenWidth = screenWidth,
                            savedRoutes = savedRoutes,
                            selectedRouteIndex = selectedRouteIndex,
                            onRouteSelect = { selectedRouteIndex = it },
                            onBackClick = {
                                animationScope.launch {
                                    performPageTransition(1, currentPage) { newPage ->
                                        currentPage = newPage
                                    }
                                }
                            },
                            onLoadClick = {
                                animationScope.launch {
                                    performPageTransition(1, currentPage) { newPage ->
                                        currentPage = newPage
                                    }
                                }
                            }
                        )
                    }

                    // Page 6: Add locations
                    item {
                        AddLocationsPage(
                            screenWidth = screenWidth,
                            searchQuery = searchQuery,
                            onSearchQueryChange = { searchQuery = it },
                            filteredLocations = filteredLocations,
                            onLocationSelect = {
                                animationScope.launch {
                                    performPageTransition(0, currentPage) { newPage ->
                                        currentPage = newPage
                                    }
                                }
                            },
                            onCloseClick = {
                                animationScope.launch {
                                    performPageTransition(0, currentPage) { newPage ->
                                        currentPage = newPage
                                    }
                                }
                            }
                        )
                    }

                    // Page 7: Map Style Selection
                    item {
                        MapStylePage(
                            screenWidth = screenWidth,
                            mapStyles = mapStyles,
                            selectedMapStyle = selectedMapStyle,
                            onMapStyleSelect = { selectedMapStyle = it },
                            onCloseClick = {
                                animationScope.launch {
                                    performPageTransition(0, currentPage) { newPage ->
                                        currentPage = newPage
                                    }
                                }
                            },
                            onApplyClick = {
                                animationScope.launch {
                                    performPageTransition(0, currentPage) { newPage ->
                                        currentPage = newPage
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
@Composable
private fun DefaultContentPage(
    screenWidth: androidx.compose.ui.unit.Dp,
    onMenuClick: () -> Unit,
    onAddClick: () -> Unit
) {
    Column(
        modifier = Modifier.width(screenWidth - 32.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularIconButton(
                icon = Icons.Filled.MoreVert,
                contentDescription = "Menu",
                onClick = onMenuClick
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Kochi - Los Angeles",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "2 Points",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            CircularIconButton(
                icon = Icons.Filled.Add,
                contentDescription = "Add Location",
                onClick = onAddClick
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = { /* Handle Create Video */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(30.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Create video",
                    color = Color.Black,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Forward",
                    tint = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun RouteSettingsPage(
    screenWidth: androidx.compose.ui.unit.Dp,
    onCloseClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onSaveClick: () -> Unit,
    onLoadClick: () -> Unit
) {
    Column(
        modifier = Modifier.width(screenWidth - 32.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.background(color = Color.White, RoundedCornerShape(30.dp))) {
                IconButton(onClick = onCloseClick) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Route settings",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onDeleteClick,
                modifier = Modifier
                    .width(104.dp)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                ),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(
                    text = "Delete",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
            Button(
                onClick = onSaveClick,
                modifier = Modifier
                    .width(104.dp)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray
                ),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(
                    text = "Save",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
            Button(
                onClick = onLoadClick,
                modifier = Modifier
                    .width(104.dp)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray
                ),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(
                    text = "Load",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun DeleteConfirmationPage(
    screenWidth: androidx.compose.ui.unit.Dp,
    onCloseClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(screenWidth - 32.dp)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.background(color = Color.White, RoundedCornerShape(30.dp))) {
                IconButton(onClick = onCloseClick) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Delete Route",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "ARE YOU SURE?",
            color = Color.Gray,
            fontSize = 26.sp,
            fontWeight = FontWeight.Thin,
            letterSpacing = 1.5.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onDeleteClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            ),
            shape = RoundedCornerShape(30.dp)
        ) {
            Text(
                text = "Delete",
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun SaveRoutePage(
    screenWidth: androidx.compose.ui.unit.Dp,
    routeName: String,
    onRouteNameChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(screenWidth - 32.dp)
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.background(color = Color.White, RoundedCornerShape(30.dp))) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Save route",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color(0xFF2B2C2F), RoundedCornerShape(30.dp))
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            BasicTextField(
                value = routeName,
                onValueChange = onRouteNameChange,
                textStyle = TextStyle(
                    color = if (routeName.isEmpty()) Color.Gray else Color.White,
                    fontSize = 16.sp
                ),
                cursorBrush = SolidColor(Color.White),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            ) { innerTextField ->
                if (routeName.isEmpty()) {
                    Text(
                        text = "Enter route name",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
                innerTextField()
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onSaveClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFB97FFF)
            ),
            shape = RoundedCornerShape(30.dp)
        ) {
            Text(
                text = "Save",
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun SaveSuccessPage(
    screenWidth: androidx.compose.ui.unit.Dp
) {
    Column(
        modifier = Modifier
            .width(screenWidth - 32.dp)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "✓",
            color = Color.Green,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "ROUTE SAVED",
            color = Color.Gray,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 2.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun LoadRoutePage(
    screenWidth: androidx.compose.ui.unit.Dp,
    savedRoutes: List<String>,
    selectedRouteIndex: Int,
    onRouteSelect: (Int) -> Unit,
    onBackClick: () -> Unit,
    onLoadClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(screenWidth - 32.dp)
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.background(color = Color.White, RoundedCornerShape(30.dp))) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Load route",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        savedRoutes.forEachIndexed { index, routeName ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(Color(0xFF2B2C2F), RoundedCornerShape(30.dp))
                    .clickable { onRouteSelect(index) }
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = routeName,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(
                            if (selectedRouteIndex == index) Color.Black.copy(alpha = 0.4f) else Color.Transparent,
                            CircleShape
                        ).border(
                            width = 2.dp,
                            color = Color.Black.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(30.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedRouteIndex == index) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Selected",
                            tint = Color(0xFFB97FFF),
                            modifier = Modifier.size(16.dp)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(Color.Transparent, CircleShape)
                                .background(Color(0xFF4A4A4A), CircleShape)
                        )
                    }
                }
            }

            if (index < savedRoutes.size - 1) {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onLoadClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFB97FFF)
            ),
            shape = RoundedCornerShape(30.dp)
        ) {
            Text(
                text = "Load",
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun AddLocationsPage(
    screenWidth: androidx.compose.ui.unit.Dp,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    filteredLocations: List<Pair<String, String>>,
    onLocationSelect: () -> Unit,
    onCloseClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(screenWidth - 32.dp)
            .wrapContentHeight()
            .padding(vertical = 8.dp)
            .background(Color.Black)
    ) {
        // Header with close functionality
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Close button (invisible but clickable for better UX)
            IconButton(
                onClick = onCloseClick,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close",
                    tint = Color.Transparent,
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(
                text = "Add locations",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            // Spacer for symmetry
            Spacer(modifier = Modifier.size(24.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Enhanced search bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color(0xFF2B2C2F), RoundedCornerShape(30.dp))
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = "Location",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            BasicTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp
                ),
                cursorBrush = SolidColor(Color.White),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            ) { innerTextField ->
                if (searchQuery.isEmpty()) {
                    Text(
                        text = "Enter Point",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
                innerTextField()
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Enhanced location list with better visual feedback
        filteredLocations.take(6).forEachIndexed { index, (locationName, locationDesc) ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onLocationSelect()
                    }
                    .padding(vertical = 16.dp)
                    .padding(horizontal = 4.dp)
            ) {
                Text(
                    text = locationName,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = locationDesc,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

            if (index < filteredLocations.take(6).size - 1) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFF2B2C2F))
                )
            }
        }

        // Additional spacing for better scrolling experience
        Spacer(modifier = Modifier.height(300.dp))
    }
}

@Composable
private fun MapStylePage(
    screenWidth: androidx.compose.ui.unit.Dp,
    mapStyles: List<Pair<String, Int>>,
    selectedMapStyle: Int,
    onMapStyleSelect: (Int) -> Unit,
    onCloseClick: () -> Unit,
    onApplyClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(screenWidth - 32.dp)
            .padding(vertical = 8.dp)
    ) {
        // Header with icon and title
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Map Style",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Map style",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Map style options
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            mapStyles.forEachIndexed { index, (styleName, drawableId) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { onMapStyleSelect(index) }
                ) {
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(80.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .border(
                                width = if (selectedMapStyle == index) 3.dp else 0.dp,
                                color = Color(0xFFB97FFF),
                                shape = RoundedCornerShape(20.dp)
                            )
                    ) {
                        Image(
                            painter = painterResource(id = drawableId),
                            contentDescription = styleName,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = styleName,
                        color = if (selectedMapStyle == index) Color.White else Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = if (selectedMapStyle == index) FontWeight.Medium else FontWeight.Normal
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Close button (X)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color(0xFF2B2C2F), CircleShape)
                    .clickable { onCloseClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Apply button
            Button(
                onClick = onApplyClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB97FFF)
                ),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(
                    text = "Apply",
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

