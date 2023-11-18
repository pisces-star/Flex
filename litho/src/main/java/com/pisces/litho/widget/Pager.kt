package com.pisces.litho.widget

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.facebook.litho.*
import com.facebook.litho.annotations.*
import com.facebook.litho.annotations.State
import com.facebook.litho.sections.Section
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.common.DataDiffSection
import com.facebook.litho.sections.common.RenderEvent
import com.facebook.litho.sections.widget.*
import com.facebook.litho.widget.ComponentRenderInfo
import com.facebook.litho.widget.LinearLayoutInfo
import com.facebook.litho.widget.RenderInfo
import com.facebook.litho.widget.SnapUtil
import com.facebook.yoga.YogaPositionType
import com.pisces.core.enums.Orientation
import com.pisces.core.enums.PagerStyle


@Suppress("DEPRECATION")
@LayoutSpec
object PagerSpec {

    @PropDefault
    val circular: Boolean = false

    @PropDefault
    val style: PagerStyle = PagerStyle.LIST

    @PropDefault
    val autoScroll: Boolean = false

    @PropDefault
    val space: Int = 0

    @PropDefault
    val numColumns: Int = 4

    @PropDefault
    val orientation: Orientation = Orientation.HORIZONTAL

    @OnCreateLayout
    fun onCreateLayout(
        context: ComponentContext,
        @Prop(optional = true)
        circular: Boolean,
        @Prop(optional = true)
        space: Int,
        @Prop(optional = true)
        style: PagerStyle,
        @Prop(optional = true)
        orientation: Orientation,
        @Prop(optional = true)
        indicatorEnable: Boolean,
        @Prop(optional = true)
        indicatorSize: Int = 0,
        @Prop(optional = true)
        indicatorHeight: Float,
        @Prop(optional = true)
        indicatorSelected: String,
        @Prop(optional = true)
        indicatorUnselected: String,
        @Prop(varArg = "child")
        children: List<Component>,
        @Prop(optional = true)
        numColumns: Int,
        @State selectedIndex: Int,
        @State eventsController: RecyclerCollectionEventsController
    ): Component {
        return if (indicatorEnable) {
            Column.create(context)
                .positionType(YogaPositionType.ABSOLUTE)
                .child(
                    buildRecycler(
                        context,
                        style,
                        selectedIndex,
                        children,
                        buildRecyclerConfiguration(style, orientation, circular, numColumns),
                        eventsController
                    )
                )
                .build()
        } else {
            buildRecycler(
                context,
                style,
                selectedIndex,
                children,
                buildRecyclerConfiguration(style, orientation, circular, numColumns),
                eventsController
            )
        }
    }


    private fun buildRecycler(
        context: ComponentContext,
        style: PagerStyle,
        selectedIndex: Int,
        children: List<Component>,
        recyclerConfiguration: RecyclerConfiguration,
        eventsController: RecyclerCollectionEventsController
    ): Component {

        return RecyclerCollectionComponent.create(context)
            .flexGrow(1f)
            .disablePTR(true)
            .section(buildDataDiffSection(style, context, children, selectedIndex))
            .eventsController(eventsController)
            .visibleHandler(Pager.onVisibleEvent(context))
            .invisibleHandler(Pager.onInvisibleEvent(context))
            .fullImpressionHandler(Pager.onFullImpressionVisibleEvent(context))
            .recyclerConfiguration(recyclerConfiguration)
            .build()
    }


    private fun buildRecyclerConfiguration(
        style: PagerStyle,
        orientation: Orientation,
        isCircular: Boolean,
        numColumns: Int
    ): RecyclerConfiguration {
        return when (style) {
            PagerStyle.LIST -> buildListRecyclerConfiguration(isCircular, orientation)
            PagerStyle.GRID -> buildGridRecyclerConfiguration(isCircular, orientation, numColumns)
            PagerStyle.VIEW_PAGER -> buildViewPagerConfiguration(isCircular)
            PagerStyle.STAGGERED_GRID -> buildStaggeredGridRecyclerConfiguration(isCircular, orientation, numColumns)
        }
    }

    private fun buildViewPagerConfiguration(isCircular: Boolean): ListRecyclerConfiguration {
        return ListRecyclerConfiguration.create()
            .orientation(LinearLayoutManager.HORIZONTAL)
            .snapMode(SnapUtil.SNAP_TO_CENTER)
            .recyclerBinderConfiguration(buildRecyclerBinderConfiguration(isCircular))
            .linearLayoutInfoFactory { context, orientation, reverseLayout, stackFromEnd ->
                ViewPagerLinearLayoutInfo(
                    context,
                    orientation,
                    reverseLayout
                )
            }
            .build()
    }


    private fun buildListRecyclerConfiguration(
        isCircular: Boolean,
        orientation: Orientation
    ): ListRecyclerConfiguration {
        return ListRecyclerConfiguration.create()
            .orientation(if (orientation == Orientation.HORIZONTAL) LinearLayoutManager.HORIZONTAL else LinearLayoutManager.VERTICAL)
            .snapMode(SnapUtil.SNAP_TO_CENTER_CHILD_WITH_CUSTOM_SPEED)
            .recyclerBinderConfiguration(
                buildRecyclerBinderConfiguration(isCircular)
            )
            .build()
    }

