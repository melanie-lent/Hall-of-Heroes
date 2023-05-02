package com.example.hallofheroes

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.hallofheroes.api.HeroFetcher
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TAG: String = "HeroFragment"

class HeroFragment : Fragment() {
    private lateinit var hero: Hero
    private lateinit var dataRecyclerView: RecyclerView
    private val responseLiveData: MutableLiveData<List<Hero>> = MutableLiveData()
    private var adapter: HeroAdapter = HeroAdapter(emptyList())
//    private val heroViewModel: HeroViewModel by lazy {
//        ViewModelProviders.of(this).get(HeroViewModel::class.java)
//    }

    private fun updateUI(spells: List<Hero>) {
        adapter = HeroAdapter(spells)
        dataRecyclerView.adapter = adapter
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recycler_view, container, false)
        dataRecyclerView = view.findViewById(R.id.data_recycler_view)
        dataRecyclerView.layoutManager = LinearLayoutManager(context)
//        Log.d(TAG, "Called onCreateView")

        HeroFetcher().fetchHeroes(responseLiveData)
        responseLiveData.observe(
            viewLifecycleOwner,
            Observer { responseString ->

//                Log.d(TAG, "Response received: $responseString")
                updateUI(responseString)
            }
        )
        return view
    }


    private inner class HeroHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var hero: Hero

        private val nameField: TextView = itemView.findViewById(R.id.name)
        private val birthYearField: TextView = itemView.findViewById(R.id.birth_year)

        fun bind(hero: Hero) {
            Log.d(TAG, "Hero Data: ${hero.name}")
            nameField.text = hero.name
            birthYearField.text = hero.birth_year
        }
    }


    private inner class HeroAdapter(var spells: List<Hero>): RecyclerView.Adapter<HeroHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroHolder {
            val view = layoutInflater.inflate(R.layout.list_item_hero, parent, false)
            return HeroHolder(view)
        }

        override fun onBindViewHolder(holder: HeroHolder, position: Int) {
            val spell = spells[position]
            holder.bind(spell)
        }

        override fun getItemCount() = spells.size
    }

    companion object {
        fun newInstance() = HeroFragment()
    }
}
