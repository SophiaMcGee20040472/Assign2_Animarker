package org.wit.animarker.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.animarker.R
import org.wit.animarker.adapters.AnimarkerAdapter
import org.wit.animarker.adapters.AnimarkerListener
import org.wit.animarker.databinding.ActivityAnimarkerBinding
import org.wit.animarker.databinding.ActivityAnimarkerListBinding
import org.wit.animarker.main.MainApp
import org.wit.animarker.models.AnimarkerModel

class AnimarkerListActivity : AppCompatActivity(), AnimarkerListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityAnimarkerListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimarkerListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadAnimarkers()

        registerRefreshCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, AnimarkerActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onAnimarkerClick(animarker: AnimarkerModel) {
        val launcherIntent = Intent(this, AnimarkerActivity::class.java)
        launcherIntent.putExtra("animarker_edit", animarker)
        refreshIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadAnimarkers() }
    }

    private fun loadAnimarkers() {
        showAnimarkers(app.animarkers.findAll())
    }

    fun showAnimarkers (animarkers: List<AnimarkerModel>) {
        binding.recyclerView.adapter = AnimarkerAdapter(animarkers, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}