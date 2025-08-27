package com.example.projectmanagement.di.utils

import com.google.gson.annotations.SerializedName

data class ImgBBApiResponse(
  @SerializedName("success")
  val success: Boolean,

  @SerializedName("status")
  val status: Int,

  @SerializedName("data")
  val data: ImgBBData
)

data class ImgBBData(
  @SerializedName("id")
  val id: String,

  @SerializedName("title")
  val title: String,

  @SerializedName("url")
  val url: String,

  @SerializedName("display_url")
  val displayUrl: String,

  @SerializedName("width")
  val width: String,

  @SerializedName("height")
  val height: String,

  @SerializedName("size")
  val size: String,

  @SerializedName("time")
  val time: String,

  @SerializedName("expiration")
  val expiration: String,

  @SerializedName("image")
  val image: ImgBBImageInfo,

  @SerializedName("delete_url")
  val deleteUrl: String
)

data class ImgBBImageInfo(
  @SerializedName("filename")
  val filename: String,

  @SerializedName("name")
  val name: String,

  @SerializedName("mime")
  val mime: String,

  @SerializedName("extension")
  val extension: String,

  @SerializedName("url")
  val url: String
)

