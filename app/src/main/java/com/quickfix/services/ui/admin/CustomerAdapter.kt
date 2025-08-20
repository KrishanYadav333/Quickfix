package com.quickfix.services.ui.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quickfix.services.R
import com.quickfix.services.data.model.User

class CustomerAdapter(
    private val customers: List<User>,
    private val onBlockCustomer: (User) -> Unit
) : RecyclerView.Adapter<CustomerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textCustomerName: TextView = view.findViewById(R.id.textCustomerName)
        val textCustomerEmail: TextView = view.findViewById(R.id.textCustomerEmail)
        val buttonBlockUnblock: Button = view.findViewById(R.id.buttonBlockUnblock)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_customer, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val customer = customers[position]
        
        holder.textCustomerName.text = customer.name
        holder.textCustomerEmail.text = customer.email
        holder.buttonBlockUnblock.text = if (customer.isBlocked) "Unblock" else "Block"
        
        holder.itemView.alpha = if (customer.isBlocked) 0.6f else 1.0f
        
        holder.buttonBlockUnblock.setOnClickListener {
            onBlockCustomer(customer)
        }
    }

    override fun getItemCount() = customers.size
}