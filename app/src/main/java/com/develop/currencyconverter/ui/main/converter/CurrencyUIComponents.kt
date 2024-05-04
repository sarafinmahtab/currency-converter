package com.develop.currencyconverter.ui.main.converter

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.develop.currencyconverter.R
import com.develop.currencyconverter.ui.theme.stroke
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/*
 * Created by Arafin Mahtab on 11/25/2021.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyPickerWithAmount(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: SheetState,
    @StringRes header: Int,
    currencyWithFlag: String,
    currencyAmount: MutableState<String>,
    currencyAmountSelector: (String) -> Unit,
    onClickCurrencyPicker: () -> Unit,
) {
    Text(
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
        text = stringResource(id = header),
        style = MaterialTheme.typography.labelMedium,
    )
    Card(
        modifier = Modifier.padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.stroke),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .padding(4.dp)
                    .weight(1.3f),
                value = currencyAmount.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                onValueChange = currencyAmountSelector,
                shape = RoundedCornerShape(4.dp),
                colors = TextFieldDefaults.colors(),
            )

            CurrencySelector(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.7f),
                currencyWithFlag = currencyWithFlag,
                onClickSelector = {
                    coroutineScope.launch {
                        if (modalBottomSheetState.isVisible) {
                            modalBottomSheetState.hide()
                        } else {
                            modalBottomSheetState.show()
                        }
                    }
                    onClickCurrencyPicker()
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun CurrencyPickerWithAmountPreview() {
    CurrencyPickerWithAmount(
        coroutineScope = rememberCoroutineScope(),
        modalBottomSheetState = rememberModalBottomSheetState(),
        header = R.string.from_currency,
        currencyWithFlag = "USD",
        currencyAmount = remember { mutableStateOf(DEFAULT_BASE_CURRENCY_VALUE.toString()) },
        currencyAmountSelector = {}
    ) { }
}


@Composable
fun DropdownCurrencyList(
    currencies: List<String>,
    selectCurrency: (String) -> Unit,
) {
    LazyColumn {
        items(currencies) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectCurrency(
                            it
                                .split(" ")
                                .last()
                        )
                    }
                    .padding(16.dp),
                text = it,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelMedium
            )
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.stroke)
        }
    }
}

@Composable
fun CurrencySelector(
    modifier: Modifier = Modifier,
    currencyWithFlag: String,
    onClickSelector: () -> Unit,
) {
    Row(
        modifier = modifier.clickable(onClick = onClickSelector),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = currencyWithFlag,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelMedium,
        )
        Icon(
            modifier = Modifier.padding(start = 8.dp),
            painter = painterResource(id = R.drawable.ic_spinner_arrow),
            contentDescription = currencyWithFlag
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CurrencySelectorPreview() {
    CurrencySelector(currencyWithFlag = "USD") {}
}
