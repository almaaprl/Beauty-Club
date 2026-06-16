package com.example.beautyclub.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.beautyclub.navigation.Screen
import com.example.beautyclub.ui.home.BottomNavBar
import com.example.beautyclub.ui.theme.*
import com.example.beautyclub.viewmodel.AuthViewModel
import com.example.beautyclub.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel,
    authViewModel: AuthViewModel
) {
    val member by profileViewModel.member.collectAsState()

    var isEditing by remember { mutableStateOf(false) }
    var name     by remember { mutableStateOf("") }
    var email    by remember { mutableStateOf("") }
    var phone    by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(member) {
        member?.let {
            name     = it.name
            email    = it.email
            phone    = it.phone
            password = it.password
        }
    }

    Scaffold(
        containerColor = Background,
        bottomBar      = { BottomNavBar(navController) }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(24.dp))

            Text(
                text       = "Profile",
                fontSize   = 24.sp,
                fontWeight = FontWeight.Bold,
                color      = PrimaryDark
            )

            Spacer(Modifier.height(28.dp))

            // ── Avatar + Edit Button ──────────────────────────────
            Box {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Primary.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text       = member?.name?.firstOrNull()?.uppercase() ?: "U",
                        fontSize   = 42.sp,
                        fontWeight = FontWeight.Bold,
                        color      = PrimaryDark
                    )
                }

                FloatingActionButton(
                    onClick        = { isEditing = !isEditing },
                    modifier       = Modifier
                        .size(42.dp)
                        .align(Alignment.BottomEnd),
                    containerColor = PrimaryDark,
                    contentColor   = Color.White
                ) {
                    Icon(
                        imageVector        = Icons.Outlined.Edit,
                        contentDescription = null
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text       = member?.name ?: "",
                fontSize   = 18.sp,
                fontWeight = FontWeight.Bold,
                color      = TextPrimary
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text          = "MEMBER ID : ${member?.id?.let { "CS" + it.toString().padStart(4, '0') } ?: "-"}",
                fontSize      = 12.sp,
                color         = TextSecondary,
                letterSpacing = 2.sp
            )

            Spacer(Modifier.height(36.dp))

            // ── Form Fields ───────────────────────────────────────
            ProfileTextField(
                label         = "Name",
                value         = name,
                onValueChange = { name = it },
                enabled       = isEditing
            )

            Spacer(Modifier.height(16.dp))

            ProfileTextField(
                label         = "Email",
                value         = email,
                onValueChange = { email = it },
                enabled       = isEditing
            )

            Spacer(Modifier.height(16.dp))

            ProfileTextField(
                label         = "Phone Number",
                value         = phone,
                onValueChange = { phone = it },
                enabled       = isEditing
            )

            Spacer(Modifier.height(16.dp))

            // ── Password Field ────────────────────────────────────
            Text(
                text       = "Password",
                modifier   = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                color      = TextPrimary
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value                = password,
                onValueChange        = { password = it },
                enabled              = isEditing,
                visualTransformation = PasswordVisualTransformation(),
                modifier             = Modifier.fillMaxWidth(),
                shape                = RoundedCornerShape(14.dp),
                singleLine           = true,
                colors               = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor     = Primary,
                    unfocusedBorderColor   = Primary.copy(alpha = 0.4f),
                    focusedTextColor       = TextPrimary,
                    unfocusedTextColor     = TextPrimary,
                    disabledTextColor      = TextPrimary.copy(alpha = 0.6f),
                    disabledBorderColor    = TextSecondary.copy(alpha = 0.3f),
                    disabledContainerColor = Color(0xFFF5F0F2)
                )
            )

            Spacer(Modifier.height(24.dp))

            // ── Save Button, muncul saat editing ───────────
            if (isEditing) {
                Button(
                    onClick = {
                        member?.let {
                            profileViewModel.updateProfile(
                                it.copy(
                                    name     = name,
                                    email    = email,
                                    phone    = phone,
                                    password = password
                                )
                            )
                            isEditing = false
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape  = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Text(
                        text       = "Save Changes",
                        color      = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(12.dp))
            }

            // ── Logout Button ─────────────────────────────────────
            Button(
                onClick = {
                    authViewModel.logout()
                    navController.navigate(Screen.Splash.route) {
                        popUpTo(0)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape  = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryDark)
            ) {
                Icon(
                    imageVector        = Icons.AutoMirrored.Outlined.Logout,
                    contentDescription = null
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text       = "Logout",
                    fontWeight = FontWeight.Bold,
                    color      = Color.White
                )
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

// ── Reusable TextField ────────────────────────────────────────────────
@Composable
private fun ProfileTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean
) {
    Text(
        text       = label,
        modifier   = Modifier.fillMaxWidth(),
        fontWeight = FontWeight.Bold,
        color      = TextPrimary
    )

    Spacer(Modifier.height(8.dp))

    OutlinedTextField(
        value         = value,
        onValueChange = onValueChange,
        enabled       = enabled,
        modifier      = Modifier.fillMaxWidth(),
        shape         = RoundedCornerShape(14.dp),
        singleLine    = true,
        colors        = OutlinedTextFieldDefaults.colors(
            focusedBorderColor     = Primary,
            unfocusedBorderColor   = Primary.copy(alpha = 0.4f),
            focusedTextColor       = TextPrimary,
            unfocusedTextColor     = TextPrimary,
            disabledTextColor      = TextPrimary.copy(alpha = 0.6f),
            disabledBorderColor    = TextSecondary.copy(alpha = 0.3f),
            disabledContainerColor = Color(0xFFF5F0F2)
        )
    )
}