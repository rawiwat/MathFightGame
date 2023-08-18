package com.example.krmathfight.CharacterModel

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer

open class Move (
    val name: String,
    val cost: Int,
) {
    open fun function(user:KamenRider, context: Context) {

    }
}

open class KamenRider (var name: String,
                       var health: Int,
                       var attack:Int,
                       var imageID:Int,
                       var energy: Int = 0,
                       var moveSet: MutableList<Move> = mutableListOf()
) {
}