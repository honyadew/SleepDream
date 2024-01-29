package com.honya.sleep.preview

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.honya.components.cards.SdTextCard
import com.honya.components.cards.SdVoiceCard
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month

@Preview
@Composable
private fun PreviewSdTextCard() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Green)){
        SdTextCard(
            name = "Amazing dream ever! or something...",
            text = "Beggining part of dream, here can be placed only 3 string of text. Next only" +
                    "for example & more & more & more & more & more & more & more & more & more & more",
            onCardClick = {},
            date = LocalDate.now()
        )
        var currentSecond by remember { mutableLongStateOf(0) }
        var pause by remember { mutableStateOf(true)}
        SdVoiceCard(
            name = "TestName",
            seconds = 100,
            date = LocalDate.now(),
            currentSecond = currentSecond,
            onSliderValueChanged = {currentSecond = it},
            onPauseChange = {pause = it},
            onCardClick = { /*TODO*/ },
            modifier = Modifier.padding(8.dp),
            paused = pause
        )
        println("something happened")
        LaunchedEffect(key1 = pause){
            while (!pause){
                if (currentSecond+1 > 100) pause = true
                println("something happened, state ${currentSecond}")
                delay(1000)
                currentSecond += 1
            }
        }

    }
}

