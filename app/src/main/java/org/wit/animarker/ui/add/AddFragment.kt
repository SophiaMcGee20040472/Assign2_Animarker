package org.wit.animarker.ui.add

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.animarker.ui.add.AddViewModel
import org.wit.animarker.R
import org.wit.animarker.databinding.FragmentAddBinding
import org.wit.animarker.main.MainApp
import org.wit.animarker.models.AnimarkerManager
import org.wit.animarker.models.AnimarkerModel
import org.wit.animarker.utils.showImagePicker
import timber.log.Timber
import java.time.LocalDate

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    // TODO map intent launcher
    lateinit var app: MainApp
    private val binding get() = _binding!!
    var animarker = AnimarkerModel()
    private lateinit var addViewModel: AddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = MainApp()
        Timber.i("ON CREATE ADD FRAGMENT")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.i("CREATE VIEW ADD FRAGMENT")

        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }
        registerImagePickerCallback()


        addViewModel = ViewModelProvider(this).get(AddViewModel::class.java)
        addViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
            status -> status?.let {render(status)}
        })
        setButtonListener(binding)
        return root
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                }
            }
            false -> Toast.makeText(context,getString(R.string.animarkerError), Toast.LENGTH_LONG).show()
        }
    }

    fun setButtonListener(layout: FragmentAddBinding) {
        layout.btnAdd.setOnClickListener {
            animarker.title = binding.animarkerTitle.text.toString()
            animarker.description = binding.animarkerDescription.text.toString()
            animarker.destination = binding.animarkerDestination.text.toString()
            animarker.date = binding.animarkerDate.text.toString()

            if (animarker.title.isNotEmpty() && animarker.description.isNotEmpty() && animarker.destination.isNotEmpty()) {
//
                addViewModel.addAnimarker(
                    AnimarkerModel(title = animarker.title, destination = animarker.destination, description = animarker.description, date = animarker.date,image = animarker.image,
                    )
                )
                findNavController().navigateUp()
                Timber.i("ADDING Animarker %s", animarker)
            } else {
                Snackbar.make(it, R.string.all_fields_required, Snackbar.LENGTH_LONG).show()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // TODO override fun onResume() {

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            animarker.image = result.data!!.data.toString()!!
                            Picasso.get().load(animarker.image).into(binding.animarkerImage)
                            binding.chooseImage.setText(R.string.edit_image)
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }
}