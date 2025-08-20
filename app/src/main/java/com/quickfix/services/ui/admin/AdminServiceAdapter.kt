package com.quickfix.services.ui.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quickfix.services.R
import com.quickfix.services.data.model.Service

class AdminServiceAdapter(
    private val services: List<Service>,
    private val onToggleVisibility: (Service) -> Unit,
    private val onDeleteService: (Service) -> Unit
) : RecyclerView.Adapter<AdminServiceAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textServiceName: TextView = view.findViewById(R.id.textServiceName)
        val textServiceDescription: TextView = view.findViewById(R.id.textServiceDescription)
        val buttonToggleVisibility: Button = view.findViewById(R.id.buttonToggleVisibility)
        val buttonDelete: Button = view.findViewById(R.id.buttonDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_service, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val service = services[position]
        
        holder.textServiceName.text = service.serviceName
        holder.textServiceDescription.text = service.description
        
        // Update button text and style based on visibility
        holder.buttonToggleVisibility.text = if (service.isVisible) "Hide" else "Show"
        
        // Visual indication for hidden services
        holder.itemView.alpha = if (service.isVisible) 1.0f else 0.6f
        
        holder.buttonToggleVisibility.setOnClickListener {
            onToggleVisibility(service)
        }
        
        holder.buttonDelete.setOnClickListener {
            onDeleteService(service)
        }
    }

    override fun getItemCount() = services.size
}