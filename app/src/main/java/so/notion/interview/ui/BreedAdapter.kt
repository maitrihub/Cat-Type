package so.notion.interview.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import so.notion.interview.R
import so.notion.interview.data.model.CatResponse

class BreedAdapter(
    private val breeds: List<CatResponse>
) : RecyclerView.Adapter<BreedAdapter.BreedViewHolder>() {

    //represents one row in the recycler view
    inner class BreedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.cat_name)
        val originText: TextView = itemView.findViewById(R.id.cat_origin)
        val descriptionText: TextView = itemView.findViewById(R.id.cat_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return BreedViewHolder(view)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        val breed = breeds[position]
        holder.nameText.text = breed.name
        holder.originText.text = breed.origin
        holder.descriptionText.text = breed.description
    }

    override fun getItemCount(): Int {
        return breeds.size
    }

}