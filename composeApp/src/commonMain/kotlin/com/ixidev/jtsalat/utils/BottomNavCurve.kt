package com.ixidev.jtsalat.utils

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection


class BottomNavCurve(
    private val radius: Int = 90
) : Shape {


    // the coordinates of the first curve
    private val mFirstCurveStartPoint = PointF()
    private val mFirstCurveControlPoint1 = PointF()
    private val mFirstCurveControlPoint2 = PointF()
    private val mFirstCurveEndPoint = PointF()


    private val mSecondCurveControlPoint1 = PointF()
    private val mSecondCurveControlPoint2 = PointF()
    private var mSecondCurveStartPoint = PointF()
    private var mSecondCurveEndPoint = PointF()
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(path = Path().apply {
            val curveDepth = radius + (radius / 4F)
            // the coordinates (x,y) of the start point before curve
            mFirstCurveStartPoint.set(
                (size.width / 2) - (radius * 2) - (radius / 3),
                curveDepth
            )

            // the coordinates (x,y) of the end point after curve
            mFirstCurveEndPoint.set(size.width / 2, 0F)

            // same thing for the second curve
            mSecondCurveStartPoint = mFirstCurveEndPoint;
            mSecondCurveEndPoint.set(
                (size.width / 2) + (radius * 2) + (radius / 3),
                curveDepth
            )

            // the coordinates (x,y)  of the 1st control point on a cubic curve
            mFirstCurveControlPoint1.set(
                mFirstCurveStartPoint.x + curveDepth,
                mFirstCurveStartPoint.y
            )

            // the coordinates (x,y)  of the 2nd control point on a cubic curve
            mFirstCurveControlPoint2.set(
                mFirstCurveEndPoint.x - (radius * 2) + radius,
                mFirstCurveEndPoint.y
            )
            mSecondCurveControlPoint1.set(
                mSecondCurveStartPoint.x + (radius * 2) - radius,
                mSecondCurveStartPoint.y
            )
            mSecondCurveControlPoint2.set(
                mSecondCurveEndPoint.x - (curveDepth),
                mSecondCurveEndPoint.y
            )

            moveTo(0f, curveDepth)
            lineTo(mFirstCurveStartPoint.x, mFirstCurveStartPoint.y)
            cubicTo(
                mFirstCurveControlPoint1.x, mFirstCurveControlPoint1.y,
                mFirstCurveControlPoint2.x, mFirstCurveControlPoint2.y,
                mFirstCurveEndPoint.x, mFirstCurveEndPoint.y
            )
            cubicTo(
                mSecondCurveControlPoint1.x, mSecondCurveControlPoint1.y,
                mSecondCurveControlPoint2.x, mSecondCurveControlPoint2.y,
                mSecondCurveEndPoint.x, mSecondCurveEndPoint.y
            )
            lineTo(size.width, curveDepth)
            lineTo(size.width, size.height)
            lineTo(0F, size.height)
        })
    }
}