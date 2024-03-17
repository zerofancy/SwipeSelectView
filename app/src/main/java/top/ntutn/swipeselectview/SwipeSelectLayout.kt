package top.ntutn.swipeselectview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.view.children
import com.google.android.material.chip.ChipGroup
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sqrt

class SwipeSelectLayout @JvmOverloads constructor(context: Context, defStyleAttr: AttributeSet? = null, defStyleRes: Int = 0) : ChipGroup(context, defStyleAttr, defStyleRes) {
    private val childrenSelectionOnMoveStart = mutableMapOf<View, Boolean>()
    private var closestViewIndex = -1

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            handleMotionEventDown(ev)
        }
        if (ev.action == MotionEvent.ACTION_MOVE) {
            return true
        }
        return super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!children.any()) {
            return super.onTouchEvent(event)
        }
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                // 拖动起始点不在一个child上，也选择一个最近的View
                // handleMotionEventDown(event)
                // return true
            }
            MotionEvent.ACTION_MOVE -> {
                handleMotionEventMove(event)
                return true
            }
            MotionEvent.ACTION_UP -> {
                childrenSelectionOnMoveStart.clear()
                return true
            }
            MotionEvent.ACTION_CANCEL -> {
                childrenSelectionOnMoveStart.clear()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun handleMotionEventDown(event: MotionEvent) {
        children.forEachIndexed { index, view ->
            // 记录所有Chip的选中状态
            childrenSelectionOnMoveStart[view] = view.isSelected
            // 查找距离起始点最近的View
            closestViewIndex = findClosestViewIndex(event.x.roundToInt(), event.y.roundToInt())
        }
    }

    private fun handleMotionEventMove(event: MotionEvent) {
        val startViewIndex = closestViewIndex
        val endViewIndex = findClosestViewIndex(event.x.roundToInt(), event.y.roundToInt())

        val range = min(startViewIndex, endViewIndex)..max(startViewIndex, endViewIndex)
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            val originSelection = childrenSelectionOnMoveStart[view] == true
            view.isSelected = if (i in range) {
                // 反选拖动区域所有View的选中状态
                !originSelection
            } else {
                // 不在拖动区域，还原到初始选择状态
                originSelection
            }
        }
    }

    private fun findClosestViewIndex(x: Int, y: Int): Int {
        var finalViewIndex = -1
        val rect = Rect()
        var distance = Int.MAX_VALUE
        children.forEachIndexed { index, view ->
            view.getHitRect(rect)
            val d = getDistanceBetweenRectAndPoint(rect, x, y)
            if (d < distance) {
                distance = d
                finalViewIndex = index
            }
        }
        return finalViewIndex
    }

    private fun getDistanceBetweenRectAndPoint(rect: Rect, x: Int, y: Int): Int {
        val xDistance = if (x in rect.left..rect.right) {
            0
        } else (
            min(abs(x - rect.left), abs(x - rect.right))
        )
        val yDistance = if (y in rect.top..rect.bottom) {
            0
        } else {
            min(abs(y - rect.top), abs(y - rect.bottom))
        }
        return sqrt((xDistance * xDistance + yDistance * yDistance).toFloat()).roundToInt()
    }
}