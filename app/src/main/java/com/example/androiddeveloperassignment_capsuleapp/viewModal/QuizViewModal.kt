package com.example.androiddeveloperassignment_capsuleapp.viewModal

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androiddeveloperassignment_capsuleapp.modal.QuizQuestions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class QuizViewModal : ViewModel() {
    private var liveData: MutableLiveData<List<QuizQuestions>> = MutableLiveData()


    private val firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val quizRef: CollectionReference =
        firebaseFireStore.collection("QuizQuestions").document("zHbWUcXxHQn1yKoSlM5E")
            .collection("demo")


    fun getQuizData() {

        quizRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("FireStoreData", task.result.toObjects(QuizQuestions::class.java).toString())
                liveData.value = task.result.toObjects(QuizQuestions::class.java)

            } else {
                Log.e("FireStoreError", task.exception?.message.toString())
            }
        }


    }

    fun getQuizMutableData(): MutableLiveData<List<QuizQuestions>> {
        return liveData
    }


}