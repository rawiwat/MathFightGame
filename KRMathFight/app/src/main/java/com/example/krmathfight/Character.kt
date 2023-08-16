package com.example.krmathfight

import androidx.compose.ui.graphics.ImageBitmap

class Player(
    var name:String,
    var HP: Int,
    var ATK:Int,
    var form:String,
    var imageBitmap: String,
    var skillPoint:Int = 0,
    var enemy: Enemy? = null,
    var energy:Int = 0
) {

    fun gainSkillPoint(amount:Int) {
        skillPoint += amount
    }

    fun getEnemy(newEnemy: Enemy) {
        enemy = newEnemy
    }

    fun changeForm(formName:String) {
        form = formName
    }

    fun getAttacked() {
        if (enemy != null) {
            HP -= enemy!!.ATK
        }
    }

    fun attack() {
        if (enemy != null) {
            enemy!!.HP -= ATK
        }
    }

    fun gainEnergy(amount: Int) {
        energy += amount
    }
}

class Enemy(
    var name: String,
    var imageBitmap: ImageBitmap,
    var HP: Int,
    var ATK: Int,
) {

}