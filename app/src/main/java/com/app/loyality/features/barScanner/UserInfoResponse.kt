package com.app.loyality.features.barScanner


import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    @SerializedName("barcode")
    val barcode: String,
    @SerializedName("card_id")
    val cardId: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("histories")
    val histories: List<History>,
    @SerializedName("image_path")
    val imagePath: String,
    @SerializedName("is_loyalty")
    val isLoyalty: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("total_paid")
    val totalPaid: Double,
    @SerializedName("total_pts")
    val totalPts: Int
) {
    data class History(
        @SerializedName("bought_prods")
        val boughtProds: Int,
        @SerializedName("store_idx")
        val storeIdx: Int,
        @SerializedName("store_name")
        val storeName: String,
        @SerializedName("total_paid")
        val totalPaid: Double,
        @SerializedName("transcation_idx")
        val transcationIdx: Int,
        @SerializedName("visit_date")
        val visitDate: String
    )
}