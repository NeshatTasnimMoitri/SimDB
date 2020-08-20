package sample;

public class History {
    private String date;
    private String offerDetails;

    public String getDate() {
        return date;
    }

    public String getOfferDetails() {
        return offerDetails;
    }

    public History(String details, String date){
        offerDetails = details;
        this.date = date;
    }
}
