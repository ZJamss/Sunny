package com.zjamss.sunny.ui.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zjamss.sunny.R
import com.zjamss.sunny.databinding.FragmentPlaceBinding
import com.zjamss.sunny.logic.model.Place
import com.zjamss.sunny.util.GlobalUtil.toast

/**
 * @Program: Sunny
 * @Description:
 * @Author: ZJamss
 * @Create: 2022-02-15 22:18
 **/
class PlaceFragment : Fragment() {
    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }

    private lateinit var adapter: PlaceAdapter
//    private lateinit var binding: FragmentPlaceBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        binding = FragmentPlaceBinding.inflate(layoutInflater)
        val layoutManager = LinearLayoutManager(activity)
        val recyclerView = activity?.findViewById<RecyclerView>(R.id.recyclerView)!!
        val searchPlaceEdit = activity?.findViewById<EditText>(R.id.searchPlaceEdit)!!
        val bgImageView = activity?.findViewById<ImageView>(R.id.bgImageView)!!
        adapter = PlaceAdapter(this,viewModel.placeList)
        recyclerView.adapter = adapter
        searchPlaceEdit.addTextChangedListener {
            val content = it.toString()
            if (content.isNotEmpty()) {
                viewModel.searchPlaces(content)
            } else {
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result ->
            val places = result.getOrNull()
            if(places != null){
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            }else {
                "未能查询到任何地点，请重新输入".toast()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }
}