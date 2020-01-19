package es.usj.mastersa.apazmino.usjquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import es.usj.mastersa.apazmino.usjquiz.Premios.DetallePremio
import es.usj.mastersa.apazmino.usjquiz.Quiz.QuizActivity
import es.usj.mastersa.apazmino.usjquiz.clases.Quiz
import es.usj.mastersa.apazmino.usjquiz.clases.Zona
import kotlinx.android.synthetic.main.activity_menu.*
import java.io.IOException
import java.io.InputStream

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btnIniciarQuiz.setOnClickListener {
            val intent = Intent(this, Activity2GPS::class.java)
            startActivity(intent)
        }

        btnPremios.setOnClickListener {
            val intent = Intent(this, DetallePremio::class.java)
            startActivity(intent)
        }

        btnTutorial.setOnClickListener{
            var json: String? = null
            try {
                val inputStream: InputStream = assets.open("quiztutorial.json")
                json = inputStream.bufferedReader().use { it.readText() }
                val gson = Gson()
                val zona = gson.fromJson(json, Zona::class.java)

                val intent = Intent(this, QuizActivity::class.java)
                intent.putExtra("jsonZonaPreguntas", gson.toJson(zona))
                startActivity(intent)

            } catch (e: IOException) {
                Toast.makeText(this, "Error al cargar el JSON", Toast.LENGTH_LONG).show()
            }
        }

    }

}
