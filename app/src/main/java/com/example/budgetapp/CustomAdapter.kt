import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetapp.entity.FeedbacksViewModel
import com.example.budgetapp.R
import com.example.budgetapp.UpdateAndDeleteFeedbackActivity
import com.example.budgetapp.config.FirebaseHelper

class CustomAdapter(private val mList: List<FeedbacksViewModel>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_user_side_users_feedback_list_card_view, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        holder.shopNameTextView.setText(ItemsViewModel.shopName.toString())
        holder.feedbackTextView.setText(ItemsViewModel.feedback.toString())
        holder.ratingTextView.setText(ItemsViewModel.rating.toString())
        holder.editButton.setOnClickListener({
            val intent = Intent(holder.itemView.context,UpdateAndDeleteFeedbackActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("reference",ItemsViewModel.reference.toString())
            holder.itemView.context.startActivity(intent)
        })
        holder.deleteButton.setOnClickListener({
            val firebaseHelper = FirebaseHelper()
            firebaseHelper.getData("feedbacks/" + ItemsViewModel.reference).removeValue()
            Toast.makeText(holder.itemView.context
                ,"Feedback Deletion is Successfull",Toast.LENGTH_LONG).show()
        })
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val shopNameTextView: TextView = itemView.findViewById(R.id.shopNameText)
        val feedbackTextView: TextView = itemView.findViewById(R.id.feedbackText)
        val ratingTextView: TextView = itemView.findViewById(R.id.ratingText)
        val deleteButton: ImageView = itemView.findViewById(R.id.btnDelete)
        val editButton: ImageView = itemView.findViewById(R.id.btnEdit)
    }
}
