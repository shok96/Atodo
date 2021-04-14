package ru.andlancer.todofl.ui.ceditCard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import ru.andlancer.spacefit.data.db.Note
import ru.andlancer.todofl.DI
import ru.andlancer.todofl.R
import ru.andlancer.todofl.databinding.FragmentCeditCardBinding
import ru.andlancer.todofl.ui.base.viewBinding
import java.util.ArrayList
//экран редактирования
class ceditCardFragment : Fragment(R.layout.fragment_cedit_card) {

    private val bindingFragment by viewBinding { FragmentCeditCardBinding.bind(it) }
    private val ceditCardViewModel by activityViewModels<CeditCardViewModel>()

    var models: MutableList<Note> = ArrayList<Note>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(bindingFragment){
//кнопка сохранить
            submit.setOnClickListener {
                if(bindingFragment.desription.editText?.text.toString().isNotEmpty() && bindingFragment.note.editText?.text.toString().isNotEmpty())
                    saveNote(this)
                else
                    Toast.makeText(context, "Вы ввели не все данные", Toast.LENGTH_SHORT).show()
            }

            arguments?.getLong("id")?.let {
                loadNote(it)
            }
        }
    }
//сохранение данных
    fun saveNote(fragmentCeditCardBinding: FragmentCeditCardBinding, id: Long? = null) {
        ceditCardViewModel.addNote(fragmentCeditCardBinding, id)
            ?.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                if(it)
                    Navigation.findNavController(fragmentCeditCardBinding.root).popBackStack()
            })
    }
//загрузка данных для редактирования
    fun loadNote(id: Long){
        ceditCardViewModel.loadNote(id)
            ?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { note ->
                note?.let {
                    bindingFragment.desription.editText?.setText(it.title)
                    bindingFragment.note.editText?.setText(it.note)

                    bindingFragment.submit.setOnClickListener { view ->
                        saveNote(bindingFragment, it.id)
                    }
                }
            })
    }



}