package com.aylinexample.yazlabproje

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private var oyunOdalariOlusturuldu: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // var sharedPreferences = this.getSharedPreferences("bilgi", MODE_PRIVATE)


        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        setContentView(R.layout.activity_main)

        val btnkayittt = findViewById<Button>(R.id.buttonkayit)
        btnkayittt.setOnClickListener {

            val intent = Intent(this, KayitActivity::class.java)
            startActivity(intent)

        }



        val btngiris = findViewById<Button>(R.id.buttongiris)
        val kullaniciadd = findViewById<EditText>(R.id.kullaniciad)
        val sifree = findViewById<EditText>(R.id.sifre)


        btngiris.setOnClickListener {
            auth.signInWithEmailAndPassword(
                kullaniciadd.text.toString(),
                sifree.text.toString()
            ).addOnCompleteListener(this) {
                if (it.isSuccessful) {

                    val intent = Intent(this, EkranActivity::class.java)
                    startActivity(intent)
                  //  finish()
                } else {

                    Toast.makeText(this, "hatali giris", Toast.LENGTH_SHORT).show()
                }
            }
            if (!oyunOdalariOlusturuldu) {
                yeniOyunOdasiOlustur("random_harf", 5)
                yeniOyunOdasiOlustur("random_harf", 6)
                yeniOyunOdasiOlustur("kelime", 5)
                yeniOyunOdasiOlustur("kelime", 6)
                oyunOdalariOlusturuldu = true
            }
        }




    }
    private fun yeniOyunOdasiOlustur(oyunTuru: String, kelimeSayisi: Int) {
        val oyunOdasi = hashMapOf(
            "oyunTuru" to oyunTuru,
            "kelimeSayisi" to kelimeSayisi,

            )

        // firestore.collection("oyunOdaları").document().set(oyunOdasi)
        firestore.collection("oyunOdaları").document("oyunOdasi_$oyunTuru$kelimeSayisi")
            .set(oyunOdasi)
    }

}

