package ru.andlancer.todofl.ui.home


import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.View

import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import ru.andlancer.spacefit.data.db.Note

import ru.andlancer.todofl.DI
import ru.andlancer.todofl.MainActivity
import ru.andlancer.todofl.R
import ru.andlancer.todofl.adapter.UniDataBindingAdapter
import ru.andlancer.todofl.databinding.FragmentHomeBinding
import ru.andlancer.todofl.databinding.ItemQueryBinding

import ru.andlancer.todofl.ui.base.viewBinding
import java.text.SimpleDateFormat

import java.util.*

//экран активных записей
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val bindingFragment by viewBinding { FragmentHomeBinding.bind(it) }
    private val homeViewModel by activityViewModels<HomeViewModel>()
    var models: MutableList<Note> = ArrayList<Note>()
    var adapter: UniDataBindingAdapter<Note, ItemQueryBinding>? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//адаптер списка активных записей
        adapter = UniDataBindingAdapter<Note, ItemQueryBinding>(models, { inflater, viewGroup ->
            ItemQueryBinding.inflate(inflater, viewGroup, false)
        }) { binding, item ->
//редактирование
            binding.edit.setOnClickListener {
                val b = Bundle()
                b.putLong("id", item.id ?: 0)
                Navigation.findNavController(it)
                    .navigate(R.id.action_homeFragment_to_ceditCardFragment, b)
            }
//архив/востановление
            binding.delete.setOnClickListener {
                deleteItem(item)
            }

            val format = SimpleDateFormat("dd/MM/yyy")
            binding.date.text = format.format(item.date)

        }

        with(bindingFragment) {
            rec.adapter = adapter
            rec.layoutManager = LinearLayoutManager(context)

            fab.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_homeFragment_to_ceditCardFragment)
            }
        }

        getData()


    }
//получаем активные данные
    fun getData() {
        homeViewModel.getQuery()
            ?.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                models.clear()
                models.addAll(it)
                adapter?.notifyDataSetChanged()
            })
    }
//архив/востановление
    fun deleteItem(item: Note) {
        homeViewModel.deleteQuery(item)
            ?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { note ->
                note?.let {
                    models.remove(it)
                    adapter?.notifyDataSetChanged()
                }
            })
    }


}