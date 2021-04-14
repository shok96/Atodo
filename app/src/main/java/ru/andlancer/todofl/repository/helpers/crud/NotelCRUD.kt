package ru.andlancer.todofl.repository.helpers.crud

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.andlancer.spacefit.data.db.Note

import ru.andlancer.todofl.repository.helpers.HelperDB
import java.util.*

//реализация интерфейсов запросов к базе
class NotelCRUD(val dataBaseInstance: HelperDB) {

    protected val compositeDisposable = CompositeDisposable()


    fun saveNote(data: Note, result: (Boolean, Long?) -> Unit){

        dataBaseInstance?.noteDao()?.insertNote(data)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe ({
                result(true, it)
            },{
                result(false, null)
            })?.let {
                compositeDisposable.add(it)
            }
    }



    fun updateNote(data: Note, result: (Boolean) -> Unit){

        dataBaseInstance?.noteDao()?.updateNote(data)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe ({
                result(true)
            },{
                result(false)
            })?.let {
                compositeDisposable.add(it)
            }
    }

    fun getNote(id:Int): Single<Note> {

        val func = dataBaseInstance?.noteDao()?.getNote(id)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())

            return func

    }




    fun getNotes(): Single<List<Note>> {

        val func = dataBaseInstance?.noteDao()?.getNotes()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())

        return func

    }

    fun getArchive(): Single<List<Note>> {

        val func = dataBaseInstance?.noteDao()?.getArchive()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())

        return func

    }

     fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }

    fun deleteNote(data: Note, result: (Boolean) -> Unit) {
        dataBaseInstance?.noteDao()?.deleteNote(data)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe ({
                result(true)
            },{
                result(false)
            })?.let {
                compositeDisposable.add(it)
            }
    }

}