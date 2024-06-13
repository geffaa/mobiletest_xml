package com.example.suitmedia_mobiletest_xml.presentation.ui.secondscreen

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suitmedia_mobiletest_xml.R
import com.example.suitmedia_mobiletest_xml.presentation.ui.thirdscreen.ThirdScreen

class SecondScreen : AppCompatActivity() {

    private lateinit var tvSelectedUser: TextView
    private lateinit var countdownTimer: CountDownTimer
    private lateinit var flashSaleContainer: LinearLayout
    private lateinit var rvFlashSaleList: RecyclerView

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

        flashSaleContainer = findViewById(R.id.flashSaleContainer)
        rvFlashSaleList = findViewById(R.id.rvFlashSaleList)

        val tvCountdown: TextView = findViewById(R.id.tvCountdown)
        val flashSaleDuration = 10000L // Example: 60 seconds for demo

        countdownTimer = object : CountDownTimer(flashSaleDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val minutes = seconds / 60
                val remainingSeconds = seconds % 60
                tvCountdown.text = String.format("%02d:%02d", minutes, remainingSeconds)
            }

            override fun onFinish() {
                flashSaleContainer.visibility = View.GONE
                rvFlashSaleList.visibility = View.GONE
            }
        }

        setupFlashSaleItems()

        flashSaleContainer.visibility = View.VISIBLE
        rvFlashSaleList.visibility = View.VISIBLE
        countdownTimer.start()
    }

    private fun setupFlashSaleItems() {
        rvFlashSaleList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val flashSaleItems = listOf("Item 1", "Item 2", "Item 3") // Example items
        val adapter = FlashSaleAdapter(flashSaleItems)
        rvFlashSaleList.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        countdownTimer.cancel()
    }
}

class FlashSaleAdapter(private val items: List<String>) : RecyclerView.Adapter<FlashSaleAdapter.FlashSaleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashSaleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_flash_sale, parent, false)
        return FlashSaleViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlashSaleViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class FlashSaleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.textView)

        fun bind(item: String) {
            textView.text = item
        }
    }
}
