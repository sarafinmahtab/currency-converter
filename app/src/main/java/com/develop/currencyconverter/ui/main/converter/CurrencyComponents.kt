package com.develop.currencyconverter.ui.main.converter

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.develop.currencyconverter.ui.components.CurrencySelector
import com.develop.currencyconverter.ui.components.appTextFieldColors
import com.develop.currencyconverter.ui.theme.stroke
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/*
 * Created by Arafin Mahtab on 11/25/2021.
 */

@ExperimentalMaterialApi
@Composable
fun CurrencyPickerWithAmount(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    @StringRes header: Int,
    currencyWithFlag: String,
    currencyAmount: MutableState<String>,
    currencyAmountSelector: (String) -> Unit,
    onClickCurrencyPicker: () -> Unit
) {
    Text(
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
        text = stringResource(id = header),
        style = MaterialTheme.typography.h2
    )
    Card(
        modifier = Modifier.padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colors.stroke),
        elevation = 4.dp
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
                colors = appTextFieldColors()
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


@Preview(showBackground = true)
@Composable
fun CurrencyPickerWithAmountPreview() {
}
