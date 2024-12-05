package com.example.parcelableserializable

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.parcelableserializable.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ArrayAdapter<Person>
    private val list = mutableListOf<Person>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.apply {
            adapter = object :
                ArrayAdapter<Person>(this@MainActivity, android.R.layout.simple_list_item_2, list) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    var row = convertView
                    if (row == null) {
                        val inflater = LayoutInflater.from(parent.context)
                        row = inflater.inflate(android.R.layout.simple_list_item_2, parent, false)
                    }
                    val person = list[position]
                    row?.findViewById<TextView>(android.R.id.text1)?.text = String
                        .format(getString(R.string.name_placeholder), person.name)
                    row?.findViewById<TextView>(android.R.id.text2)?.text = String
                        .format(getString(R.string.last_name_placeholder), person.lastName)
                    return row!!
                }
            }

            saveBTN.setOnClickListener {
                if (nameET.text.toString().isBlank() ||
                    lastNameET.text.toString().isBlank() ||
                    addressET.text.toString().isBlank()) {
                    Toast.makeText(this@MainActivity, R.string.fill_all_fields_please, Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                if (!phoneET.text.toString().matches("^\\+7\\d{10}$".toRegex())) {
                    Toast.makeText(this@MainActivity, getString(R.string.enter_correct_phone_number), Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                list.add(Person(nameET.text.toString(),
                    lastNameET.text.toString(),
                    addressET.text.toString(),
                    phoneET.text.toString()))
                adapter.notifyDataSetChanged()
                nameET.text.clear()
                lastNameET.text.clear()
                addressET.text.clear()
                phoneET.text.clear()
            }
            listLV.adapter = adapter
            listLV.setOnItemClickListener { _, _, position, _ ->
                val intent = Intent(this@MainActivity, CardActivity::class.java)
                    .putExtra(CardActivity.KEY_PERSON, list[position])
                startActivity(intent)
            }
        }

    }
}