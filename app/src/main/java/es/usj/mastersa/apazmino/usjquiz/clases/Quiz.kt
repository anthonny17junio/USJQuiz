package es.usj.mastersa.apazmino.usjquiz.clases

class Quiz(zonas: ArrayList<Zona>) {
    var zonas: ArrayList<Zona>? = null

    init {
        this.zonas = zonas
    }

    fun getZona(idZona: Int): Zona? {
        if (this.zonas != null) {
            zonas!!.forEach {
                if(it.id == idZona){
                    return it
                }
            }
            return null
        } else {
            return null
        }
    }
}