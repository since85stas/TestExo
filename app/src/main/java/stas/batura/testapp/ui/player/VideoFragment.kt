package stas.batura.testapp.ui.player

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import kotlinx.android.synthetic.main.video_fragment.*
import stas.batura.testapp.R
import stas.batura.testapp.data.room.Video

public val ARG = "teststring"

class PageFragment : Fragment() {

    private lateinit var arg: String

    private var videoPlayer: SimpleExoPlayer? = null

    companion object {
        fun newInstance(arg: String) =             PageFragment().apply {
            arguments = Bundle().apply {
                putString(ARG, arg)
            }
        }
    }

//    private lateinit var viewModel: PageViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.video_fragment, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        viewModel = ViewModelProvider(this).get(PageViewModel::class.java)
        // TODO: Use the ViewModel
        Log.d("fragm", "onActivityCreated: " + arg)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arg = arguments?.getString(ARG) ?: ""
//        view.findViewById<TextView>(R.id.message).text = arg
        url.text = arg
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    /**
     * инициалзиция Exoplayer
     */
    private fun initVideoPlayer() {

        //TODO: подумать над этим
        if (videoPlayer != null) return

        val trackSelector = DefaultTrackSelector(requireActivity())

        trackSelector.setParameters(
            trackSelector.buildUponParameters().setMaxVideoSize(1920, 1080)
        )

        videoPlayer =
            SimpleExoPlayer.Builder(requireContext()).setTrackSelector(trackSelector).build()

//        binding?.videoView?.player = videoPlayer
        videoPlayer?.repeatMode = Player.REPEAT_MODE_ALL //REPEAT_MODE_ALL
        videoPlayer?.setWakeMode(C.WAKE_MODE_NETWORK)
        videoPlayer?.playWhenReady = false
//        videoPlayer?.addListener(playbackListener)
        videoPlayer?.prepare()


//        _viewModel.restartContent() //for the case if this is return from pause
    }

    /**
     * очищаем ExoPlayer
     */
    private fun releaseVideoPlayer() {
        if (videoPlayer != null) {
//            _viewModel.stopContent() //for the case if this is return from pause
//            _viewModel.clearExoplayerList
//            videoPlayer?.removeListener(playbackListener)
            videoPlayer?.stop()
            videoPlayer?.release()
            videoPlayer = null
        }
    }

    private fun playVideo(video: Video) {
        var url: String = ""

        url = _viewModel.getMediaLocalPath(media)

        Timber.d(
            "==> Play demo video, id: ${media.id}, index: ${_viewModel.indexOf(media)}, duration: ${(media.duration * 1000).toInt()} ====================="
        )

        val mib: MediaItem.Builder =
            MediaItem.Builder().setUri(url)
                .setTag(media)
                .setMediaId(media.id.toString())

        val mediaItem = mib.build()
        val mediaSource = DefaultMediaSourceFactory(_dataSourceFactory!!)
            .createMediaSource(mediaItem)

        videoPlayer?.setMediaSource(mediaSource)
        videoPlayer?.prepare()
        videoPlayer?.play()
    }
}