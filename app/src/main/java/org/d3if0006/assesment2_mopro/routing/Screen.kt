package org.d3if0006.assesment2_mopro.routing

sealed class Screen (val route: String){

    data object Home: Screen("mainScreen")

    data object DetailOutfits: Screen("detailOutfits/{id}"){
        fun withId(id: Long): String{
            val routeWithId = route.replace("{id}", id.toString())
            println("DetailOutfits: Id included in route: $routeWithId")
            return routeWithId
        }
    }
    data object AddOutfits: Screen("addOutfits")

}