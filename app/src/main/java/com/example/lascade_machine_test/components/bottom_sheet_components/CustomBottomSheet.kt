package com.example.lascade_machine_test.components.bottom_sheet_components

import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun CustomBottomSheet(
    onPageChanged: (Int) -> Unit = {}
) {
    var currentPage by remember { mutableStateOf(0) }
    var routeName by remember { mutableStateOf("") }
    var selectedRouteIndex by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    var dragOffset by remember { mutableStateOf(0f) }
    val lazyListState = rememberLazyListState()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

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
        "Dubai DXB" to "Dubai, United Arab Emirates"
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

    // Handle page changes with proper timing
    LaunchedEffect(currentPage) {
        onPageChanged(currentPage)
        lazyListState.animateScrollToItem(index = currentPage)

        if (currentPage == 4) {
            delay(2000)
            currentPage = 0
        }
    }

    // Calculate dynamic offset based on drag - only move the content, not create white space
    val dynamicOffset = if (currentPage == 6 && dragOffset > 0) {
        (dragOffset * 0.8f).coerceAtMost(400f) // Reduced maximum offset to prevent excessive movement
    } else 0f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = dynamicOffset.dp) // Apply offset only to content
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black) // Consistent black background
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .then(
                    if (currentPage == 6) {
                        Modifier.pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { dragOffset = 0f },
                                onDragEnd = {
                                    // Reduced threshold for easier dismissal
                                    if (dragOffset > 200) {
                                        currentPage = 0
                                    }
                                    dragOffset = 0f
                                }
                            ) { change, dragAmount ->
                                if (dragAmount.y > 0) {
                                    dragOffset += dragAmount.y * 0.5f // Reduced sensitivity
                                    dragOffset = dragOffset.coerceAtMost(500f) // Reduced maximum
                                }
                            }
                        }
                    } else Modifier
                )
        ) {
            // Drag handle at the top center
            Box(
                modifier = Modifier
                    .width(57.dp)
                    .height(8.dp)
                    .background(Color(0xFF35383F), shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // LazyRow with seven pages
            LazyRow(
                state = lazyListState,
                modifier = Modifier.fillMaxWidth(),
                userScrollEnabled = false
            ) {
                // Default content page (index 0)
                item {
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
                                onClick = { currentPage = 1 }
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
                                contentDescription = "Add",
                                onClick = { currentPage = 6 }
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

                // Route settings content page (index 1)
                item {
                    Column(
                        modifier = Modifier.width(screenWidth - 32.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(modifier = Modifier.background(color = Color.White, RoundedCornerShape(30.dp))) {
                                IconButton(onClick = { currentPage = 0 }) {
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
                                onClick = { currentPage = 2 },
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
                                onClick = { currentPage = 3 },
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
                                onClick = { currentPage = 5 },
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

                // Delete confirmation page (index 2)
                item {
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
                                IconButton(onClick = { currentPage = 1 }) {
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
                            onClick = {
                                currentPage = 0
                            },
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

                // Save route page (index 3)
                item {
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
                                IconButton(onClick = { currentPage = 1 }) {
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
                                onValueChange = { routeName = it },
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
                            onClick = {
                                if (routeName.isNotEmpty())
                                    currentPage = 4
                            },
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

                // Save success page (index 4)
                item {
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

                // Load route page (index 5)
                item {
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
                                IconButton(onClick = { currentPage = 1 }) {
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
                                    .clickable { selectedRouteIndex = index }
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
                            onClick = {
                                currentPage = 1
                            },
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

                // Add locations page (index 6)
                item {
                    Column(
                        modifier = Modifier
                            .width(screenWidth - 32.dp)
                            .wrapContentHeight() // Changed from fillMaxHeight to wrapContentHeight
                            .padding(vertical = 8.dp)
                            .background(Color.Black) // Ensure black background
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Add locations",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

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
                                onValueChange = { searchQuery = it },
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

                        filteredLocations.take(4).forEach { (locationName, locationDesc) ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        currentPage = 0
                                    }
                                    .padding(vertical = 12.dp)
                            ) {
                                Text(
                                    text = locationName,
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = locationDesc,
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                            }

                            if (locationName != filteredLocations.take(4).last().first) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(Color(0xFF2B2C2F))
                                )
                            }
                        }

                        // Add fixed height spacer to ensure consistent bottom sheet height
                        Spacer(modifier = Modifier.height(400.dp))
                    }
                }
            }
        }
    }
}