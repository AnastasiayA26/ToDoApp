package com.todo.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.todo.todoCard.ToDoItem
import com.todo.todoItemData.TodoItem
import com.todo.ui.theme.Black
import java.util.Locale.filter
import com.todo.R


@Composable
fun TodoListScreen(
    todoItems: List<TodoItem>,
    modifier: Modifier = Modifier,
    onAddItemClick: () -> Unit,
    onAddItem: (TodoItem) -> Unit = {},
    onComplete: (String, Boolean) -> Unit = { _, _ -> },
    onEdit: (TodoItem) -> Unit = {},
    onDelete: (String) -> Unit,
    isDarkTheme: MutableState<Boolean>
) {
    var isExpandedCompletedList = remember { mutableStateOf(true) }
    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpandedCompletedList.value) -180f else 0f,
        animationSpec = tween(durationMillis = 300)
    )
    var isAddButtonRotated = remember { mutableStateOf(false) }
    val addButtonRotation by animateFloatAsState(
        targetValue = if (isAddButtonRotated.value) 45f else 0f,
        animationSpec = tween(durationMillis = 300)
    )





    Scaffold(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background),
        floatingActionButton = {
            FloatingActionButton(

                onClick = {
                    isAddButtonRotated.value = !isAddButtonRotated.value
                    onAddItemClick()
                },
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurface,
                shape = CircleShape,
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Добавить",
                    modifier = Modifier
                        .size(48.dp)
                        .rotate(addButtonRotation)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            Row(
                modifier = Modifier
                    .padding(top = 32.dp, start = 16.dp, end = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            )
            {
                Icon(
                    painter = painterResource(id = R.drawable.cat_svgrepo_com),
                    contentDescription = "Кот",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .size(64.dp)
                        .padding(end = 8.dp)
                )

                Text(
                    text = "Мои дела",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .weight(1f)
                )

                Switch(
                    checked = isDarkTheme.value,
                    onCheckedChange = { isDarkTheme.value = !isDarkTheme.value },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.surfaceDim,
                        uncheckedThumbColor = MaterialTheme.colorScheme.surfaceBright,
                        uncheckedTrackColor = MaterialTheme.colorScheme.onBackground,
                        checkedTrackColor = MaterialTheme.colorScheme.onBackground,
                        uncheckedBorderColor = MaterialTheme.colorScheme.outline,
                        checkedBorderColor = MaterialTheme.colorScheme.outline

                    )
                )
            }
            Divider(
                color = MaterialTheme.colorScheme.outline,
                thickness = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(end = 16.dp, bottom = 8.dp)
            ) {
                Text(
                    text = "Выполнено: ${todoItems.count { it.isCompleted }}",
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                        .weight(1f)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            isExpandedCompletedList.value = !isExpandedCompletedList.value
                        }
                ) {
                    Icon(
                        painter = painterResource(id =
                        if (isExpandedCompletedList.value) R.drawable.baseline_visibility_24
                        else R.drawable.crossedeye),
                        contentDescription = if (isExpandedCompletedList.value) "Скрыть" else "Показать",
                        tint = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .rotate(rotationAngle)
                            .size(32.dp)
                    )
                }
            }
            val screenHeight = LocalConfiguration.current.screenHeightDp.dp
            Box(modifier = Modifier
                .heightIn(max = screenHeight * 0.9f)
                .padding( start = 16.dp, end = 16.dp)
                .clip(RoundedCornerShape(15.dp))
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 16.dp)
                        .fillMaxSize()
                ) {

                    val sortedItems = todoItems
                        .filter { !it.isCompleted || isExpandedCompletedList.value }
                        .sortedByDescending { it.isCompleted }
                    itemsIndexed(sortedItems, key = { _, item -> item.id }) { index, item ->
                        if (!item.isCompleted || isExpandedCompletedList.value) {
                            val isFirst = index == 0
                            val isLast = index == sortedItems.size - 1
                            ToDoItem(
                                item = item,
                                modifier = Modifier.padding(bottom = 8.dp),
                                onComplete = { isCompleted -> onComplete(item.id, isCompleted) },
                                onEdit = { task -> onEdit(task) },
                                onDelete = { id -> onDelete(id) },
                                isFirst = isFirst,
                                isLast = isLast
                            )
                        }
                    }
                }
            }
        }
    }
}