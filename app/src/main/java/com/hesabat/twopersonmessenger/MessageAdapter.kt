package com.hesabat.twopersonmessenger
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.hesabat.twopersonmessenger.databinding.ItemMessageBinding

class MessageAdapter(private val myId:Int, private val onLong:(Message)->Unit):RecyclerView.Adapter<MessageAdapter.VH>(){val items=mutableListOf<Message>(); class VH(val b:ItemMessageBinding):RecyclerView.ViewHolder(b.root); override fun onCreateViewHolder(p:ViewGroup,v:Int)=VH(ItemMessageBinding.inflate(LayoutInflater.from(p.context),p,false)); override fun getItemCount()=items.size; override fun onBindViewHolder(h:VH,pos:Int){val m=items[pos];val mine=m.sender_id==myId;h.b.text.text=m.text?:""; val st=if(!mine) "" else when{m.read_at!=null->" ✓✓";m.delivered_at!=null->" ✓✓";else->" ✓"}; h.b.meta.text=(m.created_at?:"")+st; val bg=GradientDrawable().apply{cornerRadius=18f;setColor(Color.parseColor(if(mine)"#005C4B" else "#202C33"))};h.b.root.background=bg; val lp=h.b.root.layoutParams as RecyclerView.LayoutParams; lp.gravity=if(mine)Gravity.END else Gravity.START;h.b.root.layoutParams=lp;h.b.root.setOnLongClickListener{onLong(m);true}}}
