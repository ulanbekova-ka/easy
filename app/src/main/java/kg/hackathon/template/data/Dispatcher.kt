package kg.hackathon.template.data

import kotlinx.coroutines.CoroutineDispatcher

interface Dispatcher {
    fun ui(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
    fun default() : CoroutineDispatcher
    fun unconfined(): CoroutineDispatcher
}