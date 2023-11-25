package com.pisces.litho.factories

import com.facebook.litho.ComponentContext
import com.pisces.core.build.PropSet
import com.pisces.litho.Widget
import com.pisces.litho.factories.filler.PropsFiller
import com.pisces.litho.widget.Recycler
import kotlin.collections.List


internal object ToRecycler : ToComponent<Recycler.Builder>() {
    override val propsFiller by PropsFiller
        .create<Recycler.Builder>(CommonProps) {
            bool("enableScroll", Recycler.Builder::enableScroll)
            bool("autoScroll", Recycler.Builder::autoScroll)
            enum("style", Recycler.Builder::recyclerStyle)
            enum("orientation", Recycler.Builder::orientation)
            pt("verticalSpace", Recycler.Builder::verticalSpace)
            pt("horizontalSpace", Recycler.Builder::horizontalSpace)
            value("columns", Recycler.Builder::columns)
            value("delayTime", Recycler.Builder::delayTime)
            value("timeSpan", Recycler.Builder::timeSpan)
            bool("indicatorEnable", Recycler.Builder::indicatorEnable)
            pt("indicatorHeight", Recycler.Builder::indicatorHeight)
            pt("indicatorWidth", Recycler.Builder::indicatorWidth)
            pt("indicatorSpace", Recycler.Builder::indicatorSpace)
            pt("indicatorMargin", Recycler.Builder::indicatorMargin)
            enum("indicatorType", Recycler.Builder::indicatorType)
            text("indicatorSelected", Recycler.Builder::indicatorSelected)
            text("indicatorUnselected", Recycler.Builder::indicatorUnselected)
        }

    override fun create(
        c: ComponentContext,
        visibility: Boolean,
        attrs: PropSet
    ): Recycler.Builder {
        return Recycler.create(c)
    }

    override fun onInstallChildren(
        owner: Recycler.Builder,
        visibility: Boolean,
        attrs: PropSet,
        children: List<Widget>
    ) {
        owner.children(children)
    }

}