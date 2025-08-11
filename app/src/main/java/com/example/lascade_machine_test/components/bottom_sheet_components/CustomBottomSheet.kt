package com.example.lascade_machine_test.components.bottom_sheet_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomBottomSheet() {
    var isMenuOpen by remember { mutableStateOf(false) }
    val lazyListState = rememberLazyListState()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    // Animate to the correct position when menu state changes
    LaunchedEffect(isMenuOpen) {
        if (isMenuOpen) {
            lazyListState.animateScrollToItem(1) // Scroll to second item (route settings)
        } else {
            lazyListState.animateScrollToItem(0) // Scroll to first item (default content)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
//        Spacer(modifier = Modifier.height(5.dp))
        // Drag handle at the top center
        Box(
            modifier = Modifier
                .width(57.dp)
                .height(8.dp)
                .background(Color(0xFF35383F), shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // LazyRow with two pages
        LazyRow(
            state = lazyListState,
            modifier = Modifier.fillMaxWidth(),
            userScrollEnabled = false // Disable manual scrolling
        ) {
            // Default content page
            item {
                Column(
                    modifier = Modifier.width(screenWidth - 32.dp) // Full width minus padding
                ) {
                    // Top row with three dots, title, and plus icon
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
                            onClick = { isMenuOpen = true }
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
                            onClick = { /* Handle add click */ }
                        )
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    // Create video button
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

            // Route settings content page
            item {
                Column(
                    modifier = Modifier.width(screenWidth - 32.dp) // Full width minus padding
                ) {
                    // Route settings header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box (modifier = Modifier.background(color = Color.White,RoundedCornerShape(30.dp))){
                            IconButton(onClick = { isMenuOpen = false }) {
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

                    // Action buttons row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { /* Handle Delete */ },
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
                            onClick = { /* Handle Save */ },
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
                            onClick = { /* Handle Load */ },
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
        }
    }
}