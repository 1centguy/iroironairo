package com.onecentguy.iroironairo

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.util.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var tts: TextToSpeech
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private val colors = listOf(
        Pair("しろ", Color.WHITE),
        Pair("くろ", Color.BLACK),
        Pair("あか", Color.RED),
        Pair("あお", Color.BLUE),
        Pair("みどり", Color.GREEN),
        Pair("きいろ", Color.YELLOW),
        Pair("オレンジ", Color.rgb(255, 165, 0)),
        Pair("ピンク", Color.rgb(255, 105, 180)),
        Pair("むらさき", Color.rgb(128, 0, 128)),
        Pair("ちゃいろ", Color.rgb(210, 105, 30))
    )
    private var currentColorIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tts = TextToSpeech(this, this)
        handler = Handler()
        runnable = Runnable {
            changeColorAndSpeak()
            handler.postDelayed(runnable, 4000)
        }

    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.JAPAN)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported")
            } else {
                handler.postDelayed(runnable, 2000)
            }
        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }

    private fun changeColorAndSpeak() {
        val background: View = findViewById(R.id.background)
        val colorName = colors[currentColorIndex].first
        val colorValue = colors[currentColorIndex].second
        background.setBackgroundColor(colorValue)
        tts.speak(colorName, TextToSpeech.QUEUE_FLUSH, null, "")
        currentColorIndex = (currentColorIndex + 1) % colors.size
    }

    public override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }
}
