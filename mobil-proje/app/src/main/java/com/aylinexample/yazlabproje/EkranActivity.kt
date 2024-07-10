package com.aylinexample.yazlabproje

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class EkranActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore


    private val random5kanallist = mutableListOf<String>()
    private val random6kanallist = mutableListOf<String>()
    private val direk5kanallist = mutableListOf<String>()
    private val direk6kanallist = ArrayList<String>()

    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ekran)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        setContentView(R.layout.activity_ekran)
        auth = FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()
        databaseReference=database?.reference!!.child("bilgi")

        var currentUser=auth.currentUser

        val btn = findViewById<Button>(R.id.button)
        var kullanicisim: String? = null
//realtime database kısmına bilginin altına giris yapan kullanıcı adlarını ekliyorum...
        if (currentUser != null) {
            var userReference=databaseReference?.child(currentUser?.uid!!)
            userReference?.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                 kullanicisim = snapshot.child("kullaniciad").value.toString()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

            btn.setOnClickListener {
                val random = findViewById<CheckBox>(R.id.checkBoxrandom).isChecked
                val dk = findViewById<CheckBox>(R.id.checkBoxdirek).isChecked
                val bes = findViewById<CheckBox>(R.id.checkBox5).isChecked
                val alti = findViewById<CheckBox>(R.id.checkBox6).isChecked

                kullanicisim?.let { kullaniciAdi ->
                    kontrolEtVeListele(random, dk, bes, alti, kullaniciAdi)
                }
            }
        }

    }
    private fun kontrolEtVeListele(randomHarfSelected: Boolean, direkKelimeSelected: Boolean, besHarfSelected: Boolean, altiHarfSelected: Boolean, kullaniciAdi: String) {

        firestore = FirebaseFirestore.getInstance()
        try{
            firestore.collection("oyunOdaları").get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val oyunTuru = document.getString("oyunTuru")
                        val kelimeSayisi = document.getLong("kelimeSayisi")

                        // Checkbox'lardan seçilen verilerle eşleşen oyun odalarını kontrol et
                        if (randomHarfSelected && oyunTuru == "random_harf") {
                            if (besHarfSelected && kelimeSayisi == 5L) {

                                ekleVeListele(document.id, kullaniciAdi)
                                val intent = Intent(this, Random5Kanal::class.java)
                                intent.putExtra("documentId", document.id)
                                intent.putExtra("girisyapankullanici",kullaniciAdi)
                                startActivity(intent)
                            } else if (altiHarfSelected && kelimeSayisi == 6L) {

                                ekleVeListele(document.id, kullaniciAdi)

                                val intent = Intent(this, Random6Kanal::class.java)
                                intent.putExtra("documentId", document.id)
                                intent.putExtra("girisyapankullanici",kullaniciAdi)
                                startActivity(intent)

                            }
                        } else if (direkKelimeSelected && oyunTuru == "kelime") {
                            if (besHarfSelected && kelimeSayisi == 5L) {

                                ekleVeListele(document.id, kullaniciAdi)
                                val intent = Intent(this, Direk5Kanal::class.java)
                                intent.putExtra("documentId", document.id)
                                intent.putExtra("girisyapankullanici",kullaniciAdi)
                                startActivity(intent)

                            } else if (altiHarfSelected && kelimeSayisi == 6L) {

                                ekleVeListele(document.id, kullaniciAdi)

                                val intent = Intent(this, Direk6Kanal::class.java)
                                intent.putExtra("documentId", document.id)
                                intent.putExtra("girisyapankullanici",kullaniciAdi)
                                startActivity(intent)

                            }
                        }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "fb basarisiz", Toast.LENGTH_SHORT).show()
                }
        }catch (e: Exception) {
            // Hata durumunda yapılacaklar
            Log.e("TAG", "Hata oluştu: ${e.message}")
            Toast.makeText(this, "Hata oluştu: ${e.message}", Toast.LENGTH_SHORT).show()
        }

    }

  /*  private fun kanaldakioyuncularigoruntule(oyunOdasiId: String){
        val direk6kanallist = mutableListOf<String>()
        firestore.collection("oyunOdaları").document(oyunOdasiId).collection("oyuncular").get().addOnSuccessListener { documents ->
            for (document in documents) {
                val kullaniciAdi = document.getString("kullaniciadi")
                kullaniciAdi?.let {
                    direk6kanallist.add(kullaniciAdi)
                }
            }


        }


            .addOnFailureListener { exception ->
                // Hata durumunda
                Log.e("Firestore", "Oyuncu isimlerini alma başarısız: $exception")
            }

    }*/
//kullanıcıları oyun odalarına yerleştirmeee
    private fun ekleVeListele(oyunOdasiId: String, kullaniciAdi: String) {


        val oyunOdasiRef = firestore.collection("oyunOdaları").document(oyunOdasiId)
        val kullaniciRef = oyunOdasiRef.collection("oyuncular")

        // Kullanıcı adının daha önce eklenip eklenmediğini kontrol et
        kullaniciRef.whereEqualTo("kullaniciadi", kullaniciAdi).get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {

                    val kullaniciVerisi = hashMapOf(
                        "kullaniciadi" to kullaniciAdi,

                    )
                    kullaniciRef.add(kullaniciVerisi)

                    Toast.makeText(this, "Eşleşen oyun odası: $oyunOdasiId", Toast.LENGTH_SHORT).show()
                } else {

                    Toast.makeText(this, "Kullanıcı adı zaten mevcut", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->

                Log.e("Firestore", "Kullanıcı adı kontrolü başarısız: $exception")
                Toast.makeText(this, "Hata oluştu: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }



}


