package com.pisces.core.build

import androidx.annotation.RestrictTo
import com.pisces.core.enums.IndicatorType
import com.pisces.core.enums.Orientation
import com.pisces.core.enums.RecyclerStyle

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
object Recycler : Widget() {
    override val dataBinding by DataBinding
        .create(CommonDefine) {
            bool("enableScroll")
            bool("autoScroll")
            enum(
                "style", mapOf(
                    "list" to RecyclerStyle.LIST,
                    "grid" to RecyclerStyle.GRID,
                    "staggered_grid" to RecyclerStyle.STAGGERED_GRID,
                    "view_pager" to RecyclerStyle.VIEW_PAGER
                )
            )
            enum(
                "orientation", mapOf(
                    "vertical" to Orientation.VERTICAL,
                    "horizontal" to Orientation.HORIZONTAL
                )
            )
            value("verticalSpace")
            value("horizontalSpace")
            value("columns")
            value("delayTime")
            value("timeSpan")
            bool("indicatorEnable")
            value("indicatorHeight")
            value("indicatorWidth")
            value("indicatorSpace")
            value("indicatorMargin")
            enum(
                "indicatorType", mapOf(
                    "oval" to IndicatorType.OVAL,
                    "rectangle" to IndicatorType.RECTANGLE
                )
            )
            text("indicatorSelected")
            text("indicatorUnselected")
        }
}