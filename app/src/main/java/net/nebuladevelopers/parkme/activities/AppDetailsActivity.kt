package net.nebuladevelopers.parkme.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_app_details.*
import kotlinx.android.synthetic.main.app_bar_app_details.*
import net.nebuladevelopers.parkme.R
import net.nebuladevelopers.parkme.utils.Authentication
import android.net.Uri.fromParts



class AppDetailsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_details)
        setSupportActionBar(toolbar)

        app_details_fab.setOnClickListener { view ->
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "parking@jcu.edu.au", null))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Parking Enquiry")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi, I would like to enquire about..")
            startActivity(Intent.createChooser(emailIntent, "Parking Enquiry"))
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
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
