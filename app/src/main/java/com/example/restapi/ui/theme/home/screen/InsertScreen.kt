package com.example.restapi.ui.theme.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.restapi.navigasi.DestinasiNavigasi
import com.example.restapi.PenyediaViewModel
import com.example.restapi.ui.theme.TopAppBarKontak
import com.example.restapi.ui.theme.home.viewmodel.InsertUiEvent
import com.example.restapi.ui.theme.home.viewmodel.InsertUiState
import com.example.restapi.ui.theme.home.viewmodel.InsertViewModel


import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputSiswa(
    insertUiEvent: InsertUiEvent,
    modifier : Modifier = Modifier,
    onValueChange : (InsertUiEvent) -> Unit = {},
    enabled : Boolean = true
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = insertUiEvent.nama,
            onValueChange = {
                onValueChange(insertUiEvent.copy(nama = it))
            },
            label = { Text("Nama")},
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.email,
            onValueChange = { onValueChange(insertUiEvent.copy(email = it))
            },
            label = { Text("Email")},
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.nohp,
            onValueChange = { onValueChange(insertUiEvent.copy(nohp = it))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("NO HP")},
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if (enabled) {
            Text(
                text = "Isi semua data",
                modifier = Modifier.padding(start = 12.dp)
            )
        }
        Divider (
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )

    }
}
@Composable
fun EntryKontakBody(
    insertUiState : InsertUiState,
    onsiswaValueChange: (InsertUiEvent) -> Unit,
    onSaveClick : () -> Unit,
    modifier : Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInputSiswa(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onsiswaValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button (
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpam")
        }
    }
}

object DestinasiEntry: DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = "Entry Siswa"

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryKontakScreen (
    navigateBack : () -> Unit,
    modifier : Modifier = Modifier,
    viewModel: InsertViewModel = viewModel(factory = PenyediaViewModel.Factory),
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarKontak(title = DestinasiEntry.titleRes, canNavigateBack =true,
                scrollBehavior = scrollBehavior,
                navigateUp =  navigateBack)
        }
    ){
            innerPadding ->
        EntryKontakBody(insertUiState = viewModel.insertKontakState, onsiswaValueChange = viewModel :: updateInsertKontakState, onSaveClick = {
            coroutineScope.launch {
                viewModel.insertKontak()
                navigateBack
            }
        },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}