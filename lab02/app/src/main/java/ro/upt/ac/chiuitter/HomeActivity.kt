package ro.upt.ac.chiuitter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.view_home.*

class HomeActivity : AppCompatActivity() {

    private val dummyChiuitStore = DummyChiuitStore()

    private lateinit var listAdapter: ChiuitRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_home)

        fab_add.setOnClickListener { composeChiuit() }

        initList()
    }

    private fun initList() {
        val chiuitList = dummyChiuitStore.getAllData()

        // Instantiate the adapter, then setup the recycler view list
        val viewManager = LinearLayoutManager(this)
        listAdapter = ChiuitRecyclerViewAdapter(
                chiuitList
        ) { chiuit -> shareChiuit(chiuit.description) }

        rv_chiuit_list.apply {
            layoutManager = viewManager
            adapter = listAdapter
        }
    }

    /*
    Defines text sharing/sending *implicit* intent, opens the application chooser menu,
    and starts a new activity which supports sharing/sending text.
     */
    private fun shareChiuit(text: String) {
        val sendIntent = Intent().apply {
            // Configure to support text sending/sharing and then attach the text as intent's extra.
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)

        }

        val intentChooser = Intent.createChooser(sendIntent, "")

        if (sendIntent.resolveActivity(packageManager) != null) {
            startActivity(intentChooser)
        }
    }

    /*
    Defines an *explicit* intent which will be used to start ComposeActivity.
     */
    private fun composeChiuit() {
        // Create an explicit intent which points to ComposeActivity.
        val composeIntent = Intent(this, ComposeActivity::class.java)

        // Start a new activity with the previously defined intent.
        startActivityForResult(composeIntent, COMPOSE_REQUEST_CODE)

        // We start a new activity that we expect to return the acquired text as the result.
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            COMPOSE_REQUEST_CODE -> if (resultCode == Activity.RESULT_OK) extractText(data)
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun extractText(data: Intent?) {
        data?.let {
            // Extract the text from result intent.

            val newChiuitText = data.getStringExtra("extra_text")
            Log.i("newChiuit", newChiuitText)

            // Check if text is not null or empty, then
            // Instantiate a new chiuit object then add it to the adapter
            if (!newChiuitText.isNullOrEmpty()) {
                val newChiuit = Chiuit(newChiuitText)
                listAdapter.addItem(newChiuit)
            }
        }
    }

    companion object {
        const val COMPOSE_REQUEST_CODE = 1213
    }

}
