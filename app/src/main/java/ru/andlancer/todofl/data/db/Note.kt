package ru.andlancer.spacefit.data.db

import androidx.room.*
import ru.andlancer.todofl.repository.converter.DateConverter
import java.util.*
//таблица для хранения данных
@Entity(tableName = "Note")
@TypeConverters(DateConverter::class)
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var title: String = "",
    var date: Date = Date(),
    var note: String = "",
    var archive: Boolean = false
)
