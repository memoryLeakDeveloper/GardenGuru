package com.entexy.gardenguru.data.plant.cloud

import com.google.firebase.Timestamp

data class PlantCloud(
    var id: String? = null,
    var name: String? = null,
    var variety: String? = null,
    var localizedVariety: Map<String, String>? = null,
    var photo: String? = null,
    var customPhoto: String? = null,
    var coverPhoto: String? = null,
    var careComplexity: String? = null,
    var description: String? = null,
    var localizeDescription: Map<String, String>? = null,
    var sunRelation: String? = null,
    var pestsIds: List<String>? = null,
    var reproduction: List<String>? = null,
    var benefitsIds: List<String>? = null,
    var pruning: String? = null,
    var addingDate: Timestamp? = null,
    var plantingTime: String? = null,
    var feedingSummer: Int? = null,
    var feedingWinter: Int? = null,
    var wateringSummer: Int? = null,
    var wateringWinter: Int? = null,
    var sprayingSummer: Int? = null,
    var sprayingWinter: Int? = null,
    var minTemp: Int? = null,
    var maxTemp: Int? = null,
) {
    fun allRequiredFields(): Boolean {
        variety ?: return false
        localizedVariety ?: return false
        photo ?: return false
        coverPhoto ?: return false
        careComplexity ?: return false
        description ?: return false
        localizeDescription ?: return false
        sunRelation ?: return false
        pestsIds ?: return false
        reproduction ?: return false
        benefitsIds ?: return false
        pruning ?: return false
        plantingTime ?: return false
        addingDate ?: return false
        feedingSummer ?: return false
        feedingWinter ?: return false
        wateringSummer ?: return false
        wateringWinter ?: return false
        sprayingSummer ?: return false
        sprayingWinter ?: return false
        minTemp ?: return false
        maxTemp ?: return false

        return true
    }
}