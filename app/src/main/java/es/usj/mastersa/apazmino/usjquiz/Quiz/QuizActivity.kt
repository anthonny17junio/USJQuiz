package es.usj.mastersa.apazmino.usjquiz.Quiz

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import es.usj.mastersa.apazmino.usjquiz.Quiz.ui.main.FinQuizFragment
import es.usj.mastersa.apazmino.usjquiz.R
import es.usj.mastersa.apazmino.usjquiz.clases.ViewPagerAdapter
import es.usj.mastersa.apazmino.usjquiz.clases.Zona

var puntuacion = 0
var preguntasPendientes = 0

class QuizActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        puntuacion = 0
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val jsonZonaPreguntas: String = intent.getStringExtra("jsonZonaPreguntas")

        val adapter = ViewPagerAdapter(supportFragmentManager)
        val gson = Gson()
        val zona = gson.fromJson(jsonZonaPreguntas, Zona::class.java)
        val preguntas = zona.preguntas
        preguntasPendientes = preguntas!!.size

        val nombreZona = zona.nombre
        val descripcionZona = zona.descripcion

        adapter.addFragment(InicioQuizFragment(nombreZona, descripcionZona), "Inicio")
        var count = 0
        preguntas?.forEach {
            val pregunta = it
            count++
            adapter.addFragment(PreguntaFragment(pregunta), "Pregunta " + count)
        }
        adapter.addFragment(FinQuizFragment(zona.id), "Fin")

        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.offscreenPageLimit = 20 //IMPORTANTE OJO para que no se "recargue" al cambiar a la tercera página
        viewPager.adapter = adapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }
}