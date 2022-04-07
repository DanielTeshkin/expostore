package com.expostore.extension

import android.content.res.Resources
import android.util.TypedValue
import kotlin.math.ceil

/**
 * @author Fedotov Yakov
 */

val Number.dp: Int
    get() = ceil(
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        )
    ).toInt()

val Number.sp: Float
    get() = ceil(
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        )
    )