package com.eeema.android.charlieapp.ui.extensions

import com.eeema.android.charlieapp.R
import com.eeema.android.data.model.Character

fun Character.genderImage(): Int? {
    return when (gender) {
        "M" -> R.drawable.ic_male
        "F" -> R.drawable.ic_female
        else -> null
    }
}
