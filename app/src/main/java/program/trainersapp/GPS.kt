package program.trainersapp

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import program.trainersapp.model.Dto
import program.trainersapp.model.entities.ActiveUser


class GPS : FragmentActivity(), GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private var nameMarker: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gps)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val dto = Dto()
        val json = dto.get("activeusers")
        val parser = program.trainersapp.model.JsonParser()
        val parsed = parser.parse(json)

        for (i in 0 until parsed.size) {
            val actUser = Gson().fromJson(parsed[i], ActiveUser::class.java)
            val location = LatLng(actUser.longt.toDouble(), actUser.latt.toDouble())
            val cameraPosition = CameraPosition.Builder()
                    .target(LatLng(actUser.longt.toDouble(), actUser.latt.toDouble()))      // Sets the center of the map to location user
                    .zoom(17f)                   // Sets the zoom
                    .bearing(90f)                // Sets the orientation of the camera to east
                    .tilt(40f)                   // Sets the tilt of the camera to 30 degrees
                    .build()                   // Creates a CameraPosition from the builder

            addMarkers(location, actUser)
            mMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap!!.isMyLocationEnabled = true
            mMap!!.setOnMyLocationButtonClickListener(this)
            mMap!!.setOnMyLocationClickListener(this)
        } else {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),0)
            Toast.makeText(this, "No permission for location", Toast.LENGTH_LONG).show()
        }

    }

    private fun addMarkers(location: LatLng, actUser: ActiveUser) {
        val mark = MarkerOptions().position(location).title(actUser.name + " " + actUser.lastname)
        val height = 120
        val width = 80
        val bitmapdraw = resources.getDrawable(R.drawable.icon) as BitmapDrawable
        val b = bitmapdraw.bitmap
        val smallMarker = Bitmap.createScaledBitmap(b, width, height, false)
        val ba = BitmapDescriptorFactory.fromBitmap(smallMarker)
        mark.icon(ba)

        mMap!!.addMarker(mark)

        mMap!!.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener {
            override fun onMarkerClick(marker: Marker): Boolean {
                marker.title = "test"
                return false
            }
        })


    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(this, "Current location:\n$location", Toast.LENGTH_LONG).show()
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show()
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false
    }
}

