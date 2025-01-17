package com.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.todo.ui.theme.TODOAppTheme
import android.content.SharedPreferences
import androidx.activity.viewModels
import androidx.compose.runtime.mutableStateOf
import com.todo.dataStorage.DataStorage
import com.todo.router.AppNavigator
import com.todo.todoItemsVievModel.TodoItemsViewModel


class MainActivity : ComponentActivity() {
    private val prefs: SharedPreferences by lazy { getPreferences(MODE_PRIVATE) }
    val vievModelRepo: TodoItemsViewModel by viewModels()
    private val isDarkTheme = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataStorage.init(prefs)

        enableEdgeToEdge()
        setContent {
            TODOAppTheme(darkTheme = isDarkTheme.value) {
                AppNavigator(repositoryModel = vievModelRepo, isDarkTheme)
            }
        }
    }
}



