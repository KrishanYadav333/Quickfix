package com.example.localservicesapp.ui.services

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.localservicesapp.R
import com.example.localservicesapp.data.model.Service

class ServiceAdapter(
    private var services: List<Service>,
    private val isAdmin: Boolean,
    private val onServiceClick: (Service) -> Unit,
    private val onEditClick: (Service) -> Unit,
    private val onDeleteClick: (Service) -> Unit
) : RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    class ServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewServiceName: TextView = itemView.findViewById(R.id.textViewServiceName)
        val textViewServiceDescription: TextView = itemView.findViewById(R.id.textViewServiceDescription)
        val layoutAdminButtons: LinearLayout = itemView.findViewById(R.id.layoutAdminButtons)
        val buttonEdit: Button = itemView.findViewById(R.id.buttonEdit)
        val buttonDelete: Button = itemView.findViewById(R.id.buttonDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_service, parent, false)
        return ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = services[position]
        
        holder.textViewServiceName.text = service.serviceName
        holder.textViewServiceDescription.text = service.description
        
        if (isAdmin) {
            holder.layoutAdminButtons.visibility = View.VISIBLE
            
            holder.buttonEdit.setOnClickListener {
                onEditClick(service)
            }
            
            holder.buttonDelete.setOnClickListener {
                onDeleteClick(service)
            }
        } else {
            holder.layoutAdminButtons.visibility = View.GONE
        }
        
        holder.itemView.setOnClickListener {
            onServiceClick(service)
        }
    }

    override fun getItemCount(): Int = services.size

    fun updateServices(newServices: List<Service>) {
        services = newServices
        notifyDataSetChanged()
    }
}