package org.wit.animarker.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import org.wit.animarker.R
import org.wit.animarker.databinding.ActivityAnimarkerBinding
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
        var edit = false
        binding = ActivityAnimarkerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp

        if (intent.hasExtra("animarker_edit")) {
            edit = true
            animarker = intent.extras?.getParcelable("animarker_edit")!!
            binding.animarkerTitle.setText(animarker.title)
            binding.description.setText(animarker.description)
            binding.btnAdd.setText(R.string.save_animarker)
        }

        binding.btnAdd.setOnClickListener() {
            animarker.title = binding.animarkerTitle.text.toString()
            animarker.description = binding.description.text.toString()
            if (animarker.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_animarker_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.animarkers.update(animarker.copy())
                } else {
                    app.animarkers.create(animarker.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_animarker, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }
}