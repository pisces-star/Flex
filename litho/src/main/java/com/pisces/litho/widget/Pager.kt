package com.pisces.litho.widget

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.SizeSpec.EXACTLY
import com.facebook.litho.SizeSpec.getSize
import com.facebook.litho.SizeSpec.makeSizeSpec
import com.facebook.litho.TouchEvent
import com.facebook.litho.annotations.*
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.common.DataDiffSection
import com.facebook.litho.sections.common.RenderEvent
import com.facebook.litho.sections.widget.*
import com.facebook.litho.widget.ComponentRenderInfo
import com.facebook.litho.widget.LinearLayoutInfo
import com.facebook.litho.widget.LithoRecyclerView.TouchInterceptor
import com.facebook.litho.widget.RenderInfo
import com.facebook.litho.widget.SnapUtil.SNAP_TO_CENTER
import com.pisces.core.enums.Orientation


@Suppress("DEPRECATION")
@LayoutSpec
object PagerSpec {
    @OnCreateLayout
    fun onCreateLayout(
        context: ComponentContext,
        @Prop(optional = true)
        circular: Boolean = true,
        @Prop(optional = true)
        timeSpan: Float = 1.0f,
        @Prop(optional = true)
        orientation: Orientation = Orientation.HORIZONTAL,
        @Prop(optional = true)
        indicatorEnable: Boolean = false,
        @Prop(optional = true)
        indicatorSize: Int = 0,
        @Prop(optional = true)
        indicatorHeight: Float = 5.0f,
        @Prop(optional = true)
        indicatorSelected: String?,
        @Prop(optional = true)
        indicatorUnselected: String,
        @Prop(varArg = "child")
        children: List<Component>
    ): Component {
        return ViewPagerComponent.create<Model>(context)
            .initialPageIndex(0)
            .dataDiffSection(
                buildDataDiffSection(context, children)
            )
            .eventsController(RecyclerCollectionEventsController().apply {
                setSnapMode(SNAP_TO_CENTER)
            })
            .build()
    }

    private fun buildDataDiffSection(context: ComponentContext, children: List<Component>): DataDiffSection<Model>? {
        val dataDiffSection = DataDiffSection.create<Model>(SectionContext(context))
            .data(children.map { Model(it) })
            .renderEventHandler(Pager.onRenderEvent(context))
            .build()

        return dataDiffSection as DataDiffSection<Model>?
    }

    @OnEvent(RenderEvent::class)
    fun onRenderEvent(context: ComponentContext?, @FromEvent model: Model): RenderInfo {
        return ComponentRenderInfo.create()
            .component(model.content)
            .build()
    }


    class Model(
        val content: Component
    )

    @OnCreateLayout
    fun <T> onCreateLayout(
        c: ComponentContext?,
        @Prop dataDiffSection: DataDiffSection<T>?,
        @Prop(optional = true) eventsController: RecyclerCollectionEventsController?,
        @Prop(optional = true) initialPageIndex: Int,
        @Prop(optional = true) disableSwiping: Boolean
    ): Component {
        val recyclerConfiguration: RecyclerConfiguration = ListRecyclerConfiguration.create()
            .recyclerBinderConfiguration(RecyclerBinderConfiguration.create()
                .isCircular(true)
                .build())
            .orientation(LinearLayoutManager.HORIZONTAL)
            .snapMode(SNAP_TO_CENTER)
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
                        ViewPagerComponent.getPageSelectedEventHandler(c)
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
    fun onSwipeDisabledTouchEvent(c: ComponentContext?, @FromEvent view: View?): Boolean {
        return true
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