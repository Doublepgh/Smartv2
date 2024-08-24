package com.example.myappsmart_tv

import AcercadeNosotrosScreen
import ListaPeliculaScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MyApp { finishAffinity() }
            }
        }
    }
}

@Composable
fun MyApp(onExitClick: () -> Unit) {
    val navController = rememberNavController()
    Scaffold { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "menu",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("menu") { MainMenu(navController, onExitClick) }
            composable("movies") { ListaPeliculaScreen(navController) }
            composable("details/{movieId}") { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("movieId")
                movieId?.let {
                    DetallesPelicula(
                        movieId = it,
                        onBackClick = { navController.navigateUp() }
                    )
                }
            }
            composable("about") { AcercadeNosotrosScreen(navController) }
        }
    }
}

@Composable
fun MainMenu(navController: NavHostController, onExitClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                val buttonColors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF1E90FF),
                    contentColor = Color.White
                )
                Button(
                    onClick = { navController.navigate("movies") },
                    modifier = Modifier.padding(10.dp),
                    colors = buttonColors
                ) {
                    Text(
                        text = "Peliculas",
                        fontSize = 20.sp
                    )
                }
                Button(
                    onClick = { navController.navigate("about") },
                    modifier = Modifier.padding(8.dp),
                    colors = buttonColors
                ) {
                    Text(
                        text = "Acerca de Nosotros",
                        fontSize = 20.sp
                    )
                }
                Button(
                    onClick = {
                        onExitClick()
                    },
                    modifier = Modifier.padding(8.dp),
                    colors = buttonColors
                ) {
                    Text(
                        text = "Salir",
                        fontSize = 20.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "TMBD",
                style = MaterialTheme.typography.h6.copy(fontSize = 50.sp),
                color = Color.White,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Bienvenido",
                style = MaterialTheme.typography.h5.copy(fontSize = 45.sp),
                color = Color.White,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Millones de películas, series y gente por descubrir. Explora ya.",
                style = MaterialTheme.typography.h6.copy(fontSize = 20.sp),
                color = Color.White,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val images = listOf(
                    R.drawable.p1,
                    R.drawable.p2,
                    R.drawable.p3,
                    R.drawable.p4,
                    R.drawable.p5
                )
                items(images) { imageResId ->
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}

