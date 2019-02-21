package sample.AppFinance;

public interface IExpensesCategories
{

    String BIEDRONKA = "BIEDRONKA";
    String CARREFUR = "CARREFUR";
    String SPOLEM = "SPOLEM";
    int FOOD_MARKETS = 0;
    int PUBS_AND_BARS = 1;
    int HOTELS = 2;
    int STORES = 3;
    int GAS_STATIONS = 4;
    int FOOD_RESTAURATNS = 5;
    int DRUG_STORES = 6;
    int TRANSPORT_TICKETS = 7;

    double calculateFoodMarkets();
    double calculatePubsAndBars();
    double calculateHotels();
    double calculateStores();
    double calculateGasStations();
    double calculateFoodRestaurants();
    double calculateDrugStores();
    double calculateTransportickets();
}
