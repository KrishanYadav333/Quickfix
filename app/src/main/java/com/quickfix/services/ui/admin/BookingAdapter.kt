package com.quickfix.services.ui.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quickfix.services.R
import com.quickfix.services.data.model.Booking

class BookingAdapter(
    private val bookings: List<Booking>
) : RecyclerView.Adapter<BookingAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textServiceName: TextView = view.findViewById(R.id.textServiceName)
        val textProviderName: TextView = view.findViewById(R.id.textProviderName)
        val textCustomerInfo: TextView = view.findViewById(R.id.textCustomerInfo)
        val textBookingDateTime: TextView = view.findViewById(R.id.textBookingDateTime)
        val textStatus: TextView = view.findViewById(R.id.textStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_booking, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val booking = bookings[position]
        
        holder.textServiceName.text = booking.serviceName
        holder.textProviderName.text = "Provider: ${booking.providerName}"
        holder.textCustomerInfo.text = "Customer: ${booking.customerName}\nEmail: ${booking.customerEmail}\nContact: ${booking.customerContact}\nAddress: ${booking.customerAddress}"
        holder.textBookingDateTime.text = "${booking.bookingDate} at ${booking.bookingTime}"
        holder.textStatus.text = booking.status
    }

    override fun getItemCount() = bookings.size
}