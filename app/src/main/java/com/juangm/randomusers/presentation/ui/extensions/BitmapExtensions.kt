package com.juangm.randomusers.presentation.ui.extensions

import android.graphics.*

fun Bitmap.addBorder(borderSize: Float, borderColor: Int): Bitmap {
    val borderOffset = (borderSize * 2).toInt()
    val radius = Math.min(height / 2, width / 2).toFloat()
    val output = Bitmap.createBitmap(width + borderOffset, height + borderOffset, Bitmap.Config.ARGB_8888)
    val paint = Paint()
    val borderX = width / 2 + borderSize
    val borderY = height / 2 + borderSize
    val canvas = Canvas(output)
    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)
    paint.style = Paint.Style.FILL
    canvas.drawCircle(borderX, borderY, radius, paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(this, borderSize, borderSize, paint)
    paint.xfermode = null
    paint.style = Paint.Style.STROKE
    paint.color = borderColor
    paint.strokeWidth = borderSize
    canvas.drawCircle(borderX, borderY, radius, paint)
    return output
}