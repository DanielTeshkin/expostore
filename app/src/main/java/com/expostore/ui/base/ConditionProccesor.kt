package com.expostore.ui.base

import android.util.Log

class ConditionProcessor {
    private val list= mutableListOf<String>()
   fun  checkCondition(condition: () ->Boolean,
                                actionFalse:(()->Unit)?=null,
                                actionTrue:(() ->Unit)?=null){
        when(condition.invoke()){
            true ->{ actionTrue?.invoke()}
            false->actionFalse?.invoke()
        }

   }
    fun  checkMultiCondition( vararg action: () ->Boolean,
                                conditionFalse:()->Unit,
                                conditionTrue:() ->Unit){
        var state= true
       action.forEach {
           if (!it.invoke()) {
               state = false
               return@forEach
           }
       }
        when(state){
            true ->conditionTrue.invoke()
            false->conditionFalse.invoke()
        }

    }
    fun  checkConditionParameter( vararg condition: () ->Boolean,
                        actionFalse:(()->Unit)?=null,
                        actionTrue:((List<Boolean>) ->Unit)?=null){


        var state= false
        val states= mutableListOf<Boolean>()
        condition.forEach {
            val current=it.invoke()
            states.add(current)
        }
        state=states.contains(true)
        when(state){
            true ->actionTrue?.invoke(states)
            false->actionFalse?.invoke()
        }

    }
    fun <T,A> lop(list:List<T>,mapper: Mapper<T,A>) : List<A> = list.map{mapper.invoke(it)}
    fun Test(){
        val a=0
         checkCondition( { a in 1 downTo 0 }, { ""}, { print("")} )
    }

}
