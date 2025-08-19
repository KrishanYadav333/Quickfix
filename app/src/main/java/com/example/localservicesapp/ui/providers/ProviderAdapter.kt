package com.example.localservicesapp.ui.providers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.localservicesapp.R
import com.example.localservicesapp.data.model.Provider

class ProviderAdapter(
    private var providers: List<Provider>,
    private val onBookClick: (Provider) -> Unit
) : RecyclerView.Adapter<ProviderAdapter.ProviderViewHolder>() {

    class ProviderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewProviderName: TextView = itemView.findViewById(R.id.textViewProviderName)
        val textViewProviderContact: TextView = itemView.findViewById(R.id.textViewProviderContact)
        val buttonBookProvider: Button = itemView.findViewById(R.id.buttonBookProvider)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProviderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_provider, parent, false)
        return ProviderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProviderViewHolder, position: Int) {
        val provider = providers[position]
        
        holder.textViewProviderName.text = provider.name
        holder.textViewProviderContact.text = provider.contact
        
        holder.buttonBookProvider.setOnClickListener {
            onBookClick(provider)
        }
    }

    override fun getItemCount(): Int = providers.size

    fun updateProviders(newProviders: List<Provider>) {
        providers = newProviders
        notifyDataSetChanged()
    }
}