package org.wit.animarker.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import org.wit.animarker.R
import org.wit.animarker.databinding.ActivityAnimarkerBinding
import org.wit.animarker.databinding.ActivityAnimarkerListBinding
import org.wit.animarker.main.MainApp
import org.wit.animarker.models.AnimarkerModel
import timber.log.Timber
import timber.log.Timber.i

class AnimarkerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimarkerBinding
    var animarker = AnimarkerModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAnimarkerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        i("Animarker Activity started...")

        binding.btnAdd.setOnClickListener() {
            animarker.title = binding.animarkerTitle.text.toString()
            animarker.description = binding.description.text.toString()
            if (animarker.title.isNotEmpty()) {

                app.animarkers.add(animarker.copy())
                i("add Button Pressed: ${animarker}")
                for (i in app.animarkers.indices) {
                    i("Animarker[$i]:${this.app.animarkers[i]}")
                }
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_animarker, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
