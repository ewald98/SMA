package ro.upt.ac.chiuitter.data.database

import ro.upt.ac.chiuitter.data.ChiuitRepository
import ro.upt.ac.chiuitter.domain.Chiuit

class DbChiuitStore(private val appDatabase: AppDatabase) : ChiuitRepository {

    override suspend fun getAll(): List<Chiuit> {
        return appDatabase.chiuitDao().getAll().map { it.toDomainModel() }
    }

    override suspend fun addChiuit(chiuit: Chiuit) {
    }

    override suspend fun removeChiuit(chiuit: Chiuit) {
    }


    private fun Chiuit.toDbModel() = ChiuitEntity(timestamp, description)

    private fun ChiuitEntity.toDomainModel() = Chiuit(timestamp, description)

}