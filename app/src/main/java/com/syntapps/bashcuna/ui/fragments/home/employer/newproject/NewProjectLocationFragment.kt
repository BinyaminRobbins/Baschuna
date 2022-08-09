package com.syntapps.bashcuna.ui.fragments.home.employer.newproject

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.firebase.firestore.GeoPoint
import com.syntapps.bashcuna.BuildConfig
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.ui.viewmodels.MainViewModel
import com.syntapps.bashcuna.ui.viewmodels.LocationViewModel
import java.util.*


class NewProjectLocationFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val locationViewModel: LocationViewModel by activityViewModels()
    private var mapFragment: SupportMapFragment? = null
    private var mMap: GoogleMap? = null
    private lateinit var autocompleteFragment: AutocompleteSupportFragment

    @SuppressLint("SetTextI18n")
    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Modi'in, Israel.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val israel = LatLng(31.0461, 34.8516)
        googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(israel))
        googleMap.setMinZoomPreference(10F)
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15F))

        googleMap.setOnMapLongClickListener {
            val address = getAddressFromLocation(it)[0]
            autocompleteFragment.setText("${address.thoroughfare} ${address.subThoroughfare}")
            viewModel.newJobOffer.jobGeoCoordinates = GeoPoint(it.latitude, it.longitude)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_project_location, container, false)
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        locationViewModel.userLocation.observe(viewLifecycleOwner) {
            mMap?.moveCamera(CameraUpdateFactory.newLatLng(it))
            mMap?.moveCamera(CameraUpdateFactory.zoomTo(18F))
        }

        Places.initialize(view.context, BuildConfig.MAPS_API_KEY)
        autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG
            )
        )
            .setCountry("il")
            .setTypeFilter(TypeFilter.ADDRESS)

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                place.latLng?.let {
                    mMap?.moveCamera(CameraUpdateFactory.newLatLng(it))
                    mMap?.moveCamera(CameraUpdateFactory.zoomTo(17F))
                    viewModel.newJobOffer.jobGeoCoordinates =
                        GeoPoint(
                            it.latitude,
                            it.longitude
                        )
                }
            }

            override fun onError(status: Status) {
                Toast.makeText(view.context, status.statusMessage, Toast.LENGTH_SHORT).show()
            }
        })
        autocompleteFragment.view?.findViewById<AppCompatImageButton>(R.id.places_autocomplete_clear_button)
            ?.setOnClickListener {
                viewModel.newJobOffer.jobGeoCoordinates = null
                autocompleteFragment.setText(null)
            }

        if (viewModel.newJobOffer.jobGeoCoordinates != null) {
            val coordinates = viewModel.newJobOffer.jobGeoCoordinates
            val geocoder = Geocoder(view.context, Locale.getDefault())
            coordinates?.let {
                val address = geocoder.getFromLocation(
                    coordinates.latitude,
                    coordinates.longitude, 1
                )[0]
                autocompleteFragment.setText("${address.thoroughfare} ${address.subThoroughfare}")
                viewModel.newJobOffer.jobGeoCoordinates = GeoPoint(it.latitude, it.longitude)
                mMap?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(it.latitude, it.longitude)))
                mMap?.moveCamera(CameraUpdateFactory.zoomTo(18F))
            }
        }
    }

    private fun getAddressFromLocation(point: LatLng, maxResults: Int = 1): List<Address> {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        return geocoder.getFromLocation(point.latitude, point.longitude, maxResults)
    }
}