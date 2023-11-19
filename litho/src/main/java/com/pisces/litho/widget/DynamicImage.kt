package com.pisces.litho.widget

import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.RoundingParams
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.annotations.PropDefault
import com.facebook.litho.fresco.FrescoImage
import com.pisces.core.enums.ScaleType
import com.pisces.core.enums.ScaleType.*

@Suppress("DEPRECATION")
@LayoutSpec
object DynamicImageSpec {
    @PropDefault
    val scaleType: ScaleType = CENTER_CROP

    @PropDefault
    val aspectRatio: Float = 1.0f

    @PropDefault
    val blurRadius: Float = 0f

    @PropDefault
    val blurSample: Float = 0f

    @PropDefault
    val leftTopRadius: Float = 0f

    @PropDefault
    val rightTopRadius: Float = 0f

    @PropDefault
    val rightBottomRadius: Float = 0f

    @PropDefault
    val leftBottomRadius: Float = 0f


    @OnCreateLayout
    fun onCreateLayout(
        context: ComponentContext,
        @Prop(optional = true)
        scaleType: ScaleType,
        @Prop(optional = true, isCommonProp = true)
        aspectRatio: Float,
        @Prop
        url: Any,
        @Prop(optional = true)
        blurRadius: Float,
        @Prop(optional = true)
        blurSample: Float,
        @Prop(optional = true)
        leftTopRadius: Float,
        @Prop(optional = true)
        rightTopRadius: Float,
        @Prop(optional = true)
        rightBottomRadius: Float,
        @Prop(optional = true)
        leftBottomRadius: Float
    ): Component {
        val controller = Fresco.newDraweeControllerBuilder()
            .setUri(url.toString())
            .build()

        val frescoScaleType = when (scaleType) {
            FIT_XY -> ScalingUtils.ScaleType.FIT_XY
            FIT_START -> ScalingUtils.ScaleType.FIT_START
            FIT_CENTER -> ScalingUtils.ScaleType.FIT_CENTER
            FIT_END -> ScalingUtils.ScaleType.FIT_END
            CENTER -> ScalingUtils.ScaleType.CENTER
            CENTER_CROP -> ScalingUtils.ScaleType.CENTER_CROP
            CENTER_INSIDE -> ScalingUtils.ScaleType.CENTER_INSIDE
        }
        return FrescoImage.create(context)
            .actualImageScaleType(frescoScaleType)
            .imageAspectRatio(aspectRatio)
            .roundingParams(
                RoundingParams.fromCornersRadii(
                    leftTopRadius,
                    rightTopRadius,
                    leftBottomRadius,
                    rightBottomRadius
                )
            )
            .controller(controller)
            .build()
    }
}