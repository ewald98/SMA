package lab_sma.lab00

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        val newText = TextHolder("Bye World!")

        val mainTextView: TextView = findViewById(R.id.mainTextView)
        val mainButton: Button = findViewById(R.id.mainButton)

        mainButton.setOnClickListener {
            mainTextView.text = newText.text
        }

    }
}