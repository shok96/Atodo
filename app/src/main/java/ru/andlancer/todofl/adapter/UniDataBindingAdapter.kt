package ru.andlancer.todofl.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding

import androidx.recyclerview.widget.RecyclerView
import ru.andlancer.todofl.BR

//Класс для инициализации ViewBinding - с его помощью, можно преобразовтаь xml верстку в сгенерированный класс-обьект
class UniDataBindingAdapter<D, V : ViewDataBinding>(
    val data: List<D>,
    val viewBinding: (inflater: android.view.LayoutInflater, viewGroup: android.view.ViewGroup) -> V,
    val onBindViewHolder: (binding: V, item: D) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = viewBinding(LayoutInflater.from(parent.context), parent)
        return TemplateDataBindingViewHolder(binding as ViewDataBinding, data)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var Tholder = holder as TemplateDataBindingViewHolder<D>
        onBindViewHolder.invoke(Tholder.binding as V, data.get(position))
        holder.bind(data.get(position))

    }

    override fun getItemCount(): Int = data.size

   class TemplateDataBindingViewHolder<D>(val binding: ViewDataBinding, item: D) : RecyclerView.ViewHolder(binding.root) {

       fun bind(item: D){
           binding.setVariable(BR.item, item)
           binding.executePendingBindings()
       }

   }

}