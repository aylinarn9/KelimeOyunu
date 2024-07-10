package com.aylinexample.yazlabproje

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter5 (private val userList:ArrayList<Oyuncu5>): RecyclerView.Adapter<MyAdapter5.MyViewHolder>(){



    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onItemClick1(position: Int)
        fun onItemClick2(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter5.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return  MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyAdapter5.MyViewHolder, position: Int) {
        val user:Oyuncu5=userList[position]
        holder.kullaniciad.text=user.kullaniciadi
        holder.tvAktifDurum.text=user.aktiflikdurum
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val kullaniciad: TextView = itemView.findViewById(R.id.tvkullaniciad)
        val tvAktifDurum: TextView = itemView.findViewById(R.id.tvAktifDurum)

        val btnistek: Button =itemView.findViewById(R.id.btnistek)
        val btnistekkabul: Button =itemView.findViewById(R.id.btnistekkabul)
        val btnistekred: Button =itemView.findViewById(R.id.btnistekred)

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
                /* val aliciId = kullaniciad.text.toString()
                 val intent = Intent(itemView.context, Direk6Kanal::class.java)
                 intent.putExtra("aliciId",aliciId)
                 itemView.context.startActivity(intent)*/
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