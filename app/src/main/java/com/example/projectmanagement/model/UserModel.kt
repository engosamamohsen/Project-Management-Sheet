package com.example.projectmanagement.model


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Keep
@Serializable
@Parcelize
data class UserModel(
    @SerializedName("account_number")
    @Expose
    var accountNumber: String? = "",
    @SerializedName("active_status")
    @Expose
    var activeStatus: Int? = 0, // admin
    @SerializedName("status")
    @Expose
    var status: Int? = 0,// otp
    @SerializedName("is_added_pin_code")
    @Expose
    var isPinCodeAdded: Int? = 0,// secret password
    @SerializedName("address")
    @Expose
    var address: String? = "",
    @SerializedName("fcm_token")
    @Expose
    var fcmToken: String? = "",
    @SerializedName("id")
    @Expose
    var id: Int? = 0,
    @SerializedName("image")
    @Expose
    var image: String? = "",
    @SerializedName("national_logo")
    @Expose
    var nationalLogo: String? = "",
    @SerializedName("lat")
    @Expose
    var lat: String? = "",
    @SerializedName("lng")
    @Expose
    var lng: String? = "",
    @SerializedName("msg_code")
    @Expose
    var msgCode: String? = "",
    @SerializedName("my_balance")
    @Expose
    var myBalance: Int? = 0,
    @SerializedName("name")
    @Expose
    var name: String? = "",
    @SerializedName("national_number")
    @Expose
    var nationalNumber: String? = "",
    @SerializedName("phone")
    @Expose
    var phone: String? = "",
    @SerializedName("token")
    @Expose
    var token: String? = "",
    @SerializedName("qr_code")
    @Expose
    var qrCode: String? = "",
    @SerializedName("type")
    @Expose
    var type: Int? = 0
) : Parcelable