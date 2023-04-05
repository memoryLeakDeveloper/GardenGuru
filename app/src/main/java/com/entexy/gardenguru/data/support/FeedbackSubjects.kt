package com.entexy.gardenguru.data.support

import com.entexy.gardenguru.R

enum class FeedbackSubjects(val stringRes: Int, val cloudValue: String) {
    SuggestionForImproving(R.string.suggestion_for_improvement, "Suggestion for improvement"),
    ApplicationBug(R.string.application_bug, "Application bug"),
    Complaint(R.string.complaint, "Complaint"),
    Other(R.string.other, "Other"),
}