package ru.andlancer.todofl.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.andlancer.spacefit.data.db.Note
import ru.andlancer.todofl.DI
import ru.andlancer.todofl.repository.helpers.HelperDB
import ru.andlancer.todofl.repository.helpers.crud.NotelCRUD

class HomeViewModel : ViewModel() {
    var context = DI.appComponent.context()
    val note = NotelCRUD(HelperDB.getDatabasenIstance(context))

    private var _getNote: MutableLiveData<List<Note>>? = null
    private var _deleteNote: MutableLiveData<Note>? = null
    //получение данных из базы в ассинхронном режиме
    fun getQuery(): LiveData<List<Note>>? {

        _getNote = MutableLiveData<List<Note>>()

        NotelCRUD(HelperDB.getDatabasenIstance(context)).getNotes().subscribe(
            {
                    result ->
                _getNote?.postValue(result)
                Log.e("TAG","Получение записей")

            },
            {

            }
        )

        return _getNote
    }
    //архивация/востановление данных из базы в ассинхронном режиме
    fun deleteQuery(item: Note): LiveData<Note>? {

        _deleteNote = MutableLiveData<Note>()

        item.archive = true

        note.updateNote(
            item
        ) { bool ->
            if (bool)
                _deleteNote?.postValue(item)
            else
                _deleteNote?.postValue(null)
        }

        return _deleteNote
    }



}