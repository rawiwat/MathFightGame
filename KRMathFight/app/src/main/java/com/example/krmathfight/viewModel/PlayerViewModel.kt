package com.example.krmathfight.viewModel

import android.content.Context
import android.health.connect.datatypes.units.Energy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.krmathfight.CharacterModel.KamenRider
import com.example.krmathfight.CharacterModel.Move
import com.example.krmathfight.R
import com.example.krmathfight.utility.Problem
import com.example.krmathfight.utility.getGifDuration
import com.example.krmathfight.utility.getProblem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class KamenRiderViewModel(kamenRider: KamenRider) : ViewModel() {
    private val _kamenRider = MutableStateFlow(kamenRider)
    val currentKamenRider: MutableStateFlow<KamenRider> = _kamenRider


}
class IchigoViewModel:KamenRiderViewModel(KamenRider("Ichigo",120,13,R.drawable.ichigo_stance)) {
    init {
        currentKamenRider.value.moveSet.add(IchigoPunch())
    }
}
class IchigoPunch: Move(name = "Punch",cost = 1) {
    override fun function(user: KamenRider, context: Context) {
        super.function(user, context)
        val timer = getGifDuration(context, R.drawable.ichigo_punch)
        user.imageID = R.drawable.ichigo_punch
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                delay(timer)
            }
            user.imageID = R.drawable.ichigo_stance
        }
    }
}