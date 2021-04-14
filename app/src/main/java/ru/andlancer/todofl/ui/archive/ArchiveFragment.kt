package ru.andlancer.todofl.ui.archive

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import ru.andlancer.spacefit.data.db.Note
import ru.andlancer.todofl.DI
import ru.andlancer.todofl.MainActivity
import ru.andlancer.todofl.R
import ru.andlancer.todofl.adapter.UniDataBindingAdapter
import ru.andlancer.todofl.databinding.FragmentArchiveBinding
import ru.andlancer.todofl.databinding.FragmentHomeBinding
import ru.andlancer.todofl.databinding.ItemQueryBinding
import ru.andlancer.todofl.ui.base.viewBinding
import ru.andlancer.todofl.ui.home.HomeViewModel
import java.text.SimpleDateFormat
import java.util.ArrayList
//архивный экран
class ArchiveFragment : Fragment(R.layout.fragment_archive) {

    private val bindingFragment by viewBinding { FragmentArchiveBinding.bind(it) }
    private val archiveViewModel by activityViewModels<ArchiveViewModel>()
    var models: MutableList<Note> = ArrayList<Note>()
    var adapter: UniDataBindingAdapter<Note, ItemQueryBinding>? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //адаптер списка записей
        adapter = UniDataBindingAdapter<Note, ItemQueryBinding>(models, { inflater, viewGroup ->
            ItemQueryBinding.inflate(inflater, viewGroup, false)
        }) { binding, item ->
            //покараска в серый цвет
            binding.card.setBackgroundColor(Color.LTGRAY)
            //скрываем кнопку редактирования
            binding.edit.visibility = View.GONE
            //архивация/востановления
            binding.delete.setOnClickListener {
                deleteItem(item)
            }

            val format = SimpleDateFormat("dd/MM/yyy")
            binding.date.text = format.format(item.date)

        }

        with(bindingFragment) {
            rec.adapter = adapter
            rec.layoutManager = LinearLayoutManager(context)


        }

        getData()


    }
//получить список
    fun getData() {
        archiveViewModel.getQuery()
            ?.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                models.clear()
                models.addAll(it)
                adapter?.notifyDataSetChanged()
            })
    }
//архивация востановления
    fun deleteItem(item: Note) {
        archiveViewModel.deleteQuery(item)
            ?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { note ->
                note?.let {
                    models.remove(it)
                    adapter?.notifyDataSetChanged()
                }
            })
    }


}