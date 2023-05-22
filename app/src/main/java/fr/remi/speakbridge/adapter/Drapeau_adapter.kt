package fr.Remi.speakbridge.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.Remi.speakbridge.MainActivity
import fr.Remi.speakbridge.R
import fr.remi.speakbridge.DrapeauModel

class Drapeau_adapter (
    private val context: MainActivity,
    private val drapeauList: List<DrapeauModel>
        ): RecyclerView.Adapter<Drapeau_adapter.ViewHolder>(){

    //boite pour ranger tous les composants à controller
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        //image du drapeau
        val drapeauImage = view.findViewById<ImageView>(R.id.image_item)
        val drapeauSelect = view.findViewById<ImageView>(R.id.image_item)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_drapeaux, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return drapeauList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //recuperer les informations du drapeau
        val currentDrapeau = drapeauList[position]

        //recupeter l'image et l'ajouter à son composant
        Glide.with(context).load(Uri.parse(currentDrapeau.imageURL)).into(holder.drapeauImage)

        // Set the opacity based on the selected state
        holder.drapeauImage.alpha = if (currentDrapeau.isSelected) 1.0f else 0.4f

        // Set click listener to handle flag selection
        holder.drapeauImage.setOnClickListener {
            // Deselect all flags
            for (drapeau in drapeauList) {
                drapeau.isSelected = false
            }

            // Select the clicked flag
            currentDrapeau.isSelected = true

            // Notify adapter about the data change
            notifyDataSetChanged()
        }

    }
}