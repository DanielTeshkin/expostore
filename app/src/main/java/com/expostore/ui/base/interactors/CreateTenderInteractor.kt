package com.expostore.ui.base.interactors

import com.expostore.data.remote.api.pojo.getcategory.CharacteristicRequest
import com.expostore.data.remote.api.pojo.gettenderlist.TenderRequest
import com.expostore.data.remote.api.pojo.gettenderlist.TenderResponse
import com.expostore.data.repositories.CategoryRepository
import com.expostore.data.repositories.MultimediaRepository
import com.expostore.data.repositories.TenderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

 class CreateTenderInteractor @Inject constructor(
    private val repository: TenderRepository,
    override val multimedia: MultimediaRepository,
    override val category: CategoryRepository
):BaseCreatorInteractor<TenderResponse,TenderResponse,TenderRequest>() {
    override fun published(id: String) = repository.publishedTender(id)
    override fun update(id: String, request: TenderRequest) = repository.updateTender(id, request)
    override fun create(request: TenderRequest) = repository.createTender(request)
     fun getTender(id:String) = repository.getTender(id)
}