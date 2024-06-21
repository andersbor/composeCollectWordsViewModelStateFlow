package com.example.collectwordsviewmodelstateflow

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// https://medium.com/@husayn.fakher/compose-state-vs-stateflow-state-management-in-jetpack-compose-c99740732023
class WordsViewModelStateFlow: ViewModel() {
    private val _words = MutableStateFlow(listOf<String>())
    val words: StateFlow<List<String>> = _words

    fun add(item: String) {
        _words.value = _words.value + item
    }

    fun clear() {
        _words.value = listOf()
    }

    fun remove(item: String) {
        _words.value = _words.value - item
    }
}