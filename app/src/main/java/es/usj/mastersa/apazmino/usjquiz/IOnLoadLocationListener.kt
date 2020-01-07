package es.usj.mastersa.apazmino.usjquiz

interface IOnLoadLocationListener {
    fun onLocationLoadSuccess(latLngs:List<MyLatLng>)
    fun onLocationLoadFailed(message:String)
}