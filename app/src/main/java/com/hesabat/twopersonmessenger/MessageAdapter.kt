package com.hesabat.twopersonmessenger

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hesabat.twopersonmessenger.databinding.ItemMessageBinding

class MessageAdapter(
    private val myId: Int,
    private val onLong: (Message) -> Unit
) : RecyclerView.Adapter<MessageAdapter.VH>() {

    val items = mutableListOf<Message>()

    class VH(
        val b: ItemMessageBinding
    ) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH {
        val binding = ItemMessageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: VH,
        position: Int
    ) {
        val message = items[position]
        val mine = message.sender_id == myId

        holder.b.text.text = message.text ?: ""

        val status = if (!mine) {
            ""
        } else {
            when {
                message.read_at != null -> " ✓✓"
                message.delivered_at != null -> " ✓✓"
                else -> " ✓"
            }
        }

        holder.b.meta.text = (message.created_at ?: "") + status

        val background = GradientDrawable().apply {
            cornerRadius = 18f
            setColor(
                Color.parseColor(
                    if (mine) "#005C4B" else "#202C33"
                )
            )
        }

        holder.b.root.background = background

        /*
         * RecyclerView.LayoutParams-da gravity yoxdur.
         * Bubble-ı sağa/sola çəkmək üçün translation istifadə edirik.
         */
        holder.b.root.post {
            val parentWidth = (holder.b.root.parent as? ViewGroup)?.width ?: 0

            if (parentWidth > 0) {
                if (mine) {
                    holder.b.root.translationX =
                        (parentWidth - holder.b.root.width).coerceAtLeast(0).toFloat()
                } else {
                    holder.b.root.translationX = 0f
                }
            }
        }

        holder.b.root.setOnLongClickListener {
            onLong(message)
            true
        }
    }
}
