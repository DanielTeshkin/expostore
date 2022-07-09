package com.expostore.api.pojo.getcategory

import com.fasterxml.jackson.annotation.JsonProperty

data class GetCategoryResponseData(
   val categories: List<Category>?
)
