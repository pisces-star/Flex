package com.pisces.litho.widget

import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentScope
import com.facebook.litho.KComponent
import com.facebook.litho.sections.widget.RecyclerCollectionEventsController
import com.facebook.litho.sections.widget.ViewPagerComponent
import com.facebook.litho.widget.SnapUtil.SNAP_TO_CENTER
import com.pisces.core.enums.Orientation

class Pager private constructor(private var data: List<Component> = emptyList()) : KComponent() {
    override fun ComponentScope.render(): Component? {
        return ViewPagerComponent.create<Component>(context)
            .initialPageIndex(0)
            .eventsController(RecyclerCollectionEventsController().apply {
                setSnapMode(SNAP_TO_CENTER)
            })
            .build()
    }

    class Builder internal constructor(
        context: ComponentContext,
        defStyleAttr: Int,
        defStyleRes: Int,
        component: Pager
    ) : DefaultComponentBuilder<Pager>(context, defStyleAttr, defStyleRes, component) {

        fun isCircular(isCircular: Boolean) = apply {

        }

        fun indicatorEnable(enable: Boolean) = apply {

        }

        fun children(children: List<Component>) = apply {
        }

        fun timeSpan(interval: Long) = apply {

        }

        fun orientation(orientation: Orientation) = apply {

        }

        fun indicatorSize(indicatorSize: Int) = apply {

        }

        fun indicatorHeight(indicatorHeight: Int) = apply {

        }

        fun indicatorSelected(selected: String) = apply {

        }

        fun indicatorUnselected(unselected: String) = apply {

        }

    }

    companion object {
        @JvmStatic
        fun create(context: ComponentContext?): Builder =
            create(context, 0, 0)

        @JvmStatic
        @JvmOverloads
        fun create(
            context: ComponentContext?,
            defStyleAttr: Int = 0,
            defStyleRes: Int = 0
        ): Builder {
            val pager = Pager()
            return Builder(requireNotNull(context), defStyleAttr, defStyleRes, pager)
        }
    }
}