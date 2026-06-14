package com.example.beautyclub.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.beautyclub.navigation.Screen
import com.example.beautyclub.ui.component.PrimaryButton
import com.example.beautyclub.ui.component.glowOutlinedTextFieldColors
import com.example.beautyclub.ui.theme.*

@Composable
fun RegisterScreen(navController: NavHostController) {
    var namaLengkap     by remember { mutableStateOf("") }
    var email           by remember { mutableStateOf("") }
    var nomorTelepon    by remember { mutableStateOf("") }
    var password        by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

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
            IconButton(
                onClick  = { navController.popBackStack() },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector        = Icons.Outlined.ArrowBackIosNew,
                    contentDescription = "Kembali",
                    tint               = Primary,
                    modifier           = Modifier.size(20.dp)
                )
            }

            Spacer(Modifier.height(32.dp))

            Text(
                text       = "Registrasi",
                fontSize   = 28.sp,
                fontWeight = FontWeight.Bold,
                color      = TextPrimary
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text     = "Buat akun baru untuk mulai menikmati layanan.",
                fontSize = 14.sp,
                color    = TextSecondary
            )

            Spacer(Modifier.height(36.dp))

            Card(
                modifier  = Modifier.fillMaxWidth(),
                shape     = RoundedCornerShape(24.dp),
                colors    = CardDefaults.cardColors(containerColor = Surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {

                    // Nama Lengkap
                    FormLabel("Nama Lengkap")
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value         = namaLengkap,
                        onValueChange = { namaLengkap = it },
                        placeholder   = { Text("Masukkan nama lengkap", color = TextSecondary) },
                        leadingIcon   = { Icon(Icons.Outlined.Person, null, tint = Secondary) },
                        modifier   = Modifier.fillMaxWidth(),
                        shape      = RoundedCornerShape(14.dp),
                        singleLine = true,
                        colors     = glowOutlinedTextFieldColors()
                    )

                    Spacer(Modifier.height(20.dp))

                    // Email
                    FormLabel("Email")
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value         = email,
                        onValueChange = { email = it },
                        placeholder   = { Text("contoh@gmail.com", color = TextSecondary) },
                        leadingIcon   = { Icon(Icons.Outlined.Email, null, tint = Secondary) },
                        modifier        = Modifier.fillMaxWidth(),
                        shape           = RoundedCornerShape(14.dp),
                        singleLine      = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors          = glowOutlinedTextFieldColors()
                    )

                    Spacer(Modifier.height(20.dp))

                    // Nomor Telepon
                    FormLabel("Nomor Telepon")
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value         = nomorTelepon,
                        onValueChange = { nomorTelepon = it },
                        placeholder   = { Text("08xxxxxxxx", color = TextSecondary) },
                        leadingIcon   = { Icon(Icons.Outlined.Phone, null, tint = Secondary) },
                        modifier        = Modifier.fillMaxWidth(),
                        shape           = RoundedCornerShape(14.dp),
                        singleLine      = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        colors          = glowOutlinedTextFieldColors()
                    )

                    Spacer(Modifier.height(20.dp))

                    // Password
                    FormLabel("Password")
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value         = password,
                        onValueChange = { password = it },
                        placeholder   = { Text("Minimal 6 karakter", color = TextSecondary) },
                        leadingIcon   = { Icon(Icons.Outlined.Lock, null, tint = Secondary) },
                        trailingIcon  = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    if (passwordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                                    contentDescription = "Toggle password",
                                    tint = TextSecondary
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier        = Modifier.fillMaxWidth(),
                        shape           = RoundedCornerShape(14.dp),
                        singleLine      = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        colors          = glowOutlinedTextFieldColors()
                    )

                    Spacer(Modifier.height(28.dp))

                    PrimaryButton(
                        text    = "Daftar",
                        onClick = {
                            // TODO: simpan via ViewModel, lalu navigate
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Register.route) { inclusive = true }
                            }
                        }
                    )

                    Spacer(Modifier.height(20.dp))

                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = buildAnnotatedString {
                                append("Sudah punya akun? ")
                                withStyle(SpanStyle(color = Primary, fontWeight = FontWeight.SemiBold)) {
                                    append("Masuk Disini")
                                }
                            },
                            fontSize = 13.sp,
                            color    = TextSecondary,
                            modifier = Modifier.clickable { navController.popBackStack() }
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun FormLabel(text: String) {
    Text(text = text, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    MyGlowBeautyTheme {
        RegisterScreen(rememberNavController())
    }
}