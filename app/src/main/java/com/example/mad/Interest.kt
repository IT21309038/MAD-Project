package com.example.mad

data class Interest(
    var interestId: String? = null,
    var comName: String? = null,
    var conMail: String? = null,
    var AdViewerName: String? = null,
    var position: String? = null,
    var UserUID: String? = null,
    val jobData: Map<String, Any>? = null
)

