package com.example.beautyclub.ui.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.beautyclub.navigation.Screen
import com.example.beautyclub.ui.theme.*
import com.example.beautyclub.viewmodel.TransactionViewModel

@Composable
fun TransactionSuccessScreen(
    navController: NavHostController,
    pointEarned: Int,
    totalPoints: Int,
    transactionViewModel: TransactionViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        // ── Dekorasi confetti (kotak-kotak kecil) ──────────────
        ConfettiDecoration()

        Column(
            modifier              = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment   = Alignment.CenterHorizontally,
            verticalArrangement   = Arrangement.Center
        ) {

            // ── Ikon Centang ────────────────────────────────────
            Box(
                modifier         = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(PrimaryDark),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = Icons.Outlined.Check,
                    contentDescription = "Sukses",
                    tint               = Color.White,
                    modifier           = Modifier.size(52.dp)
                )
            }

            Spacer(Modifier.height(28.dp))

            Text(
                text       = "Transaction\nSuccessful",
                fontSize   = 28.sp,
                fontWeight = FontWeight.Bold,
                color      = PrimaryDark,
                textAlign  = TextAlign.Center,
                lineHeight = 36.sp
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text      = "Thank you for entrusting your beauty\nto Beauty Club",
                fontSize  = 13.sp,
                color     = TextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(36.dp))

            // ── Card info poin ──────────────────────────────────
            Card(
                modifier  = Modifier.fillMaxWidth(),
                shape     = RoundedCornerShape(20.dp),
                colors    = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
                    SuccessInfoRow(
                        label = "Points Earned",
                        value = "+ $pointEarned Poin",
                        valueColor = Primary
                    )
                    HorizontalDivider(color = Primary.copy(alpha = 0.10f))
                    SuccessInfoRow(
                        label      = "Latest total points",
                        value      = "✦ $totalPoints",
                        valueColor = Primary
                    )
                }
            }

            Spacer(Modifier.height(40.dp))

            // ── Tombol OK → kembali ke Home ─────────────────────
            Button(
                onClick  = {
                    transactionViewModel.resetAddState()
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route)
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape  = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryDark)
            ) {
                Text(
                    text       = "OK",
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color      = Color.White
                )
            }
        }
    }
}

@Composable
private fun SuccessInfoRow(label: String, value: String, valueColor: Color) {
    Row(
        modifier              = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 14.sp, color = TextSecondary)
        Text(
            text       = value,
            fontSize   = 16.sp,
            fontWeight = FontWeight.Bold,
            color      = valueColor
        )
    }
}

// ── Dekorasi confetti sederhana pakai Box ─────────────────────────────
@Composable
private fun ConfettiDecoration() {
    val confettiItems = listOf(
        Triple(30.dp, 60.dp, Primary.copy(alpha = 0.7f)),
        Triple(80.dp, 40.dp, Accent.copy(alpha = 0.8f)),
        Triple(280.dp, 50.dp, Secondary.copy(alpha = 0.6f)),
        Triple(320.dp, 80.dp, Primary.copy(alpha = 0.5f)),
        Triple(60.dp, 100.dp, Accent.copy(alpha = 0.5f)),
        Triple(300.dp, 30.dp, Primary.copy(alpha = 0.9f)),
        Triple(200.dp, 20.dp, Secondary.copy(alpha = 0.7f)),
        Triple(160.dp, 70.dp, Accent.copy(alpha = 0.4f)),
    )
    Box(modifier = Modifier.fillMaxSize()) {
        confettiItems.forEach { (x, y, color) ->
            Box(
                modifier = Modifier
                    .offset(x = x, y = y)
                    .size(10.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(color)
            )
        }
    }
}