package es.usj.mastersa.apazmino.usjquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import es.usj.mastersa.apazmino.usjquiz.Quiz.QuizActivity
import es.usj.mastersa.apazmino.usjquiz.clases.Quiz
import kotlinx.android.synthetic.main.activity_menu.*
import java.io.IOException
import java.io.InputStream

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        button.setOnClickListener {
            val intent = Intent(this, ActivityGPS::class.java)
            startActivity(intent)
        }

        btnCuestionario.setOnClickListener {

            //Borrar luego desde aquí
            var json: String? = null
            try {
                val inputStream: InputStream = assets.open("quiz.json")
                json = inputStream.bufferedReader().use { it.readText() }
                val gson = Gson()
                val quiz = gson.fromJson(json, Quiz::class.java)
                val zona = quiz.getZona(2)

                val intent = Intent(this, QuizActivity::class.java)
                intent.putExtra("jsonZonaPreguntas", gson.toJson(zona))
                startActivity(intent)

            } catch (e: IOException) {
                Toast.makeText(this, "Error al cargar el JSON", Toast.LENGTH_LONG).show()
            }
            //Borrar hasta aquí

        }
    }

}
