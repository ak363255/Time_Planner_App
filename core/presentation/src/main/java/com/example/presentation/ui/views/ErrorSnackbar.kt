package com.example.presentation.ui.views

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable

@Composable
fun ErrorSnackbar(snackbarData: SnackbarData) = Snackbar (
    snackbarData = snackbarData,
    containerColor = MaterialTheme.colorScheme.errorContainer,
    contentColor = MaterialTheme.colorScheme.onErrorContainer,
    actionColor = MaterialTheme.colorScheme.error,
    dismissActionContentColor = MaterialTheme.colorScheme.onSurface
)