package com.pisces.litho.factories

import android.view.MotionEvent
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.widget.HorizontalScroll
import com.facebook.litho.widget.LithoScrollView
import com.facebook.litho.widget.VerticalScroll
import com.guet.flexbox.litho.factories.filler.FillViewportFiller
import com.pisces.core.build.PropSet
import com.pisces.core.enums.Orientation
import com.pisces.litho.Widget
import com.pisces.litho.factories.filler.PropsFiller
import com.pisces.litho.factories.filler.ScrollBarEnableFiller

internal object ToScroller : ToComponent<Component.Builder<*>>(),
    LithoScrollView.OnInterceptTouchListener {

    override val propsFiller by PropsFiller
        .create(CommonProps) {
            register("scrollBarEnable", ScrollBarEnableFiller)
            register("fillViewport", FillViewportFiller)
        }


    override fun create(
        c: ComponentContext,
        visibility: Boolean,
        attrs: PropSet
    ): Component.Builder<*> {
        return when (attrs.getOrElse("orientation") { Orientation.VERTICAL }) {
            Orientation.HORIZONTAL -> {
                HorizontalScroll.create(c)
            }

            else -> {
                VerticalScroll.create(c).apply {
                    onInterceptTouchListener(this@ToScroller)
                }
            }
        }
    }

    override fun onInstallChildren(
        owner: Component.Builder<*>,
        visibility: Boolean,
        attrs: PropSet,
        children: List<Widget>
    ) {
        if (children.isEmpty()) {
            return
        }
        if (owner is HorizontalScroll.Builder) {
            owner.contentProps(children.single())
        } else if (owner is VerticalScroll.Builder) {
            owner.childComponent(children.single())
        }
    }

    private fun onInterceptTouchEvent(view: ViewGroup, event: MotionEvent?): Boolean {
        view.requestDisallowInterceptTouchEvent(event?.action == MotionEvent.ACTION_MOVE)
        return when (event?.action) {
            MotionEvent.ACTION_MOVE -> true
            else -> false
        }
    }

    override fun onInterceptTouch(nestedScrollView: NestedScrollView, event: MotionEvent?): Boolean {
        return onInterceptTouchEvent(nestedScrollView, event)
    }
}