
package com.bassvideo.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Background = Color(0xFF10111A)
private val SurfaceDark = Color(0xFF1C1E2C)
private val Accent = Color(0xFF8B5CF6)
private val TextWhite = Color(0xFFF5F5FA)
private val Muted = Color(0xFFAAAAAD)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                BassVideoApp()
            }
        }
    }
}

@Composable
fun BassVideoApp() {
    var prompt by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("60 ثانية") }
    var selectedMode by remember { mutableStateOf("صورة إلى فيديو") }
    var selectedImage by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("مرحباً بك في استوديو Bass AI") }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImage = uri != null
        if (uri != null) {
            message = "تم اختيار الصورة بنجاح"
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "BASS VIDEO AI",
                color = Accent,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = "استوديو صناعة الفيديو بالذكاء الاصطناعي",
                color = Muted,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = SurfaceDark
                )
            ) {
                Column(modifier = Modifier.padding(18.dp)) {

                    Text(
                        text = "أنشئ إبداعك الآن",
                        color = TextWhite,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "نوع الإنشاء",
                        color = TextWhite,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    listOf(
                        "نص إلى صورة",
                        "صورة إلى فيديو",
                        "نص إلى فيديو"
                    ).forEach { mode ->
                        ModeButton(
                            title = mode,
                            selected = selectedMode == mode,
                            onClick = { selectedMode = mode }
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "وصف المشهد",
                        color = TextWhite,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = prompt,
                        onValueChange = { prompt = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(125.dp),
                        placeholder = {
                            Text(
                                "اكتب وصف الصورة أو الفيديو...",
                                color = Muted
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite,
                            focusedBorderColor = Accent,
                            unfocusedBorderColor = Muted,
                            cursorColor = Accent
                        ),
                        shape = RoundedCornerShape(14.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedButton(
                        onClick = {
                            imagePicker.launch("image/*")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = TextWhite
                        )
                    ) {
                        Text(
                            if (selectedImage)
                                "تم اختيار الصورة ✓"
                            else
                                "اختيار صورة من الهاتف"
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "مدة الفيديو",
                        color = TextWhite,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf(
                            "15 ثانية",
                            "30 ثانية",
                            "60 ثانية"
                        ).forEach { time ->
                            FilterChip(
                                selected = duration == time,
                                onClick = { duration = time },
                                label = { Text(time) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Accent,
                                    selectedLabelColor = Color.White,
                                    labelColor = TextWhite
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            message = if (prompt.isBlank()) {
                                "يرجى كتابة وصف المشهد أولاً"
                            } else {
                                "واجهة الإنشاء جاهزة، يلزم ربط خدمة الذكاء الاصطناعي"
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Accent
                        )
                    ) {
                        Text(
                            "ابدأ الإنشاء ✨",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = SurfaceDark
                ),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "حالة التطبيق",
                        color = TextWhite,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        message,
                        color = Muted,
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Bass Video AI • الإصدار 1.0",
                color = Muted,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun ModeButton(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(
                if (selected) Accent.copy(alpha = 0.2f)
                else Background,
                RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = Accent,
                unselectedColor = Muted
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            title,
            color = TextWhite,
            fontWeight = if (selected)
                FontWeight.Bold else FontWeight.Normal
        )
    }
}
