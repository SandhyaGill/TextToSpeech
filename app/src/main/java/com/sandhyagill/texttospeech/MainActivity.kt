package com.sandhyagill.texttospeech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import com.sandhyagill.texttospeech.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    lateinit var binding : ActivityMainBinding
    lateinit var texttospeak : TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        texttospeak = TextToSpeech(this,this)

        binding.btnSpeak.setOnClickListener {
            val text = binding.etTypeHere.text.toString()
            if (text.isNotEmpty()){
                speakOut(text)
            }else{
                Toast.makeText(this, resources.getString(R.string.please_enter_text), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = texttospeak.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, resources.getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, resources.getString(R.string.texttospeak_initialization_failed), Toast.LENGTH_SHORT).show()
        }
    }

    private fun speakOut(text: String) {
        texttospeak.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onDestroy() {
        if (texttospeak.isSpeaking) {
            texttospeak.stop()
        }
        texttospeak.shutdown()
        super.onDestroy()
    }
}