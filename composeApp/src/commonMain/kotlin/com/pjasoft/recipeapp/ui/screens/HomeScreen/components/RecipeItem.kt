package com.pjasoft.recipeapp.ui.screens.HomeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.pjasoft.recipeapp.domain.models.Recipe

@Composable
fun RecipeItem(recipe: Recipe, onClick : () -> Unit){
    val colors = MaterialTheme.colorScheme
    Row(
        modifier = Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .shadow(16.dp,RoundedCornerShape(16.dp))
            .background(colors.surface)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = recipe.imageUrl,
            contentDescription = recipe.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(end = 10.dp)
                .size(60.dp)
                .clip(CircleShape)
                .background(colors.primary.copy(0.1f))

        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = recipe.title,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                color = colors.onSurface
            )
            Text(
                text = recipe.category,
                color = colors.onSurfaceVariant,
                fontSize = 12.sp
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector = Icons.Default.Schedule,
                contentDescription = "minutes",
                tint = colors.primary
            )
            Text(
                text = "${recipe.minutes} min",
                color = colors.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}