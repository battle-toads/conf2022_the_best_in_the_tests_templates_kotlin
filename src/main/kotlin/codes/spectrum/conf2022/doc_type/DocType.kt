package codes.spectrum.conf2022.doc_type

/**
 *
 * */
enum class DocType(val normaliseRegex: Regex) {
    UNDEFINED("".toRegex()),

    /**
     * Паспорт РФ
     * */
    PASSPORT_RF("\\d{10}".toRegex()),

    /**
     * Водительское удостоверение
     * */
    DRIVER_LICENSE("\\d{10}".toRegex()),

    /**
     * Идентификационный номер транспортного средства
     * */
    VIN("[A-Z0-9]{17}".toRegex()),

    /**
     * Государственный регистрационный номер транспортного средства
     * */
    GRZ("[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2}\\d{2,3}".toRegex()),

    /**
     * Свидетельство о регистрации транспортного средства
     * */
    STS("\\d{2}[А-ЯA-Z0-9]{2}\\d{6}".toRegex()),

    /**
     * ИНН Юр. лица
     * */
    INN_FL("\\d{12}".toRegex()),

    /**
     * ИНН Физ. лица
     * */
    INN_UL("\\d{10}".toRegex()),

    /**
     * ОГРН
     * */
    OGRN("\\d{13}".toRegex()),

    /**
     * ОГРНИП
     * */
    OGRNIP("\\d{15}".toRegex()),

    /**
     * СНИЛС
     * */
    SNILS("\\d{3}-\\d{3}-\\d{3}-\\d{2}".toRegex()),
}