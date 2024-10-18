package com.umutgultekin.satelliteapp.common

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class NoAnimationItemAnimator : DefaultItemAnimator() {

    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        dispatchAddFinished(holder)
        dispatchAddStarting(holder)
        return false
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
        dispatchRemoveFinished(holder)
        dispatchRemoveStarting(holder)
        return false
    }

    override fun animateMove(
        holder: RecyclerView.ViewHolder,
        fromX: Int,
        fromY: Int,
        toX: Int,
        toY: Int
    ): Boolean {
        dispatchMoveFinished(holder)
        dispatchMoveStarting(holder)
        return false
    }

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder,
        newHolder: RecyclerView.ViewHolder,
        fromLeft: Int,
        fromTop: Int,
        toLeft: Int,
        toTop: Int
    ): Boolean {
        dispatchChangeFinished(oldHolder, true)
        dispatchChangeFinished(newHolder, false)
        dispatchChangeStarting(oldHolder, true)
        dispatchChangeStarting(newHolder, false)
        return false
    }

    override fun runPendingAnimations() {
        // Override this method and leave it empty to disable the pending animations
    }
}
