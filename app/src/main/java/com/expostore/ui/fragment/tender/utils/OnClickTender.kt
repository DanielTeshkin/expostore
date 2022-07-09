package com.expostore.ui.fragment.tender.utils

import com.expostore.model.tender.TenderModel

interface OnClickTender {
    fun call(phone:String)
    fun showInfoTender(tender:TenderModel)
}