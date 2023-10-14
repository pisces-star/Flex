package com.pisces.litho.drawable.load

import android.content.Context
import com.facebook.drawee.backends.pipeline.Fresco
import com.pisces.core.build.BuildKit

internal object DrawableLoader: BuildKit {
    override fun init(c: Context) {
        Fresco.initialize(c)
    }
}