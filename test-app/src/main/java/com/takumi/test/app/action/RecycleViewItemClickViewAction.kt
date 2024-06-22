package com.takumi.test.app.action

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.util.HumanReadables
import org.hamcrest.Matcher
import org.hamcrest.Matchers

fun <VH : RecyclerView.ViewHolder> actionOnItemAtPosition(
    position: Int, clickId: Int, viewAction: ViewAction
): RecycleViewItemClickViewAction<VH> {
    return RecycleViewItemClickViewAction<VH>(
        position = position, clickId = clickId, viewAction = viewAction
    )
}

class RecycleViewItemClickViewAction<VH : RecyclerView.ViewHolder>(
    private val position: Int, private val clickId: Int, private val viewAction: ViewAction
) : ViewAction {

    override fun getDescription(): String {
        return ("actionOnItemAtPosition performing ViewAction: ${viewAction.description} on item at position: $position and clickId $clickId")
    }

    override fun getConstraints(): Matcher<View> {
        return Matchers.allOf(
            ViewMatchers.isAssignableFrom(
                RecyclerView::class.java
            ), ViewMatchers.isDisplayed()
        )
    }

    override fun perform(uiController: UiController?, view: View?) {
        val recyclerView = view as? RecyclerView ?: throw PerformException.Builder()
            .withActionDescription(this.toString())
            .withViewDescription(HumanReadables.describe(view))
            .withCause(IllegalStateException("View is not RecyclerView")).build()

        recyclerView.scrollToPosition(position)
        uiController?.loopMainThreadUntilIdle()

        val viewHolderForPosition = recyclerView.findViewHolderForAdapterPosition(position) as VH?
            ?: throw PerformException.Builder().withActionDescription(this.toString())
                .withViewDescription(HumanReadables.describe(view))
                .withCause(IllegalStateException("No view holder at position: $position")).build()

        val clickView = viewHolderForPosition.itemView.findViewById<View>(clickId)

        viewAction.perform(uiController, clickView)
    }
}