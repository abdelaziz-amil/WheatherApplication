package com.android.ubo.androidweatherubo.domain.view.components

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import com.android.ubo.androidweatherubo.util.PermissionUtil

@Composable
fun PermissionPopupSystem(
    context: Context,
    permissions: Array<String>,
    permissionRationale: String,
    permissionAction: (PermissionAction) -> Unit
) {
    val scaffoldState = remember { SnackbarHostState() }
    val permissionGranted =
        PermissionUtil.checkIfPermissionGranted(
            context,
            permissions
        )

    if (permissionGranted) {
        permissionAction(PermissionAction.OnPermissionGranted)
        return
    }


    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsResult ->
        val isGranted = permissions.any {
            permissionsResult.containsKey(it) && permissionsResult.getOrDefault(it, false)
        }

        if (isGranted) {
            // Permission Accepted
            permissionAction(PermissionAction.OnPermissionGranted)
        } else {
            // Permission Denied
            permissionAction(PermissionAction.OnPermissionDenied)
        }
    }

    val showPermissionRationale = PermissionUtil.shouldShowPermissionRationale(
        context,
        permissions
    )

    if (showPermissionRationale) {
        LaunchedEffect(showPermissionRationale) {
            val snackbarResult = scaffoldState.showSnackbar(
                message = permissionRationale,
                actionLabel = "Grant Access",
                duration = SnackbarDuration.Long

            )
            when (snackbarResult) {
                SnackbarResult.Dismissed -> {
                    //User denied the permission, do nothing
                    permissionAction(PermissionAction.OnPermissionDenied)
                }
                SnackbarResult.ActionPerformed -> {
                    launcher.launch(permissions)
                }
            }
        }
    } else {
        // https://developer.android.com/jetpack/compose/side-effects
        SideEffect {
            launcher.launch(permissions)
        }
    }
}

sealed class PermissionAction {
    object OnPermissionGranted : PermissionAction()
    object OnPermissionDenied : PermissionAction()
}