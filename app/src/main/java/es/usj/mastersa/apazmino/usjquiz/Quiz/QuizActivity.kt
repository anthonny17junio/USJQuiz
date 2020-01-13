package es.usj.mastersa.apazmino.usjquiz.Quiz

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.gson.Gson
import es.usj.mastersa.apazmino.usjquiz.Quiz.ui.main.SectionsPagerAdapter
import es.usj.mastersa.apazmino.usjquiz.R
import es.usj.mastersa.apazmino.usjquiz.clases.ViewPagerAdapter
import es.usj.mastersa.apazmino.usjquiz.clases.Zona

class QuizActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val jsonZonaPreguntas: String = intent.getStringExtra("jsonZonaPreguntas")

        val adapter = ViewPagerAdapter(supportFragmentManager)
        val gson = Gson()
        val preguntas = gson.fromJson(jsonZonaPreguntas, Zona::class.java).preguntas

        var count = 0
        preguntas?.forEach {
            val pregunta = it
            count++
            adapter.addFragment(PreguntaFragment(pregunta), "Pregunta " + count)
        }

        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = adapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }
}