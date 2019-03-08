package sample.AppFinance;

class Utilities {
    private static final int JANUARY = 0;
    private static final int FEBURARY = 1;
    private static final int MARCH = 2;
    private static final int APRIL = 3;
    private static final int MAY = 4;
    private static final int JUNE = 5;
    private static final int JULY = 6;
    private static final int AUGUST = 7;
    private static final int SEPTEMBER = 8;
    private static final int OCTOBER = 9;
    private static final int NOVEMBER = 10;
    private static final int DECEMBER = 11;
    private static final String _JAN = "January";
    private static final String _FEB = "February";
    private static final String _MAR = "March";
    private static final String _APR = "April";
    private static final String _MAY = "May";
    private static final String _JUN = "June";
    private static final String _JUL = "July";
    private static final String _AUG = "August";
    private static final String _SEP = "September";
    private static final String _OCT = "October";
    private static final String _NOV = "November";
    private static final String _DEC = "December";

    static String monthsToStringList(int intMonths) {
        String month = null;
        switch (intMonths) {
            case JANUARY:
                month = _JAN;
                break;
            case FEBURARY:
                month = _FEB;
                break;
            case MARCH:
                month = _MAR;
                break;
            case APRIL:
                month = _APR;
                break;
            case MAY:
                month = _MAY;
                break;
            case JUNE:
                month = _JUN;
                break;
            case JULY:
                month = _JUL;
                break;
            case AUGUST:
                month = _AUG;
                break;
            case SEPTEMBER:
                month = _SEP;
                break;
            case OCTOBER:
                month = _OCT;
                break;
            case NOVEMBER:
                month = _NOV;
                break;
            case DECEMBER:
                month = _DEC;
                break;

        }
        return month;
    }

    static int monthsToInt(String month) {
        Integer monthToInt;
        switch (month) {
            case _JAN:
                monthToInt = JANUARY;
                break;
            case _FEB:
                monthToInt = FEBURARY;
                break;
            case _MAR:
                monthToInt = MARCH;
                break;
            case _APR:
                monthToInt = APRIL;
                break;
            case _MAY:
                monthToInt = MAY;
                break;
            case _JUN:
                monthToInt = JUNE;
                break;
            case _JUL:
                monthToInt = JULY;
                break;
            case _AUG:
                monthToInt = AUGUST;
                break;
            case _SEP:
                monthToInt = SEPTEMBER;
                break;
            case _OCT:
                monthToInt = OCTOBER;
                break;
            case _NOV:
                monthToInt = NOVEMBER;
                break;
            case _DEC:
                monthToInt = DECEMBER;
                break;

            default:
                monthToInt = 0;

        }
        return monthToInt;
    }
}
