package stas.batura.testapp.ui.player

import android.content.Context
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
import android.widget.Toast

import com.google.android.material.snackbar.Snackbar


public val ARG = "ArgumentKey"

private const val TAG = "VideoFragment"

class PageFragment : Fragment() {

    private lateinit var arg: String

    private var videoPlayer: SimpleExoPlayer? = null

    companion object {
        fun newInstance(arg: String) = PageFragment().apply {
            arguments = Bundle().apply {
                putString(ARG, arg)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.video_fragment, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        viewModel = ViewModelProvider(this).get(PageViewModel::class.java)
        // TODO: Use the ViewModel
        Log.d(TAG, "onActivityCreated: " + arg)

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
        val mib: MediaItem.Builder =
            MediaItem.Builder().setUri(arg)

        val mediaItem = mib.build()
        val mediaSource = DefaultMediaSourceFactory(
            DefaultDataSourceFactory(
                requireContext(),
                "Google Chrome"
            )
        ).createMediaSource(mediaItem)

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
                    Log.d(TAG,
                        "onPlayerError: $error")
                    createSnack("Video error: Source")
                }

                ExoPlaybackException.TYPE_RENDERER -> {
                    Log.d(
                        TAG,
                        "ExoPlayer error, TYPE_RENDERER: $error"
                    )
                    createSnack("Video error: Renderer")
                }

                ExoPlaybackException.TYPE_UNEXPECTED -> {
                    Log.d(
                        TAG,
                        "ExoPlayer error, TYPE_UNEXPECTED: $error"
                    )
                    createSnack("Video error: Unexpected")
                }
                ExoPlaybackException.TYPE_REMOTE -> {
                    Log.d(
                        TAG,
                        "ExoPlayer error, TYPE_REMOTE: $error"
                    )
                    createSnack("Video error: Remote")
                }
                else -> {
                    Log.d(
                        TAG,
                        "ExoPlayer error, TYPE_NOT_FOUND: $error"
                    )
                    createSnack("Video error: Unknown")
                }
            }

        }
    }

    private fun createSnack(text:String) {
        val snackbar =
            Snackbar.make(requireView(), text, Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction(
            "CLOSE"
        ) {

        }
        snackbar.show()
    }
}