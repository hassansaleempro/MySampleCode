package com.casttypes.app.utils.extensions

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.hideKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}
fun View.isPointInBounds(x: Float, y: Float): Boolean {
    val location = IntArray(2)
    getLocationOnScreen(location)
    val rect = Rect(location[0], location[1], location[0] + width, location[1] + height)
    return rect.contains(x.toInt(), y.toInt())
}