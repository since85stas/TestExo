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

private val TAG = "PlayerFragment"

@AndroidEntryPoint
class PlayerFragment: Fragment() {

    private lateinit var pagerAdapter: ScreenSlidePagerAdapter

    private lateinit var viewModel: PlayerViewModel

    private var startPage: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel =
            ViewModelProvider(this).get(PlayerViewModel::class.java)
        return inflater.inflate(R.layout.player_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.users.observe(viewLifecycleOwner) {videos ->
            initPlayerVideos(videos = videos)
        }

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

        // показывает скачались ли уже данные, если удачного скачивания не было,
        // то вью с плеером не показываем
        viewModel.isDataReady.observe(viewLifecycleOwner) { ready ->
            if (ready) {
                viewPager.visibility = View.VISIBLE
            } else {
                viewPager.visibility = View.GONE
            }
        }
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

        for (video in videos) {
            pagerAdapter.addFragment(PageFragment.newInstance((video.url)))
        }

        viewPager.currentItem = startPage
    }

    /**
     * пеоказывает тост с заданным сообщением
     * @param message строка для отображения
     */
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    /**
     * простой пейджер - добавляет фрагменты с плеером по количеству видео,
     * в нашем случае будет 4
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