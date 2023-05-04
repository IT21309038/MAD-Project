package com.example.mad

import android.icu.text.CaseMap.Title
import android.media.Image
import android.renderscript.RenderScript.Priority

class DataClasss {
    var dataTitle: String? = null
    var dataDesc: String? = null
    var dataPriority: String? = null
    var dataImage: String? = null

    constructor(dataTitle: String?, dataDesc: String?, dataPriority: String?, dataImage: String?){
        this.dataTitle = dataTitle
        this.dataDesc = dataDesc
        this.dataPriority = dataPriority
        this.dataImage = dataImage
    }
    constructor(){

    }

}