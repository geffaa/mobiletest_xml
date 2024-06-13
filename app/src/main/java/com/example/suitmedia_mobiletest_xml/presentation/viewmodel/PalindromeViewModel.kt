package com.example.suitmedia_mobiletest_xml.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Locale

class PalindromeViewModel : ViewModel() {

    private val _isPalindrome = MutableLiveData<Boolean>()
    val isPalindrome: LiveData<Boolean> get() = _isPalindrome

    fun checkPalindrome(sentence: String) {
        val cleanSentence = sentence.replace("\\s".toRegex(), "").lowercase(Locale.ROOT)
        _isPalindrome.value = cleanSentence == cleanSentence.reversed()

//        val reversedSentence = StringBuilder(cleanSentence).reverse().toString()
//        return cleanSentence == reversedSentence
    }
}