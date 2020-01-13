package es.usj.mastersa.apazmino.usjquiz

import android.Manifest
import android.content.IntentSender
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
import com.firebase.geofire.GeoQuery
import com.firebase.geofire.GeoQueryEventListener
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.google.maps.android.data.kml.KmlLayer
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class Activity2GPS : AppCompatActivity(), OnMapReadyCallback, IOnLoadLocationListener,
    GeoQueryEventListener {

    private  var mMap: GoogleMap? = null
    private lateinit var locationRequest: LocationRequest
    private var locationCallback: LocationCallback? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private  var currentMarker: Marker? = null
    private lateinit var myLocationRef: DatabaseReference
    private lateinit var quizzArea: MutableList<LatLng>
    private lateinit var listener: IOnLoadLocationListener

    private lateinit var myCity: DatabaseReference
    private lateinit var lastLocation: Location
    private  var geoQuery: GeoQuery? = null
    private lateinit var  geoFire: GeoFire
    private var layer: KmlLayer? = null
    //private var geoCoder: Geocoder = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity2_gps)

        //Request runtime
        Dexter.withActivity(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    buildLocationRequest()
                    buildLocationCallBack()
                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this@Activity2GPS)
                    initArea()
                    settingGeoFire()

                    //Add quiz areas to Firebase
                    addQuizzesToFirebase()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Toast.makeText(this@Activity2GPS, "You must enable this permission", Toast.LENGTH_SHORT).show()
                }

            }).check()
    }

    private fun addQuizzesToFirebase() {
        quizzArea = ArrayList()
        //Home

        //USJ
        quizzArea.add(LatLng(41.757400, -0.831999)) // Salud
        quizzArea.add(LatLng(41.662501, -0.863960))

        quizzArea.add(LatLng(41.756963, -0.832471)) // Rectorado
        quizzArea.add(LatLng(41.756308 , -0.832450)) // Comunicación


        //Submit this list to Firebase
        FirebaseDatabase.getInstance().getReference("usjquiz").child("Mycity").setValue(quizzArea)
            .addOnCompleteListener(object: OnCompleteListener<Void> {
                override fun onComplete(p0: Task<Void>) {
                    Toast.makeText(this@Activity2GPS,"Updated", Toast.LENGTH_SHORT).show()
                }


            }).addOnFailureListener { _ -> Toast.makeText(this@Activity2GPS,"Fail", Toast.LENGTH_SHORT).show()
            }
    }

    private fun settingGeoFire() {
        myLocationRef = FirebaseDatabase.getInstance().getReference("MyLocation")
        geoFire = GeoFire(myLocationRef)
    }

    private fun initArea() {
        myCity = FirebaseDatabase.getInstance().getReference("usjquiz").child("Mycity")
        listener = this

        //Add realtime change update
        myCity.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //Update Quizz Area list
                val latLngList = ArrayList<MyLatLng>()
                for (locationSnapShot in dataSnapshot.children) {
                    val latLng = locationSnapShot.getValue(MyLatLng::class.java)
                    latLngList.add(latLng!!)
                }
                listener.onLocationLoadSuccess(latLngList)
            }

        })
    }

    private fun buildLocationCallBack() {
        locationCallback = object : LocationCallback() {
            //Ctrl + O
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                if (mMap != null) {
                    lastLocation = locationResult!!.lastLocation
                    addUserMarker()
                }
            }
        }
    }

    private fun addUserMarker() {
        geoFire.setLocation("You", GeoLocation(lastLocation.latitude, lastLocation.longitude)) {_,_->
            if (currentMarker != null) {
                currentMarker!!.remove()
            }
            currentMarker = mMap!!.addMarker(MarkerOptions().position(LatLng(lastLocation.latitude, lastLocation.longitude)).title("You"))
            //After add marker, move camera
            mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(currentMarker!!.position,18.0f))
        }
    }

    private fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 50
        locationRequest.fastestInterval = 10
        locationRequest.smallestDisplacement = 1f


    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap!!.uiSettings.isZoomControlsEnabled = true
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback!!, Looper.myLooper())
        addCircleArea()
        addLayer()
    }

    override fun onLocationLoadSuccess(latLngs: List<MyLatLng>) {
        quizzArea = ArrayList()
        for (myLatLng in latLngs) {
            val convert = LatLng(myLatLng.latitude, myLatLng.longitude)
            quizzArea.add(convert)
        }
        //Now, after quizz area has data, we will call map display
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Clear map and add data again
        if (mMap != null){
            mMap!!.clear()
            //Add again user marker
            addUserMarker()
            //Add circle of quizz area
            addCircleArea()
        }
    }

    private fun addCircleArea() {
        if (geoQuery != null){
            //Remove old listener, image if you remove a location in Firebase
            //It must be removed the listener in GeoFire too
            geoQuery!!.removeGeoQueryEventListener(this@Activity2GPS)
            geoQuery!!.removeAllListeners()
        }
        //Add again
        for (latLng in quizzArea) {
            mMap!!.addCircle(CircleOptions().center(latLng).radius(20.0).strokeColor(Color.BLUE) //Radius in m
                .fillColor(0x220000FF).strokeWidth(5.0f))
            //Create GeoQuery when user in quizz location
            geoQuery = geoFire.queryAtLocation(GeoLocation(latLng.latitude, latLng.longitude), 0.02) // 0.02 = 20m
            geoQuery!!.addGeoQueryEventListener(this@Activity2GPS)
        }
    }

    override fun onLocationLoadFailed(message: String) {
        Toast.makeText(this, "The map loader failed", Toast.LENGTH_SHORT).show()
    }

    override fun onGeoQueryReady() {

    }

    override fun onKeyEntered(key: String?, location: GeoLocation?) {
        //getFromLocationName()
        var count = 0
        var idMenor = 0
        var distanciaMenor: Double = 0.0
        quizzArea.forEach {
            if (count == 0) {
                distanciaMenor = coordinateDistance(location!!, it)
            } else {
                val distancia = coordinateDistance(location!!, it)
                if (distancia < distanciaMenor) {
                    distanciaMenor = distancia
                    idMenor = count
                }
            }
            count++
        }
        Toast.makeText(
            this,
            "Entraste a la zona " + idMenor,
            Toast.LENGTH_LONG
        ).show()
    }

    fun coordinateDistance(location: GeoLocation, area: LatLng): Double{

        val earthRadiusKm: Double = 6372.8
        val dLat = Math.toRadians(location!!.latitude - area.latitude)
        val dLon = Math.toRadians(location.longitude - area.longitude)
        val userLat = Math.toRadians(area.latitude)
        val areaLat = Math.toRadians(location.latitude)

        val a = Math.pow(Math.sin(dLat / 2), 2.toDouble()) + Math.pow(Math.sin(dLon / 2), 2.toDouble()) * Math.cos(userLat) * Math.cos(areaLat)
        val c = 2 * Math.asin(Math.sqrt(a))
        return earthRadiusKm * c
    }

    override fun onKeyMoved(key: String?, location: GeoLocation?) {

    }

    override fun onKeyExited(key: String?) {
        Toast.makeText(this, "You went out from the quizz building", Toast.LENGTH_SHORT).show()
    }

    override fun onGeoQueryError(error: DatabaseError?) {
        Toast.makeText(this, "An error ocurred", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        super.onStop()
    }

    fun addLayer(){
        layer = KmlLayer(mMap, R.raw.usj_quiz, this)
        layer!!.addLayerToMap()
    }
}
