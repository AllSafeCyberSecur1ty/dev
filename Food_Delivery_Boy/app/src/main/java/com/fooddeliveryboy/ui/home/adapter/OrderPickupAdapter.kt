package com.fooddeliveryboy.ui.home.adapter

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.fooddeliveryboy.CommonActivity
import com.fooddeliveryboy.R
import com.fooddeliveryboy.model.OrderModel
import com.fooddeliveryboy.ui.order_detail.OrderDetailActivity
import com.fooddeliveryboy.ui.tracking.TrackingActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.row_order.view.*
import kotlinx.android.synthetic.main.row_order_pickup.view.*
import java.io.Serializable


class OrderPickupAdapter(
    val context: Context,
    val modelList: ArrayList<OrderModel>,
    val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<OrderPickupAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val tv_address: TextView = view.tv_order_pickup_address
        val tv_id: TextView = view.tv_order_pickup_id
        val tv_item: TextView = view.tv_order_pickup_items
        val tv_total_price: TextView = view.tv_order_pickup_total_price
        val ll_location: LinearLayout = view.ll_order_pickup_location
        val ll_call: LinearLayout = view.ll_order_pickup_call
        val tv_delivered: LinearLayout = view.tv_order_pickup_delivered
        val cardView: LinearLayout = view.cv_order_pickup

        init {

            PushDownAnim.setPushDownAnimTo(tv_delivered)

            cardView.setOnClickListener(this)
            ll_location.setOnClickListener(this)
            ll_call.setOnClickListener(this)
            tv_delivered.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            val orderModel = modelList[position]

            when (v?.id) {
                R.id.ll_order_pickup_location -> {
                    /*Intent(context, TrackingActivity::class.java).apply {
                        putExtra("orderData", orderModel as Serializable)
                        context.startActivity(this)
                    }*/
                    Intent(context, TrackingActivity::class.java).apply {
                        putExtra("orderData", modelList[position] as Serializable)
                        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            context as Activity,
                            cardView, "animListtodetail"
                        )
                        (context as Activity).startActivity(this, options.toBundle())
                    }
                }
                R.id.tv_order_pickup_delivered -> {
                    CommonActivity.runBounceAnimation(context, tv_delivered)
                    onItemClickListener.onDeliveredClick(
                        position,
                        orderModel
                    )
                }
                R.id.ll_order_pickup_call -> {
                    Dexter.withActivity(context as Activity)
                        .withPermission(Manifest.permission.CALL_PHONE)
                        .withListener(object : PermissionListener {
                            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                                Intent(
                                    Intent.ACTION_CALL,
                                    Uri.parse("tel:${orderModel.user_phone}")
                                ).apply {
                                    context.startActivity(this)
                                }
                            }

                            override fun onPermissionRationaleShouldBeShown(
                                permission: PermissionRequest?,
                                token: PermissionToken?
                            ) {
                                token?.continuePermissionRequest()
                            }

                            override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                                //onPermissionDenied
                            }
                        }).check()
                }
                else -> {
                    //onItemClickListener.onClick(position, orderModel)
                    Intent(context, OrderDetailActivity::class.java).apply {
                        putExtra("orderData", modelList[position] as Serializable)
                        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            context as Activity,
                            cardView, "animListtodetail"
                        )
                        (context as Activity).startActivity(this, options.toBundle())
                    }
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_order_pickup, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val mList = modelList[position]

        holder.tv_address.text = mList.address_line1
        holder.tv_id.text = mList.order_id
        holder.tv_item.text = mList.total_qty
        holder.tv_total_price.text = CommonActivity.getPriceWithCurrency(
            CommonActivity.getPriceFormat(
                mList.net_amount,
                true
            ), true
        )

        if (mList.user_phone.isNullOrEmpty()) {
            holder.ll_call.visibility = View.GONE
        } else {
            holder.ll_call.visibility = View.VISIBLE
        }

    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    interface OnItemClickListener {
        fun onClick(position: Int, orderModel: OrderModel)
        fun onDeliveredClick(position: Int, orderModel: OrderModel)
    }

}