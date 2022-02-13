public class Date {
    private int year;
    private int month;
    private int day;
    private static int[][] mDays = {{31, 31}, {28, 29}, {31, 31}, {30, 30}, {31, 31}, {30, 30},
    {31, 31}, {31, 31}, {30, 30}, {31, 31}, {30, 30}, {31, 31}};

    public Date(int year, int month, int day) throws DateException{
        if (!isLegalDate(day, month, year)){
            throw new DateException(String.format("Illegal date %02d.%02d.%d", day, month, year));
        }
        else{
            this.day = day;
            this.month = month;
            this.year = year;
        }
    }

    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }

    public int getDay() {
        return this.day;
    }

    public String toString(){
        return String.format("%02d.%02d.%d", this.day, this.month, this.year);
    }


    private static boolean isLeapYear(int year) {
        // Karkausvuosi: jaollinen 4:llä ja ei jaollinen 100:lla tai jaollinen 400:lla.
        // Javan loogisaritmeettiset operaatiot ja return-lause kuin C-kielessä.
        return (year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0));
      }

    private static int monthDays(int month, int year) {
        int days = -1;
        if(1 <= month && month <= 12) {
            // Ehdollinen operaattori kuin C-kielessä.
            days = isLeapYear(year) ? mDays[month-1][1] : mDays[month-1][0];
    }
        return days;
    }
    private static boolean isLegalDate(int day, int month, int year) {
        // Tuloksen laskenta on suoraviivaista, koska monthDays
        // palauttaa -1, jos kuukausi on laiton.
        return (1 <= day) && (day <= monthDays(month, year));
      }
    
}
