package com.dev.nicehash.helpers

import com.dev.nicehash.R

/**
 * Created by Alex Gladkov on 16.07.18.
 * Holder for small enum collections
 */
class EnumCollections {

    enum class Language(val title: Int, val intCode: Int, val countryCode: String) {
        Russian(title = R.string.settings_language_russian, intCode = 0, countryCode = "ru"),
        English(title = R.string.settings_language_english, intCode = 1, countryCode = "en"),
        Chinese(title = R.string.settings_language_chinese, intCode = 2, countryCode = "cn")
    }

    enum class Algos(val id: Int, val title: String) {
        Scrypt(id = 0, title = "Scrypt"), Sha256(id = 1, title = "SHA256"),
        ScryptNf(id = 2, title = "Scrypt Nf"), X11(id = 3, title = "X11"),
        X13(id = 4, title = "X13"), Keccak(id = 5, title = "Keccak"),
        X15(id = 6, title = "X15"), Nist5(id = 7, title = "Nist 5"),
        NeoScrypt(id = 8, title = "Neo Scrypt"), Lyra2RE(id = 9, title = "Lyra2RE"),
        WhirlpoolX(id = 10, title = "WhirlPoolX"), Qubit(id = 11, title = "Qubit"),
        Quark(id = 12, title = "Quark"), Axiom(id = 13, title = "Axiom"),
        Lyra2REv2(id = 14, title = "Lyra2REv2"), ScryptJaneNf16(id = 15, title = "Scrypt Jane Nf16"),
        Blake256r8(id = 16, title = "Blake 256 r8"), Black256r14(id = 17, title = "Blake 256 r14"),
        Blake256r8vnl(id = 18, title = "Blake 256 r8 vnl"), Hodl(id = 19, title = "Hodl"),
        DaggerHashimoto(id = 20, title = "DaggerHashimoto"), Decred(id = 21, title = "Decred"),
        CryptoNight(id = 22, title = "CryptoNight"), Lbry(id = 23, title = "Lbry"),
        Equihash(id = 24, title = "Equihash"), Pascal(id = 25, title = "Pascal"),
        X11Gost(id = 26, title = "X11Gost"), Sia(id = 27, title = "Sia"),
        Blake2s(id = 28, title = "Blake2s"), Skunk(id = 29, title = "Skunk"),
        CryptoNightV7(id = 30, title = "CryptoNightV7"), CryptoNightHeavy(id = 31, title = "CryptoNightHeavy"),
        Lyra2Z(id = 32, title = "Lyra2Z"), X16R(id = 33, title = "X16R")
    }

    enum class Status {
        Green, Yellow, Red
    }

    enum class Currency(val id: Int, val titleRes: Int, val flagRes: Int) {
        Russian(id = 0, titleRes = R.string.currency_rub, flagRes = R.drawable.ic_rub),
        Euro(id = 1, titleRes = R.string.currency_eur, flagRes = R.drawable.ic_eur),
        BritainPound(id = 2, titleRes = R.string.currency_eng, flagRes = R.drawable.ic_gbp),
        USD(id = 3, titleRes = R.string.currency_usd, flagRes = R.drawable.ic_usd),
        Australian(id = 4, titleRes = R.string.currency_aud, flagRes = 0),
        Belgium(id = 5, titleRes = R.string.currency_bgn, flagRes = R.drawable.ic_bgn),
        Canada(id = 6, titleRes = R.string.currency_cad, flagRes = R.drawable.ic_cad),
        CHF(id = 7, titleRes = R.string.currency_chf, flagRes = R.drawable.ic_chf),
        China(id = 8, titleRes = R.string.currency_cny, flagRes = R.drawable.ic_cny),
        Czech(id = 9, titleRes = R.string.currency_czk, flagRes = R.drawable.ic_czk),
        Dania(id = 10, titleRes = R.string.currency_dkk, flagRes = R.drawable.ic_dkk)
    }
}