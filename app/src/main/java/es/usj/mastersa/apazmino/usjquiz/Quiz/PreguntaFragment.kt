package es.usj.mastersa.apazmino.usjquiz.Quiz


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import es.usj.mastersa.apazmino.usjquiz.R
import es.usj.mastersa.apazmino.usjquiz.clases.Pregunta

/**
 * A simple [Fragment] subclass.
 */
class PreguntaFragment(preg: Pregunta) : Fragment() {
    var premio = 10
    var pregunta: Pregunta
    lateinit var lblPregunta: TextView
    lateinit var btnOp1: Button
    lateinit var btnOp2: Button
    lateinit var btnOp3: Button
    lateinit var btnOp4: Button
    lateinit var lblPuntuacion: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pregunta, container, false)
        lblPregunta = view.findViewById(R.id.txtPregunta)
        btnOp1 = view.findViewById(R.id.btnOp1)
        btnOp2 = view.findViewById(R.id.btnOp2)
        btnOp3 = view.findViewById(R.id.btnOp3)
        btnOp4 = view.findViewById(R.id.btnOp4)
        lblPuntuacion = view.findViewById(R.id.puntosPregunta)

        val respuestas = pregunta.respuestas
        val respuesta1 = respuestas?.get(0)
        val respuesta2 = respuestas?.get(1)
        val respuesta3 = respuestas?.get(2)
        val respuesta4 = respuestas?.get(3)

        //Seteo el texto en los botones
        lblPregunta.text = pregunta.pregunta
        btnOp1.text = respuesta1?.respuesta
        btnOp2.text = respuesta2?.respuesta
        btnOp3.text = respuesta3?.respuesta
        btnOp4.text = respuesta4?.respuesta
        return view
    }

    init {
        pregunta = preg
    }
}