    private fun buildGridRecyclerConfiguration(
        isCircular: Boolean,
        orientation: Orientation,
        numColumns: Int
    ): GridRecyclerConfiguration {
        return GridRecyclerConfiguration.create()
            .orientation(if (orientation == Orientation.HORIZONTAL) GridLayoutManager.HORIZONTAL else GridLayoutManager.VERTICAL)
            .snapMode(SnapUtil.SNAP_TO_CENTER_CHILD_WITH_CUSTOM_SPEED)
            .numColumns(numColumns)
            .recyclerBinderConfiguration(
                buildRecyclerBinderConfiguration(isCircular, true)
            )
            .build()
    }

    private fun buildStaggeredGridRecyclerConfiguration(
        isCircular: Boolean,
        orientation: Orientation,
        numColumns: Int
    ): StaggeredGridRecyclerConfiguration {
        return StaggeredGridRecyclerConfiguration.create()
            .orientation(if (orientation == Orientation.HORIZONTAL) StaggeredGridLayoutManager.HORIZONTAL else StaggeredGridLayoutManager.VERTICAL)
            .snapMode(SnapUtil.SNAP_TO_CENTER_CHILD_WITH_CUSTOM_SPEED)
            .numSpans(numColumns)
            .recyclerBinderConfiguration(
                buildRecyclerBinderConfiguration(isCircular)
            )
            .build()
    }

    private fun buildRecyclerBinderConfiguration(
        isCircular: Boolean,
        dynamicHeight: Boolean = false
    ): RecyclerBinderConfiguration {
        return RecyclerBinderConfiguration.create()
            .hasDynamicItemHeight(dynamicHeight)
            .isCircular(isCircular)
            .build()
    }

    private fun buildDataDiffSection(
        style: PagerStyle,
        context: ComponentContext,
        children: List<Component>,
        initialPageIndex: Int
    ): Section {

        val dataDiffSection = DataDiffSection.create<Model>(SectionContext(context))
            .data(children.map { Model(it) })
            .renderEventHandler(Pager.onRenderEvent(context))
            .build()

        return when (style) {
            PagerStyle.VIEW_PAGER -> ViewPagerHelperSection.create<Model>(SectionContext(context))
                .delegateSection(dataDiffSection as DataDiffSection<Model>?)
                .pageSelectedEventEventHandler(
                    Pager.onPageSelectedEvent(context)
                )
                .initialPageIndex(initialPageIndex)
                .build()

            else -> dataDiffSection
        }
    }

    @OnCreateInitialState
    fun onCreateInitialState(
        context: ComponentContext,
        handler: StateValue<Handler>,
        runnable: StateValue<Runnable>,
        selectedIndex: StateValue<Int>,
        eventsController: StateValue<RecyclerCollectionEventsController>
    ) {
        Handler(Looper.getMainLooper()).also { handler.set(it) }
        val controller = RecyclerCollectionEventsController().also { eventsController.set(it) }
        Runnable { controller.requestScrollToNextPosition(true) }.also { runnable.set(it) }
        selectedIndex.set(0)
    }

    @OnEvent(VisibleEvent::class)
    fun onVisibleEvent(
        context: ComponentContext,
        @Prop(optional = true)
        timeSpan: Float,
        @State
        eventsController: RecyclerCollectionEventsController,
        @State
        handler: Handler,
        @State
        runnable: Runnable
    ) {
        if (timeSpan != 0f) {
            eventsController.recyclerView?.stopScroll()
            handler.removeCallbacks(runnable)
        }
    }

    @OnEvent(FullImpressionVisibleEvent::class)

    fun onFullImpressionVisibleEvent(
        context: ComponentContext,
        @Prop(optional = true)
        timeSpan: Float,
        @State
        eventsController: RecyclerCollectionEventsController,
        @State
        handler: Handler,
        @State
        runnable: Runnable,
        @Prop(optional = true)
        autoScroll: Boolean,
        @Prop(optional = true)
        style: PagerStyle,
    ) {
        if (autoScroll && style != PagerStyle.VIEW_PAGER) {
            handler.postDelayed(runnable, (timeSpan * 1000L).toLong())
        }
    }

    @OnEvent(InvisibleEvent::class)
    fun onInvisibleEvent(
        context: ComponentContext,
        @Prop(optional = true)
        timeSpan: Float,
        @State
        handler: Handler,
        @State
        runnable: Runnable
    ) {
        if (timeSpan != 0f) {
            handler.postDelayed(runnable, (timeSpan * 1000L).toLong())
        }
    }

    @OnEvent(PageSelectedEvent::class)
    fun onPageSelectedEvent(context: ComponentContext, @FromEvent selectedPageIndex: Int) {
        Pager.updateSelectedIndex(context, selectedPageIndex)
    }

    @OnUpdateState
    fun updateSelectedIndex(selectedIndex: StateValue<Int>, @Param indexedValue: Int) {
        selectedIndex.set(indexedValue)
    }

    @OnEvent(RenderEvent::class)
    fun onRenderEvent(context: ComponentContext?, @FromEvent model: Model): RenderInfo {
        return ComponentRenderInfo.create()
            .component(model.content)
            .build()
    }

    @OnEvent(TouchEvent::class)
    @JvmStatic
    fun onSwipeDisabledTouchEvent(c: ComponentContext?, @FromEvent view: View?): Boolean {
        return true
    }


    class Model(
        val content: Component
    )

    private class ViewPagerLinearLayoutInfo(context: Context?, orientation: Int, reverseLayout: Boolean) :
        LinearLayoutInfo(context, orientation, reverseLayout) {
        override fun getChildWidthSpec(widthSpec: Int, renderInfo: RenderInfo): Int {
            val hscrollWidth = SizeSpec.getSize(widthSpec)
            return SizeSpec.makeSizeSpec(hscrollWidth, SizeSpec.EXACTLY)
        }
    }

}