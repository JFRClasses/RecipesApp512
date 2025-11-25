package com.pjasoft.recipeapp.ui.screens.HomeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Tag(
    text : String,
    isBold: Boolean = false
){
    val colors = MaterialTheme.colorScheme
    Text(
        text = text,
        modifier = Modifier
            .clip(CircleShape)
            .background(colors.primary.copy(alpha = 0.1f))
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .clickable{

            },
        color = colors.primary,
        fontWeight = if(isBold) FontWeight.Bold else FontWeight.Light
    )
}