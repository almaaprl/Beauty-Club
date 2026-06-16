package com.example.beautyclub.ui.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.beautyclub.data.TreatmentData
import com.example.beautyclub.data.toPoints
import com.example.beautyclub.navigation.Screen
import com.example.beautyclub.ui.theme.*
import com.example.beautyclub.viewmodel.AddTransactionState
import com.example.beautyclub.viewmodel.HomeViewModel          // ← import ini
import com.example.beautyclub.viewmodel.TransactionViewModel
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    navController: NavHostController,
    memberId: Int,
    transactionViewModel: TransactionViewModel,
    homeViewModel: HomeViewModel                               // ← parameter ini
) {
    var selectedTreatment by remember { mutableStateOf<com.example.beautyclub.data.Treatment?>(null) }
    var dropdownExpanded  by remember { mutableStateOf(false) }

    val addState by transactionViewModel.addState.collectAsState()

    LaunchedEffect(addState) {
        if (addState is AddTransactionState.Success) {
            val s = addState as AddTransactionState.Success
            navController.navigate(
                Screen.TransactionSuccess.createRoute(
                    pointEarned = s.pointEarned,
                    totalPoints = s.totalPoints
                )
            ) {
                popUpTo(Screen.AddTransaction.route) { inclusive = true }
            }
        }
    }

    val treatments = TreatmentData.list

    fun formatRp(amount: Double): String =
        "Rp " + NumberFormat.getNumberInstance(Locale("id", "ID")).format(amount.toLong())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp)
        ) {
            // ── Back + Title ─────────────────────────────────────
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick  = { navController.popBackStack() },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector        = Icons.Outlined.ArrowBackIosNew,
                        contentDescription = "Kembali",
                        tint               = Primary,
                        modifier           = Modifier.size(18.dp)
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text       = "Add Transaction",
                    fontSize   = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color      = Primary
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text     = "Log today's treatment to collect your membership points.",
                fontSize = 13.sp,
                color    = TextSecondary,
                modifier = Modifier.padding(start = 4.dp)
            )

            Spacer(Modifier.height(28.dp))

            // ── Form Card ────────────────────────────────────────
            Card(
                modifier  = Modifier.fillMaxWidth(),
                shape     = RoundedCornerShape(20.dp),
                colors    = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    // ── Dropdown Treatment ───────────────────────
                    Text(
                        text       = "Treatment Name",
                        fontSize   = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color      = TextPrimary
                    )
                    Spacer(Modifier.height(8.dp))

                    ExposedDropdownMenuBox(
                        expanded         = dropdownExpanded,
                        onExpandedChange = { dropdownExpanded = it }
                    ) {
                        OutlinedTextField(
                            value         = selectedTreatment?.name ?: "",
                            onValueChange = {},
                            readOnly      = true,
                            placeholder   = {
                                Text("Example: Hydrafacial Premium", color = TextSecondary)
                            },
                            leadingIcon  = {
                                Icon(
                                    Icons.Outlined.Bookmark, null,
                                    tint     = Secondary,
                                    modifier = Modifier.size(18.dp)
                                )
                            },
                            trailingIcon = {
                                Icon(Icons.Outlined.ArrowDropDown, null, tint = TextSecondary)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            shape  = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor   = Primary,
                                unfocusedBorderColor = Primary.copy(alpha = 0.3f),
                                focusedLabelColor    = Primary
                            )
                        )

                        ExposedDropdownMenu(
                            expanded         = dropdownExpanded,
                            onDismissRequest = { dropdownExpanded = false },
                            modifier         = Modifier.background(Color.White)
                        ) {
                            val grouped = treatments.groupBy { it.category }
                            grouped.forEach { (category, items) ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text       = category.label,
                                            fontSize   = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color      = Primary.copy(alpha = 0.7f)
                                        )
                                    },
                                    onClick  = {},
                                    enabled  = false,
                                    modifier = Modifier.background(Primary.copy(alpha = 0.04f))
                                )
                                items.forEach { treatment ->
                                    DropdownMenuItem(
                                        text = {
                                            Row(
                                                modifier              = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text(
                                                    text     = treatment.name,
                                                    fontSize = 13.sp,
                                                    color    = TextPrimary,
                                                    modifier = Modifier.weight(1f)
                                                )
                                                Text(
                                                    text     = formatRp(treatment.price),
                                                    fontSize = 12.sp,
                                                    color    = TextSecondary
                                                )
                                            }
                                        },
                                        onClick = {
                                            selectedTreatment = treatment
                                            dropdownExpanded  = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    // ── Payment Amount ───────────────────────────
                    Text(
                        text       = "Payment Amount",
                        fontSize   = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color      = TextPrimary
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value         = if (selectedTreatment != null)
                            NumberFormat.getNumberInstance(Locale("id", "ID"))
                                .format(selectedTreatment!!.price.toLong())
                        else "",
                        onValueChange = {},
                        readOnly      = true,
                        placeholder   = { Text("0", color = TextSecondary) },
                        prefix        = {
                            Text("Rp  ", fontWeight = FontWeight.Medium, color = TextPrimary)
                        },
                        modifier        = Modifier.fillMaxWidth(),
                        shape           = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors          = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor   = Primary,
                            unfocusedBorderColor = Primary.copy(alpha = 0.3f),
                            disabledBorderColor  = Primary.copy(alpha = 0.2f)
                        )
                    )

                    Spacer(Modifier.height(16.dp))

                    // ── Point Calculation Info ───────────────────
                    Card(
                        modifier  = Modifier.fillMaxWidth(),
                        shape     = RoundedCornerShape(12.dp),
                        colors    = CardDefaults.cardColors(
                            containerColor = Primary.copy(alpha = 0.06f)
                        ),
                        elevation = CardDefaults.cardElevation(0.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Outlined.Bookmark,
                                    contentDescription = null,
                                    tint     = Primary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(Modifier.width(6.dp))
                                Text(
                                    text       = "Point Calculation:",
                                    fontSize   = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color      = Primary
                                )
                            }
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text       = "1 point = Rp10.000",
                                fontSize   = 12.sp,
                                color      = Primary,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text  = "Example: Rp150.000 = 15 points",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                            if (selectedTreatment != null) {
                                Spacer(Modifier.height(6.dp))
                                HorizontalDivider(color = Primary.copy(alpha = 0.15f))
                                Spacer(Modifier.height(6.dp))
                                Text(
                                    text       = "This treatment: +${selectedTreatment!!.price.toPoints()} points",
                                    fontSize   = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color      = Color(0xFF2E7D32)
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(28.dp))

                    // ── Save Button ──────────────────────────────
                    Button(
                        onClick = {
                            selectedTreatment?.let { t ->
                                transactionViewModel.addTransaction(
                                    memberId      = memberId,
                                    treatmentName = t.name,
                                    amount        = t.price,
                                    onSuccess     = {
                                        homeViewModel.refreshMember()  // ← refresh poin
                                    }
                                )
                            }
                        },
                        enabled  = selectedTreatment != null &&
                                addState !is AddTransactionState.Loading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape  = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor         = Primary,
                            disabledContainerColor = Primary.copy(alpha = 0.4f)
                        )
                    ) {
                        Icon(
                            Icons.Outlined.Save,
                            contentDescription = null,
                            tint     = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text       = if (addState is AddTransactionState.Loading)
                                "Saving..." else "Save",
                            fontSize   = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color      = Color.White
                        )
                    }
                }
            }
        }
    }
}