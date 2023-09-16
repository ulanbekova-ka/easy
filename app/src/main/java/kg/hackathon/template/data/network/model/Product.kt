package kg.hackathon.template.data.network.model

import java.io.Serializable

data class Product(
    var id: Long? = null,
    var name: String? = null,
) : Serializable