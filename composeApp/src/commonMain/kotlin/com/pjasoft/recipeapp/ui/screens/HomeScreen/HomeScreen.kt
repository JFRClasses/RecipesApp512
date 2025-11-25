package com.pjasoft.recipeapp.ui.screens.HomeScreen

import HomeScreenRoute
import LoginScreenRoute
import RecipeTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pjasoft.recipeapp.domain.dtos.Prompt
import com.pjasoft.recipeapp.domain.dtos.RecipeDTO
import com.pjasoft.recipeapp.domain.utils.Preferences
import com.pjasoft.recipeapp.domain.utils.hideKeyboard
import com.pjasoft.recipeapp.ui.components.CustomOutlinedTextField
import com.pjasoft.recipeapp.ui.components.LoadingOverlay
import com.pjasoft.recipeapp.ui.screens.HomeScreen.components.GeneratedRecipe
import com.pjasoft.recipeapp.ui.screens.HomeScreen.components.Header
import com.pjasoft.recipeapp.ui.screens.HomeScreen.components.RecipeCard
import com.pjasoft.recipeapp.ui.screens.HomeScreen.components.RecipeItem
import com.pjasoft.recipeapp.ui.viewmodels.AuthViewModel
import com.pjasoft.recipeapp.ui.viewmodels.RecipeViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.min
import kotlin.reflect.KClass

//Sharedpreferences
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController){
    val colors = MaterialTheme.colorScheme
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    val scope = rememberCoroutineScope()
    var prompt by remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current
    val viewModel: RecipeViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                return RecipeViewModel() as T
            }
        }
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .safeContentPadding()
            .padding(5.dp)
    ) {
        // Header
        item {
            Header(
                onLogout = {
                    Preferences.clearSettings()
                    navController.navigate(LoginScreenRoute){
                        popUpTo(HomeScreenRoute) {  inclusive = true }
                    }
                }
            )
        }
        // Fin del Header
        item {
            Text(
                text = "Crea, Cocina, Comparte y disfruta",
                style = MaterialTheme
                    .typography
                    .headlineMedium
                    .copy(fontWeight = FontWeight.ExtraBold)
            )
            Spacer(
                modifier = Modifier.height(10.dp)
            )
            CustomOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = prompt,
                onValueChange = { prompt = it },
                trailingIcon = Icons.Default.AutoAwesome,
                placeHolder = "Escribe tus ingredientes...",
                onTrailingIconClick = {
                    hideKeyboard(focusManager)
                    viewModel.generateRecipe(
                        prompt = Prompt(
                            ingredients = prompt
                        )
                    )
                    scope.launch {
                        sheetState.partialExpand()
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        hideKeyboard(focusManager)
                        viewModel.generateRecipe(
                            prompt = Prompt(
                                ingredients = prompt
                            )
                        )
                        scope.launch {
                            sheetState.partialExpand()
                        }
                    }
                )
            )
        }

        item {
            Text(
                text = "Tus recetas recientes"
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(viewModel.recipes){ recipe ->
                    RecipeCard(recipe){
                       scope.launch {
                           val recipeDTO = RecipeDTO(
                               category = recipe.category,
                               ingredients = recipe.ingredients,
                               instructions = recipe.instructions,
                               minutes = recipe.minutes,
                               prompt = "",
                               stars = recipe.stars,
                               title = recipe.title,
                               imageUrl = recipe.imageUrl ?: ""
                           )
                           viewModel.showModalFromList(
                               recipe = recipeDTO
                           )
                           sheetState.partialExpand()
                       }
                    }
                }
            }
        }

        //Quick ideas
        item {
            val tags = listOf(
                "Rápidas (10 min)",
                "Pocas calorias",
                "Sin horno",
                "Desayunos"
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "Ideas Rapidas"
            )
            Spacer(Modifier.height(10.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(tags){ tag ->
                    Text(
                        text = tag,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(colors.primary.copy(alpha = 0.1f))
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                            .clickable{

                            },
                        color = colors.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(colors.primary.copy(0.1f))
                    .padding(20.dp)
                    .clickable{
                        // GENERAR RECETA ALEATORIA
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "¿No sabes que cocinar hoy?"
                    )
                    Text(
                        text = "Genera una receta aleatoria"
                    )
                }
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null
                )
            }
        }

        // TODAS LAS RECETAS
        items(viewModel.recipes){ recipe ->
            RecipeItem(
                recipe
            ){

            }
        }
    }
    // MODAL
    if(viewModel.showSheet){
        ModalBottomSheet(
            onDismissRequest = { viewModel.hideModal() },
            dragHandle = { BottomSheetDefaults.DragHandle() },
            containerColor = colors.surface,
            sheetState = sheetState,
        )
        {
            GeneratedRecipe(
                recipe = viewModel.generatedRecipe,
                onSave = {
                    scope.launch {
                        viewModel.hideModal()
                        sheetState.hide()
                    }
                    viewModel.saveRecipeInDb()
                }
            )
        }
    }

    if(viewModel.isLoading){
        LoadingOverlay(colors)
    }
}

@Preview(
    showBackground = true
)
@Composable
fun HomeScreenPreview(){
    RecipeTheme {
        HomeScreen(
            navController = rememberNavController()
        )
    }
}