package com.dev.nicehash.domain.models

/**
 * Created by Alex Gladkov on 15.07.18.
 * Model for income tab (domain)
 */
data class Income(var hourValue: Double, val hourBtc: Double, var dayValue: Double, val dayBtc: Double,
                  var weekValue: Double, val weekBtc: Double, var monthValue: Double, val monthBtc: Double)