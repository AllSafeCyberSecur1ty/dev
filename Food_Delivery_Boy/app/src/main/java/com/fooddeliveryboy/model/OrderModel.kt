package com.fooddeliveryboy.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created on 08-05-2020.
 */
class OrderModel : Serializable {

    val order_id: String? = null
    val branch_id: String? = null
    val order_no: String? = null
    val order_date: String? = null
    val order_type: String? = null
    val user_id: String? = null
    val coupon_code: String? = null
    val discount: String? = null
    val discount_type: String? = null
    val discount_amount: String? = null
    val order_amount: String? = null
    val net_amount: String? = null
    val paid_amount: String? = null
    val paid_by: String? = null
    val payment_ref: String? = null
    val payment_log: String? = null
    val paid_date: String? = null
    val delivery_amount: String? = null
    val gateway_charges: String? = null
    val user_address_id: String? = null
    val order_note: String? = null
    var status: String? = null
    val created_at: String? = null
    val modified_at: String? = null
    val created_by: String? = null
    val modified_by: String? = null
    val draft: String? = null
    val user_firstname: String? = null
    val user_lastname: String? = null
    val user_phone: String? = null
    val user_email: String? = null
    val branch_name_en: String? = null
    val branch_name_ar: String? = null
    val opening_time: String? = null
    val closing_time: String? = null
    val address_line1: String? = null
    val address_line2: String? = null
    val city: String? = null
    val latitude: String? = null
    val longitude: String? = null
    val total_qty: String? = null

    @SerializedName("items")
    val orderItemModelList: ArrayList<OrderItemModel>? = null

    @SerializedName("order_status")
    val orderStatusModelList: ArrayList<OrderStatusModel>? = null

}