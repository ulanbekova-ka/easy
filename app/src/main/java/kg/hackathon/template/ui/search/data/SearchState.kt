package kg.hackathon.template.ui.search.data

import kg.hackathon.template.data.network.model.Product

data class SearchState(
    val inpSearch: String? = "",
    val products: List<Product>? = emptyList(),
    val loading: Boolean = false
)
