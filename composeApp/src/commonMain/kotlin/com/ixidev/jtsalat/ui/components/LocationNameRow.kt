package com.ixidev.jtsalat.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ixidev.jtsalat.data.models.LocationInfo

@Composable
fun LocationNameRow(modifier: Modifier, locationInfo: LocationInfo) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.LocationOn,
            contentDescription = null,
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = locationInfo.city,
            style = MaterialTheme.typography.h6,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}