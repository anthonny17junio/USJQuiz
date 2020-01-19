package es.usj.mastersa.apazmino.usjquiz.Quiz.ui.main


import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import es.usj.mastersa.apazmino.usjquiz.Activity2GPS
import es.usj.mastersa.apazmino.usjquiz.Quiz.preguntasPendientes
import es.usj.mastersa.apazmino.usjquiz.Quiz.puntuacion

import es.usj.mastersa.apazmino.usjquiz.R

/**
 * A simple [Fragment] subclass.
 */
class FinQuizFragment(id: Int) : Fragment() {

    var idZona: Int

    init {
        this.idZona = id
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fin_quiz, container, false)
        val btnGuardar: Button = view.findViewById(R.id.btnGuardar)
        val btnRegresar: Button = view.findViewById(R.id.btnRegresar)

        val prefs = PreferenceManager.getDefaultSharedPreferences(activity)

        btnGuardar.setOnClickListener {
            if (preguntasPendientes > 0) {
                Toast.makeText(
                    activity,
                    "Responde todas las preguntas antes de guardar.",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                //Guardo en preferencias la puntuación
                val editor = prefs.edit()
                val keyZona = "PUNT" + idZona
                editor.putInt(keyZona, puntuacion)
                editor.apply()

                //Muestro la puntuación en el text
                val txtPuntuacion: TextView = view.findViewById(R.id.txtPuntuacion)
                txtPuntuacion.text = "Obtuviste " + puntuacion + " puntos"

                btnGuardar.isEnabled = false
            }
        }

        btnRegresar.setOnClickListener {
            val txtPuntuacion: TextView = view.findViewById(R.id.txtPuntuacion)
            if (txtPuntuacion.text.equals("")) {

                val builder = AlertDialog.Builder(activity as Context)
                builder.setTitle("Regresar al mapa")
                builder.setMessage("Se perderá todo el progreso de esta zona. ¿Deseas salir?")
                builder.setPositiveButton("Si", { dialogInterface: DialogInterface, i: Int ->
                    activity!!.finish()
                })

                builder.setNegativeButton("No", { dialogInterface: DialogInterface, i: Int -> })
                builder.show()
            } else {
                activity!!.finish()
            }
        }

        return view
    }


}
