package es.usj.mastersa.apazmino.usjquiz.clases

class Respuesta(respuesta: String, correcto: Boolean) {
    var respuesta: String = ""
    var correcto: Boolean = false

    init {
        this.respuesta = respuesta
        this.correcto = correcto
    }
}