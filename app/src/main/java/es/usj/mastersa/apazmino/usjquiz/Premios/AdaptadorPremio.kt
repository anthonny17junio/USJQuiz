package es.usj.mastersa.apazmino.usjquiz.Premios

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import es.usj.mastersa.apazmino.usjquiz.R
import es.usj.mastersa.apazmino.usjquiz.clases.Zona

class AdaptadorPremio(var contexto: Context, premios: ArrayList<Zona>) : BaseAdapter() {
    var premios: ArrayList<Zona>? = null

    init {
        this.premios = premios
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder: ViewHolder? = null
        var vista: View? = convertView

        if (vista == null) {
            vista = LayoutInflater.from(contexto).inflate(R.layout.template_premio, null)
            viewHolder = ViewHolder(vista)
            vista.tag = viewHolder
        } else {
            viewHolder = vista.tag as? ViewHolder
        }

        val premio = getItem(position) as Zona
        viewHolder?.txtNombreZona?.text = premio.nombre
        if (premio.puntuacion == -1) {
            premio.puntuacion = 0
        }
        viewHolder?.txtPuntuacion?.text = premio.puntuacion.toString()
        if (premio.puntuacion > 0) {
            viewHolder?.foto?.setImageResource(R.drawable.premio)
            viewHolder?.btnEliminar?.setOnClickListener {
                eliminarPuntuacion(premio.id, contexto)
            }
        } else {
            viewHolder?.btnEliminar?.isVisible = false
        }
        return vista!!
    }

    fun eliminarPuntuacion(idZona: Int, contexto: Context) {
        val builder = AlertDialog.Builder(contexto)
        builder.setTitle("Eliminar zona")
        builder.setMessage("Se perderá todo el progreso de esta zona. ¿Deseas continuar?")
        builder.setPositiveButton("Si", { dialogInterface: DialogInterface, i: Int ->
            val prefs = PreferenceManager.getDefaultSharedPreferences(contexto)
            val editor = prefs.edit()
            val keyZona = "PUNT" + idZona
            editor.remove(keyZona)
            editor.apply()
            recargarPagina(contexto)
        })

        builder.setNegativeButton("No", { dialogInterface: DialogInterface, i: Int -> })
        builder.show()
    }

    fun recargarPagina(contexto: Context) {
        val intent = Intent(contexto, DetallePremio::class.java)
        (contexto as DetallePremio).finish()
        contexto.startActivity(intent)
    }

    override fun getItem(position: Int): Any {
        return this.premios?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return this.premios?.count()!!
    }

    private class ViewHolder(vista: View) {
        var txtNombreZona: TextView? = null
        var txtPuntuacion: TextView? = null
        var foto: ImageView? = null
        var btnEliminar: Button? = null

        init {
            txtNombreZona = vista.findViewById(R.id.txtNombreZona)
            txtPuntuacion = vista.findViewById(R.id.txtPuntosZona)
            foto = vista.findViewById(R.id.ivFotoPremio)
            btnEliminar = vista.findViewById(R.id.btnBorrarPuntuacion)
        }
    }
}