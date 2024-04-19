package com.example.a31p_quiz_app

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

import android.widget.Toast
import androidx.core.content.ContextCompat

class FIrstPage : AppCompatActivity() {

    private lateinit var progressBarQuiz: ProgressBar
    private lateinit var buttonSubmitAnswer: Button
    private lateinit var radioGroupOptions: RadioGroup
    private lateinit var displayUsername: TextView
    private lateinit var displayQuestion: TextView
    private lateinit var progressText: TextView

    private val questions = arrayOf(
        "What famous natural landmark is located in the Northern Territory of Australia?",
        "What natural landmark is located along the Great Ocean Road in Victoria, Australia?",
        "Which Australian city is known for its annual Australian Open tennis tournament?",
        "Which Australian city is famous for its iconic opera house?",
        "Which sport is considered the national sport of Australia?"
    )

    private val options = arrayOf(
        arrayOf("a) The Great Barrier Reef",
                "b) Uluru (Ayers Rock)",
                "c) The Twelve Apostles"),
        arrayOf("a) Sydney Opera House",
                "b) Great Barrier Reef",
                "c) The Twelve Apostles"),
        arrayOf("a) Melbourne",
                "b) Adelaide",
                "c) Perth"),
        arrayOf("a) Brisbane",
                "b) Sydney",
                "c) Perth"),
        arrayOf("a) Rugby",
                "b) Cricket",
                "c) Australian Football League (AFL)")
    )

    private val correctAnswers = arrayOf(
        "b) Uluru (Ayers Rock)", "c) The Twelve Apostles", "a) Melbourne", "b) Sydney", "c) Australian Football League (AFL)"
    )

    private var totalScore = 0
    private var currentQuestionIndex = 0
    private val handler = Handler()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)

        progressBarQuiz = findViewById(R.id.progressBar)
        buttonSubmitAnswer = findViewById(R.id.submitanswer)
        radioGroupOptions = findViewById(R.id.radiogroup)
        displayUsername = findViewById(R.id.usernameDisplay)
        displayQuestion = findViewById(R.id.questionDisplay)
        progressText = findViewById(R.id.progressText)

        val intent = intent
        val userName = intent.getStringExtra("UserName")
        displayUsername.text = userName
        loadQuestion()

        buttonSubmitAnswer.setOnClickListener {
            val selectedRadioButtonId = radioGroupOptions.checkedRadioButtonId
            val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)

//            if (selectedRadioButton != null && selectedRadioButton.text.toString() == correctAnswers[currentQuestionIndex]) {
//                selectedRadioButton.setBackgroundColor(resources.getColor(android.R.color.holo_green_light))
//                totalScore += 2
//            } else {
//                selectedRadioButton?.setBackgroundColor(resources.getColor(android.R.color.holo_red_light))
//            }


// Inside your if-else block
                    if (selectedRadioButton != null) {
                        val selectedText = selectedRadioButton.text.toString()
                        val correctText = correctAnswers[currentQuestionIndex]

                        if (selectedText == correctText) {
                            selectedRadioButton.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_light))
                            selectedRadioButton.setTypeface(null, Typeface.BOLD) // Set text bold
                            totalScore += 2
                        } else {
                            selectedRadioButton.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_light))
                            selectedRadioButton.setTypeface(null, Typeface.NORMAL) // Set text normal (non-bold)

                            // Highlight the correct answer
                            for (i in 0 until radioGroupOptions.childCount) {
                                val radioButton = radioGroupOptions.getChildAt(i) as RadioButton
                                if (radioButton.text.toString() == correctText) {
                                    radioButton.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_light))
                                    radioButton.setTypeface(null, Typeface.BOLD) // Set text bold for correct answer
                                    break
                                }
                            }
                        }
                    }


            handler.postDelayed({
                currentQuestionIndex++
                if (currentQuestionIndex < questions.size) {
                    loadQuestion()
                } else {
                    val intent = Intent(this@FIrstPage, finalPage::class.java)
                    intent.putExtra("username", userName)
                    intent.putExtra("finalscore", totalScore)
                    startActivity(intent)
                }
            }, 1000)
        }
    }

    private fun loadQuestion() {
        displayQuestion.text = questions[currentQuestionIndex]
        radioGroupOptions.removeAllViews()
        for (i in options[currentQuestionIndex].indices) {
            val radioButton = RadioButton(this)
            radioButton.text = options[currentQuestionIndex][i]
            radioButton.id = i
            radioGroupOptions.addView(radioButton)
        }
        val progress = ((currentQuestionIndex.toFloat() / questions.size) * 100).toInt()
        progressBarQuiz.progress = progress
        progressText.text = "Progress: $progress%"
    }
}
