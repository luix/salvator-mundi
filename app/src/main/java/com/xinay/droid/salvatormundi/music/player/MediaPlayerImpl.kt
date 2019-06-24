package com.xinay.droid.salvatormundi.music.player

import android.content.Context
import android.net.Uri
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.xinay.droid.salvatormundi.R

class MediaPlayerImpl : MediaPlayer {

    companion object {
        private const val TAG = "MediaPlayerTag"
    }

    private lateinit var exoPlayer: ExoPlayer
    private lateinit var context: Context
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var stateBuilder: PlaybackStateCompat.Builder

    override fun play(url: String) {

        val userAgent = Util.getUserAgent(context, context.getString(R.string.app_name))

        val mediaSource = ExtractorMediaSource.Factory(DefaultDataSourceFactory(context, userAgent))
            .setExtractorsFactory(DefaultExtractorsFactory())
            .createMediaSource(Uri.parse(url))

        exoPlayer.prepare(mediaSource)

        exoPlayer.playWhenReady = true

    }

    override fun getPlayerImpl(context: Context): ExoPlayer {
        this.context = context
        initializePlayer()
        initializeMediaSession()
        return exoPlayer
    }

    override fun releasePlayer() {
        exoPlayer.stop()
        exoPlayer.release()
    }

    override fun setMediaSessionState(isActive: Boolean) {
        mediaSession.isActive = isActive
    }

    private fun initializePlayer() {

        val trackSelector = DefaultTrackSelector()
        val loadControl = DefaultLoadControl()
        val renderersFactory = DefaultRenderersFactory(context)

        exoPlayer = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl)
    }
}