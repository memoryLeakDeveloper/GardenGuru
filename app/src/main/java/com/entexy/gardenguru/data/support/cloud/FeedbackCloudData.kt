package com.entexy.gardenguru.data.support.cloud

data class FeedbackCloudData(
    val app: String,
    val subject: String,
    val description: String,
    val client: ClientCloudData,
    val media: List<Int>
)