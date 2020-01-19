package es.usj.mastersa.apazmino.usjquiz.Quiz


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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

        //Seteo los click de los botones
        btnOp1.setOnClickListener {
            if (respuesta1!!.correcto) {
                acierto(btnOp1)
            } else {
                fallo(btnOp1)
            }
        }

        btnOp2.setOnClickListener {
            if (respuesta2!!.correcto) {
                acierto(btnOp2)
            } else {
                fallo(btnOp2)
            }
        }

        btnOp3.setOnClickListener {
            if (respuesta3!!.correcto) {
                acierto(btnOp3)
            } else {
                fallo(btnOp3)
            }
        }

        btnOp4.setOnClickListener {
            if (respuesta4!!.correcto) {
                acierto(btnOp4)
            } else {
                fallo(btnOp4)
            }
        }
        return view
    }

    init {
        pregunta = preg
    }

    private fun fallo(btn: Button) {
        btn.isEnabled = false
        if (premio > 0) {
            premio -= 2
        }
        btn.setBackgroundColor(Color.argb(13, 255, 0, 0))
    }

    private fun acierto(btn: Button) {
        puntuacion += premio
        preguntasPendientes--

        btnOp1.isEnabled = false
        btnOp2.isEnabled = false
        btnOp3.isEnabled = false
        btnOp4.isEnabled = false

        btn.setBackgroundColor(Color.argb(13, 0, 255, 0))
        lblPuntuacion.text = "Obtuviste " + premio.toString() + " puntos!"
    }
}
