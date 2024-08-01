package com.sample.code.data.network.apiResponses.models

import com.google.gson.annotations.SerializedName

data class FilmMakerProfileData (

    @SerializedName("id"                 ) var id                : Int?    = null,
    @SerializedName("user_id"            ) var userId            : Int?    = null,
    @SerializedName("profile_image"      ) var profileImage      : String? = null,
    @SerializedName("compnay_name"       ) var compnayName       : String? = null,
    @SerializedName("full_name"          ) var fullName          : String? = null,
    @SerializedName("phone"              ) var phone             : String? = null,
    @SerializedName("address"            ) var address           : String? = null,
    @SerializedName("instagram"          ) var instagram         : String? = null,
    @SerializedName("website"            ) var website           : String? = null,
    @SerializedName("address_verified"   ) var addressVerified   : Int?    = null,
    @SerializedName("instagram_verified" ) var instagramVerified : Int?    = null,
    @SerializedName("website_verified"   ) var websiteVerified   : Int?    = null,
    @SerializedName("phone_verified"     ) var phoneVerified     : Int?    = null,
    @SerializedName("created_at"         ) var createdAt         : String? = null,
    @SerializedName("updated_at"         ) var updatedAt         : String? = null,
    @SerializedName("cover_image"        ) var coverImage        : String? = null,
    @SerializedName("company_type_id"    ) var companyTypeId     : Int?    = null,
    @SerializedName("role_id"            ) var roleId            : Int?    = null,
    @SerializedName("email"              ) var email             : String? = null

)