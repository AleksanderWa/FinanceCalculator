public interface IBankOperations {
    String CREDIT_CARD = "P�atno�� kart�";
    String ATM_CASH_BACK = "Wyp�ata z bankomatu";
    String GET_FROM = "Przelew na rachunek";
    String SEND_TO = "Przelew z rachunku";
    String RECEIVER = "Nazwa odbiorcy: ";
    String SENDER = "Nazwa nadawcy: ";
    String RECEIVER_ADDRESS = "Adres odbiorcy: ";
    String SENDER_ADDRESS = "Adres nadawcy: ";
    String CITY = "Miasto";
    String ADDRESS = "Adres";
    String DATE = "Data i czas operacji: ";
    String DESCRIPTION = "Tytu�: ";

    int SHORT_ROW_SEND_TO = 1; // 8 fields row
    int LONG_ROW_SEND_TO = 2;  // 8+ fields row
}
