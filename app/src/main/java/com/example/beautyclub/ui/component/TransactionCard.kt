package com.example.beautyclub.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beautyclub.ui.theme.*

@Composable
fun TransactionCard(
    treatmentName: String,
    date: String,
    points: Int,
    modifier: Modifier = Modifier
) {
    val isPositive = points >= 0
    val pointColor = if (isPositive) PointGreen else PointRed
    val pointText  = if (isPositive) "+$points pts" else "$points pts"

    Card(
        modifier  = modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment    = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text       = treatmentName,
                    fontSize   = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = TextPrimary
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text     = date,
                    fontSize = 12.sp,
                    color    = TextSecondary
                )
            }
            Text(
                text       = pointText,
                fontSize   = 14.sp,
                fontWeight = FontWeight.Bold,
                color      = pointColor
            )
        }
    }
}