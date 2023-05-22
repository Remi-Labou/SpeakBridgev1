package fr.Remi.speakbridge.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.Remi.speakbridge.MainActivity
import fr.Remi.speakbridge.R
import fr.Remi.speakbridge.adapter.Drapeau_adapter
import fr.remi.speakbridge.DrapeauModel

class LanguageFragment (
    private val context: MainActivity
        ): Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_language, container, false)

        // Créer une liste qui va stocker les drapeaux
        val drapeaulist= arrayListOf<DrapeauModel>()

        //enregistrer un premier drapeau
        drapeaulist.add(DrapeauModel("francais", "https://cdn.pixabay.com/photo/2013/07/13/14/15/france-162295_1280.png",false, 0.5F))

        //enregistrer un deuxieme drapeau
        drapeaulist.add(DrapeauModel("anglais", "https://cdn.pixabay.com/photo/2015/11/06/13/29/union-jack-1027898_1280.jpg",false, 0.5F))

        //enregistrer un troisieme drapeau
        drapeaulist.add(DrapeauModel("allemand", "https://cdn.pixabay.com/photo/2012/04/12/23/52/germany-31017_1280.png",false, 0.5F))

        //enregistrer un quatrieme drapeau
        drapeaulist.add(DrapeauModel("chinois", "https://cdn.pixabay.com/photo/2016/10/08/18/16/the-chinese-national-flag-1724256_1280.png",false, 0.5F))

        //enregistrer un cinquieme drapeau
        drapeaulist.add(DrapeauModel("espagnol", "https://cdn.pixabay.com/photo/2017/10/31/23/52/spain-2906824_1280.png",false, 0.5F))

        //enregistrer un sixieme drapeau
        drapeaulist.add(DrapeauModel("japonais", "https://cdn.pixabay.com/photo/2012/04/10/22/59/japan-26803_1280.png",false, 0.5F))

        //enregistrer un septieme drapeau
        drapeaulist.add(DrapeauModel("portugais", "https://cdn.pixabay.com/photo/2012/04/10/23/11/portugal-26886_1280.png",false, 0.5F))

        //enregistrer un huitieme drapeau
        drapeaulist.add(DrapeauModel("indien", "https://cdn.pixabay.com/photo/2012/04/10/23/03/india-26828_1280.png",false, 0.5F))



        //recuperer le recycler view
        val RecyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val layoutManager = GridLayoutManager(context, 2)
        RecyclerView.layoutManager = layoutManager

        RecyclerView.adapter = Drapeau_adapter(context, drapeaulist)

        return view
    }
}