package ro.upt.ac.chiuitter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.view_home.*
import org.w3c.dom.Text

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_home)

        ibt_share.setOnClickListener { shareChiuit(txv_content.text.toString()) }
        fab_add.setOnClickListener { composeChiuit() }
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
//        Log.i("returned", "SUNTEM AICI")
        when (requestCode) {
            COMPOSE_REQUEST_CODE -> if (resultCode == Activity.RESULT_OK) extractText(data)
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun extractText(data: Intent?) {
        data?.let {
            // Extract the text from result intent.

            val newChiuit = data.getStringExtra("extra_text")
//            Log.i("newChiuit", newChiuit)

            // Check if text is not null or empty, then set the new "chiuit" content.
            if (newChiuit != null && newChiuit.isNotEmpty()) {
                txv_content.text = newChiuit
            }

        }
    }

    companion object {
        const val COMPOSE_REQUEST_CODE = 1213
    }

}
