package com.xinay.droid.salvatormundi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY
import android.service.media.MediaBrowserService
import android.support.v4.media.session.MediaSessionCompat

class MySessionCallback(private val context: Context): MediaSessionCompat.Callback() {

    private val intentFilter = IntentFilter(ACTION_AUDIO_BECOMING_NOISY)

    // Defined elsewhere...
    private lateinit var afChangeListener: AudioManager.OnAudioFocusChangeListener
    private val myNoisyAudioStreamReceiver = BecomingNoisyReceiver()
    private lateinit var myPlayerNotification: MediaStyleNotification
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var service: MediaBrowserService
    private lateinit var player: SomeKindOfPlayer

    private lateinit var audioFocusRequest: AudioFocusRequest

    private val callback = object: MediaSessionCompat.Callback() {
        override fun onPlay() {
            val am = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            // Request audio focus for playback, this registers the afChangeListener

            audioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN).run {
                setOnAudioFocusChangeListener(afChangeListener)
                setAudioAttributes(AudioAttributes.Builder().run {
                    setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    build()
                })
                build()
            }
            val result = am.requestAudioFocus(audioFocusRequest)
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                // Start the service
                startService(Intent(context, MediaBrowserService::class.java))
                // Set the session active  (and update metadata and state)
                mediaSession.isActive = true
                // start the player (custom call)
                player.start()
                // Register BECOME_NOISY BroadcastReceiver
                registerReceiver(myNoisyAudioStreamReceiver, intentFilter)
                // Put the service in the foreground, post notification
                service.startForeground(id, myPlayerNotification)
            }
        }
    }

    public override fun onStop() {
        val am = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        // Abandon audio focus
        am.abandonAudioFocusRequest(audioFocusRequest)
        unregisterReceiver(myNoisyAudioStreamReceiver)
        // Stop the service
        service.stopSelf()
        // Set the session inactive  (and update metadata and state)
        mediaSession.isActive = false
        // stop the player (custom call)
        player.stop()
        // Take the service out of the foreground
        service.stopForeground(false)
    }

    public override fun onPause() {
        val am = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        // Update metadata and state
        // pause the player (custom call)
        player.pause()
        // unregister BECOME_NOISY BroadcastReceiver
        unregisterReceiver(myNoisyAudioStreamReceiver)
        // Take the service out of the foreground, retain the notification
        service.stopForeground(false)
    }

    private class BecomingNoisyReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == AudioManager.ACTION_AUDIO_BECOMING_NOISY) {
                // Pause the playback
            }
        }
    }
}