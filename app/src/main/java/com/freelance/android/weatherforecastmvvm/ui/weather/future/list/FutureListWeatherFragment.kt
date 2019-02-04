package com.freelance.android.weatherforecastmvvm.ui.weather.future.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.freelance.android.weatherforecastmvvm.R
import com.freelance.android.weatherforecastmvvm.data.db.LocalDateConverter
import com.freelance.android.weatherforecastmvvm.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import com.freelance.android.weatherforecastmvvm.ui.base.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.future_list_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.threeten.bp.LocalDate

class FutureListWeatherFragment : ScopedFragment(), KodeinAware {

    private val LOG_TAG = FutureListWeatherFragment::class.java.name
    override val kodein by closestKodein()
    private val viewModelFactory: FutureListWeatherViewModelFactory by instance()
    private lateinit var viewModel: FutureListWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(LOG_TAG, "TEST: onCreateView() called...")

        return inflater.inflate(R.layout.future_list_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.i(LOG_TAG, "TEST: onActivityCreated() called...")

        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FutureListWeatherViewModel::class.java)// must put "viewModelFactory"

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        Log.i(LOG_TAG, "TEST: bindUI() called...")

        val futureWeatherEntries = viewModel.weatherEntries.await() //await() is asynchronous function.
        val weatherLocation = viewModel.weatherLocation.await()

        futureWeatherEntries.observe(this@FutureListWeatherFragment, Observer { weatherEntries ->
            if (weatherEntries == null) return@Observer

            futureListGroup.visibility = View.GONE
            updateDateToNextWeek()
            initRecyclerView(weatherEntries.toFutureWeatherItems())
        })

        weatherLocation.observe(this@FutureListWeatherFragment, Observer { location ->
            if (location == null) return@Observer
            updateLocation(location.name)
        })
    }

    private fun List<UnitSpecificSimpleFutureWeatherEntry>.toFutureWeatherItems(): List<FutureWeatherItem> {
        Log.i(LOG_TAG, "TEST: List<UnitSpecificSimpleFutureWeatherEntry>.toFutureWeatherItems() called...")

        return this.map {
            FutureWeatherItem(it)
        }
    }

    private fun initRecyclerView(items: List<FutureWeatherItem>) {
        Log.i(LOG_TAG, "TEST: initRecyclerView() called...")

        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@FutureListWeatherFragment.context)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, view ->
            /*Toast.makeText(this@FutureListWeatherFragment.context, "Clicked", Toast.LENGTH_SHORT).show()*/
            Log.i(LOG_TAG, "TEST: group.setOnItemClickListener () called...")

            (item as? FutureWeatherItem)?.let {
                showWeatherDetail(it.weatherEntry.date, view)
            }
        }
    }

    private fun showWeatherDetail(date: LocalDate, view: View){
        Log.i(LOG_TAG, "TEST: showWeatherDetail() called...")

        val dateString = LocalDateConverter.dateToString(date) !!
        val actionDetail = FutureListWeatherFragmentDirections.actionDetail(dateString)
        Navigation.findNavController(view).navigate(actionDetail)
    }//FutureListWeatherFragmentDirections is rebuild and then generate that file.

    private fun updateDateToNextWeek() {
        Log.i(LOG_TAG, "TEST: updateDateToNextWeek() called...")

        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Next Week"
    }

    private fun updateLocation(location: String) {
        Log.i(LOG_TAG, "TEST: updateLocation() called...")

        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }
}