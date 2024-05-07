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
                code = currencyCode,
                flag = countryFlag,
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
                    text = currency.formattedName(),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelMedium
                )
            },
            supportingContent = {
                Text(
                    text = currency.code,
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
    code: String,
    flag: String,
    onClickSelector: () -> Unit,
) {
    Row(
        modifier = modifier.clickable(onClick = onClickSelector),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CountryFlag(
            modifier = Modifier.size(24.dp),
            flagBase64 = flag,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = code,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelLarge,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_spinner_arrow),
            contentDescription = flag
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
            code = "AED",
            name = "UAE Dirham",
            country = "United Arab Emirates",
            countryCode = "AE",
            flag = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAACXBIWXMAAAsTAAALEwEAmpwYAAAAIGNIUk0AAHolAACAgwAA+f8AAIDpAAB1MAAA6mAAADqYAAAXb5JfxUYAAAG5SURBVHja7JdLihRBEEBfVqUU6rQNggiCFxA8gswFRNy49gAeQdx4G8HbuHDvRkRUnKxPZ2dGhous6Y9TtavPZmITtYggXsWPSKOqrCkFK8stgAFKoOr1kiKAt8CD76/f/KYYj//u7bPpU28Mn199eGiBLabg7uWLUePLp08mB/j66xvA1gKVSkK9J/29guuxNCZrVX60905qZlD0xvd5XbPvmN22uo+XCFDZXI2Idjt0txuk9TFM+ve7Yk9MAkAPIKSuI3XdoEMX/aQAd4qSfYpHAI0RbVt0FGA/KYAtyvMMaBTUObRpBh2a0E3cgspewkkJQkDqGm3bQfNPL9/PtIQ+cmjC5OqbTaj9qppRcglCAFej3h9H8P9xnBUgCtRNBllYDj0QmxbWAkgxggiktFjg60PosAeMJnQtAIkRq7poBlIfK5cgRBQdzYC1dtLgVVVRluUJgEQo7XH0RminlBDCKUDK99AIwByXs4gcb0JJafaFc7aCjTlktQBIqpiVAPIYas5AcXEx6LCRzaxjKAn4465GjZ1zs13GBngMPAceLbyFfwJfTP8m2PR6SfGAM7eP07UB/g0Aw73uXdMbeJMAAAAASUVORK5CYII=",
        ),
        onSelectCurrency = {},
        showDivider = false,
    )
}

@Preview(showBackground = true)
@Composable
fun CurrencySelectorPreview() {
    CurrencySelector(
        code = "AED",
        flag = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAACXBIWXMAAAsTAAALEwEAmpwYAAAAIGNIUk0AAHolAACAgwAA+f8AAIDpAAB1MAAA6mAAADqYAAAXb5JfxUYAAAG5SURBVHja7JdLihRBEEBfVqUU6rQNggiCFxA8gswFRNy49gAeQdx4G8HbuHDvRkRUnKxPZ2dGhous6Y9TtavPZmITtYggXsWPSKOqrCkFK8stgAFKoOr1kiKAt8CD76/f/KYYj//u7bPpU28Mn199eGiBLabg7uWLUePLp08mB/j66xvA1gKVSkK9J/29guuxNCZrVX60905qZlD0xvd5XbPvmN22uo+XCFDZXI2Idjt0txuk9TFM+ve7Yk9MAkAPIKSuI3XdoEMX/aQAd4qSfYpHAI0RbVt0FGA/KYAtyvMMaBTUObRpBh2a0E3cgspewkkJQkDqGm3bQfNPL9/PtIQ+cmjC5OqbTaj9qppRcglCAFej3h9H8P9xnBUgCtRNBllYDj0QmxbWAkgxggiktFjg60PosAeMJnQtAIkRq7poBlIfK5cgRBQdzYC1dtLgVVVRluUJgEQo7XH0RminlBDCKUDK99AIwByXs4gcb0JJafaFc7aCjTlktQBIqpiVAPIYas5AcXEx6LCRzaxjKAn4465GjZ1zs13GBngMPAceLbyFfwJfTP8m2PR6SfGAM7eP07UB/g0Aw73uXdMbeJMAAAAASUVORK5CYII=",
    ) {}
}
