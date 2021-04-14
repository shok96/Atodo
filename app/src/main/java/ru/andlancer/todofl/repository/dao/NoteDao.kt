package ru.andlancer.todofl.repository.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import ru.andlancer.spacefit.data.db.Note
import java.util.*
//интерфейсы для обращения к бд
@Dao
interface NoteDao {
//вставка записи
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note) : Maybe<Long>

//получение 1 записи
    @Query("SELECT * FROM Note Where id = :id")
    fun getNote(id: Int): Single<Note>

//получение активных записей
    @Query("SELECT * FROM Note WHERE archive = 0")
    fun getNotes(): Single<List<Note>>

    //получение архивных записей
    @Query("SELECT * FROM Note WHERE archive = 1")
    fun getArchive(): Single<List<Note>>

    //удаление записи
    @Delete
    fun deleteNote(note: Note) : Completable


    //обновление записи
    @Update
    fun updateNote(note: Note): Completable

}