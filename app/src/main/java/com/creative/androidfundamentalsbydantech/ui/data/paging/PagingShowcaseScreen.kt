package com.creative.androidfundamentalsbydantech.ui.data.paging

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.creative.androidfundamentalsbydantech.data.remote.Repo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagingShowcaseScreen(onBack: () -> Unit, vm: PagingViewModel = hiltViewModel()) {
    val items = vm.repos.collectAsLazyPagingItems()
    var query by remember { mutableStateOf("android compose") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Paging 3") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
    ) { inner ->
        Column(modifier = Modifier.fillMaxSize().padding(inner)) {
            Card(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        label = { Text("Query") },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Button(onClick = { vm.updateQuery(query) }, modifier = Modifier.fillMaxWidth()) {
                        Text("Search (paged)")
                    }
                }
            }

            LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp)) {
                items(count = items.itemCount, key = { idx -> items[idx]?.id ?: idx.toLong() }) { idx ->
                    val repo = items[idx] ?: return@items
                    RepoRow(repo)
                }
                when (val append = items.loadState.append) {
                    is LoadState.Loading -> item { LoadingRow() }
                    is LoadState.Error -> item { ErrorRow(append.error.message ?: "error") }
                    else -> Unit
                }
                when (val refresh = items.loadState.refresh) {
                    is LoadState.Loading -> item { LoadingRow() }
                    is LoadState.Error -> item { ErrorRow(refresh.error.message ?: "error") }
                    else -> Unit
                }
            }
        }
    }
}

@Composable
private fun RepoRow(repo: Repo) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(repo.full_name, style = MaterialTheme.typography.titleLarge)
            if (!repo.description.isNullOrBlank()) {
                Text(repo.description, style = MaterialTheme.typography.bodyLarge)
            }
            Text(
                "⭐ ${repo.stargazers_count}  ${repo.language ?: "—"}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun LoadingRow() {
    Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorRow(message: String) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(
            "Error: $message",
            modifier = Modifier.padding(12.dp),
            color = MaterialTheme.colorScheme.error,
        )
    }
}
