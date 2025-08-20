package com.example.localservicesapp.ui.services

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.localservicesapp.R

class ServiceAdapter(
    private val services: List<SimpleService>,
    private val isAdmin: Boolean,
    private val onServiceClick: (SimpleService) -> Unit,
    private val onEditClick: (SimpleService) -> Unit,
    private val onDeleteClick: (SimpleService) -> Unit
) : RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_service, parent, false)
        return ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(services[position])
    }

    override fun getItemCount() = services.size



    inner class ServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textServiceName: TextView = itemView.findViewById(R.id.textServiceName)
        private val textServiceDescription: TextView = itemView.findViewById(R.id.textServiceDescription)
        private val layoutAdminButtons: LinearLayout = itemView.findViewById(R.id.layoutAdminButtons)
        private val buttonEdit: Button = itemView.findViewById(R.id.buttonEdit)
        private val buttonDelete: Button = itemView.findViewById(R.id.buttonDelete)

        fun bind(service: SimpleService) {
            textServiceName.text = service.name
            textServiceDescription.text = service.description

            if (isAdmin) {
                layoutAdminButtons.visibility = View.VISIBLE
                buttonEdit.setOnClickListener { onEditClick(service) }
                buttonDelete.setOnClickListener { onDeleteClick(service) }
            } else {
                layoutAdminButtons.visibility = View.GONE
                itemView.setOnClickListener { onServiceClick(service) }
            }
        }
    }
}