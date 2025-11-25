package com.pjasoft.recipeapp.ui.screens.Auth

import MainScreenRoute
import RecipeTheme
import RegisterScreenRoute
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pjasoft.recipeapp.ui.viewmodels.AuthViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.reflect.KClass

@Composable
fun RegisterScreen(navController: NavController){
    val colors = MaterialTheme.colorScheme
    val viewModel: AuthViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                return AuthViewModel() as T
            }
        }
    )
    var name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ){
        Column(
            modifier = Modifier.fillMaxSize()
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
                    .background(colors.primary),
            )
            Spacer(
                modifier = Modifier.weight(2f)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .height(550.dp)
                .padding(horizontal = 24.dp)
                .shadow(10.dp,RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .background(colors.surface)
                .padding(vertical = 10.dp, horizontal = 10.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = "Crear cuenta"
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = name,
                shape = CircleShape,
                onValueChange = { name = it },
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Nombre"
                    )
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = email,
                shape = CircleShape,
                onValueChange = { email = it },
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Correo Electronico"
                    )
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = password,
                shape = CircleShape,
                onValueChange = { password = it },
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Contraseña"
                    )
                },
                visualTransformation = PasswordVisualTransformation()
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = confirmPassword,
                shape = CircleShape,
                onValueChange = { confirmPassword = it },
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Confirmar contraseña"
                    )
                },
                visualTransformation = PasswordVisualTransformation()
            )
            Button(
                onClick = {
                    if(
                        name.isBlank() ||
                        email.isBlank() ||
                        password.isBlank() ||
                        confirmPassword.isBlank()

                        ){
                        // ALGUN FIELD ESTA BLANCO
                        return@Button
                    }

                    if(password != confirmPassword){
                        //Las contraseñas no coinciden
                        // REGEX
                        return@Button
                    }

                    viewModel.register(
                        name = name,
                        email = email,
                        password = password
                    ){ result, message ->
                        if(result){
                            navController.navigate(MainScreenRoute){
                                popUpTo(RegisterScreenRoute){
                                    inclusive = true
                                }
                            }
                        }
                        else{
                            // MOSTRAR UN ERROR
                            println(message)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = "Registrarse"
                )
            }
        }
    }
}

@Composable
@Preview
fun RegisterScreenPreview(){
    RecipeTheme {
        RegisterScreen(
            navController = rememberNavController()
        )
    }

}