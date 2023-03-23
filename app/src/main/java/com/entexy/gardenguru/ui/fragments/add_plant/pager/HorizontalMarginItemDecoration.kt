import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalMarginItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val horizontalMarginInPx: Int = (30F * context.resources.displayMetrics.density).toInt()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.right = horizontalMarginInPx
        outRect.left = horizontalMarginInPx
    }
}