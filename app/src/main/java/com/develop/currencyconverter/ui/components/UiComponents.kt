package com.develop.currencyconverter.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.toSize
import com.develop.currencyconverter.R
import com.develop.currencyconverter.ui.theme.light


/*
 * Created by Arafin Mahtab on 11/2/2021.
 */

@Composable
fun appTextFieldColors(
    textColor: Color = MaterialTheme.colors.onSurface,
    disabledTextColor: Color = MaterialTheme.colors.light,
    backgroundColor: Color = Color.White,
    cursorColor: Color = MaterialTheme.colors.secondary,
    errorCursorColor: Color = MaterialTheme.colors.error
) = TextFieldDefaults.textFieldColors(
    textColor = textColor,
    disabledTextColor = disabledTextColor,
    backgroundColor = backgroundColor,
    cursorColor = cursorColor,
    errorCursorColor = errorCursorColor
)

@Composable
fun ItemSelectionSpinner(
    modifier: Modifier = Modifier,
    items: List<String>,
    initialSelection: String
) {
    val item = remember { mutableStateOf(initialSelection) }
    val isOpen = remember { mutableStateOf(false) }
    val openCloseOfDropDownList: (Boolean) -> Unit = { isOpen.value = it }
    val itemSelector: (String) -> Unit = { item.value = it }
    var labelSize by remember { mutableStateOf(Size.Zero) }

    val angle by animateFloatAsState(
        targetValue = if (isOpen.value) 180F else 0F,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
    )

    Box(modifier) {
        Column {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        labelSize = coordinates.size.toSize()
                    },
                onValueChange = { item.value = it },
                value = item.value,
                singleLine = true,
                readOnly = true,
                trailingIcon = {
                    Icon(
                        modifier = Modifier.rotate(angle),
                        painter = painterResource(id = R.drawable.ic_spinner_arrow),
                        contentDescription = initialSelection
                    )
                }
            )
            DropDownList(
                modifier = Modifier.width(
                    with(LocalDensity.current) {
                        labelSize.width.toDp()
                    }),
                requestToOpen = isOpen.value,
                list = items,
                request = openCloseOfDropDownList,
                selectItem = itemSelector
            )
        }

        Spacer(
            modifier = Modifier
                .matchParentSize()
                .clickable {
                    isOpen.value = true
                }
        )
    }
}

@Composable
fun DropDownList(
    modifier: Modifier = Modifier,
    requestToOpen: Boolean = false,
    list: List<String>,
    request: (Boolean) -> Unit,
    selectItem: (String) -> Unit
) {
    DropdownMenu(
        modifier = modifier,
        expanded = requestToOpen,
        onDismissRequest = { request(false) }
    ) {
        list.forEach {
            DropdownMenuItem(
                modifier = Modifier.wrapContentWidth(),
                onClick = {
                    request(false)
                    selectItem(it)
                }
            ) {
                Text(it, modifier = Modifier.wrapContentWidth())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemSelectionSpinnerPreview() {
    val currencyList = listOf("USD", "EUR", "BDT")

    ItemSelectionSpinner(
        items = currencyList,
        initialSelection = currencyList.first()
    )
}
