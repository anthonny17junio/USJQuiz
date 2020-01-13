package es.usj.mastersa.apazmino.usjquiz.clases

class Pregunta(pregunta: String, respuestas: ArrayList<Respuesta>) {
    var pregunta: String = ""
    var respuestas: ArrayList<Respuesta>? = null

    init {
        this.pregunta = pregunta
        this.respuestas = respuestas
    }
}