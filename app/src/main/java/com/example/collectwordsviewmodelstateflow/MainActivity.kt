package com.example.collectwordsviewmodelstateflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.collectwordsviewmodelstateflow.ui.theme.CollectWordsViewModelStateFlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CollectWordsViewModelStateFlowTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CollectWords(modifier = Modifier.padding(innerPadding), viewModel = viewModel())
                }
            }
        }
    }
}

@Composable
fun CollectWords(modifier: Modifier = Modifier, viewModel: WordsViewModelStateFlow = viewModel()) {
    // Add to gradle file  implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")
    // https://tigeroakes.com/posts/mutablestateof-list-vs-mutablestatelistof/
    val words = viewModel.words.collectAsState()
    var word by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var showList by remember { mutableStateOf(true) }
    // val delete: (String) -> Unit = { viewModel.remove(it) }

    Column(modifier = modifier) {
        Text(text = "Collect words", style = MaterialTheme.typography.headlineLarge)
        OutlinedTextField(
            value = word,
            onValueChange = { word = it },
            // https://medium.com/@GkhKaya00/exploring-keyboard-types-in-kotlin-jetpack-compose-ca1f617e1109
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Enter a word") }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                viewModel.add(word)
            }) {
                Text("Add")
            }
            Button(onClick = {
                viewModel.clear()
                word = ""
                result = ""
            }) {
                Text("Clear")
            }
            Button(onClick = { result = words.value.toString() }) {
                Text("Show")
            }
        }
        if (result.isNotEmpty()) {
            Text(result)
        } else {
            Text("Empty", fontStyle = FontStyle.Italic)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Show list")
            Spacer(modifier = Modifier.padding(5.dp))
            Switch(checked = showList, onCheckedChange = { showList = it })
        }
        if (showList) {
            if (words.value.isEmpty()) {
                Text("No words")
            } else {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(words.value) { word: String ->
                        Text(word, modifier = Modifier.clickable { viewModel.remove(word) })
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CollectWordsPreview() {
    CollectWords(viewModel = WordsViewModelStateFlow())
}