package ro.upt.ac.chiuitter.data.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ro.upt.ac.chiuitter.data.ChiuitRepository
import ro.upt.ac.chiuitter.domain.Chiuit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseChiuitStore : ChiuitRepository {

    private val database = FirebaseDatabase.getInstance().reference.child("chiuits")

    override suspend fun getAll(): List<Chiuit> = suspendCoroutine { continuation ->
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                database.removeEventListener(this)
                continuation.resumeWithException(p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                val values = mutableListOf<ChiuitNode>()

                val children = p0.children

                // Iterate through the children and get the node value
                for (child in children) {
                    values.add(ChiuitNode(child.child("timestamp").value as Long, child.child("description").value as String))
                }

                database.removeEventListener(this)

                continuation.resume(values.map { chiuitNode -> chiuitNode.toDomainModel() })
            }

        })
    }

    override suspend fun addChiuit(chiuit: Chiuit): Unit = suspendCoroutine { continuation ->
        // Insert the object into database - don't forget to use the right model
        database.child(chiuit.timestamp.toString()).setValue(chiuit.toFirebaseModel())

        // Make sure the continuation is called
        continuation.resume(Unit)
    }

    override suspend fun removeChiuit(chiuit: Chiuit) : Unit = suspendCoroutine { continuation ->
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                database.removeEventListener(this)
                continuation.resumeWithException(p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                val children = p0.children


                // Iterate through the children and find the matching node, then perform removal.
                for (child in children) {
                    if (child.child("timestamp").value == chiuit.timestamp)
                        database.child(chiuit.timestamp.toString()).removeValue()
                }

                database.removeEventListener(this)

                // Make sure the continuation is called
                continuation.resume(Unit)
            }

        })
    }

    fun Chiuit.toFirebaseModel(): ChiuitNode {
        return ChiuitNode(timestamp, description)
    }

    fun ChiuitNode.toDomainModel(): Chiuit {
        return Chiuit(timestamp, description)
    }

}