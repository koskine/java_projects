public class DateTime extends Date {
    private int hour;
    private int minute;
    private int second;


    public DateTime(int year, int month, int day, int hour, int minute, int second) throws DateException{
        super(year, month, day);
        if (!isLegalTime(hour, minute, second)){
            throw new DateException(String.format("Illegal time %02d:%02d:%02d", hour, minute, second));
        }
        else {
            this.hour = hour;
            this.minute = minute;
            this.second = second;
        }
    }

    public int getHour() {
        return this.hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public int getSecond() {
        return this.second;
    }

    public String toString(){
        String date = super.toString();
        String time = String.format("%02d:%02d:%02d", this.hour, this.minute, this.second);
        return date + " " + time;
    }

    private static boolean isLegalTime(int hour, int minute, int second){
        if (hour < 0 || hour > 23){
            return false;
        }
        else if (minute < 0 || minute > 59){
            return false;
        }
        else if (second < 0 || second > 59){
            return false;
        }
        else {
            return true;
        }
    }
}
