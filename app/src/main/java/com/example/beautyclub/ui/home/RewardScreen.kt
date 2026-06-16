package com.example.beautyclub.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.CardGiftcard
import androidx.compose.material.icons.outlined.Redeem
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.beautyclub.data.Reward
import com.example.beautyclub.data.RewardData
import com.example.beautyclub.ui.theme.*
import com.example.beautyclub.viewmodel.HomeViewModel

@Composable
fun RewardScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel
) {
    val member by homeViewModel.member.collectAsState()
    val currentPoints = member?.points ?: 0

    // Dialog konfirmasi redeem
    var redeemTarget by remember { mutableStateOf<Reward?>(null) }
    var showSuccess  by remember { mutableStateOf<Reward?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // ── Top Bar ──────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 8.dp)
            ) {
                IconButton(
                    onClick  = { navController.popBackStack() },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 8.dp)
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector        = Icons.Outlined.ArrowBackIosNew,
                        contentDescription = "Kembali",
                        tint               = Primary,
                        modifier           = Modifier.size(18.dp)
                    )
                }
                Text(
                    text       = "Rewards",
                    fontSize   = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color      = Primary,
                    modifier   = Modifier.align(Alignment.Center)
                )
            }

            // ── Points Balance Card ───────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(PrimaryDark, Primary)
                        )
                    )
            ) {
                // Dekorasi lingkaran
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.CenterEnd)
                        .offset(x = 30.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.07f))
                )
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.CenterEnd)
                        .offset(x = 10.dp, y = (-20).dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.05f))
                )

                Row(
                    modifier          = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text          = "CURRENT BALANCE",
                            fontSize      = 11.sp,
                            color         = TextOnPrimary.copy(alpha = 0.75f),
                            fontWeight    = FontWeight.Medium,
                            letterSpacing = 1.5.sp
                        )
                        Spacer(Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text       = currentPoints.toString(),
                                fontSize   = 44.sp,
                                fontWeight = FontWeight.Bold,
                                color      = TextOnPrimary,
                                lineHeight = 48.sp
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text       = "points",
                                fontSize   = 14.sp,
                                color      = TextOnPrimary.copy(alpha = 0.75f),
                                modifier   = Modifier.padding(bottom = 6.dp)
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector        = Icons.Outlined.Redeem,
                            contentDescription = null,
                            tint               = Color.White,
                            modifier           = Modifier.size(26.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // ── Section title ─────────────────────────────────────
            Text(
                text       = "Available Rewards",
                fontSize   = 16.sp,
                fontWeight = FontWeight.Bold,
                color      = TextPrimary,
                modifier   = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(Modifier.height(12.dp))

            // ── Reward List ───────────────────────────────────────
            LazyColumn(
                modifier            = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding      = PaddingValues(bottom = 24.dp)
            ) {
                items(RewardData.list) { reward ->
                    RewardItem(
                        reward        = reward,
                        currentPoints = currentPoints,
                        onRedeem      = { redeemTarget = reward }
                    )
                }
            }
        }
    }

    // ── Dialog Konfirmasi Redeem ──────────────────────────────────
    redeemTarget?.let { reward ->
        AlertDialog(
            onDismissRequest = { redeemTarget = null },
            containerColor   = Surface,
            shape            = RoundedCornerShape(20.dp),
            icon = {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(Primary.copy(alpha = 0.10f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector        = Icons.Outlined.CardGiftcard,
                        contentDescription = null,
                        tint               = Primary,
                        modifier           = Modifier.size(28.dp)
                    )
                }
            },
            title = {
                Text(
                    text       = "Redeem Reward?",
                    fontWeight = FontWeight.Bold,
                    fontSize   = 18.sp,
                    color      = TextPrimary,
                    textAlign  = TextAlign.Center,
                    modifier   = Modifier.fillMaxWidth()
                )
            },
            text = {
                Column(
                    modifier            = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text      = reward.name,
                        fontSize  = 14.sp,
                        color     = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Primary.copy(alpha = 0.08f))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text       = "${reward.pointCost} points will be deducted",
                            fontSize   = 13.sp,
                            color      = Primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Kurangi poin member
                        member?.let { m ->
                            homeViewModel.redeemReward(m, rewardName = reward.name, reward.pointCost)
                        }
                        showSuccess  = reward
                        redeemTarget = null
                    },
                    shape  = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Text(
                        text       = "Redeem",
                        fontWeight = FontWeight.SemiBold,
                        color      = Color.White
                    )
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { redeemTarget = null },
                    shape   = RoundedCornerShape(12.dp),
                    colors  = ButtonDefaults.outlinedButtonColors(contentColor = Primary)
                ) {
                    Text("Cancel", fontWeight = FontWeight.Medium)
                }
            }
        )
    }

    // ── Dialog Sukses ─────────────────────────────────────────────
    showSuccess?.let { reward ->
        AlertDialog(
            onDismissRequest = { showSuccess = null },
            containerColor   = Surface,
            shape            = RoundedCornerShape(20.dp),
            icon = {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2E7D32).copy(alpha = 0.10f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "✓", fontSize = 24.sp, color = Color(0xFF2E7D32))
                }
            },
            title = {
                Text(
                    text       = "Successfully Exchanged!",
                    fontWeight = FontWeight.Bold,
                    fontSize   = 18.sp,
                    color      = TextPrimary,
                    textAlign  = TextAlign.Center,
                    modifier   = Modifier.fillMaxWidth()
                )
            },
            text = {
                Text(
                    text      = "${reward.name} Successfully redeemed. Please collect your reward at the cashier",
                    fontSize  = 14.sp,
                    color     = TextSecondary,
                    textAlign = TextAlign.Center,
                    modifier  = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = { showSuccess = null },
                    modifier = Modifier.fillMaxWidth(),
                    shape    = RoundedCornerShape(12.dp),
                    colors   = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Text("OK", fontWeight = FontWeight.SemiBold, color = Color.White)
                }
            }
        )
    }
}

