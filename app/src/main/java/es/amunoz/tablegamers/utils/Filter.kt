package es.amunoz.tablegamers.utils

data class Filter(
    var title:String?="",
    var price_since:Int?=0,
    var price_until:Int?=0,
    var province:String?="",
){

    fun validationPrice(): Boolean{
        return price_since!! <= price_until!!
    }
}