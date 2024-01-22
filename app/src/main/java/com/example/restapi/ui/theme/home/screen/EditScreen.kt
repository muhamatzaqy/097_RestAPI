package com.example.restapi.ui.theme.home.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.restapi.PenyediaViewModel
import com.example.restapi.navigasi.DestinasiNavigasi
import com.example.restapi.ui.theme.TopAppBarKontak
import com.example.restapi.ui.theme.home.viewmodel.EditViewModel
import kotlinx.coroutines.launch

object EditDestination: DestinasiNavigasi {
    override val route= "edit"
    override val titleRes= "Edit Kontak"
    const val kontakId= "itemId"
    val routeWithArgs = "$route/{$kontakId}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBarKontak(
                title = DestinasiHome.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,

                )
        },
        modifier = modifier
    ) { innerPadding ->
        EntryKontakBody(
            insertUiState = viewModel.editKontakState,
            onsiswaValueChange = viewModel::updateInsertKontakState,
            onSaveClick = {
                // Note: If the user rotates the screen very fast, the operation may get cancelled
                // and the item may not be updated in the Database. This is because when config
                // change occurs, the Activity will be recreated and the rememberCoroutineScope will
                // be cancelled - since the scope is bound to composition.
                coroutineScope.launch {
                    viewModel.updateKontak()
                    onNavigateUp()

                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}