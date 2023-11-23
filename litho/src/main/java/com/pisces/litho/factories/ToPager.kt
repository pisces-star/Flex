package com.pisces.litho.factories

import com.facebook.litho.ComponentContext
import com.pisces.core.build.PropSet
import com.pisces.litho.Widget
import com.pisces.litho.factories.filler.PropsFiller
import com.pisces.litho.widget.Pager


internal object ToPager : ToComponent<Pager.Builder>() {
    override val propsFiller by PropsFiller
        .create<Pager.Builder>(CommonProps) {
            bool("circular", Pager.Builder::circular)
            bool("enableScroll", Pager.Builder::enableScroll)
            bool("autoScroll", Pager.Builder::autoScroll)
            enum("style", Pager.Builder::pagerStyle)
            enum("orientation", Pager.Builder::orientation)
            pt("verticalSpace", Pager.Builder::verticalSpace)
            pt("horizontalSpace", Pager.Builder::horizontalSpace)
            value("columns", Pager.Builder::columns)
            value("delayTime", Pager.Builder::delayTime)
            value("timeSpan", Pager.Builder::timeSpan)
            bool("indicatorEnable", Pager.Builder::indicatorEnable)
            pt("indicatorHeight", Pager.Builder::indicatorHeight)
            pt("indicatorWidth", Pager.Builder::indicatorWidth)
            pt("indicatorSpace", Pager.Builder::indicatorSpace)
            pt("indicatorMargin", Pager.Builder::indicatorMargin)
            enum("indicatorType", Pager.Builder::indicatorType)
            text("indicatorSelected", Pager.Builder::indicatorSelected)
            text("indicatorUnselected", Pager.Builder::indicatorUnselected)
        }

    override fun create(
        c: ComponentContext,
        visibility: Boolean,
        attrs: PropSet
    ): Pager.Builder {
        return Pager.create(c)
    }

    override fun onInstallChildren(
        owner: Pager.Builder,
        visibility: Boolean,
        attrs: PropSet,
        children: List<Widget>
    ) {
        owner.children(children)
    }

}