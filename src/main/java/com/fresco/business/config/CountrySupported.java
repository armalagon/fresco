package com.fresco.business.config;

import com.zacate.util.DecimalFormatType;
import com.zacate.util.FormatUtils;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class CountrySupported {

    public static final CountrySupported NICARAGUA = new CountrySupported(new Locale("es", "NI"), "dd/MM/yyyy", "dd/MM/yyyy HH:mm:ss",
            "dd/MM/yyyy HH:mm:ss.SSS", "dd/MM/yyyy", "dd/MM/yyyy HH:mm:ss", "dd/MM/yyyy HH:mm:ss.SSS");

    private final Locale locale;
    private final String patternForDeprecatedDate;
    private final String patternForDeprecatedTimestamp;
    private final String patternForDeprecatedTimestampMillis;
    private final String patternForDate;
    private final String patternForTimestamp;
    private final String patternForTimestampMillis;
    private final DateTimeFormatter formatterForDate;
    private final DateTimeFormatter formatterForTimestamp;
    private final DateTimeFormatter formatterForTimestampMillis;

    private CountrySupported(final Locale locale, final String patternForDeprecatedDate, final String patternForDeprecatedTimestamp,
            final String patternForDeprecatedTimestampMillis, final String patternForDate, final String patternForTimestamp,
            final String patternForTimestampMillis) {
        this.locale = locale;
        this.patternForDeprecatedDate = patternForDeprecatedDate;
        this.patternForDeprecatedTimestamp = patternForDeprecatedTimestamp;
        this.patternForDeprecatedTimestampMillis = patternForDeprecatedTimestampMillis;
        this.patternForDate = patternForDate;
        this.patternForTimestamp = patternForTimestamp;
        this.patternForTimestampMillis = patternForTimestampMillis;
        this.formatterForDate = DateTimeFormatter.ofPattern(patternForDate, locale);
        this.formatterForTimestamp = DateTimeFormatter.ofPattern(patternForTimestamp, locale);
        this.formatterForTimestampMillis = DateTimeFormatter.ofPattern(patternForTimestampMillis, locale);
    }

    public Locale getLocale() {
        return locale;
    }

    public CountrySupported changeLanguage(Locale language) {
        Objects.requireNonNull(language, "language");
        return new CountrySupported(new Locale(language.getLanguage(), locale.getCountry()), patternForDeprecatedDate, patternForDeprecatedTimestamp,
                patternForDeprecatedTimestampMillis, patternForDate, patternForTimestamp, patternForTimestampMillis);
    }

    public String getPatternForDeprecatedDate() {
        return patternForDeprecatedDate;
    }

    public String getPatternForDeprecatedTimestamp() {
        return patternForDeprecatedTimestamp;
    }

    public String getPatternForDeprecatedTimestampMillis() {
        return patternForDeprecatedTimestampMillis;
    }

    public String getPatternForDate() {
        return patternForDate;
    }

    public String getPatternForTimestamp() {
        return patternForTimestamp;
    }

    public String getPatternForTimestampMillis() {
        return patternForTimestampMillis;
    }

    public String format(long number) {
        return DecimalFormatType.QUANTITY.format(number, locale);
    }

    public String format(BigDecimal number) {
        return DecimalFormatType.AMOUNT.format(number, locale);
    }

    public String formatAsDate(Date date) {
        return FormatUtils.dateFormat(date, patternForDeprecatedDate, locale);
    }

    public String formatAsTimestamp(Date date) {
        return FormatUtils.dateFormat(date, patternForDeprecatedTimestamp, locale);
    }

    public String formatAsTimestampMillis(Date date) {
        return FormatUtils.dateFormat(date, patternForDeprecatedTimestampMillis, locale);
    }

    public String formatAsDate(LocalDate date) {
        return FormatUtils.dateFormat(date, formatterForDate, locale);
    }

    public String formatAsTimestamp(LocalDateTime date) {
        return FormatUtils.dateFormat(date, formatterForTimestamp, locale);
    }

    public String formatAsTimestampMillis(LocalDateTime date) {
        return FormatUtils.dateFormat(date, formatterForTimestampMillis, locale);
    }

}
