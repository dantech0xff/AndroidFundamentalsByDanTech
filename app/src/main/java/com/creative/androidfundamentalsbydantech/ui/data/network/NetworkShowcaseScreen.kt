package com.creative.androidfundamentalsbydantech.ui.data.network

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.creative.androidfundamentalsbydantech.data.remote.Repo
import com.creative.androidfundamentalsbydantech.ui.common.DemoScaffold
import com.creative.androidfundamentalsbydantech.ui.common.Explanation
import com.creative.androidfundamentalsbydantech.ui.common.SectionHeader

@Composable
fun NetworkShowcaseScreen(onBack: () -> Unit, vm: NetworkViewModel = hiltViewModel()) {
    val state by vm.state.collectAsState()
    var query by remember { mutableStateOf("android compose") }

    DemoScaffold(title = "Retrofit + OkHttp", onBack = onBack) {
        SectionHeader("Search GitHub Repos")
        Explanation(
            "Retrofit + OkHttp + kotlinx.serialization. Sealed UiState với " +
                "Idle / Loading / Success / Error.",
        )

        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    label = { Text("Query") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Button(onClick = { vm.search(query) }, modifier = Modifier.fillMaxWidth()) {
                    Text("Search")
                }
            }
        }

        when (val s = state) {
            NetworkUiState.Idle -> {
                Text("— idle —", modifier = Modifier.padding(8.dp))
            }
            NetworkUiState.Loading -> Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                CircularProgressIndicator()
                Text("Loading…")
            }
            is NetworkUiState.Error -> {
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Text(
                        "Error: ${s.message}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(12.dp),
                    )
                }
            }
            is NetworkUiState.Success -> {
                SectionHeader("${s.repos.size} / ${s.total} results")
                s.repos.forEach { RepoCard(it) }
            }
        }
    }
}

@Composable
private fun RepoCard(repo: Repo) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(repo.full_name, style = MaterialTheme.typography.titleLarge)
            if (!repo.description.isNullOrBlank()) {
                Text(repo.description, style = MaterialTheme.typography.bodyLarge)
            }
            Text(
                "⭐ ${repo.stargazers_count}  🍴 ${repo.forks_count}  ${repo.language ?: "—"}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
