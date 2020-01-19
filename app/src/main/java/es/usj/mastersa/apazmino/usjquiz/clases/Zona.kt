package es.usj.mastersa.apazmino.usjquiz.clases

class Zona(id: Int, nombre: String, descripcion: String, preguntas: ArrayList<Pregunta>) {
    var id: Int = 0
    var nombre: String = ""
    var descripcion: String = ""
    var preguntas: ArrayList<Pregunta>? = null
    var puntuacion: Int = -1

    init {
        this.id = id
        this.nombre = nombre
        this.descripcion = descripcion
        this.preguntas = preguntas
    }
}