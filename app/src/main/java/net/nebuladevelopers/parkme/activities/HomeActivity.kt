package net.nebuladevelopers.parkme.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Button
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.kml.KmlLayer
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_main.*
import net.nebuladevelopers.parkme.R
import net.nebuladevelopers.parkme.utils.Authentication
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class HomeActivity : AppCompatActivity(), OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mMap: GoogleMap
    private var allParkDataUrl = "https://s3-ap-southeast-2.amazonaws.com/parkmejcu/parkdata"
    private var freeParkDataUrl = "https://s3-ap-southeast-2.amazonaws.com/parkmejcu/parkdatafree"
    private var currentUrl = allParkDataUrl

    private fun updateMap() {
        Thread(Runnable {
            val url = URL(currentUrl);
            val urlConnection =  url.openConnection();
            val stream = BufferedInputStream(urlConnection.getInputStream());
            val layer = KmlLayer(mMap, stream, this)
            runOnUiThread {
                mMap.clear()
                layer.addLayerToMap()
            }
        }).start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

         findViewById<Button>(R.id.freeParksBtn).setOnClickListener {
             findViewById<Button>(R.id.freeParksBtn).alpha = 1f
             findViewById<Button>(R.id.allParksBtn).alpha = 0.4f
             println("FREE CLICKED")
             currentUrl = freeParkDataUrl
             updateMap()
         }

        findViewById<Button>(R.id.allParksBtn).setOnClickListener {
            findViewById<Button>(R.id.freeParksBtn).alpha = 0.4f
            findViewById<Button>(R.id.allParksBtn).alpha = 1f
            println("ALL CLICKED")
            currentUrl = allParkDataUrl
            updateMap()
        }

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val townsville = LatLng(-19.329285, 146.759358)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(townsville, 17f))
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                updateMap()
            }
        }, 0, 5000)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                startActivity(Intent(this, HomeActivity::class.java))
            }
            R.id.nav_my_profile -> {
                startActivity(Intent(this, TimetableActivity::class.java))
            }
            R.id.nav_app_info -> {
                startActivity(Intent(this, AppDetailsActivity::class.java))
            }
            R.id.nav_logout -> {
                Authentication.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
