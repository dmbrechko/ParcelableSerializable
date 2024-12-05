package com.example.parcelableserializable

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.parcelableserializable.databinding.ActivityCardBinding
import java.util.Locale

class CardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.apply {
            val person = intent.getParcelableCompat<Person>(KEY_PERSON) ?: throw IllegalStateException("Person must be supplied")
            nameTV.text = String.format(Locale.getDefault(), getString(R.string.name_placeholder), person.name)
            lastNameTV.text = String.format(Locale.getDefault(), getString(R.string.last_name_placeholder), person.lastName)
            addressTV.text = String.format(Locale.getDefault(), getString(R.string.address_placeholder), person.address)
            phoneTV.text = String.format(Locale.getDefault(), getString(R.string.phone_placeholder), person.phone)
        }
    }

    companion object {
        const val KEY_PERSON = "key person"
    }
}

inline fun <reified T: Parcelable> Intent.getParcelableCompat(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(key, T::class.java)
    } else {
        getParcelableExtra<T>(key)
    }
}