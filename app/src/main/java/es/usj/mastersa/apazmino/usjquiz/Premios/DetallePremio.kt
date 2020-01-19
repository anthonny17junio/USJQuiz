package es.usj.mastersa.apazmino.usjquiz.Premios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.ListView
import android.widget.Toast
import com.google.gson.Gson
import es.usj.mastersa.apazmino.usjquiz.R
import es.usj.mastersa.apazmino.usjquiz.clases.Quiz
import java.io.IOException
import java.io.InputStream

class DetallePremio : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_premio)

        var json: String? = null
        try {
            val inputStream: InputStream = assets.open("quiz.json")
            json = inputStream.bufferedReader().use { it.readText() }
            val gson = Gson()
            val quiz = gson.fromJson(json, Quiz::class.java)

            val zonas = quiz.zonas
            zonas?.forEach {
                val zona = it
                val idZona = zona.id

                val prefs = PreferenceManager.getDefaultSharedPreferences(this)
                val keyZona = "PUNT" + idZona
                val puntuacionZona = prefs.getInt(keyZona, -1)
                zona.puntuacion = puntuacionZona
            }

            val lista = findViewById<ListView>(R.id.lista)
            val adaptador = AdaptadorPremio(this, zonas!!)

            lista.adapter = adaptador
        } catch (e: IOException) {
            Toast.makeText(this, "Error al cargar el JSON", Toast.LENGTH_LONG).show()
        }
    }
}
