package com.example.beautyclub.data

import androidx.annotation.DrawableRes
import com.example.beautyclub.R

data class Reward(
    val name: String,
    val pointCost: Int,
    val description: String,
    @DrawableRes val imageRes: Int? = null
)

object RewardData {
    val list = listOf(
        Reward(
            name        = "GlowMe Sheet Mask",
            pointCost   = 75,
            description = "Deep hydration for radiant and refreshed skin.",
            imageRes    = R.drawable.reward_sheet_mask
        ),
        Reward(
            name        = "GlowMe Gentle Cleanser",
            pointCost   = 100,
            description = "Gently cleanses your skin without irritation.",
            imageRes    = R.drawable.reward_cleanser
        ),
        Reward(
            name        = "GlowMe Hydrating Moisturizer",
            pointCost   = 150,
            description = "Provides long-lasting hydration throughout the day.",
            imageRes    = R.drawable.reward_moisturizer
        ),
        Reward(
            name        = "Royal Body Scrub",
            pointCost   = 200,
            description = "Luxurious exfoliation for smooth and glowing skin.",
            imageRes    = R.drawable.reward_body_scrub
        ),
        Reward(
            name        = "GlowMe Brightening Serum",
            pointCost   = 250,
            description = "Enhances skin radiance with powerful brightening ingredients.",
            imageRes    = R.drawable.reward_serum
        ),
        Reward(
            name        = "Korean Brightening Facial",
            pointCost   = 400,
            description = "A Korean-inspired facial treatment for a healthy glow.",
            imageRes    = R.drawable.reward_facial_korean
        ),
        Reward(
            name        = "Premium Hydrafacial",
            pointCost   = 600,
            description = "An exclusive hydrafacial treatment for deep cleansing and hydration.",
            imageRes    = R.drawable.reward_hydrafacial
        )
    )
}