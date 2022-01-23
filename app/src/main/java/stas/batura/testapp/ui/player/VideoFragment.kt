package stas.batura.testapp.ui.player

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.audio.AudioSink
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.android.synthetic.main.video_fragment.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import stas.batura.testapp.R
import stas.batura.testapp.data.room.Video

public val ARG = "teststring"

private const val TAG = "VideoFragment"

class PageFragment : Fragment() {

    private lateinit var arg: String

    private var videoPlayer: SimpleExoPlayer? = null

//    private var _dataSourceFactory: DefaultDataSourceFactory? = null

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

        initVideoPlayer()

        play_button.setOnClickListener {
            playVideo()
        }

        pause_button.setOnClickListener {
            pauseVideo()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onPause() {
        pauseVideo()
        super.onPause()
    }

    override fun onStop() {

        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseVideoPlayer()
    }

    /**
     * инициалзиция Exoplayer
     */
    private fun initVideoPlayer() {

        val trackSelector = DefaultTrackSelector(requireActivity())

        trackSelector.setParameters(
            trackSelector.buildUponParameters().setMaxVideoSize(1920, 1080)
        )

        videoPlayer =
            SimpleExoPlayer.Builder(requireContext()).setTrackSelector(trackSelector).build()

        video_view.player = videoPlayer

        videoPlayer?.apply {
            repeatMode = Player.REPEAT_MODE_ALL //REPEAT_MODE_ALL
            setWakeMode(C.WAKE_MODE_NETWORK)
            playWhenReady = false
            addListener(playbackStateListener())
            prepare()
        }

    }

    /**
     * очищаем ExoPlayer
     */
    private fun releaseVideoPlayer() {
        videoPlayer?.apply {
            stop()
            release()
        }
            videoPlayer = null
    }

    private fun playVideo() {
        var url: String = arg

        val mib: MediaItem.Builder =
            MediaItem.Builder().setUri(url)

        val mediaItem = mib.build()
        val mediaSource = DefaultMediaSourceFactory(DefaultDataSourceFactory(
            requireContext(),
            "Google Chrome")).createMediaSource(mediaItem)

        videoPlayer?.setMediaSource(mediaSource)
        videoPlayer?.prepare()
        videoPlayer?.play()
    }

    private fun pauseVideo() {
        videoPlayer?.pause()
    }

    /**
     * слушатель для ответов плеера
     */
    private fun playbackStateListener(): Player.Listener = object : Player.Listener {

        // обработка ошибок
        override fun onPlayerError(error: ExoPlaybackException) {

            when (error.type) {
                ExoPlaybackException.TYPE_SOURCE -> {
                    Log.d(TAG, "onPlayerError: $error")
                }

                ExoPlaybackException.TYPE_RENDERER -> {
                    Log.d(TAG,
                        "ExoPlayer error, TYPE_RENDERER: $error"
                    )
                }

                ExoPlaybackException.TYPE_UNEXPECTED -> {
                    Log.d(TAG,
                        "ExoPlayer error, TYPE_UNEXPECTED: $error"
                    )
                }
                ExoPlaybackException.TYPE_REMOTE -> {
                    Log.d(TAG,
                        "ExoPlayer error, TYPE_REMOTE: $error"
                    )
                }
                else -> {
                    Log.d(TAG,
                        "ExoPlayer error, TYPE_NOT_FOUND: $error"
                    )
                }
            }

        }
    }
}