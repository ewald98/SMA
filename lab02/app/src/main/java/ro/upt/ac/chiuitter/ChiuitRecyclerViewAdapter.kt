package ro.upt.ac.chiuitter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.item_chiuit.view.*

class ChiuitRecyclerViewAdapter(
        private val chiuitList: MutableList<Chiuit>,
        private val onClick: (Chiuit) -> (Unit))
    : RecyclerView.Adapter<ChiuitRecyclerViewAdapter.ChiuitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChiuitViewHolder {
        // Inflate the item layout and return the view holder
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chiuit, parent, false) as MaterialCardView

        return ChiuitViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        // Return the size of samples
        return chiuitList.size
    }

    override fun onBindViewHolder(holder: ChiuitViewHolder, position: Int) {
        // Bind the view holder with chiuit data sample
        holder.bind(chiuitList[position])
    }

    fun addItem(chiuit: Chiuit) {
        // Add the new item to the list then SMARTLY notify an addition
        chiuitList.add(chiuit)
        notifyItemInserted(itemCount - 1)
    }

    inner class ChiuitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.ibt_share.setOnClickListener { onClick(chiuitList[adapterPosition]) }
        }

        fun bind(chiuit: Chiuit) {
            // Set the text view with the content of chiuit's description
            itemView.txv_content.text = chiuit.description
        }

    }

}