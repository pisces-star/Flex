package com.pisces.core.build

import androidx.annotation.RestrictTo
import com.pisces.core.enums.Orientation

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
object Pager : Widget() {
    override val dataBinding by DataBinding
        .create(CommonDefine) {
            bool("isCircular")
            value("timeSpan", fallback = 3000.0f)
            enum("orientation", mapOf(
                "vertical" to Orientation.VERTICAL,
                "horizontal" to Orientation.HORIZONTAL
            ))
            bool("indicatorEnable")
            value("indicatorSize")
            value("indicatorHeight", fallback = 5.0f)
            text("indicatorSelected")
            text("indicatorUnselected")
        }
}