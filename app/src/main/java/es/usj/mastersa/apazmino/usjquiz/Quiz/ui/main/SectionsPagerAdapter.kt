package es.usj.mastersa.apazmino.usjquiz.Quiz.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.gson.Gson
import es.usj.mastersa.apazmino.usjquiz.R
import es.usj.mastersa.apazmino.usjquiz.clases.Zona

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2, R.string.tab_text_1
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(
    private val context: Context,
    fm: FragmentManager,
    jsonZonaPreguntas: String
) :
    FragmentPagerAdapter(fm) {

    var zona: Zona

    init {
        val gson = Gson()
        zona = gson.fromJson(jsonZonaPreguntas, Zona::class.java)
    }

    override fun getItem(position: Int): Fragment {
        return PlaceholderFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return zona.preguntas!!.size
    }
}