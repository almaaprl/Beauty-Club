package com.example.beautyclub.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CardMembership
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Redeem
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.beautyclub.data.local.entity.TransactionEntity
import com.example.beautyclub.navigation.Screen
import com.example.beautyclub.ui.component.BeautyCard
import com.example.beautyclub.ui.theme.*
import com.example.beautyclub.viewmodel.HomeViewModel
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.foundation.clickable
import java.text.NumberFormat
import java.util.Locale

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel
) {
    val member       by homeViewModel.member.collectAsState()
    val transactions by homeViewModel.transactions.collectAsState()

    val userName    = member?.name ?: "User"
    val totalPoints = member?.points ?: 0
    val memberId = member?.id ?: 0

    Scaffold(
        containerColor = Background,
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Background)
                .verticalScroll(rememberScrollState())
        ) {
            // ── Header ─────────────────────────────────────────────
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
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text       = "Halo, $userName",
                            fontSize   = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color      = TextPrimary
                        )
                        Spacer(Modifier.height(5.dp))
                        Text(
                            text     = "Welcome to GlowMe Beauty Area",
                            fontSize = 13.sp,
                            color    = TextSecondary
                        )
                    }


                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Secondary.copy(alpha = 0.18f))
                            .clickable {
                                navController.navigate(Screen.Profile.route) {
                                    popUpTo(Screen.Home.route) { saveState = true }
                                    launchSingleTop = true
                                    restoreState    = true
                                }
                            },
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

                // ── Total Points Card ───────────────────────────────
                Card(
                    modifier  = Modifier.fillMaxWidth(),
                    shape     = RoundedCornerShape(20.dp),
                    colors    = CardDefaults.cardColors(containerColor = PrimaryDark),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        // Dekorasi lingkaran
                        Box(
                            modifier = Modifier
                                .size(140.dp)
                                .align(Alignment.CenterEnd)
                                .offset(x = 30.dp)
                                .clip(CircleShape)
                                .background(Primary.copy(alpha = 0.25f))
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.CenterEnd)
                                .offset(x = 10.dp, y = (-20).dp)
                                .clip(CircleShape)
                                .background(Primary.copy(alpha = 0.15f))
                        )

                        Row(
                            modifier          = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp, vertical = 20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text          = "TOTAL POINTS",
                                    fontSize      = 11.sp,
                                    color         = TextOnPrimary.copy(alpha = 0.75f),
                                    fontWeight    = FontWeight.Medium,
                                    letterSpacing = 1.5.sp
                                )
                                Spacer(Modifier.height(6.dp))
                                Text(
                                    text       = totalPoints.toString(),
                                    fontSize   = 52.sp,
                                    fontWeight = FontWeight.Bold,
                                    color      = TextOnPrimary,
                                    lineHeight = 56.sp
                                )
                            }

                            // ── Icon coin ────────────────────────────────
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector        = Icons.Outlined.MonetizationOn,  // icon coin
                                    contentDescription = null,
                                    tint               = Color.White,
                                    modifier           = Modifier.size(30.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // ── Quick Action Buttons ────────────────────────────
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

                // ── Recent Activity ─────────────────────────────────
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

                if (transactions.isEmpty()) {
                    // Empty state
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape    = RoundedCornerShape(16.dp),
                        colors   = CardDefaults.cardColors(
                            containerColor = Primary.copy(alpha = 0.06f)
                        ),
                        elevation = CardDefaults.cardElevation(0.dp)
                    ) {
                        Box(
                            modifier         = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 36.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector        = Icons.Outlined.Receipt,
                                    contentDescription = null,
                                    tint               = Primary.copy(alpha = 0.4f),
                                    modifier           = Modifier.size(36.dp)
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    text     = "No transactions",
                                    fontSize = 14.sp,
                                    color    = TextSecondary
                                )
                            }
                        }
                    }
                } else {
                    // List transaksi — ambil 3 terbaru dari DB
                    Card(
                        modifier  = Modifier.fillMaxWidth(),
                        shape     = RoundedCornerShape(16.dp),
                        colors    = CardDefaults.cardColors(
                            containerColor = Primary.copy(alpha = 0.06f)
                        ),
                        elevation = CardDefaults.cardElevation(0.dp)
                    ) {
                        Column(modifier = Modifier.padding(vertical = 4.dp)) {
                            transactions.take(3).forEachIndexed { index, tx ->
                                TransactionRow(transaction = tx)
                                if (index < minOf(transactions.size, 3) - 1) {
                                    HorizontalDivider(
                                        modifier  = Modifier.padding(horizontal = 16.dp),
                                        thickness = 0.5.dp,
                                        color     = Primary.copy(alpha = 0.12f)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

// ── Baris transaksi individual ─────────────────────────────────────────
@Composable
private fun TransactionRow(transaction: TransactionEntity) {
    val isEarned  = transaction.pointEarned >= 0
    val pointText = if (isEarned) "+${transaction.pointEarned} pts"
    else "${transaction.pointEarned} pts"
    val pointColor = if (isEarned) Color(0xFF2E7D32) else Color(0xFFC62828)

    Row(
        modifier              = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text       = transaction.treatmentName,
                fontSize   = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color      = TextPrimary
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text     = transaction.date,
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

// ── Bottom Navigation Bar ──────────────────────────────────────────────
@Composable
fun BottomNavBar(navController: NavHostController) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp,
        modifier        = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
    ) {
        data class NavItem(val label: String, val icon: ImageVector, val route: String)

        val items = listOf(
            NavItem("Home",        Icons.Outlined.Home,       Screen.Home.route),
            NavItem("Transaction",   Icons.Outlined.Receipt,       Screen.Transaction.route),
            NavItem("Profile",      Icons.Outlined.Person,        Screen.Profile.route)
        )

        val currentRoute = navController.currentBackStackEntry?.destination?.route

        items.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                selected = selected,
                onClick  = {
                    if (!selected) {
                        navController.navigate(item.route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState    = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector        = item.icon,
                        contentDescription = item.label,
                        modifier           = Modifier.size(22.dp)
                    )
                },
                label  = { Text(item.label, fontSize = 11.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor   = Primary,
                    selectedTextColor   = Primary,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary,
                    indicatorColor      = Primary.copy(alpha = 0.12f)
                )
            )
        }
    }
}