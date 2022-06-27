package com.example.newsapp

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/** Adapter class **/
class newsadapter(var arr:List<Article>):RecyclerView.Adapter<newsadapter.myviewholder>(){
    /** View holder class **/
    class myviewholder(itemView:View):RecyclerView.ViewHolder(itemView) {
        var myimage=itemView.findViewById<ImageView>(R.id.imageView)
        var mydesc=itemView.findViewById<TextView>(R.id.descriptionNews)
        var mytitle = itemView.findViewById<TextView>(R.id.titleNews)
    }

    /* Member functions for adapter class */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {
        var v = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        return myviewholder(v)
    }

    override fun onBindViewHolder(holder: myviewholder, position: Int) {
        Glide.with(mycontext).load(arr[position].urlToImage).into(holder.myimage);
        holder.mytitle.setText(arr[position].title)
        holder.mydesc.setText(arr[position].author)

        // setting on click listener on news item
        holder.myimage.setOnClickListener(){
            val builder = CustomTabsIntent.Builder();
            val customTabsIntent = builder.build();
            customTabsIntent.launchUrl(mycontext, Uri.parse(arr[position].url));
        }
    }

    override fun getItemCount(): Int {
        return arr.size
    }
}