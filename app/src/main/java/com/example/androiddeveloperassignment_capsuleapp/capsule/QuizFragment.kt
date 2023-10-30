package com.example.androiddeveloperassignment_capsuleapp.capsule

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.androiddeveloperassignment_capsuleapp.R
import com.example.androiddeveloperassignment_capsuleapp.databinding.FragmentQuizBinding
import com.example.androiddeveloperassignment_capsuleapp.modal.QuizQuestions
import com.example.androiddeveloperassignment_capsuleapp.viewModal.QuizViewModal

class QuizFragment : Fragment(), View.OnClickListener {
    lateinit var viewModal: QuizViewModal
    private lateinit var binding: FragmentQuizBinding


    private lateinit var allQuestions: ArrayList<QuizQuestions>
    private lateinit var selectedQuestions: ArrayList<QuizQuestions>
    private val numberOfQues: Long = 5

    private lateinit var countDownTimer: CountDownTimer
    private var isCorrect: Boolean = false
    private var current: Int = 0
    private var right: Int = 0
    private var wrong: Int = 0
    private var notAnswer: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentQuizBinding.inflate(inflater, container, false);

        initViews()
        setListeners()
        getQuestionsList()

        return binding.root

    }

    private fun initViews() {
        allQuestions = ArrayList()
        selectedQuestions = ArrayList()
    }

    private fun getQuestionsList() {
        viewModal = ViewModelProvider(requireActivity())[QuizViewModal::class.java]
        viewModal.getQuizData()
        viewModal.getQuizMutableData()
            .observe(
                viewLifecycleOwner
            ) { list ->
                allQuestions = list as ArrayList<QuizQuestions>
                takeQuestion()
                updateUI()
            }
    }

    private fun updateUI() {


        enableOptions()

        loadQuestions(1)

    }

    private fun loadQuestions(questionNo: Int) {
        binding.question.text = selectedQuestions[questionNo].question
        binding.optionA.text = selectedQuestions[questionNo].a
        binding.optionB.text = selectedQuestions[questionNo].b
        binding.optionC.text = selectedQuestions[questionNo].c
        binding.optionD.text = selectedQuestions[questionNo].d

        startTimer(1)
        isCorrect = true

        binding.progress.visibility = View.GONE
        binding.scrollView.visibility = View.VISIBLE

    }

    private fun startTimer(questionNo: Int) {

        val timeToAns: Long = selectedQuestions[questionNo].timer

        binding.time.text = timeToAns.toString()

        //start countdown
        countDownTimer = object : CountDownTimer(timeToAns * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.time.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                isCorrect = false
                showBtn()

            }
        }
        countDownTimer.start()

        notAnswer++


    }

    private fun enableOptions() {
        binding.optionA.visibility = View.VISIBLE
        binding.optionB.visibility = View.VISIBLE
        binding.optionC.visibility = View.VISIBLE
        binding.optionD.visibility = View.VISIBLE

        //enable options

        binding.optionA.isEnabled = true
        binding.optionB.isEnabled = true
        binding.optionC.isEnabled = true
        binding.optionD.isEnabled = true

        binding.btnNext.visibility = View.INVISIBLE
        binding.btnNext.isEnabled = true
    }


    private fun setListeners() {
        binding.btnNext.setOnClickListener(this)
        binding.optionA.setOnClickListener(this)
        binding.optionB.setOnClickListener(this)
        binding.optionC.setOnClickListener(this)
        binding.optionD.setOnClickListener(this)
    }


    override fun onClick(view: View?) {
        when (view) {
            binding.btnNext -> {
                if (current == numberOfQues.toInt()) {
                    submitResult()
                } else {
                    current++
                    loadQuestions(current)
                    reset()
                }

            }

            binding.optionA -> checkAns(binding.optionA)
            binding.optionB -> checkAns(binding.optionB)
            binding.optionC -> checkAns(binding.optionC)
            binding.optionD -> checkAns(binding.optionD)
        }
    }

    private fun submitResult() {
        binding.topConstraint.visibility = View.GONE
        binding.scrollView.visibility = View.GONE
        binding.result.visibility = View.VISIBLE
        binding.result.text = "Correct Ans: $right\nWrong Ans: $wrong"
    }


    private fun checkAns(selectTextView: TextView) {
        if (isCorrect) {

            if (selectedQuestions[current].ans == selectTextView.text) {
                selectTextView.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.teal_700
                    )
                )
                right++
                binding.cAns.text = right.toString()
            } else {
                binding.optionA.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red
                    )
                )
                wrong++
                binding.wAns.text = wrong.toString()

            }
            isCorrect = false
            countDownTimer.cancel()
            showBtn()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showBtn() {

        if (current == numberOfQues.toInt()) {
            binding.btnNext.text = "Go to result"
        }
        binding.btnNext.visibility = View.VISIBLE
        binding.btnNext.isEnabled = true
    }

    private fun reset() {
        binding.optionA.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.optionB.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.optionC.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.optionD.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))

        binding.btnNext.visibility = View.INVISIBLE
    }

    private fun getRandomInteger(max: Int, min: Int): Int {
        return ((Math.random() * (max - min)) + min).toInt()
    }

    private fun takeQuestion() {
        for (i in 0..numberOfQues) {
            val randomNum = getRandomInteger(allQuestions.size, 0)
            selectedQuestions.add(allQuestions[randomNum])
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d("state", "on destroy calling")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("state", "on destroy view calling")

    }

    override fun onPause() {
        super.onPause()

    }

    override fun onStop() {
        super.onStop()
        Log.d("state", "on stop calling")

    }

    override fun onResume() {
        super.onResume()

    }

}