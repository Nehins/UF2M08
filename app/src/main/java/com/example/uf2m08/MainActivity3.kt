package com.example.uf2m08

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.uf2m08.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityMain3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main3)
        setContentView(binding.root)

        val results = intent.getStringExtra("json_results").toString()

        val array: Array<String> = results.toCharArray().map { it.toString() }.toTypedArray()

        var num = 0

        for (i in array.indices) {
            if(array[i].equals("i")){
                num++
            }
        }

        binding.texto.text= num.toString()
    }


}