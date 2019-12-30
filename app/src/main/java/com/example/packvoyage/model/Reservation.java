package com.example.packvoyage.model;

public class Reservation {
    private Boolean IsPayed;
    private Integer NbTravelers;
    private Double PlaneSeatsTotalPrice;
    private Double TrainSeatsTotalPrice;
    private Double ActivitiesTotalPrice;
    private Double AccomodationTotalPrice;
    private String CustomerId;
    private Integer Packid;

    public Reservation(Boolean isPayed, Integer nbTravelers, Double planeSeatsTotalPrice,
                       Double trainSeatsTotalPrice, Double activitiesTotalPrice,
                       Double accomodationTotalPrice, String customerId, Integer packid) {
        IsPayed = isPayed;
        NbTravelers = nbTravelers;
        PlaneSeatsTotalPrice = planeSeatsTotalPrice;
        TrainSeatsTotalPrice = trainSeatsTotalPrice;
        ActivitiesTotalPrice = activitiesTotalPrice;
        AccomodationTotalPrice = accomodationTotalPrice;
        CustomerId = customerId;
        Packid = packid;
    }

    public Reservation(){}

    public Boolean getPayed() {
        return IsPayed;
    }

    public void setPayed(Boolean payed) {
        IsPayed = payed;
    }

    public Integer getNbTravelers() {
        return NbTravelers;
    }

    public void setNbTravelers(Integer nbTravelers) {
        NbTravelers = nbTravelers;
    }

    public Double getPlaneSeatsTotalPrice() {
        return PlaneSeatsTotalPrice;
    }

    public void setPlaneSeatsTotalPrice(Double planeSeatsTotalPrice) {
        PlaneSeatsTotalPrice = planeSeatsTotalPrice;
    }

    public Double getTrainSeatsTotalPrice() {
        return TrainSeatsTotalPrice;
    }

    public void setTrainSeatsTotalPrice(Double trainSeatsTotalPrice) {
        TrainSeatsTotalPrice = trainSeatsTotalPrice;
    }

    public Double getActivitiesTotalPrice() {
        return ActivitiesTotalPrice;
    }

    public void setActivitiesTotalPrice(Double activitiesTotalPrice) {
        ActivitiesTotalPrice = activitiesTotalPrice;
    }

    public Double getAccomodationTotalPrice() {
        return AccomodationTotalPrice;
    }

    public void setAccomodationTotalPrice(Double accomodationTotalPrice) {
        AccomodationTotalPrice = accomodationTotalPrice;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public Integer getPackid() {
        return Packid;
    }

    public void setPackid(Integer packid) {
        Packid = packid;
    }
}
