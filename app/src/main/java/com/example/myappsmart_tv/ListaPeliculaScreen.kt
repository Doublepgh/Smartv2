import android.content.Context
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.request.CachePolicy
import com.example.myappsmart_tv.model.Movie
import com.example.myappsmart_tv.repository.MovieRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

fun createImageLoader(context: Context): ImageLoader {
    return ImageLoader.Builder(context)
        .crossfade(true)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED)
        .networkCachePolicy(CachePolicy.ENABLED)
        .okHttpClient {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        }
        .build()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListaPeliculaScreen(navController: NavHostController) {
    val apiKey = "b4446ddad817244855c8fd59f6efa423"
    val repository = MovieRepository()
    var movies by remember { mutableStateOf<List<Movie>?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val imageLoader = createImageLoader(LocalContext.current)

    LaunchedEffect(Unit) {
        repository.getPopularMovies(apiKey) { movieResponse ->
            if (movieResponse != null) {
                movies = movieResponse.results
                errorMessage = null
            } else {
                errorMessage = "No se pudieron cargar las películas."
                Log.e("MoviesScreen", errorMessage ?: "Error desconocido")
            }
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Películas") },
                backgroundColor = Color(0xFF003366),
                contentColor = Color.Black,
                elevation = 4.dp,
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar al menú")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.fillMaxSize())
            } else if (errorMessage != null) {
                Text(text = errorMessage ?: "", color = MaterialTheme.colors.error, modifier = Modifier.padding(16.dp))
            } else {
                if (movies != null && movies!!.isNotEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(5),
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxSize()
                    ) {
                        items(movies!!.take(20)) { movie ->
                            MovieItem(movie = movie, navController = navController, imageLoader = imageLoader)
                        }
                    }
                } else {
                    Text(
                        text = "No hay suficientes películas disponibles para mostrar.",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie, navController: NavHostController, imageLoader: ImageLoader) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .shadow(2.dp, RoundedCornerShape(8.dp))
            .clickable { navController.navigate("details/${movie.id}") },
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(4.dp)
        ) {
            val imageUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    imageLoader = imageLoader
                ),
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.7f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.Gray.copy(alpha = 0.2f))
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = movie.title,
                style = MaterialTheme.typography.body2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = { navController.navigate("details/${movie.id}") },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF003366)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = "Ver Detalles",
                    fontSize = 12.sp,
                    color = Color.White
                )
            }
        }
    }
}
