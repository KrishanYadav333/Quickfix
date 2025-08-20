package com.quickfix.services.ui.services

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quickfix.services.R

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
        private val imageServiceIcon: ImageView = itemView.findViewById(R.id.imageServiceIcon)
        private val textServiceName: TextView = itemView.findViewById(R.id.textServiceName)
        private val textServiceDescription: TextView = itemView.findViewById(R.id.textServiceDescription)
        private val layoutAdminButtons: LinearLayout = itemView.findViewById(R.id.layoutAdminButtons)
        private val buttonEdit: Button = itemView.findViewById(R.id.buttonEdit)
        private val buttonDelete: Button = itemView.findViewById(R.id.buttonDelete)

        fun bind(service: SimpleService) {
            textServiceName.text = service.name
            textServiceDescription.text = service.description
            
            // Set service-specific icon
            val iconRes = when(service.name) {
                "Plumber" -> R.drawable.ic_plumber
                "Electrician" -> R.drawable.ic_electrician
                "Mechanic" -> R.drawable.ic_mechanic
                "Cleaning" -> R.drawable.ic_cleaning
                "Carpenter" -> R.drawable.ic_carpenter
                "Painter" -> R.drawable.ic_painter
                "AC Repair" -> R.drawable.ic_ac_repair
                "Appliance Repair" -> R.drawable.ic_appliance
                "Pest Control" -> R.drawable.ic_pest_control
                "Gardening" -> R.drawable.ic_gardening
                "Locksmith" -> R.drawable.ic_locksmith
                "Beauty & Spa" -> R.drawable.ic_beauty_spa
                else -> R.drawable.ic_service_default
            }
            imageServiceIcon.setImageResource(iconRes)

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