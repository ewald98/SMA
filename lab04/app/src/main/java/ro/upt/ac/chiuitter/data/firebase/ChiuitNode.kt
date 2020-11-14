package ro.upt.ac.chiuitter.data.firebase

data class ChiuitNode(var timestamp: Long, var description: String) {
    constructor() : this(0L, "")
}