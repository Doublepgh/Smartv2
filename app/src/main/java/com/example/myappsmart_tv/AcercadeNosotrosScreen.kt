import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myappsmart_tv.R

data class TeamMember(
    val name: String,
    val grade: String,
    val photoResId: Int,
    val bio: String
)

@Composable
fun AcercadeNosotrosScreen(navController: NavController) {
    var selectedMember by remember { mutableStateOf<TeamMember?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D47A1))
            .padding(16.dp)
    ) {
        Button(
            onClick = { navController.navigate("main_menu") },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
                .background(Color(0xFF1976D2))
        ) {
            Text("Menú Principal", color = Color.White)
        }
        Text(
            text = "Acerca de Nosotros",
            style = MaterialTheme.typography.h5.copy(fontSize = 30.sp),
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        val team = listOf(
            TeamMember("Pedro Pablo Guzman Hernandez", "9B", R.drawable.pablo, "Desarrollo y Gestión de Software")
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp)
        ) {
            items(team) { member ->
                TeamMemberItem(member = member, onClick = { selectedMember = member })
            }
        }

        if (selectedMember != null) {
            MemberDetailModal(member = selectedMember!!, onDismiss = { selectedMember = null })
        }

        Card(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            elevation = 4.dp,
            border = BorderStroke(2.dp, Color.Gray)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(Color(0xFF1E88E5)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Información de la materia",
                    style = MaterialTheme.typography.h6.copy(fontSize = 24.sp),
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = "DESARROLLO PARA DISPOSITIVOS INTELIGENTES",
                    style = MaterialTheme.typography.body1.copy(fontSize = 18.sp),
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = "Profesor: Dr. Armando Méndez Morales",
                    style = MaterialTheme.typography.body1.copy(fontSize = 18.sp),
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = "Noveno Cuatrimestre",
                    style = MaterialTheme.typography.body2.copy(fontSize = 16.sp),
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = "2024",
                    style = MaterialTheme.typography.body2.copy(fontSize = 16.sp),
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun TeamMemberItem(member: TeamMember, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = member.photoResId),
                contentDescription = member.name,
                modifier = Modifier
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = member.name,
                    style = MaterialTheme.typography.h6.copy(fontSize = 20.sp),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Grado y Grupo: ${member.grade}",
                    style = MaterialTheme.typography.body2.copy(fontSize = 16.sp),
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun MemberDetailModal(member: TeamMember, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = null,
        text = {
            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 8.dp,
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .background(Color.White)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Información del Estudiante",
                        style = MaterialTheme.typography.h6.copy(fontSize = 20.sp),
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Image(
                        painter = painterResource(id = member.photoResId),
                        contentDescription = member.name,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .align(Alignment.CenterHorizontally),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Grado: ${member.grade}",
                        style = MaterialTheme.typography.body1.copy(fontSize = 18.sp),
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = member.bio,
                        style = MaterialTheme.typography.body2.copy(fontSize = 16.sp),
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { onDismiss() },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1976D2)),
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                        ) {
                            Text("Contactar", color = Color.White)
                        }

                        OutlinedButton(
                            onClick = { onDismiss() },
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp)
                        ) {
                            Text("Cerrar", color = Color(0xFF1976D2))
                        }
                    }
                }
            }
        },
        buttons = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAboutScreen() {
    AcercadeNosotrosScreen(navController = rememberNavController())
}
