package com.aylinexample.yazlabproje

import android.content.Intent
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val userList:ArrayList<Oyuncu>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {



    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onItemClick1(position: Int)
        fun onItemClick2(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
    private var listener: OnItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return  MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {
       val user:Oyuncu=userList[position]
        holder.kullaniciad.text=user.kullaniciadi
        holder.tvAktifDurum.text=user.aktiflikdurum
       // holder.tvAktifDurum.visibility = View.VISIBLE


    }

    override fun getItemCount(): Int {
       return userList.size
    }


    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val kullaniciad: TextView= itemView.findViewById(R.id.tvkullaniciad)
        val tvAktifDurum: TextView = itemView.findViewById(R.id.tvAktifDurum)

        val btnistek:Button=itemView.findViewById(R.id.btnistek)
        val btnistekkabul:Button=itemView.findViewById(R.id.btnistekkabul)
        val btnistekred:Button=itemView.findViewById(R.id.btnistekred)

     /*   init {
            btnistek.setOnClickListener {
                // Butona tıklandığında ne yapılacağını burada belirleyin
                val aliciId = kullaniciad.text.toString()
                val intent = Intent(itemView.context, Direk6Kanal::class.java)
                intent.putExtra("aliciId",aliciId)

                intent.putExtra("butonTiklama", true) // Buton tıklandığında bir işlem gerçekleştiğini belirtmek için bir veri aktarın
                itemView.context.startActivity(intent) // Hedef aktiviteyi başlatın

            }
            */

            init {

                btnistek.setOnClickListener {

                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener?.onItemClick(position)
                    }
                    println("buton tıklama basarili")

                }
            }
        init {


            btnistekkabul.setOnClickListener {

                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick1(position)
                }
                println("İkinci butona tıklama başarılı")

            }
        }

        init {


            btnistekred.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick2(position)
                }
                println("3. butona tıklama başarılı")

            }
        }



        }



    }


