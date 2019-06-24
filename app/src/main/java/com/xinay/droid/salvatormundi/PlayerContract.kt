package com.xinay.droid.salvatormundi

interface PlayerContract {

    interface Presenter {

        fun fetchSampleVideos()

        fun deactivate()

        fun showVideoScreen(videoUrl: String)
    }

    interface View {

        fun renderVideos(videos: List<ApiVideo>)

        fun showErrorMessage()
    }
}