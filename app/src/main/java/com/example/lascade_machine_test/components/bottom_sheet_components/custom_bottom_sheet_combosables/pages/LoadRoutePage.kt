package com.example.lascade_machine_test.components.bottom_sheet_components.custom_bottom_sheet_combosables.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lascade_machine_test.components.bottom_sheet_components.custom_bottom_sheet_combosables.PageHeaderWithBack

@Composable
fun LoadRoutePage(
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
        PageHeaderWithBack(
            title = "Load route",
            onBackClick = onBackClick
        )

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