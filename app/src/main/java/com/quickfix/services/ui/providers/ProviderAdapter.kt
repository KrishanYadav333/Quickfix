package com.quickfix.services.ui.providers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quickfix.services.R

class ProviderAdapter(
    private val providers: List<SimpleProvider>,
    private val onBookClick: (SimpleProvider) -> Unit
) : RecyclerView.Adapter<ProviderAdapter.ProviderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProviderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_provider, parent, false)
        return ProviderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProviderViewHolder, position: Int) {
        holder.bind(providers[position])
    }

    override fun getItemCount() = providers.size

    inner class ProviderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textProviderName: TextView = itemView.findViewById(R.id.textProviderName)
        private val textProviderContact: TextView = itemView.findViewById(R.id.textProviderContact)
        private val buttonBookNow: Button = itemView.findViewById(R.id.buttonBookNow)

        fun bind(provider: SimpleProvider) {
            textProviderName.text = provider.name
            textProviderContact.text = provider.contact
            buttonBookNow.setOnClickListener {
                onBookClick(provider)
            }
        }
    }
}