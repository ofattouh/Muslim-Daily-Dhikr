package mohamed.omar.com.muslimdailydhikr2.network

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import mohamed.omar.com.muslimdailydhikr2.R
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import android.app.Activity


object LocationProvider : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    private val tag = "Location provider"

    private val listeners = CopyOnWriteArrayList<WeakReference<LocationListener>>()

    private var mLayout: View? = null
    private val PERMISSION_REQUEST_ACCESS_FINE_LOCATION   = 0
    private val PERMISSION_REQUEST_ACCESS_COARSE_LOCATION = 0

    private val locationListener = object : LocationListener {

        // Called when location has changed.
        override fun onLocationChanged(location: Location) {
            Log.i(tag, String.format(Locale.ENGLISH, "Location [ lat: %s ][ long: %s ]", location.latitude, location.longitude))
            val iterator = listeners.iterator()
            while (iterator.hasNext()) {
                val reference = iterator.next()
                val listener = reference.get()
                listener?.onLocationChanged(location)
            }
        }

        // Called when provider status changes.
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            Log.d(tag, String.format(Locale.ENGLISH, "Status changed [ %s][ %d ]", provider, status))
            val iterator = listeners.iterator()
            while (iterator.hasNext()) {
                val reference = iterator.next()
                val listener = reference.get()
                listener?.onStatusChanged(provider, status, extras)
            }
        }

        // Called when the location provider is enabled by the user
        override fun onProviderEnabled(provider: String) {
            Log.i(tag, String.format("Provider [ %s ][ ENABLED ]", provider))
            val iterator = listeners.iterator()
            while (iterator.hasNext()) {
                val reference = iterator.next()
                val listener = reference.get()
                listener?.onProviderEnabled(provider)
            }
        }

        // Called when provider is disabled by user
        override fun onProviderDisabled(provider: String) {
            Log.i(tag, String.format("Provider [ %s ][ DISABLED ]", provider))
            val iterator = listeners.iterator()
            while (iterator.hasNext()) {
                val reference = iterator.next()
                val listener = reference.get()
                listener?.onProviderDisabled(provider)
            }
        }
    }

    fun subscribe(subscriber: LocationListener, ctx: Context?): Boolean {
        val result = doSubscribe(subscriber)
        turnOnLocationListening(ctx)
        return result
    }

    fun unsubscribe(subscriber: LocationListener, ctx: Context?): Boolean {
        val result = doUnsubscribe(subscriber)
        if (listeners.isEmpty()) {
            turnOffLocationListening(ctx)
        }
        return result
    }

    private fun turnOnLocationListening(ctx: Context? = null) {
        Log.v(tag, "We are about to turn on location listening.")

        if (ctx != null) {
            Log.v(tag, "We are about to check location permissions.")

            mLayout = (ctx as Activity).findViewById(R.id.main_layout) as View

            // Determine whether you have been granted a particular permission.
            val permissionsOk =
                    ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

            if (!permissionsOk) {
                //throw IllegalStateException("Permissions required [ ACCESS_FINE_LOCATION ][ ACCESS_COARSE_LOCATION ]") as Throwable
                // Permission is missing and must be requested.
                requestLocationPermission(mLayout!!, ctx)
            }
            else {
                // Permission is granted
                Log.v(tag, "Location permissions are ok. We are about to request location changes.")

                val locationManager = ctx.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val gps_enabled     = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                val network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                val criteria = Criteria()
                criteria.accuracy           = Criteria.ACCURACY_FINE
                criteria.powerRequirement   = Criteria.POWER_HIGH
                criteria.isAltitudeRequired = false
                criteria.isBearingRequired  = false
                criteria.isSpeedRequired    = false
                criteria.isCostAllowed      = true

                if (network_enabled) {
                    Log.v(tag, "We are about to fetch location updates using Network Provider.")
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 50000, 10000F, locationListener, Looper.getMainLooper())
                } else if (gps_enabled) {
                    //requestLocationUpdates(String provider, long minTime, float minDistance, LocationListener listener, Looper looper)
                    Log.v(tag, "We are about to fetch location updates using GPS.")
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50000, 10000F, locationListener, Looper.getMainLooper())
                } else {
                    // Register for location updates using a Criteria, and a callback on the specified looper thread
                    Log.v(tag, "We are about to fetch location updates using Criteria.")
                    locationManager.requestLocationUpdates(50000, 10000F, criteria, locationListener, Looper.getMainLooper())
                }
            }
        }
    }

    private fun doSubscribe(listener: LocationListener): Boolean {
        val iterator = listeners.iterator()

        while (iterator.hasNext()) {
            val reference = iterator.next()
            val refListener = reference.get()
            if (refListener != null && refListener === listener) {
                Log.v(tag, "Already subscribed: " + listener)
                return false
            }
        }

        listeners.add(WeakReference(listener))
        Log.v(tag, "Subscribed, subscribers count: " + listeners.size)
        return true
    }

    private fun doUnsubscribe(listener: LocationListener): Boolean {
        var result = true
        val iterator = listeners.iterator()

        while (iterator.hasNext()) {
            val reference = iterator.next()
            val refListener = reference.get()

            if (refListener != null && refListener === listener) {

                val success = listeners.remove(reference)

                if (!success) {
                    Log.w(tag, "Couldn't un subscribe, subscribers count: " + listeners.size)
                } else {
                    Log.v(tag, "Un subscribed, subscribers count: " + listeners.size)
                }

                if (result) {
                    result = success
                }
            }
        }

        return result
    }

    private fun turnOffLocationListening(ctx: Context? = null) {
        Log.v(tag, "We are about to turn off location listening.")

        if (ctx != null) {
            val locationManager = ctx.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            // Removes all location updates for LocationListener, updates will no longer occur for this listener.
            locationManager.removeUpdates(locationListener)
        } else {
            Log.e(tag, "No application context available.")
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        // BEGIN_INCLUDE(onRequestPermissionsResult)
        if (requestCode == PERMISSION_REQUEST_ACCESS_FINE_LOCATION || requestCode == PERMISSION_REQUEST_ACCESS_COARSE_LOCATION) {
            // Request for GPS Location permission.
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start fetching daily prayer times.
                mLayout?.let {
                    Snackbar.make(it, R.string.gps_location_permission_granted,
                            Snackbar.LENGTH_SHORT)
                            .show()
                }
            } else {
                // Permission request was denied.
                mLayout?.let {
                    Snackbar.make(it, R.string.gps_location_permission_denied,
                            Snackbar.LENGTH_SHORT)
                            .show()
                }
            }
        }
        // END_INCLUDE(onRequestPermissionsResult)
    }

    /**
     * Requests the [android.Manifest.permission.ACCESS_FINE_LOCATION] permission.
     * Requests the [android.Manifest.permission.ACCESS_COARSE_LOCATION] permission.
     * If an additional rationale should be displayed, the user has to launch the request from
     * a SnackBar that includes additional information.
     */
    private fun requestLocationPermission(mLayout: View, mActivity: Activity) {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) ||
                ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with cda button to request the missing permission.
            mLayout?.let {
                Snackbar.make(it, R.string.gps_location_access_required,
                        Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok) {
                    // Request the permission
                    ActivityCompat.requestPermissions(mActivity,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                            PERMISSION_REQUEST_ACCESS_FINE_LOCATION)
                }.show()
            }

        } else {
            mLayout?.let {
                Snackbar.make(mLayout!!, R.string.gps_location_unavailable, Snackbar.LENGTH_LONG).show()
                // Request the permission. The result will be received in onRequestPermissionResult().
                ActivityCompat.requestPermissions(mActivity,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                        PERMISSION_REQUEST_ACCESS_FINE_LOCATION)
            }
        }
    }

}