package com.example.joinbtnenabled

import com.chibatching.kotpref.KotprefModel

object Prefs : KotprefModel(){
    var cnt by intPref(-1)
}