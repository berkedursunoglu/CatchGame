package com.berkedursunoglu.catchgame

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*

class GameActivity : AppCompatActivity() {
    private var runnableTimer: Runnable = Runnable {  }
    private var runnableBaloon: Runnable = Runnable {  }
    private var handler: Handler = Handler()
    private var timer:Int = 0
    private var screenWitdh:Int = 0
    private var screenHeight:Int = 0
    private var score:Int = 0
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        sharedPreferences = this.getSharedPreferences("com.berkedursunoglu.catchgame", MODE_PRIVATE)
        scoreText.text = "Skor: $score"
        game()
        getScreenDisplay()
        baloon.setOnClickListener {
            explosionsBaloon()
            score++
            scoreText.text = "Skor: $score"
        }
    }

    private fun game(){
        scoreText.text = "Skor: 0"
        timerText.text = "Kalan Zaman: 10"
        timer = 20
        runnableTimer = object :Runnable{
            val intent = Intent(this@GameActivity,ResultActivity::class.java)
            override fun run() {
                timer -= 1
                timerText.text = "Kalan Zaman: $timer"
                handler.postDelayed(this,1000)
                if (timer == 0){
                    handler.removeCallbacks(this)
                    var lastscore = sharedPreferences.getInt("score",0)
                    if (lastscore < score){
                        sharedPreferences.edit().putInt("score",score).apply()
                        startActivity(intent)
                        finish()
                    }else{
                        intent.putExtra("currentScore",score)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
        runnableBaloon = object :Runnable{
            override fun run() {
                imageViewRandom()
                handler.postDelayed(this,500)
                if (timer == 0){
                    handler.removeCallbacks(this)
                }
            }

        }
        handler.post(runnableTimer)
        handler.post(runnableBaloon)
    }

    private fun imageViewRandom(){
        var random:Random = Random()
        var imageWitdh = (0..screenWitdh-256).random()
        var imageHeight = (0..screenHeight-256).random()
        baloon.translationX = (imageWitdh).toFloat()
        baloon.translationY = (imageHeight).toFloat()
        baloon.setImageResource(R.drawable.balloon)
    }

    private fun getScreenDisplay(){
        var constraintLayout = findViewById<ConstraintLayout>(R.id.constraintLayout)
        constraintLayout.viewTreeObserver.addOnGlobalLayoutListener {
            screenWitdh = constraintLayout.width
            screenHeight = constraintLayout.height
            println("$screenWitdh witdh")
            println("$screenHeight height")
        }

    }

    private fun explosionsBaloon(){
        baloon.setImageResource(R.drawable.explosion)
    }

}