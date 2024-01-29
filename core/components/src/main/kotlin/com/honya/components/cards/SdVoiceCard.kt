package com.honya.components.cards

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.honya.logbook.R
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

//Whole file refactor
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SdVoiceCard(
    name: String,
    seconds: Long,
    date: LocalDate,
    paused: Boolean,
    currentSecond: Long,
    onSliderValueChanged: (second: Long) -> Unit,
    onPauseChange: (newValue: Boolean) -> Unit,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var dragging by remember { mutableStateOf(false) }
    val sliderPosition = remember { mutableLongStateOf(currentSecond) }

    Box(modifier = modifier){
        Card(
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            onClick = onCardClick,
            modifier = Modifier.fillMaxWidth().defaultMinSize(minWidth = 256.dp, minHeight = 96.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp).fillMaxWidth()){
                Text(
                    text = name,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth(0.7f),
                    overflow = TextOverflow.Ellipsis
                )
                Row(modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    //Todo pass Locale
                    val month = date.month.getDisplayName(TextStyle.SHORT, Locale.US)
                    Text(
                        text = month + " " + date.dayOfMonth,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    val untilEndTime = seconds-currentSecond
                    val minutes = if (currentSecond.toInt() == 0 || (seconds-currentSecond).toInt() == 0) seconds/60 else untilEndTime/60
                    val leftSeconds = if (currentSecond.toInt() == 0 || (seconds-currentSecond).toInt() == 0) seconds%60 else untilEndTime%60


                    //TODO always 2symbol 00:03 or 02:00
                    Text(
                        text = if (currentSecond.toInt() == 0 || (seconds-currentSecond).toInt() == 0) "${minutes}:$leftSeconds" else "-${minutes}:$leftSeconds",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                //TODO add colors from design
                Slider(
                    modifier = Modifier.height(21.dp),
                    value = if (!dragging){
                        currentSecond.toFloat()
                    } else{
                        sliderPosition.longValue.toFloat()
                    },
                    valueRange = 0f..seconds.toFloat(),
                    onValueChange = {newValue ->
                        onPauseChange.invoke(true)
                        sliderPosition.longValue = newValue.toLong()
                        dragging = true
                    },
                    onValueChangeFinished = {
                        onPauseChange.invoke(false)
                        dragging = false
                        onSliderValueChanged.invoke( sliderPosition.longValue)
                    }
                )
            }
        }
        IconButton(
            onClick = {onPauseChange.invoke(!paused)},
            modifier = Modifier.align(Alignment.TopEnd).padding(top = 4.dp, end = 4.dp)
        ){
            Icon(
                painter = painterResource(
                    if (paused) R.drawable.ic_play_24 else R.drawable.ic_pause_24
                ),
                contentDescription = "Play|Pause",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }

}

