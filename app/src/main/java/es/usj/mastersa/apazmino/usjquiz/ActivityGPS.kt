package es.usj.mastersa.apazmino.usjquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_gps.*

class ActivityGPS : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gps)
        btnBack.setOnClickListener{ back()}
    }
    fun back(){
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }
}
