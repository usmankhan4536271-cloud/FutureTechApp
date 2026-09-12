package com.futuretech.school

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FutureTechTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF0A192F)
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

// Theme Config
val DarkNavy = Color(0xFF0A192F)
val TechBlue = Color(0xFF0073E6)
val SoftWhite = Color(0xFFF8F9FA)
val CardNavy = Color(0xFF172A45)

@Composable
fun FutureTechTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = TechBlue,
            background = DarkNavy,
            surface = CardNavy,
            onPrimary = Color.White,
            onBackground = SoftWhite,
            onSurface = SoftWhite
        ),
        content = content
    )
}

// Navigation Host
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("dashboard/{role}") { backStackEntry ->
            val role = backStackEntry.arguments?.getString("role") ?: "Student"
            DashboardScreen(navController, role)
        }
        composable("classes") { ClassesScreen(navController) }
        composable("fees") { FeesScreen(navController) }
        composable("ai_teacher") { AiTeacherScreen(navController) }
    }
}

// Login Screen
@Composable
fun LoginScreen(navController: NavController) {
    var selectedRole by remember { mutableStateOf("Student") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "FutureTech International School",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Learn Today. Build Tomorrow.",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("Student", "Teacher", "Parent", "Admin").forEach { role ->
                Button(
                    onClick = { selectedRole = role },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedRole == role) TechBlue else CardNavy
                    ),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(role, fontSize = 11.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("$selectedRole ID / Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate("dashboard/$selectedRole") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = TechBlue)
        ) {
            Text("Login as $selectedRole", fontSize = 16.sp)
        }
    }
}

// Dashboard Screen
@Composable
fun DashboardScreen(navController: NavController, role: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "$role Dashboard",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Welcome to FutureTech Portal",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Button(
            onClick = { navController.navigate("classes") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CardNavy)
        ) {
            Text("📚 Classes (1 to 10) & Subjects")
        }

        Button(
            onClick = { navController.navigate("fees") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CardNavy)
        ) {
            Text("💳 Fee Structure & Payment Details")
        }

        Button(
            onClick = { navController.navigate("ai_teacher") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
            colors = ButtonDefaults.buttonColors(containerColor = TechBlue)
        ) {
            Text("🤖 AI Teacher Interface (Urdu + English)")
        }
    }
}

// Classes Screen
@Composable
fun ClassesScreen(navController: NavController) {
    val classesList = (1..10).map { "Class $it" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Academic Classes",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(classesList) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = CardNavy)
                ) {
                    Text(
                        text = "$item - Mathematics, Science, English, Urdu",
                        color = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

// Fee Structure Screen
@Composable
fun FeesScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Fee Management",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CardNavy)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Admission Fee: Rs. 15,000", fontSize = 16.sp, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Monthly Fee: Rs. 6,000", fontSize = 16.sp, color = Color.White)
                Spacer(modifier = Modifier.height(16.dp))
                Text("JazzCash Payment Account:", fontSize = 14.sp, color = Color.Gray)
                Text("03355879658", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TechBlue)
            }
        }
    }
}

// AI Teacher Interface
@Composable
fun AiTeacherScreen(navController: NavController) {
    var query by remember { mutableStateOf("") }
    var response by remember { mutableStateOf("السلام علیکم! میں آپ کا AI ٹیچر ہوں۔ آپ مجھ سے اردو یا انگریزی میں کوئی بھی سوال پوچھ سکتے ہیں۔") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "AI Teacher Assistant",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(containerColor = CardNavy)
        ) {
            Text(
                text = response,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Ask anything (Urdu / English)...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (query.isNotEmpty()) {
                    response = "You asked: '$query'\n\n[AI Response Placeholder: High-security API connectivity standard in production setup.]"
                    query = ""
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = TechBlue)
        ) {
            Text("Send Question")
        }
    }
}

