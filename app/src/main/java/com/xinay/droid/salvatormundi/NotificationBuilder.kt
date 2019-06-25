package com.xinay.droid.salvatormundi

import android.app.Notification
import android.content.Context
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.media.session.MediaButtonReceiver

class NotificationBuilder(private val context: Context) {

    private val channelId = "SomeChannelId"


    fun buildNotification(mediaSession: MediaSessionCompat) {
        // Given a media session and its context (usually the component containing the session)
        // Create a NotificationCompat.Builder

        // Get the session's metadata
        val controller = mediaSession.controller
        val mediaMetadata = controller.metadata
        val description = mediaMetadata.description

        val builder = NotificationCompat.Builder(context, channelId).apply {
            // Add the metadata for the currently playing track
            setContentTitle(description.title)
            setContentText(description.subtitle)
            setSubText(description.description)
            setLargeIcon(description.iconBitmap)

            // Enable launching the player by clicking the notification
            setContentIntent(controller.sessionActivity)

            // Stop the service when the notification is swiped away
            setDeleteIntent(
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                    context,
                    PlaybackStateCompat.ACTION_STOP
                )
            )

            // Make the transport controls visible on the lockscreen
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

            // Add an app icon and set its accent color
            // Be careful about the color
            setSmallIcon(R.drawable.notification_icon)
            color = ContextCompat.getColor(context, R.color.primary_material_dark)

            // Add a pause button
            addAction(
                NotificationCompat.Action(
                    R.drawable.exo_controls_pause,
                    "SomeNotificationTitle", // getString(R.string.pause),
                    MediaButtonReceiver.buildMediaButtonPendingIntent(
                        context,
                        PlaybackStateCompat.ACTION_PLAY_PAUSE
                    )
                )
            )

            // Take advantage of MediaStyle features
            setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                //android.support.v4.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSession.sessionToken)
                    .setShowActionsInCompactView(0)

                    // Add a cancel button
                    .setShowCancelButton(true)
                    .setCancelButtonIntent(
                        MediaButtonReceiver.buildMediaButtonPendingIntent(
                            context,
                            PlaybackStateCompat.ACTION_STOP
                        )
                    )
            )
        }

        // fake id
        val id = 1234

        // Display the notification and place the service in the foreground
        startForeground(id, builder.build())
    }

    fun startForeground(id: Int, builder: Notification) {
        TODO("Implement this")
    }
}