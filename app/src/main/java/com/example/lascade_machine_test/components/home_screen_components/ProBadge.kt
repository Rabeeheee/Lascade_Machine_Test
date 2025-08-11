package com.example.lascade_machine_test.components.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProBadge(
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(87.dp)
            .height(46.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Black)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val path = Path().apply {
                moveTo(size.width * 0.6f, 0f)
                lineTo(size.width, 0f)
                lineTo(size.width, size.height)
                lineTo(size.width * 0.6f, size.height)
                lineTo(size.width * 0.8f, size.height * 0.5f)
                close()
                fillType = PathFillType.EvenOdd
            }
            drawPath(path, Color(0xFF8A2BE2), style = Fill)
        }

        // Icon + Text
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "PRO Icon",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = "PRO",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}