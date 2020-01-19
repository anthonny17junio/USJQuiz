package es.usj.mastersa.apazmino.usjquiz.Quiz


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import es.usj.mastersa.apazmino.usjquiz.R

/**
 * A simple [Fragment] subclass.
 */
class InicioQuizFragment(titulo: String, descripcion: String) : Fragment() {

    lateinit var tituloQuiz: String
    lateinit var descripcionQuiz: String

    init {
        this.tituloQuiz = titulo
        this.descripcionQuiz = descripcion
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_inicio_quiz, container, false)

        val txtTitulo: TextView = view.findViewById(R.id.txtTituloQuiz)
        val txtDescripcion: TextView = view.findViewById(R.id.txtDescripcionQuiz)

        txtTitulo.text = tituloQuiz
        txtDescripcion.text = descripcionQuiz
        return view
    }


}