// ── Reward Item Card ──────────────────────────────────────────────────
@Composable
private fun RewardItem(
    reward: Reward,
    currentPoints: Int,
    onRedeem: () -> Unit
) {
    val canRedeem = currentPoints >= reward.pointCost

    Card(
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier          = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ── Gambar produk ────────────────────────────────────
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Primary.copy(alpha = 0.06f)),
                contentAlignment = Alignment.Center
            ) {
                if (reward.imageRes != null) {
                    androidx.compose.foundation.Image(
                        painter            = painterResource(id = reward.imageRes),
                        contentDescription = reward.name,
                        contentScale       = ContentScale.Crop,
                        modifier           = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                    )
                } else {
                    Icon(
                        imageVector        = Icons.Outlined.CardGiftcard,
                        contentDescription = null,
                        tint               = Primary.copy(alpha = 0.4f),
                        modifier           = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(Modifier.width(12.dp))

            // ── Info + Tombol ────────────────────────────────────
            Column(modifier = Modifier.weight(1f)) {
                // Badge poin
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            if (canRedeem) Primary.copy(alpha = 0.10f)
                            else TextSecondary.copy(alpha = 0.10f)
                        )
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text       = "${reward.pointCost} pts",
                        fontSize   = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color      = if (canRedeem) Primary else TextSecondary
                    )
                }

                Spacer(Modifier.height(4.dp))

                Text(
                    text       = reward.name,
                    fontSize   = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = TextPrimary
                )

                Spacer(Modifier.height(2.dp))

                Text(
                    text     = reward.description,
                    fontSize = 12.sp,
                    color    = TextSecondary,
                    lineHeight = 16.sp
                )

                Spacer(Modifier.height(10.dp))

                Button(
                    onClick  = onRedeem,
                    enabled  = canRedeem,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp),
                    shape  = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor         = Primary,
                        disabledContainerColor = TextSecondary.copy(alpha = 0.15f),
                        contentColor           = Color.White,
                        disabledContentColor   = TextSecondary.copy(alpha = 0.5f)
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
                ) {
                    Text(
                        text       = if (canRedeem) "Redeem" else "Insufficient points",
                        fontSize   = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}