package com.xinay.droid.salvatormundi.music.player

import android.content.Context
import android.net.Uri
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
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
        TODO("not implemented")
    }

    override fun releasePlayer() {
        TODO("not implemented")
    }

    override fun setMediaSessionState(isActive: Boolean) {
        TODO("not implemented")
    }
}