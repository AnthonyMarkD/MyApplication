package com.example.swipeableList

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.swipeableList.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                val cards = remember {
                    mutableStateListOf(true, false, true, true, false)
                }


                LazyColumn() {
                    items(5) { index ->
                        Card(cards[index], updateState = { cards[index] = it })
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun Card(boolean: Boolean, updateState: (Boolean) -> Unit) {
    println(boolean)
    val swipeAbleState =
        rememberSwipeableState(initialValue = boolean,
            confirmStateChange = { newValue ->
                updateState(newValue)
                true
            })

    //println("${swipeAbleState.targetValue}")

    val anchors = mapOf(0f to false, 48f to true)
    Card(
        modifier = Modifier
            .height(64.dp)
            .fillMaxWidth()
            .swipeable(
                swipeAbleState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Horizontal,
                resistance = SwipeableDefaults.resistanceConfig(
                    anchors.keys,
                    0f, 0f
                )

            )
            .padding(start = swipeAbleState.offset.value.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column {


            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Swipe Me")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}