package com.example.beautyclub.data

data class Treatment(
    val name: String,
    val price: Double,   // dalam Rupiah
    val category: TreatmentCategory
)

enum class TreatmentCategory(val label: String) {
    FACIAL("Facial"),
    LASER("Laser"),
    BODY("Body"),
    HAIR("Hair"),
    NAIL("Nail"),
    PRODUCT("Product")
}

// Rp10.000 = 1 poin
fun Double.toPoints(): Int = (this / 10_000).toInt()

object TreatmentData {
    val list = listOf(
        Treatment("Chromosome Facial", 1_150_000.0, TreatmentCategory.FACIAL),
        Treatment("Facial Korean Brightening", 850_000.0, TreatmentCategory.FACIAL),
        Treatment("Facial Gold + LED", 950_000.0, TreatmentCategory.FACIAL),
        Treatment("Facial Contouring", 2_500_000.0, TreatmentCategory.FACIAL),
        Treatment("Hydrafacial Premium", 1_200_000.0, TreatmentCategory.FACIAL),

        Treatment("Picofront Laser", 1_500_000.0, TreatmentCategory.LASER),
        Treatment("Carbon Laser Peel", 900_000.0, TreatmentCategory.LASER),
        Treatment("Laser Hair Removal", 750_000.0, TreatmentCategory.LASER),

        Treatment("Body Scrub Royal", 450_000.0, TreatmentCategory.BODY),
        Treatment("Body Wrap Slimming", 600_000.0, TreatmentCategory.BODY),

        Treatment("Keratin Hair Treatment", 800_000.0, TreatmentCategory.HAIR),
        Treatment("Nail Art Premium", 250_000.0, TreatmentCategory.NAIL),

        Treatment("GlowMe Gentle Cleanser", 120_000.0, TreatmentCategory.PRODUCT),
        Treatment("GlowMe Brightening Serum", 280_000.0, TreatmentCategory.PRODUCT),
        Treatment("GlowMe Hydrating Moisturizer", 220_000.0, TreatmentCategory.PRODUCT)
    )
}