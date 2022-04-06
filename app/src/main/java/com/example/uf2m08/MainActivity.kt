package com.example.uf2m08

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.uf2m08.databinding.ActivityMainBinding
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.eliminarAlumne.setOnClickListener {
            val idBorrar = binding.idBorrarAlumne.text.toString()
            deleteAlumne(idBorrar)

        }

        binding.getNomCicle.setOnClickListener {
            val idCicle = binding.idCicle.text.toString()
            getCiclesId(idCicle)
        }

        binding.cambiAlumne.setOnClickListener {
            val idAlumne = binding.idBorrarAlumne.text.toString()
            val nomAlumne = binding.nomAlumne.text.toString()
            cambiaAlumne(idAlumne, nomAlumne)

        }

        binding.getAprovats.setOnClickListener {
            getAprovats();
        }

    }

    private fun getCiclesId(id : String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3001")
            .build()
        val service = retrofit.create(RestApi::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getCicle(id.toInt())
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    Log.d("Pretty Printed JSON :", prettyJson)
                    val intent = Intent(this@MainActivity, MainActivity2::class.java)
                    intent.putExtra("json_results", prettyJson)
                    this@MainActivity.startActivity(intent)
                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            }
        }
    }

    private fun getAprovats() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3001")
            .build()
        val service = retrofit.create(RestApi::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getQualificacions()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    Log.d("Pretty Printed JSON :", prettyJson)
                    val intent = Intent(this@MainActivity, MainActivity3::class.java)
                    intent.putExtra("json_results", prettyJson)
                    this@MainActivity.startActivity(intent)
                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            }
        }
        }

    fun deleteAlumne(id : String){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.deleteAlumne(id).enqueue(
            object : Callback<AlumneInfo> {
                override fun onFailure(call: Call<AlumneInfo>, t: Throwable) {}
                override fun onResponse(call: Call<AlumneInfo>, response: Response<AlumneInfo>) {}
            }
        )
    }

    fun cambiaAlumne( id : String, nom : String){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        val alumn = AlumneInfo(nom)
        retrofit.cambiarAlumne(id, alumn).enqueue(
            object : Callback<AlumneInfo> {
                override fun onFailure(call: Call<AlumneInfo>, t: Throwable) {}
                override fun onResponse(call: Call<AlumneInfo>, response: Response<AlumneInfo>) {}
            }
        )
    }

}