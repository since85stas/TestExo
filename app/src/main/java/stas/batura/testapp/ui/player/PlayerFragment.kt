package stas.batura.testapp.ui.player

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.player_fragment.*
import stas.batura.testapp.R
import stas.batura.testapp.data.room.Video
import stas.batura.testapp.utils.ZoomOutPageTransformer

private val TAG = "PlayerFragment.kt"

@AndroidEntryPoint
class PlayerFragment: Fragment() {

    private val TAG = "MainActivity.kt"

    private lateinit var pagerAdapter: ScreenSlidePagerAdapter

    private lateinit var testText: String

    private lateinit var viewModel: PlayerViewModel

    private var videoPlayer: SimpleExoPlayer? = null

//    private var _dataSourceFactory: DefaultDataSourceFactory? = null
//
//    private lateinit var bindings: ReadFragmentBinding

    private var startPage: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel =
            ViewModelProvider(this).get(PlayerViewModel::class.java)
//
//        bindings = DataBindingUtil.inflate(inflater,
//        R.layout.read_fragment,
//        container,
//        false)
//
//        bindings.viewModel = viewModel
//
//        bindings.lifecycleOwner = viewLifecycleOwner
//
//        startPage = viewModel.repository.getLastPage()
//        inflater.inflate(R.layout.player_fragment, container, false)
        return inflater.inflate(R.layout.player_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.users.observe(viewLifecycleOwner) {videos ->
            initPlayerVideos(videos = videos)
        }
//        initPlayerVideos()

        viewModel.spinner.observe(viewLifecycleOwner) { visible ->
            if (visible) {
                loadingSpinner.visibility = View.VISIBLE
            } else {
                loadingSpinner.visibility = View.GONE
            }
        }

        viewModel.toastTex.observe(viewLifecycleOwner) {text ->
            if (text.isNotBlank()) {
                showToast(text)
            }
        }

//        initVideoPlayer()

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    private fun initPlayerVideos(videos: List<Video>) {
        viewPager.setPageTransformer(ZoomOutPageTransformer())

        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        pagerAdapter = ScreenSlidePagerAdapter(requireActivity().supportFragmentManager, videos)
        viewPager.setAdapter(pagerAdapter)
//        viewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this)

        for (video in videos) {
            pagerAdapter.addFragment(PageFragment.newInstance((video.url)))
        }

        viewPager.currentItem = startPage
//        viewPager.getViewTreeObserver().addOnGlobalLayoutListener(object :
//            ViewTreeObserver.OnGlobalLayoutListener {
//            override fun onGlobalLayout() {
//                pagerAdapter = ScreenSlidePagerAdapter(requireActivity().supportFragmentManager, videos)
//                viewPager.setAdapter(pagerAdapter)
//                viewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this)
//
//                for (video in videos) {
//                    pagerAdapter.addFragment(PageFragment.newInstance((video.url)))
//                }
//
//                viewPager.currentItem = startPage
//            }
//        })
    }

    /**
     * пеоказывает тост с заданным сообщением
     * @param message строка для отображения
     */
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    /**
     * A simple pager adapter that represents 4 ScreenSlidePageFragment objects, in
     * sequence.
     */
    inner class ScreenSlidePagerAdapter(
        val fragmentManager: FragmentManager,
        val videosList: List<Video>?
    ) :
        FragmentStateAdapter(fragmentManager, viewLifecycleOwner.lifecycle) {

        private var arrayList: ArrayList<Fragment> = ArrayList()

        override fun createFragment(position: Int): Fragment {
            Log.d(TAG, "fragment pos=${position} created")
            Log.d(TAG, "createFragment: ")
            return arrayList.get(position)
        }

        override fun getItemId(position: Int): Long {
            Log.d(TAG, "getItemId: $position" )
            return super.getItemId(position)
        }



        fun addFragment(fragment: Fragment?) {
            arrayList.add(fragment!!)
        }

        override fun getItemCount(): Int {
            return videosList!!.size
        }

        fun removeFragments() {
            arrayList = ArrayList()
        }

    }



}