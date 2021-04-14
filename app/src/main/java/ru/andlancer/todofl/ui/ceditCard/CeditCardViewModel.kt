package ru.andlancer.todofl.ui.ceditCard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.andlancer.spacefit.data.db.Note
import ru.andlancer.todofl.DI
import ru.andlancer.todofl.databinding.FragmentCeditCardBinding
import ru.andlancer.todofl.notifications.BuildNotifications
import ru.andlancer.todofl.repository.helpers.HelperDB
import ru.andlancer.todofl.repository.helpers.crud.NotelCRUD

class CeditCardViewModel : ViewModel() {
    val context = DI.appComponent.context()
    val note = NotelCRUD(HelperDB.getDatabasenIstance(context))

    private var _saveNote: MutableLiveData<Boolean>? = null
    private var _loadNote: MutableLiveData<Note>? = null
        //добавление/обновление новой карточки
    fun addNote(fragmentCeditCardBinding: FragmentCeditCardBinding, id: Long? = null): LiveData<Boolean>? {

        _saveNote = MutableLiveData<Boolean>()

        if(id != null){
            note.updateNote(
                Note(id = id,
                    title = fragmentCeditCardBinding.desription.editText?.text.toString(),
                    note = fragmentCeditCardBinding.note.editText?.text.toString()
                )
            ) { bool ->
                _saveNote?.postValue(bool)
                Log.e("TAG","Заметка обновлена")
            }
        }
        else {
            note.saveNote(
                Note(
                    title = fragmentCeditCardBinding.desription.editText?.text.toString(),
                    note = fragmentCeditCardBinding.note.editText?.text.toString()
                )
            ) { bool, res ->
                _saveNote?.postValue(bool)
                BuildNotifications(context).createNotification("Уведомление", "Новая заметка")
                Log.e("TAG","Новая заметка")
            }
        }

        return _saveNote
    }
//загрузка данных
    fun loadNote(id: Long): LiveData<Note>? {

        _loadNote = MutableLiveData<Note>()

        note.getNote(id.toInt()).subscribe(
            {
                res ->
                _loadNote?.postValue(res)
            },
            {

            }
        )

        return _loadNote
    }

}