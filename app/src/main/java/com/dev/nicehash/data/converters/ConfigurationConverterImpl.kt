package com.dev.nicehash.data.converters

import com.dev.nicehash.data.room.models.ConfigurationEntity
import com.dev.nicehash.domain.models.Configuration
import com.dev.nicehash.enums.Theme
import com.dev.nicehash.helpers.EnumCollections

/**
 * Created by Alex Gladkov on 16.07.18.
 * Implementation for configuration converter
 */
class ConfigurationConverterImpl: ConfigurationConverter {
    override fun modelToDB(configuration: Configuration): ConfigurationEntity {
        return ConfigurationEntity(id = configuration.id, theme = configuration.theme.value,
                defaultTab = configuration.defaultTab, defaultLanguage = configuration.defaultLanguage.intCode,
                isTop = configuration.isTop, isSound = configuration.isSound, isStrix = configuration.isStrix,
                isBottom = configuration.isBottom, isVibro = configuration.isVibro, isPayments = configuration.isPayments,
                isMaintenance = configuration.isMaintenance, minerId = configuration.minerId, currency = configuration.currency.id)
    }

    override fun dbToModel(configurationEntity: ConfigurationEntity): Configuration {
        return Configuration(id = configurationEntity.id, defaultTab = configurationEntity.defaultTab,
                isMaintenance = configurationEntity.isMaintenance, isPayments = configurationEntity.isPayments,
                isVibro = configurationEntity.isVibro, isBottom = configurationEntity.isVibro,
                minerId = configurationEntity.minerId, isStrix = configurationEntity.isStrix,
                isTop = configurationEntity.isTop, isSound = configurationEntity.isSound,
                theme = when (configurationEntity.theme) {
            Theme.LightGreen.value -> Theme.LightGreen
            Theme.LightPurple.value -> Theme.LightPurple
            Theme.LightBlue.value -> Theme.LightBlue
            Theme.LightRed.value -> Theme.LightRed
            Theme.LightOrange.value -> Theme.LightOrange
            Theme.DarkPurple.value -> Theme.DarkPurple
            Theme.DarkGreen.value -> Theme.DarkGreen
            Theme.DarkOrange.value -> Theme.DarkOrange
            Theme.DarkRed.value -> Theme.DarkRed
            Theme.DarkBlue.value -> Theme.DarkPurple
            else -> Theme.LightBlue
        }, defaultLanguage = when (configurationEntity.defaultLanguage) {
            EnumCollections.Language.Russian.intCode -> EnumCollections.Language.Russian
            EnumCollections.Language.English.intCode -> EnumCollections.Language.English
            EnumCollections.Language.Chinese.intCode -> EnumCollections.Language.Chinese
            else -> EnumCollections.Language.English
        }, currency = when (configurationEntity.currency) {
            EnumCollections.Currency.Euro.id -> EnumCollections.Currency.Euro
            EnumCollections.Currency.Dania.id -> EnumCollections.Currency.Dania
            EnumCollections.Currency.Czech.id -> EnumCollections.Currency.Czech
            EnumCollections.Currency.China.id -> EnumCollections.Currency.China
            EnumCollections.Currency.CHF.id -> EnumCollections.Currency.CHF
            EnumCollections.Currency.Canada.id -> EnumCollections.Currency.Canada
            EnumCollections.Currency.Belgium.id -> EnumCollections.Currency.Belgium
            EnumCollections.Currency.Australian.id -> EnumCollections.Currency.Australian
            EnumCollections.Currency.USD.id -> EnumCollections.Currency.USD
            EnumCollections.Currency.BritainPound.id -> EnumCollections.Currency.BritainPound
            else -> EnumCollections.Currency.Russian
        })
    }
}