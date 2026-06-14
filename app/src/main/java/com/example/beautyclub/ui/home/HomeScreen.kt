
package com.example.beautyclub.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CardMembership
import androidx.compose.material.icons.outlined.Redeem
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.beautyclub.navigation.Screen
import com.example.beautyclub.ui.component.BeautyCard
import com.example.beautyclub.ui.component.TransactionCard
import com.example.beautyclub.ui.theme.*

data class RecentTransaction(
    val treatmentName: String,
    val date: String,
    val points: Int
)

val sampleTransactions = listOf(
    RecentTransaction("Facial Gold + LED",         "Jun 10, 2026", +15),
    RecentTransaction("Facial Korean Brightening", "Jun 07, 2026", +15),
    RecentTransaction("SKINTIFIC Moist",           "Jun 03, 2026", -15)
)

@Composable
fun HomeScreen(
    navController: NavHostController,
    userName: String = "Rosi",
    totalPoints: Int = 450,
    recentTransactions: List<RecentTransaction> = sampleTransactions
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {
        // ── Header ───────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Primary.copy(alpha = 0.06f), Background)
                    )
                )
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {
            Row(
                modifier              = Modifier.fillMaxWidth(),
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text       = "MyGlow Beauty",
                        fontSize   = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color      = Secondary
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text       = "Halo, $userName 👋",
                        fontSize   = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color      = TextPrimary
                    )
                    Text(
                        text     = "Welcome to MyGlow Beauty Area",
                        fontSize = 13.sp,
                        color    = TextSecondary
                    )
                }

                // Avatar
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Secondary.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text       = userName.first().uppercaseChar().toString(),
                        fontSize   = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color      = Primary
                    )
                }
            }
        }

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {

            // ── Total Points Card ─────────────────────────────────────
            Card(
                modifier  = Modifier.fillMaxWidth(),
                shape     = RoundedCornerShape(24.dp),
                colors    = CardDefaults.cardColors(containerColor = PrimaryDark),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)) {
                    Text(
                        text          = "TOTAL POINTS",
                        fontSize      = 11.sp,
                        color         = TextOnPrimary.copy(alpha = 0.75f),
                        fontWeight    = FontWeight.Medium,
                        letterSpacing = androidx.compose.ui.unit.TextUnit(
                            1.5f,
                            androidx.compose.ui.unit.TextUnitType.Sp
                        )
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text       = totalPoints.toString(),
                        fontSize   = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color      = TextOnPrimary
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // ── Quick Actions ─────────────────────────────────────────
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BeautyCard(
                    label           = "MY CARD",
                    icon            = Icons.Outlined.CardMembership,
                    backgroundColor = Secondary,
                    onClick         = { navController.navigate(Screen.MyCard.route) },
                    modifier        = Modifier.weight(1f)
                )
                BeautyCard(
                    label           = "REWARDS",
                    icon            = Icons.Outlined.Redeem,
                    backgroundColor = Accent,
                    onClick         = { navController.navigate(Screen.Reward.route) },
                    modifier        = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(28.dp))

            // ── Recent Activity ───────────────────────────────────────
            Row(
                modifier              = Modifier.fillMaxWidth(),
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text       = "Recent Activity",
                    fontSize   = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color      = TextPrimary
                )
                TextButton(onClick = { navController.navigate(Screen.Transaction.route) }) {
                    Text(
                        text       = "View All",
                        fontSize   = 13.sp,
                        color      = Primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            if (recentTransactions.isEmpty()) {
                Box(
                    modifier         = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Belum ada transaksi", fontSize = 14.sp, color = TextSecondary)
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    recentTransactions.take(3).forEach { tx ->
                        TransactionCard(
                            treatmentName = tx.treatmentName,
                            date          = tx.date,
                            points        = tx.points
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MyGlowBeautyTheme {
        HomeScreen(rememberNavController())
    }
}