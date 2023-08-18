package com.example.krmathfight.utility

import android.content.Context
import android.graphics.ImageDecoder
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import pl.droidsonroids.gif.GifDrawable

fun getGifDuration(context: Context, @DrawableRes gifResourceId: Int): Long {
    val gifDrawable = GifDrawable(context.resources, gifResourceId)
    val frameCount = gifDrawable.numberOfFrames
    var duration = 0L

    for (i in 0 until frameCount) {
        duration += gifDrawable.getFrameDuration(i)
    }

    gifDrawable.recycle()

    return duration
}