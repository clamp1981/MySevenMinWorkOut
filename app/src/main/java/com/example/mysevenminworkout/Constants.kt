package com.example.mysevenminworkout

object Constants {

    fun getDefultExerciseModel() : ArrayList<ExerciseModel> {
        var Exercises = ArrayList<ExerciseModel>()
        Exercises.add( ExerciseModel(0, "Jumping Jack", R.drawable.ic_jumping_jacks ) )
        Exercises.add( ExerciseModel(1, "High Knees running", R.drawable.ic_high_knees_running_in_place ) )
        Exercises.add( ExerciseModel(2, "Abdominal crunch", R.drawable.ic_abdominal_crunch ) )

        return Exercises


    }
}