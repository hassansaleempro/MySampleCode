package com.sample.code.data.network.apiResponses.models

import com.google.gson.annotations.SerializedName

data class UserData(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("user_type") var userType: String? = null,
    @SerializedName("email_verified_at") var emailVerifiedAt: String? = null,
    @SerializedName("provider") var provider: String? = null,
    @SerializedName("provider_id") var providerId: String? = null,
    @SerializedName("barcode") var barcode: String? = null,
    @SerializedName("deleted_at") var deletedAt: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("referrer_id") var referrerId: String? = null,
    @SerializedName("profileprogess_id") var profileprogessId: String? = null,
    @SerializedName("device_token") var deviceToken: String? = null,
    @SerializedName("email_verification_code") var emailVerificationCode: String? = null,
    @SerializedName("email_verification_code_expires") var emailVerificationCodeExpires: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("isFromSocialLogin") var isFromSocialLogin: String? = null,

    //Filmmaker Object
    @SerializedName("user_id") var userId: Int? = null,
    @SerializedName("profile_image") var profileImage: String? = null,
    @SerializedName("compnay_name") var compnayName: String? = null,
    @SerializedName("full_name") var fullName: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("instagram") var instagram: String? = null,
    @SerializedName("website") var website: String? = null,
    @SerializedName("address_verified") var addressVerified: Int? = null,
    @SerializedName("instagram_verified") var instagramVerified: Int? = null,
    @SerializedName("website_verified") var websiteVerified: Int? = null,
    @SerializedName("phone_verified") var phoneVerified: Int? = null,
    @SerializedName("cover_image") var coverImage: String? = null,
    @SerializedName("company_type_id") var companyTypeId: Int? = null,
    @SerializedName("role_id") var roleId: Int? = null,

)