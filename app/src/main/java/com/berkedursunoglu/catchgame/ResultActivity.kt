package com.berkedursunoglu.catchgame

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    var topScore: Int = 0
    var currentScore: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        sharedPreferences = this.getSharedPreferences("com.berkedursunoglu.catchgame", MODE_PRIVATE)
        topScore = sharedPreferences.getInt("score",0)
        currentScore = intent.getIntExtra("currentScore",0)
        topScoreText.text = "En yüksek skor: ${topScore.toString()}"
        currentScoreText.text = "Skorunuz: ${currentScore.toString()}"
        restartGame.setOnClickListener {
            startActivity(Intent(this,GameActivity::class.java))
            finish()
        }

    }
}