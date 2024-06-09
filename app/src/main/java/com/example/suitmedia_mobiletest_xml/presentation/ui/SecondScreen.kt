package com.example.suitmedia_mobiletest_xml.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.suitmedia_mobiletest_xml.R

class SecondScreen : AppCompatActivity() {

    private lateinit var tvSelectedUser: TextView

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedUserName = result.data?.getStringExtra("SELECTED_USER")
            tvSelectedUser.text = selectedUserName
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_screen)

        val ivBack: ImageView = findViewById(R.id.ivBack)
        ivBack.setOnClickListener {
            finish()
        }

        val tvName: TextView = findViewById(R.id.tvName)
        tvSelectedUser = findViewById(R.id.tvSelectedUser)
        val btnChooseUser: Button = findViewById(R.id.btnChooseUser)

        val name = intent.getStringExtra("EXTRA_NAME")
        tvName.text = name

        btnChooseUser.setOnClickListener {
            val intent = Intent(this, ThirdScreen::class.java)
            startForResult.launch(intent)
        }
    }
}
