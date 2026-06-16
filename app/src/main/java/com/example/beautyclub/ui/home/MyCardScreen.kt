package com.example.beautyclub.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.beautyclub.ui.theme.*
import com.example.beautyclub.viewmodel.HomeViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import android.graphics.Bitmap

// ── Helper: tier berdasarkan poin ─────────────────────────────────────
data class MemberTier(
    val label: String,
    val color: Color
)

fun getMemberTier(points: Int): MemberTier = when {
    points >= 601 -> MemberTier("PLATINUM", Color(0xFF5C6BC0))
    points >= 301 -> MemberTier("GOLD",     Color(0xFFB8860B))
    else          -> MemberTier("SILVER",   Color(0xFF78909C))
}

// ── Format nomor kartu: CS + id 4 digit ───────────────────────────────
fun formatCardNumber(id: Int): String = "CS" + id.toString().padStart(4, '0')

@Composable
fun MyCardScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel
) {
    val member by homeViewModel.member.collectAsState()

    val userName   = member?.name    ?: "User"
    val points     = member?.points  ?: 0
    val memberId   = member?.id      ?: 0
    val cardNumber = formatCardNumber(memberId)
    val tier       = getMemberTier(points)

    val qrContent  = "BEAUTYCLUB:$cardNumber:$userName"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // ── Top bar ───────────────────────────────────────────
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
                    text       = "My Card",
                    fontSize   = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color      = Primary,
                    modifier   = Modifier.align(Alignment.Center)
                )
            }

            Spacer(Modifier.height(8.dp))

            // ── Member Card ───────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(PrimaryDark, Primary)
                        )
                    )
                    .padding(24.dp)
            ) {
                Column {
                    Text(
                        text       = "MyGlow Beauty",
                        fontSize   = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color      = Color.White
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text       = userName,
                        fontSize   = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color      = Color.White.copy(alpha = 0.9f)
                    )
                    Spacer(Modifier.height(20.dp))
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment     = Alignment.CenterVertically
                    ) {
                        Text(
                            text      = cardNumber,
                            fontSize  = 13.sp,
                            color     = Color.White.copy(alpha = 0.75f),
                            fontWeight = FontWeight.Medium
                        )
                        // Tier badge
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(tier.color.copy(alpha = 0.85f))
                                .padding(horizontal = 14.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text       = tier.label,
                                fontSize   = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color      = Color.White,
                                letterSpacing = 1.sp
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // ── QR Code Card ──────────────────────────────────────
            Card(
                modifier  = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape     = RoundedCornerShape(20.dp),
                colors    = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier            = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text       = "Scan for My Card",
                        fontSize   = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = TextPrimary
                    )

                    Spacer(Modifier.height(20.dp))

                    // QR Code via ZXing
                    Box(
                        modifier = Modifier
                            .size(220.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF0D1B4B)),
                        contentAlignment = Alignment.Center
                    ) {
                        QRCodeImage(
                            content  = qrContent,
                            modifier = Modifier.size(160.dp)
                        )
                    }

                    Spacer(Modifier.height(20.dp))

                    // Total Points strip
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Primary.copy(alpha = 0.08f))
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text          = "TOTAL POINTS",
                                fontSize      = 11.sp,
                                color         = TextSecondary,
                                fontWeight    = FontWeight.Medium,
                                letterSpacing = 1.5.sp
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text       = points.toString(),
                                fontSize   = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color      = Primary
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // ── Info Tier Progress ────────────────────────────────
            Card(
                modifier  = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape     = RoundedCornerShape(16.dp),
                colors    = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text       = "Member Tier",
                        fontSize   = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color      = TextPrimary
                    )
                    Spacer(Modifier.height(12.dp))

                    TierRow(label = "Silver",   range = "0 – 300 pts",   color = Color(0xFF78909C), active = points < 301)
                    Spacer(Modifier.height(8.dp))
                    TierRow(label = "Gold",     range = "301 – 600 pts", color = Color(0xFFB8860B), active = points in 301..600)
                    Spacer(Modifier.height(8.dp))
                    TierRow(label = "Platinum", range = "601+ pts",      color = Color(0xFF5C6BC0), active = points >= 601)

                    if (points < 301) {
                        Spacer(Modifier.height(12.dp))
                        val needed = 301 - points
                        Text(
                            text     = "Tambah $needed poin lagi untuk naik ke Gold 🌟",
                            fontSize = 12.sp,
                            color    = TextSecondary
                        )
                    } else if (points < 601) {
                        Spacer(Modifier.height(12.dp))
                        val needed = 601 - points
                        Text(
                            text     = "Tambah $needed poin lagi untuk naik ke Platinum 💎",
                            fontSize = 12.sp,
                            color    = TextSecondary
                        )
                    } else {
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text     = "Kamu sudah di tier tertinggi! 🎉",
                            fontSize = 12.sp,
                            color    = Color(0xFF5C6BC0)
                        )
                    }
                }
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun TierRow(label: String, range: String, color: Color, active: Boolean) {
    Row(
        modifier          = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(RoundedCornerShape(50))
                .background(if (active) color else color.copy(alpha = 0.25f))
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text       = label,
            fontSize   = 13.sp,
            fontWeight = if (active) FontWeight.Bold else FontWeight.Normal,
            color      = if (active) color else TextSecondary,
            modifier   = Modifier.weight(1f)
        )
        Text(
            text     = range,
            fontSize = 12.sp,
            color    = if (active) color.copy(alpha = 0.8f) else TextSecondary.copy(alpha = 0.6f)
        )
        if (active) {
            Spacer(Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(color.copy(alpha = 0.12f))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(text = "Aktif", fontSize = 10.sp, color = color, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ── QR Code composable menggunakan ZXing ──────────────────────────────
@Composable
fun QRCodeImage(content: String, modifier: Modifier = Modifier) {
    val darkColor  = Color(0xFF0D1B4B).toArgb()
    val lightColor = Color.White.toArgb()

    AndroidView(
        factory = { ctx ->
            android.widget.ImageView(ctx).apply {
                scaleType = android.widget.ImageView.ScaleType.FIT_CENTER
            }
        },
        update = { imageView ->
            try {
                val writer = MultiFormatWriter()
                val matrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512)

                val width  = matrix.width
                val height = matrix.height
                val pixels = IntArray(width * height) { i ->
                    val x = i % width
                    val y = i / width
                    if (matrix[x, y]) darkColor else lightColor
                }

                val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
                imageView.setImageBitmap(bitmap)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        },
        modifier = modifier
    )
}