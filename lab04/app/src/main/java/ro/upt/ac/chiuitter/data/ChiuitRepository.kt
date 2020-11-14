package ro.upt.ac.chiuitter.data

import ro.upt.ac.chiuitter.domain.Chiuit

interface ChiuitRepository {
    suspend fun getAll() : List<Chiuit>
    suspend fun addChiuit(chiuit: Chiuit)
    suspend fun removeChiuit(chiuit: Chiuit)
}