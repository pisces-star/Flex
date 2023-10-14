package com.pisces.litho.widget

import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.RoundingParams
import com.facebook.litho.*
import com.facebook.litho.fresco.FrescoImage
import com.pisces.core.enums.ScaleType

class DynamicImage private constructor(
    private var scaleType: ScalingUtils.ScaleType = ScalingUtils.ScaleType.CENTER,
    private var aspectRatio: Float = 1.0f,
    private var url: Any = "",
    private var blurRadius: Float = 0f,
    private var blurSample: Float = 0f,
    private var leftTopRadius: Float = 0f,
    private var rightTopRadius: Float = 0f,
    private var rightBottomRadius: Float = 0f,
    private var leftBottomRadius: Float = 0f
) : KComponent() {
    override fun ComponentScope.render(): Component? {
        val controller = Fresco.newDraweeControllerBuilder()
            .setUri(url.toString())
            .build()
        return FrescoImage.create(context)
            .actualImageScaleType(scaleType)
            .imageAspectRatio(aspectRatio)
            .roundingParams(RoundingParams.fromCornersRadii(floatArrayOf()))
            .controller(controller)
            .build()
    }

    class Builder internal constructor(
        context: ComponentContext,
        defStyleAttr: Int,
        defStyleRes: Int,
        component: DynamicImage
    ) : DefaultComponentBuilder<DynamicImage>(context, defStyleAttr, defStyleRes, component) {

        fun scaleType(scaleType: ScaleType) = apply {
            component.scaleType = when (scaleType) {
                ScaleType.FIT_XY -> ScalingUtils.ScaleType.CENTER
                ScaleType.FIT_START -> ScalingUtils.ScaleType.FIT_START
                ScaleType.FIT_CENTER -> ScalingUtils.ScaleType.FIT_CENTER
                ScaleType.FIT_END -> ScalingUtils.ScaleType.FIT_END
                ScaleType.CENTER -> ScalingUtils.ScaleType.CENTER
                ScaleType.CENTER_CROP -> ScalingUtils.ScaleType.CENTER_CROP
                ScaleType.CENTER_INSIDE -> ScalingUtils.ScaleType.CENTER_INSIDE
            }
        }

        fun blurRadius(blurRadius: Float) = apply {
            component.blurRadius = blurRadius
        }

        fun blurSample(blurSample: Float) = apply {
            component.blurSample = blurSample
        }

        fun imageAspectRatio(aspectRatio: Float) = apply {
            component.aspectRatio = aspectRatio
        }

        fun leftTopRadius(radius: Float) = apply {
            component.leftTopRadius = radius
        }

        fun rightTopRadius(radius: Float) = apply {
            component.rightTopRadius = radius
        }

        fun rightBottomRadius(radius: Float) = apply {
            component.rightBottomRadius = radius
        }

        fun leftBottomRadius(radius: Float) = apply {
            component.leftBottomRadius = radius
        }

        fun url(url: Any) = apply {
            component.url = url
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
            val dynamicImage = DynamicImage()
            return Builder(requireNotNull(context), defStyleAttr, defStyleRes, dynamicImage)
        }
    }
}