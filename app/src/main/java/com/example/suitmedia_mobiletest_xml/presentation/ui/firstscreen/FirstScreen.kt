package com.example.suitmedia_mobiletest_xml.presentation.ui.firstscreen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.suitmedia_mobiletest_xml.R
import com.example.suitmedia_mobiletest_xml.presentation.ui.secondscreen.SecondScreen
import com.example.suitmedia_mobiletest_xml.presentation.viewmodel.PalindromeViewModel

class FirstScreen : AppCompatActivity() {

    private val viewModel: PalindromeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_screen)

        val etName: EditText = findViewById(R.id.etName)
        val etSentence: EditText = findViewById(R.id.etSentence)
        val btnCheck: Button = findViewById(R.id.btnCheck)
        val btnNext: Button = findViewById(R.id.btnNext)

        btnCheck.setOnClickListener {
            val name = etName.text.toString()
            val sentence = etSentence.text.toString()
            if (name.isEmpty()) {
                showAlert("Name cannot be empty")
            } else if (sentence.isEmpty()) {
                showAlert("Please enter a sentence to check palindrome")
            } else {
                viewModel.checkPalindrome(sentence)
            }
        }

        viewModel.isPalindrome.observe(this) { isPalindrome ->
            val message = if (isPalindrome) "isPalindrome" else "not palindrome"
            AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show()
        }

        btnNext.setOnClickListener {
            val name = etName.text.toString()
            if (name.isEmpty()) {
                showAlert("Name cannot be empty")
            } else {
                val intent = Intent(this, SecondScreen::class.java).apply {
                    putExtra("EXTRA_NAME", name)
                }
                startActivity(intent)
            }
        }
    }

    private fun showAlert(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}
