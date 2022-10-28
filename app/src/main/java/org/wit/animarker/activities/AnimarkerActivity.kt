package org.wit.animarker.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.RatingBar
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.animarker.R
import org.wit.animarker.databinding.ActivityAnimarkerBinding
import org.wit.animarker.helpers.showImagePicker
import org.wit.animarker.main.MainApp
import org.wit.animarker.models.AnimarkerModel
import org.wit.animarker.models.Location
import timber.log.Timber.i
import java.util.*

class AnimarkerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimarkerBinding
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
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

        val rBar = findViewById<RatingBar>(R.id.rBar)
        if (rBar != null) {
            val button = findViewById<Button>(R.id.button)
            button?.setOnClickListener {
                val msg = rBar.rating.toString()
                Toast.makeText(this@AnimarkerActivity,
                    "Rating is: "+msg, Toast.LENGTH_SHORT).show()
            }
        }
        registerImagePickerCallback()

        i("Animarker Activity started...")
        registerImagePickerCallback()



        /*val datePicker = findViewById<DatePicker>(R.id.datePicker)
        val today = Calendar.getInstance()
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            val month = month + 1
            val msg = "You Selected: $day/$month/$year"
            Toast.makeText(this@AnimarkerActivity, msg, Toast.LENGTH_SHORT).show()
        }
*/
        registerMapCallback()
        binding.animarkerLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (animarker.zoom != 0f) {
                location.lat =  animarker.lat
                location.lng = animarker.lng
                location.zoom = animarker.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }
        if (intent.hasExtra("animarker_edit")) {
            edit = true
            animarker = intent.extras?.getParcelable("animarker_edit")!!
            binding.animarkerTitle.setText(animarker.title)
            binding.description.setText(animarker.description)
            binding.destination.setText(animarker.destination)
            binding.btnAdd.setText(R.string.save_animarker)
            binding.deleteAnimarker.setText(R.string.button_delete_animarker)

            /*        datePicker.init(animarker.dateAvailable.year, animarker.dateAvailable.monthValue,
                        animarker.dateAvailable.dayOfMonth
                    ) { view, year, month, day ->
                        val month = month + 1
                        val msg = "You Selected: $day/$month/$year"
                        Toast.makeText(this@AnimarkerActivity, msg, Toast.LENGTH_SHORT).show()
                    }*/
            // only visible in edit mode
            binding.deleteAnimarker.setVisibility(View.VISIBLE)
            Picasso.get()
                .load(animarker.image)
                .into(binding.animarkerImage)
            if (animarker.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_animarker_image)
            }
        }

        binding.btnAdd.setOnClickListener() {
            animarker.title = binding.animarkerTitle.text.toString()
            animarker.description = binding.description.text.toString()
            animarker.destination = binding.destination.text.toString()
            if (animarker.title.isNotEmpty() && animarker.description.isNotEmpty() && animarker.destination.isNotEmpty()) {
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
                startActivityForResult(launcherIntent, 0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            animarker.lat = location.lat
                            animarker.lng = location.lng
                            animarker.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            animarker.image = result.data!!.data!!
                            Picasso.get()
                                .load(animarker.image)
                                .into(binding.animarkerImage)
                            binding.chooseImage.setText(R.string.change_animarker_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }

            }

    }
}