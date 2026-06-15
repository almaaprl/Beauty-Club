package com.example.beautyclub.ui.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.beautyclub.data.TreatmentCategory
import com.example.beautyclub.data.local.entity.TransactionEntity
import com.example.beautyclub.navigation.Screen
import com.example.beautyclub.ui.home.BottomNavBar
import com.example.beautyclub.ui.theme.*
import com.example.beautyclub.viewmodel.TransactionViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun TransactionScreen(
    navController: NavHostController,
    transactionViewModel: TransactionViewModel,
    memberId: Int
) {
    LaunchedEffect(memberId) {
        transactionViewModel.loadTransactions(memberId)
    }
    val transactions by transactionViewModel.transactions.collectAsState()

    // Filter tabs: All / Purchases / Rewards
    var selectedFilter by remember { mutableStateOf(0) }
    val filterLabels = listOf("All", "Purchases", "Rewards")

    val filtered = when (selectedFilter) {
        1 -> transactions.filter { it.pointEarned >= 0 }  // Purchases (earn poin)
        2 -> transactions.filter { it.pointEarned < 0 }   // Rewards (redeem poin)
        else -> transactions
    }

    Scaffold(
        containerColor = Background,
        bottomBar      = { BottomNavBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Background)
        ) {
            // ── Header ───────────────────────────────────────────
            Box(
                modifier         = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text       = "Transaction",
                    fontSize   = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color      = Primary
                )
            }

            // ── Filter Chips ─────────────────────────────────────
            Row(
                modifier              = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filterLabels.forEachIndexed { index, label ->
                    val selected = selectedFilter == index
                    FilterChip(
                        selected = selected,
                        onClick  = { selectedFilter = index },
                        label    = {
                            Text(
                                text       = label,
                                fontSize   = 13.sp,
                                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                            )
                        },
                        shape  = RoundedCornerShape(20.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor    = Primary,
                            selectedLabelColor        = Color.White,
                            containerColor            = Primary.copy(alpha = 0.10f),
                            labelColor                = TextSecondary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled          = true,
                            selected         = selected,
                            borderColor      = Color.Transparent,
                            selectedBorderColor = Color.Transparent
                        )
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // ── Section title ─────────────────────────────────────
            Text(
                text       = "Recent Activity",
                fontSize   = 16.sp,
                fontWeight = FontWeight.Bold,
                color      = TextPrimary,
                modifier   = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(Modifier.height(12.dp))

            // ── List transaksi ────────────────────────────────────
            if (filtered.isEmpty()) {
                Box(
                    modifier         = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector        = Icons.Outlined.Receipt,
                            contentDescription = null,
                            tint               = Primary.copy(alpha = 0.35f),
                            modifier           = Modifier.size(48.dp)
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text     = "Belum ada transaksi",
                            fontSize = 14.sp,
                            color    = TextSecondary
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier            = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding      = PaddingValues(bottom = 16.dp)
                ) {
                    items(filtered) { tx ->
                        TransactionItem(tx)
                    }
                }
            }

            // ── Tombol Add Transaction ────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Button(
                    onClick = {
                        navController.navigate(
                            Screen.AddTransaction.createRoute(memberId)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape  = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Text(
                        text       = "+ Add Transaction",
                        fontSize   = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = Color.White
                    )
                }
            }
        }
    }
}

// ── Item card transaksi ────────────────────────────────────────────────
@Composable
fun TransactionItem(tx: TransactionEntity) {
    val isEarned   = tx.pointEarned >= 0
    val pointColor = if (isEarned) Color(0xFF2E7D32) else Color(0xFFC62828)
    val pointText  = if (isEarned) "+${tx.pointEarned} pts" else "${tx.pointEarned} pts"
    val priceText  = "Rp" + NumberFormat.getNumberInstance(Locale("id", "ID"))
        .format(tx.amount.toLong())

    Card(
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(14.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text       = tx.treatmentName,
                    fontSize   = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = TextPrimary
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text     = tx.date,
                    fontSize = 12.sp,
                    color    = TextSecondary
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text       = priceText,
                    fontSize   = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = TextPrimary
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text       = pointText,
                    fontSize   = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color      = pointColor
                )
            }
        }
    }
}
