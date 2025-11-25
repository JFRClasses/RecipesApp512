package com.pjasoft.recipeapp.domain.utils

import androidx.compose.ui.focus.FocusManager

actual fun hideKeyboard(focusManager: FocusManager) {
    focusManager.clearFocus()
}