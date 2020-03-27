package fariborz.rezara.ui.Fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import fariborz.rezara.Injection

import fariborz.rezara.R
import fariborz.rezara.adapters.TopRatedAdapter
import fariborz.rezara.database.AppDatabase
import fariborz.rezara.database.Entities.TopRatedEntry
import fariborz.rezara.interfaces.OnMovieClickListener
import fariborz.rezara.models.Movie
import fariborz.rezara.network.NetworkService
import fariborz.rezara.ui.Activities.DetailActivity
import fariborz.rezara.viewmodels.TopRatedViewModel
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking


/**
 * A simple [Fragment] subclass.
 */
class TopRatedMoviesFragment : Fragment(), OnMovieClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private val TAG:String = "TopRatedMoviesFragment"
    private val GRID_COLUMNS_PORTRAIT = 1
    private val GRID_COLUMNS_LANDSCAPE = 2
    private lateinit var mMainView : View
    private lateinit var mRecyclerView : RecyclerView
    private lateinit var mSwipeRefreshLayout : SwipeRefreshLayout
    private lateinit var mGridLayoutManager : GridLayoutManager
    private lateinit var emptyList: TextView

    private lateinit var viewModel: TopRatedViewModel
    private lateinit var mSharedPreferences: SharedPreferences
    private lateinit var networkService: NetworkService
    private lateinit var mDatabase: AppDatabase

    private var region: String = ""

    lateinit var mMovieAdapter: TopRatedAdapter
    lateinit var data:MutableList<Movie>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mMainView = inflater.inflate(R.layout.fragment_top_rated_movies, container, false)

        initViews()
        initRecyclerView()
        getSelectedRegion()
        getTopRatedData(region)
        setSwipeRefreshLayoutListener()

        return mMainView
    }

    private fun initViews(){
        mRecyclerView = mMainView.findViewById(R.id.fragment_top_rated_movies_recycler_view)
        mSwipeRefreshLayout = mMainView.findViewById(R.id.fragment_top_rated_movies_swipe_refresh)
        emptyList = mMainView.findViewById(R.id.emptyTopRatedList)

        data = mutableListOf()
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this)

        networkService = NetworkService.instance
        mDatabase = AppDatabase.getInstance(context!!.applicationContext)
    }

    private fun initRecyclerView() {
        configureRecyclerAdapter(resources.configuration.orientation)
        viewModel = ViewModelProviders.of(this, Injection.provideTopRatedViewModelFactory(context!!))
                .get(TopRatedViewModel::class.java)

        mMovieAdapter = TopRatedAdapter(this,mSharedPreferences)
        mRecyclerView.adapter = mMovieAdapter


        viewModel.topRated.observe(this, Observer<PagedList<TopRatedEntry>> {
            Log.i("asdfghjkjhgfdfghj", "list: ${it?.size}")
            showEmptyList(it?.size == 0)
            mMovieAdapter.submitList(it!!)
        })
        viewModel.networkErrors.observe(this, Observer<String> {
            Toast.makeText(context, "\uD83D\uDE28 Wooops ${it}", Toast.LENGTH_LONG).show()
        })
    }

    private fun getSelectedRegion() {
        val set: Set<String>? = mSharedPreferences.getStringSet(getString(R.string.pref_region_key), HashSet())
        if (set != null) {

            if (set.contains("all")){
                region = ""
                return
            }
            region = set.toString().replace(" ","").replace("[","").replace("]","").replace(",","|")
        } else{
            region = "US"
        }
    }


    private fun showEmptyList(show: Boolean) {
        if (show) {
            emptyList.visibility = View.VISIBLE
            mRecyclerView.visibility = View.GONE
        } else {
            emptyList.visibility = View.GONE
            mRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun getTopRatedData(region: String){
        if (region.isEmpty())
            viewModel.getTopRated("US")
        else
            viewModel.getTopRated(region)
        mMovieAdapter.submitList(null)
        mSwipeRefreshLayout.isRefreshing = false
    }

    private fun setSwipeRefreshLayoutListener() {
        mSwipeRefreshLayout.setOnRefreshListener {
            refreshTable()
            mSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun refreshTable(){
        mSwipeRefreshLayout.isEnabled = false
        runBlocking {
            async(CommonPool) {
                mDatabase.topRatedDao().deleteAll()
            }.await()
        }
        mSwipeRefreshLayout.isEnabled = true
        mRecyclerView.scrollToPosition(0)
        viewModel.getTopRated(region)
        mMovieAdapter.submitList(null)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        configureRecyclerAdapter(newConfig!!.orientation)
    }
    private fun configureRecyclerAdapter(orientation: Int) {
        val isPortrait = orientation == Configuration.ORIENTATION_PORTRAIT
        mGridLayoutManager = GridLayoutManager(context, if (isPortrait) GRID_COLUMNS_PORTRAIT else GRID_COLUMNS_LANDSCAPE)
        mRecyclerView.setLayoutManager(mGridLayoutManager)
    }
    override fun onMovieClickListener(movie: Movie) {
        val detailIntent = Intent(context, DetailActivity::class.java)
        detailIntent.putExtra("movie",movie)
        context!!.startActivity(detailIntent)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if(key.equals(getString(R.string.pref_region_key))){
            val set: Set<String>? = sharedPreferences!!.getStringSet(getString(R.string.pref_region_key), HashSet())
            if (set != null) {
                if (set.contains("all")){
                    region = ""
                    return
                }
                region = set.toString().replace(" ","").replace("[","").replace("]","").replace(",","|")
            } else{
                region = "US"
            }
            refreshTable()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(context).unregisterOnSharedPreferenceChangeListener(this)
    }

}// Required empty public constructor
