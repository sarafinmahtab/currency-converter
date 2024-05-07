package com.sarafinmahtab.currencyconverter.ui.main.converter

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarafinmahtab.currencyconverter.R
import com.sarafinmahtab.currencyconverter.data.domain.CountryCurrency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/*
 * Created by Arafin Mahtab on 11/25/2021.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyPickerWithAmount(
    coroutineScope: CoroutineScope,
    bottomSheetState: SheetState,
    @StringRes titleRes: Int,
    currencyCode: String,
    countryFlag: String,
    currencyAmount: MutableState<String>,
    currencyAmountSelector: (String) -> Unit,
    onClickCurrencyPicker: () -> Unit,
) {
    Text(
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
        text = stringResource(id = titleRes),
        style = MaterialTheme.typography.labelMedium,
    )
    Card(
        modifier = Modifier.padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
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
                currencyCode = currencyCode,
                countryFlag = countryFlag,
                onClickSelector = {
                    coroutineScope.launch {
                        if (bottomSheetState.isVisible) {
                            bottomSheetState.hide()
                        } else {
                            bottomSheetState.show()
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
        bottomSheetState = rememberModalBottomSheetState(),
        titleRes = R.string.from_currency,
        currencyCode = "USD",
        countryFlag = "USD",
        currencyAmount = remember { mutableStateOf(DEFAULT_BASE_CURRENCY_VALUE.toString()) },
        currencyAmountSelector = {},
        onClickCurrencyPicker = {},
    )
}

@Composable
fun DropdownCurrencyList(
    currencies: List<CountryCurrency>,
    onSelectCurrency: (CountryCurrency) -> Unit,
) {
    LazyColumn {
        itemsIndexed(currencies) { index, currency ->
            CurrencyItem(
                currency = currency,
                onSelectCurrency = onSelectCurrency,
                showDivider = index < currencies.lastIndex,
            )
        }
    }
}

@Composable
fun CurrencyItem(
    currency: CountryCurrency,
    onSelectCurrency: (CountryCurrency) -> Unit,
    showDivider: Boolean,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onSelectCurrency(currency) })
    ) {
        ListItem(
            leadingContent = {
                CountryFlag(
                    modifier = Modifier.size(24.dp),
                    flagBase64 = currency.flag,
                )
            },
            headlineContent = {
                Text(
                    text = currency.currency,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelMedium
                )
            },
            supportingContent = {
                Text(
                    text = currency.currencyCode,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
        )

        if (showDivider) {
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 12.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline,
            )
        }
    }
}

@Composable
fun CurrencySelector(
    modifier: Modifier = Modifier,
    currencyCode: String,
    countryFlag: String,
    onClickSelector: () -> Unit,
) {
    Row(
        modifier = modifier.clickable(onClick = onClickSelector),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CountryFlag(
            modifier = Modifier.size(24.dp),
            flagBase64 = countryFlag,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = currencyCode,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelLarge,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_spinner_arrow),
            contentDescription = countryFlag
        )
    }
}

@Composable
fun CountryFlag(
    modifier: Modifier = Modifier,
    flagBase64: String,
) {
    val pureBase64Encoded = flagBase64.substring(flagBase64.indexOf(",") + 1)
    val imageData: ByteArray = Base64.decode(pureBase64Encoded, Base64.DEFAULT)
    val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)

    Image(
        modifier = modifier,
        bitmap = bitmap.asImageBitmap(),
        contentDescription = "Country Flag",
    )
}

@Preview(showBackground = true)
@Composable
fun CurrencyItemPreview() {
    CurrencyItem(
        currency = CountryCurrency(
            id = 18,
            country = "Bangladesh",
            currency = "Dollar",
            currencyCode = "USD",
            symbol = '$',
            hasCurrencySymbol = true,
            flag = "iVBORw0KGgoAAAANSUhEUgAAAB4AAAAUCAYAAACaq43EAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyRpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw",
        ), onSelectCurrency = { }, showDivider = false
    )
}

@Preview(showBackground = true)
@Composable
fun CurrencySelectorPreview() {
    CurrencySelector(currencyCode = "USD", countryFlag = "iVBORw0KGgoAAAANSUhEUgAAAB4AAAAUCAYAAACaq43EAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyRpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw") {}
}
