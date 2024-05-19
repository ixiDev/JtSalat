package com.ixidev.jtsalat.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ixidev.jtsalat.data.models.SalatInfo
import com.ixidev.jtsalat.ui.getSalatName
import com.ixidev.jtsalat.utils.DateTimeFormatter
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SalatItemView(modifier: Modifier = Modifier, salat: SalatInfo) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        // horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(getSalatName(salat.salatype)),
            style = MaterialTheme.typography.h5
        )
        Text(
            // modifier = Modifier.weight(1f),
            text = formatSalatTime(salat.time),
            style = MaterialTheme.typography.h4
        )
    }
}

private fun formatSalatTime(instant: Instant): String {
    val time = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return DateTimeFormatter.format(
        time.time,
        "HH:mm"
    )
}