package com.pisces.litho.widget

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.InvisibleEvent
import com.facebook.litho.SizeSpec.EXACTLY
import com.facebook.litho.SizeSpec.getSize
import com.facebook.litho.SizeSpec.makeSizeSpec
import com.facebook.litho.TouchEvent
import com.facebook.litho.VisibleEvent
import com.facebook.litho.annotations.*
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.common.DataDiffSection
import com.facebook.litho.sections.widget.*
import com.facebook.litho.widget.LinearLayoutInfo
import com.facebook.litho.widget.LithoRecyclerView.TouchInterceptor
import com.facebook.litho.widget.RenderInfo
import com.facebook.litho.widget.SnapUtil


@Suppress("DEPRECATION")
@LayoutSpec(events = [PageSelectedEvent::class,VisibleEvent::class,InvisibleEvent::class])
class ViewPager2ComponentSpec<T> {

    companion object {
        @OnCreateLayout
        @JvmStatic
        fun <T> onCreateLayout(
            c: ComponentContext?,
            @Prop dataDiffSection: DataDiffSection<T>?,
            @Prop(optional = true) eventsController: RecyclerCollectionEventsController?,
            @Prop(optional = true) initialPageIndex: Int,
            @Prop(optional = true) disableSwiping: Boolean,
            @Prop(optional = true) isCircular: Boolean
        ): Component {
            val recyclerConfiguration: RecyclerConfiguration = ListRecyclerConfiguration.create()
                .orientation(LinearLayoutManager.HORIZONTAL)
                .snapMode(SnapUtil.SNAP_TO_CENTER)
                .recyclerBinderConfiguration(RecyclerBinderConfiguration.create().isCircular(isCircular).build())
                .linearLayoutInfoFactory { context, orientation, reverseLayout, stackFromEnd ->
                    ViewPagerLinearLayoutInfo(
                        context,
                        orientation,
                        reverseLayout
                    )
                }
                .build()
            val builder = RecyclerCollectionComponent.create(c)
                .flexGrow(1f)
                .disablePTR(true)
                .section(
                    ViewPagerHelperSection.create<T>(SectionContext(c))
                        .delegateSection(dataDiffSection)
                        .pageSelectedEventEventHandler(
                            ViewPager2Component.getPageSelectedEventHandler(c)
                        )
                        .initialPageIndex(initialPageIndex)
                )
                .eventsController(eventsController)
                .recyclerConfiguration(recyclerConfiguration)
            if (disableSwiping) {
                // Consume the touch event before it can get to the RV to disable swiping, and also disable
                // the RV's normal touchIntercept behavior by ignoring onInterceptTouchEvent.
                builder
                    .touchInterceptor { recyclerView, ev -> TouchInterceptor.Result.IGNORE_TOUCH_EVENT }
                    .recyclerTouchEventHandler(ViewPagerComponent.onSwipeDisabledTouchEvent(c))
            }
            return builder.build()
        }

        @OnEvent(TouchEvent::class)
        @JvmStatic
        fun onSwipeDisabledTouchEvent(c: ComponentContext?, @FromEvent view: View?): Boolean {
            return true
        }
    }

    /** Custom implementation of LinearLayout to assign parent's width to items.  */
    private class ViewPagerLinearLayoutInfo(context: Context?, orientation: Int, reverseLayout: Boolean) :
        LinearLayoutInfo(context, orientation, reverseLayout) {
        override fun getChildWidthSpec(widthSpec: Int, renderInfo: RenderInfo): Int {
            val hscrollWidth = getSize(widthSpec)
            return makeSizeSpec(hscrollWidth, EXACTLY)
        }
    }
}
