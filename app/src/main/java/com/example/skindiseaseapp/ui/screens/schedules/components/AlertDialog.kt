package com.example.skindiseaseapp.ui.screens.schedules.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.skindiseaseapp.ui.theme.medium

@Composable
fun SingleSelectDialog(
    modifier: Modifier,
    title: String,
    optionsList: List<String>,
    defaultSelected: Int,
    submitButtonText: String,
    dismissButtonText: String,
    onSubmitButtonClick: (Int) -> Unit,
    onDismissRequest: (Boolean) -> Unit
) {

    var selectedOption by remember {
        mutableIntStateOf(defaultSelected)
    }

    Dialog(onDismissRequest = { onDismissRequest(false) }) {
        Surface(
            modifier = modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)
        ) {
            Column(modifier = modifier.padding(16.dp)) {

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                )

                LazyColumn {
                    items(optionsList) {
                        RadioButton(
                            modifier = modifier, it, optionsList[selectedOption]
                        ) { selectedValue ->
                            selectedOption = optionsList.indexOf(selectedValue)
                        }
                    }
                }

                Spacer(modifier = modifier.height(10.dp))

                Row(
                    modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Button(
                        modifier = modifier
                            .height(48.dp)
                            .wrapContentWidth(), onClick = {
                            onSubmitButtonClick.invoke(selectedOption)
                            onDismissRequest.invoke(false)
                        }, shape = MaterialTheme.shapes.extraLarge
                    ) {
                        Text(text = submitButtonText, fontFamily = medium, color = Color.White)
                    }

                    Button(
                        modifier = modifier
                            .height(48.dp)
                            .wrapContentWidth(),
                        onClick = {
                            onDismissRequest.invoke(false)
                        },
                        shape = MaterialTheme.shapes.extraLarge
                    ) {
                        Text(text = dismissButtonText, fontFamily = medium, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun RadioButton(
    modifier: Modifier, text: String, selectedValue: String, onClickListener: (String) -> Unit
) {
    Row(
        modifier
            .fillMaxWidth()
            .selectable(selected = (text == selectedValue), onClick = {
                onClickListener(text)
            }), verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.material3.RadioButton(selected = (text == selectedValue), onClick = {
            onClickListener(text)
        })
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            modifier = modifier.padding(start = 2.dp)
        )
    }
}