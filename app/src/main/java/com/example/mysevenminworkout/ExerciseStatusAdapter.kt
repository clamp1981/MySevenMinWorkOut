package com.example.mysevenminworkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mysevenminworkout.databinding.ItemExerciseStatusBinding


class ExerciseStatusAdapter(val items :ArrayList<ExerciseModel>) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    class ViewHolder( binding: ItemExerciseStatusBinding)
            : RecyclerView.ViewHolder(binding.root){
            val tvItem = binding.tvItem
        }

    //생성한 ViewHolder 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(ItemExerciseStatusBinding.inflate(
            LayoutInflater.from(parent.context), parent, false ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model : ExerciseModel = items[position]
        holder.tvItem.text = model.getId().toString()

        when {
            //선택되었을때..
            model.getSelected() -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable( holder.itemView.context,
                    R.drawable.item_circular_color_accent_border )

            }
            //완료 되었을때.
            model.getCompleted() ->{
                holder.tvItem.background =
                    ContextCompat.getDrawable( holder.itemView.context,
                        R.drawable.item_circular_color_accent_background )

            }
            //그외
            else -> {
                ContextCompat.getDrawable( holder.itemView.context,
                    R.drawable.item_circular_color_gray_background )

            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}