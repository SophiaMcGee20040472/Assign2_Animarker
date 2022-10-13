package org.wit.animarker.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.animarker.R
import org.wit.animarker.databinding.ActivityAnimarkerBinding
import org.wit.animarker.helpers.showImagePicker
import org.wit.animarker.main.MainApp
import org.wit.animarker.models.AnimarkerModel
import timber.log.Timber.i

class AnimarkerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimarkerBinding
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
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
        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }
        registerImagePickerCallback()

        i("Animarker Activity started...")


        if (intent.hasExtra("animarker_edit")) {
            edit = true
            animarker = intent.extras?.getParcelable("animarker_edit")!!
            binding.animarkerTitle.setText(animarker.title)
            binding.description.setText(animarker.description)
            binding.btnAdd.setText(R.string.save_animarker)
            binding.deleteAnimarker.setText(R.string.button_delete_animarker)

            // only visible in edit mode
            binding.deleteAnimarker.setVisibility(View.VISIBLE)
        }


        binding.btnAdd.setOnClickListener() {
            animarker.title = binding.animarkerTitle.text.toString()
            animarker.description = binding.description.text.toString()
            if (animarker.title.isNotEmpty()) {
                if (edit) {
                    app.animarkers.update(animarker.copy())
                } else {
                    app.animarkers.create(animarker.copy())
                    i("add Button Pressed: $animarker")
                }
                setResult(RESULT_OK)
                finish()
            } else {
                Snackbar
                    .make(it, R.string.enter_animarker_title, Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        binding.deleteAnimarker.setOnClickListener() {
            app.animarkers.delete(animarker)
            i("delete button pressed: $animarker")
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
            R.id.cancel_button -> {
                val launcherIntent = Intent(this, AnimarkerActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            animarker.image = result.data!!.data!!
                            Picasso.get()
                                .load(animarker.image)
                                .into(binding.animarkerImage)
                        } // end of if
                    }
                    RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }
}

