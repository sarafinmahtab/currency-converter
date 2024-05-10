package com.sarafinmahtab.currencyconverter.ui.components

import androidx.annotation.StringRes
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sarafinmahtab.currencyconverter.R

@Composable
fun ShimmerAnimation(
    shimmerType: ShimmerType,
) {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val shimmerLightColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = shimmerType.toString())
    val translateAnim = transition.animateFloat(
        initialValue = 100f,
        targetValue = 800f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 600,
                easing = FastOutSlowInEasing,
            ),
            repeatMode = RepeatMode.Reverse,
        ),
        label = shimmerType.toString(),
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value),
    )
    val lightBrush = Brush.linearGradient(
        colors = shimmerLightColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value),
    )

    when (shimmerType) {
        is ShimmerType.CurrencyPickerWithAmount -> ShimmerCurrencyPickerWithAmount(
            titleRes = shimmerType.titleRes,
            code = shimmerType.code,
            brush = brush,
            lightBrush = lightBrush,
        )
    }
}

@Composable
fun ShimmerCurrencyPickerWithAmount(
    @StringRes titleRes: Int,
    code: String,
    brush: Brush,
    lightBrush: Brush,
) {
    Text(
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
        text = stringResource(id = titleRes),
        style = MaterialTheme.typography.labelMedium,
    )
    Card(
        modifier = Modifier
            .height(80.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxHeight()
                    .weight(1.3f)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface,
                        shape = RoundedCornerShape(4.dp),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Spacer(
                    modifier = Modifier
                        .size(width = 48.dp, height = 16.dp)
                        .padding(8.dp)
                        .background(brush = brush, shape = RoundedCornerShape(10.dp))
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.7f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Spacer(
                    modifier = Modifier
                        .size(24.dp)
                        .background(lightBrush),
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
                    contentDescription = "",
                )
            }
        }
    }
}

sealed class ShimmerType {
    data class CurrencyPickerWithAmount(
        @StringRes
        val titleRes: Int,
        val code: String,
    ) : ShimmerType()
}
