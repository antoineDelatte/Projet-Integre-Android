package com.example.packvoyage.repository;

import android.content.Context;
import android.util.Log;

import com.example.packvoyage.Constant.Constants;
import com.example.packvoyage.Utils.ConnectionState;
import com.example.packvoyage.ViewModel.PackDetailVM;
import com.example.packvoyage.bindingModel.AccommodationOfPackBindingModel;
import com.example.packvoyage.bindingModel.AccommodationTypeBindingModel;
import com.example.packvoyage.bindingModel.ActivityBindingModel;
import com.example.packvoyage.bindingModel.AirportBindingModel;
import com.example.packvoyage.bindingModel.FlightOfPackBindingModel;
import com.example.packvoyage.bindingModel.LocalityBindingModel;
import com.example.packvoyage.bindingModel.PackBindingModel;
import com.example.packvoyage.bindingModel.PlaneSeatBindingModel;
import com.example.packvoyage.model.Accommodation;
import com.example.packvoyage.model.AccommodationType;
import com.example.packvoyage.model.Activity;
import com.example.packvoyage.model.ActivityTag;
import com.example.packvoyage.model.Airport;
import com.example.packvoyage.model.BedRoom;
import com.example.packvoyage.model.Comment;
import com.example.packvoyage.model.Flight;
import com.example.packvoyage.model.Locality;
import com.example.packvoyage.model.Pack;
import com.example.packvoyage.model.PlaneSeat;
import com.example.packvoyage.model.User;
import com.example.packvoyage.service.IActivityService;
import com.example.packvoyage.service.IFlightService;
import com.example.packvoyage.service.PackService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PackDao {

    public void loadPacks(PackDetailVM packVM, int pageIndex, Context context) {
        if(!ConnectionState.isNetworkAvailable(context)){
            packVM.setApiCallStatus(Constants.NO_CONNECTION);
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PackService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PackService pack = retrofit.create(PackService.class);
        Call<List<PackBindingModel>> call = pack.getPacks(pageIndex, Constants.PAGING_SIZE);
        call.enqueue(new Callback<List<PackBindingModel>>() {
            @Override
            public void onResponse(Call<List<PackBindingModel>> call, Response<List<PackBindingModel>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                List<PackBindingModel> packList = response.body();
                ArrayList<Pack> packs = new ArrayList<>();
                Pack pack;
                packs.clear();
                for(PackBindingModel packBindingModel : packList){
                    pack = new Pack(packBindingModel.getId(), packBindingModel.getName(), null, packBindingModel.getPictureOrVideo().get(0).getContent());
                    packs.add(pack);
                }
                packVM.setPacks(packs);
            }

            @Override
            public void onFailure(Call<List<PackBindingModel>> call, Throwable t) {
                Log.e("Trip4Student", t.getMessage());
            }
        });
    }

    public void loadPackDescription(int packId, String language, PackDetailVM packVM, Context context){
        if(!ConnectionState.isNetworkAvailable(context)){
            packVM.setApiCallStatus(Constants.NO_CONNECTION);
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PackService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PackService pack = retrofit.create(PackService.class);
        Call<PackBindingModel> call = pack.getPackDescription(packId, language);
        call.enqueue(new Callback<PackBindingModel>() {
            @Override
            public void onResponse(Call<PackBindingModel> call, Response<PackBindingModel> response) {
                if (!response.isSuccessful()) {
                    packVM.setApiCallStatus(response.code());
                    return;
                }
                PackBindingModel packBindingModel = response.body();
                packVM.setCurrentPackDescription(packBindingModel.getTraduction().getDescription());
            }

            @Override
            public void onFailure(Call<PackBindingModel> call, Throwable t) {
                Log.e("Trip4Student", t.getMessage());
            }
        });
    }

    public void loadAccommodations(PackDetailVM packVM, int packId, Context context){
        if(!ConnectionState.isNetworkAvailable(context)){
            packVM.setApiCallStatus(Constants.NO_CONNECTION);
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PackService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PackService service = retrofit.create(PackService.class);
        Call<List<AccommodationOfPackBindingModel>> call = service.getAccommodationsOfPack(packId);
        call.enqueue(new Callback<List<AccommodationOfPackBindingModel>>() {
            @Override
            public void onResponse(Call<List<AccommodationOfPackBindingModel>> call, Response<List<AccommodationOfPackBindingModel>> response) {
                if (!response.isSuccessful()) {
                    packVM.setApiCallStatus(response.code());
                    return;
                }

                ArrayList<Accommodation>accommodations = new ArrayList<>();
                Accommodation accommodation;
                List<AccommodationOfPackBindingModel>apiAccommodations = response.body();

                for(AccommodationOfPackBindingModel accommodationOfPackBindingModel : apiAccommodations){
                    accommodation = new Accommodation();
                    accommodation.setName(accommodationOfPackBindingModel.getAccommodation().getName());
                    accommodation.setImage_uri(accommodationOfPackBindingModel.getAccommodation().getPictureOrVideo().get(0).getContent());
                    LocalityBindingModel lBM = accommodationOfPackBindingModel.getAccommodation().getLocality();
                    accommodation.setLocality(new Locality(lBM.getId(), lBM.getName(), lBM.getZipCode(), lBM.getCountryName()));
                    accommodation.setAccommodationType(new AccommodationType(accommodationOfPackBindingModel.getAccommodation().getAccommodationType().getName()));
                    accommodations.add(accommodation);
                }
                packVM.setCurrentPackAccommodations(accommodations);
            }

            @Override
            public void onFailure(Call<List<AccommodationOfPackBindingModel>> call, Throwable t) {
                Log.e("Trip4Student", t.getMessage());
            }
        });
    }

    public void loadPackActivities(PackDetailVM packVM, int packId, Context context) {
        if (!ConnectionState.isNetworkAvailable(context)) {
            packVM.setApiCallStatus(Constants.NO_CONNECTION);
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IActivityService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IActivityService service = retrofit.create(IActivityService.class);
        Call<List<PackBindingModel>> call = service.getActivitiesOfPack(packId);
        call.enqueue(new Callback<List<PackBindingModel>>() {
            @Override
            public void onResponse(Call<List<PackBindingModel>> call, Response<List<PackBindingModel>> response) {
                if (!response.isSuccessful()) {
                    packVM.setApiCallStatus(response.code());
                    return;
                }
                List<PackBindingModel> apiListOfPackWithActi = response.body();
                if(apiListOfPackWithActi.size() == 0)
                    return;

                PackBindingModel apiPackWithActi = apiListOfPackWithActi.get(0);
                ArrayList<Activity> activities = new ArrayList<>();
                Activity activity;
                String location;
                for(ActivityBindingModel activityBindingModel : apiPackWithActi.getActivity()){
                    activity = new Activity();
                    activity.setId(activityBindingModel.getId());
                    activity.setImage_url(activityBindingModel.getPictureOrVideo().get(0).getContent());
                    activity.setPrice(activityBindingModel.getPrice());
                    activity.setName(activityBindingModel.getName());
                    location = activityBindingModel.getLocality().getName() + " : " + activityBindingModel.getLocality().getCountryName();
                    activity.setLocation(location);
                    activities.add(activity);
                }
                packVM.setCurrentPackActivities(activities);
            }

            @Override
            public void onFailure(Call<List<PackBindingModel>> call, Throwable t) {
                Log.e("Trip4Student", "erreur : " + t.getMessage());
            }
        });
    }

    public void loadPackFlights(PackDetailVM packVM, int packId, Context context){
        if (!ConnectionState.isNetworkAvailable(context)) {
            packVM.setApiCallStatus(Constants.NO_CONNECTION);
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IFlightService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IFlightService service = retrofit.create(IFlightService.class);
        Call<List<FlightOfPackBindingModel>> call = service.getPackFlights(packId);
        call.enqueue(new Callback<List<FlightOfPackBindingModel>>() {
            @Override
            public void onResponse(Call<List<FlightOfPackBindingModel>> call, Response<List<FlightOfPackBindingModel>> response) {
                if (!response.isSuccessful()) {
                    packVM.setApiCallStatus(response.code());
                    return;
                }
                List<FlightOfPackBindingModel> apiFlightOfPackBMs = response.body();
                if(apiFlightOfPackBMs.size() == 0)
                    return;

                ArrayList<Flight> flights = new ArrayList<>();
                Flight flight;
                // destination airport
                AirportBindingModel destinationAirportBM;
                LocalityBindingModel destinationAirportLocalityBM;
                Airport destinationAirport;
                Locality destinationAirportLocality;

                //departure airport
                AirportBindingModel departureAirportBM;
                LocalityBindingModel departureAirportLocalityBM;
                Airport departureAirport;
                Locality departureAirportLocality;

                for(FlightOfPackBindingModel flightOfPackBM: apiFlightOfPackBMs){
                    flight = new Flight();
                    flight.setGoing(flightOfPackBM.getGoing());

                    // destination airport
                    destinationAirportBM = flightOfPackBM.getFlight().getDestinationAirportNavigation();
                    destinationAirportLocalityBM = flightOfPackBM.getFlight().getDestinationAirportNavigation().getLocality();
                    destinationAirportLocality = new Locality(destinationAirportLocalityBM.getId(), destinationAirportLocalityBM.getName(),
                            destinationAirportLocalityBM.getZipCode(), destinationAirportLocalityBM.getCountryName());
                    destinationAirport = new Airport(destinationAirportBM.getName(), destinationAirportLocality);
                    flight.setArrivalAirport(destinationAirport);

                    // departure airport
                    departureAirportBM = flightOfPackBM.getFlight().getDepartureAirportNavigation();
                    departureAirportLocalityBM = flightOfPackBM.getFlight().getDepartureAirportNavigation().getLocality();
                    departureAirportLocality = new Locality(departureAirportLocalityBM.getId(), departureAirportLocalityBM.getName(),
                            departureAirportLocalityBM.getZipCode(), departureAirportLocalityBM.getCountryName());
                    departureAirport = new Airport(departureAirportBM.getName(), departureAirportLocality);
                    flight.setDepartureAirport(departureAirport);

                    flights.add(flight);
                }
                packVM.setCurrentPackFlights(flights);
            }

            @Override
            public void onFailure(Call<List<FlightOfPackBindingModel>> call, Throwable t) {
                Log.e("Trip4Student", "erreur : " + t.getMessage());
            }
        });
    }

    public void loadFlightsWithSeats(PackDetailVM packVM, int packId, Context context){
        if (!ConnectionState.isNetworkAvailable(context)) {
            packVM.setApiCallStatus(Constants.NO_CONNECTION);
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IFlightService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IFlightService service = retrofit.create(IFlightService.class);
        Call<List<FlightOfPackBindingModel>> call = service.getPackFlightsWithSeats(packId);
        call.enqueue(new Callback<List<FlightOfPackBindingModel>>() {
            @Override
            public void onResponse(Call<List<FlightOfPackBindingModel>> call, Response<List<FlightOfPackBindingModel>> response) {
                if (!response.isSuccessful()) {
                    packVM.setApiCallStatus(response.code());
                    return;
                }
                List<FlightOfPackBindingModel> apiFlightOfPackBMs = response.body();
                if(apiFlightOfPackBMs.size() == 0)
                    return;

                ArrayList<Flight> flights = new ArrayList<>();
                Flight flight;
                // destination airport
                AirportBindingModel destinationAirportBM;
                LocalityBindingModel destinationAirportLocalityBM;
                Airport destinationAirport;
                Locality destinationAirportLocality;

                //departure airport
                AirportBindingModel departureAirportBM;
                LocalityBindingModel departureAirportLocalityBM;
                Airport departureAirport;
                Locality departureAirportLocality;

                // plane seats
                PlaneSeat planeSeat;
                ArrayList<PlaneSeat>planeSeats;

                for(FlightOfPackBindingModel flightOfPackBM: apiFlightOfPackBMs){
                    flight = new Flight();
                    flight.setGoing(flightOfPackBM.getGoing());

                    // destination airport
                    destinationAirportBM = flightOfPackBM.getFlight().getDestinationAirportNavigation();
                    destinationAirportLocalityBM = flightOfPackBM.getFlight().getDestinationAirportNavigation().getLocality();
                    destinationAirportLocality = new Locality(destinationAirportLocalityBM.getId(), destinationAirportLocalityBM.getName(),
                            destinationAirportLocalityBM.getZipCode(), destinationAirportLocalityBM.getCountryName());
                    destinationAirport = new Airport(destinationAirportBM.getName(), destinationAirportLocality);
                    flight.setArrivalAirport(destinationAirport);

                    // departure airport
                    departureAirportBM = flightOfPackBM.getFlight().getDepartureAirportNavigation();
                    departureAirportLocalityBM = flightOfPackBM.getFlight().getDepartureAirportNavigation().getLocality();
                    departureAirportLocality = new Locality(departureAirportLocalityBM.getId(), departureAirportLocalityBM.getName(),
                            departureAirportLocalityBM.getZipCode(), departureAirportLocalityBM.getCountryName());
                    departureAirport = new Airport(departureAirportBM.getName(), departureAirportLocality);
                    flight.setDepartureAirport(departureAirport);

                    // plane seats
                    planeSeats = new ArrayList<>();
                    for(PlaneSeatBindingModel planeSeatBindingModel : flightOfPackBM.getPlaneSeat()){
                        planeSeat = new PlaneSeat();
                        planeSeat.setPrice(planeSeatBindingModel.getPrice());
                        planeSeat.setSeatNumber(planeSeatBindingModel.getSeatNumber());
                        planeSeat.setId(planeSeatBindingModel.getId());
                        planeSeat.setBusinessClass(planeSeatBindingModel.getBusinessClass());
                        planeSeats.add(planeSeat);
                    }

                    flight.setPlaneSeats(planeSeats);
                    flights.add(flight);
                }
                packVM.setCurrentPackFlightsWithSeats(flights);
            }

            @Override
            public void onFailure(Call<List<FlightOfPackBindingModel>> call, Throwable t) {
                Log.e("Trip4Student", "erreur : " + t.getMessage());
            }
        });
    }

    public ArrayList<Flight>getFlightsWithAirportAndSeats(int packId){
        Locality depLocality = new Locality("New York");
        Airport depAirport = new Airport("JFk", depLocality);
        Locality arrLocality = new Locality("Sydney");
        Airport arrAirport = new Airport("Croco airport", arrLocality);

        ArrayList<Flight>flights = new ArrayList<>();

        ArrayList<PlaneSeat>planeSeatsF1 = new ArrayList<>();
        planeSeatsF1.add(new PlaneSeat(1, "A3", 120.5));
        planeSeatsF1.add(new PlaneSeat(2, "A4", 120.5));
        planeSeatsF1.add(new PlaneSeat(3, "B3", 120.5));
        planeSeatsF1.add(new PlaneSeat(4, "C3", 120.5));
        planeSeatsF1.add(new PlaneSeat(5, "D3", 120.5));
        Flight flight1 = new Flight(1, true);
        flight1.setDepartureAirport(depAirport);
        flight1.setArrivalAirport(arrAirport);
        flight1.setPlaneSeats(planeSeatsF1);


        ArrayList<PlaneSeat>planeSeatsF2 = new ArrayList<>();
        planeSeatsF2.add(new PlaneSeat(6, "E3", 120.5));
        planeSeatsF2.add(new PlaneSeat(7, "F3", 120.5));
        planeSeatsF2.add(new PlaneSeat(8, "G3", 120.5));
        planeSeatsF2.add(new PlaneSeat(9, "H3", 120.5));
        planeSeatsF2.add(new PlaneSeat(10, "I3", 120.5));
        Flight flight2 = new Flight(2, true);
        flight2.setDepartureAirport(depAirport);
        flight2.setArrivalAirport(arrAirport);
        flight2.setPlaneSeats(planeSeatsF2);


        ArrayList<PlaneSeat>planeSeatsF3 = new ArrayList<>();
        planeSeatsF3.add(new PlaneSeat(11, "J3", 120.5));
        planeSeatsF3.add(new PlaneSeat(12, "K3", 120.5));
        planeSeatsF3.add(new PlaneSeat(13, "L3", 120.5));
        planeSeatsF3.add(new PlaneSeat(14, "M3", 120.5));
        planeSeatsF3.add(new PlaneSeat(15, "N3", 120.5));
        Flight flight3 = new Flight(2, false);
        flight3.setPlaneSeats(planeSeatsF2);
        flight3.setDepartureAirport(depAirport);
        flight3.setArrivalAirport(arrAirport);

        flights.add(flight1);
        flights.add(flight2);
        flights.add(flight3);

        return flights;
    }

    public Pack getPackActivities(int packId){
        //appel methode correspondante
        // todo
        ArrayList<Activity>activities = new ArrayList<>();
        activities.add(new Activity(1, "ISL 8eme de finale", 200.5, "London oympic pool", "https://japantoday-asset.scdn3.secure.raxcdn.com/img/store/b3/a7/44f82be596a68366d52059e37116b974bc79/urn:publicid:ap.org:d6697388b6364fe885538c9205009cf3/_w850.jpg"));
        activities.add(new Activity(2, "ISL quart de finale", 200.5, "London oympic pool", "https://japantoday-asset.scdn3.secure.raxcdn.com/img/store/b3/a7/44f82be596a68366d52059e37116b974bc79/urn:publicid:ap.org:d6697388b6364fe885538c9205009cf3/_w850.jpg"));
        activities.add(new Activity(3, "ISL demi finale", 0, "London oympic pool", "https://japantoday-asset.scdn3.secure.raxcdn.com/img/store/b3/a7/44f82be596a68366d52059e37116b974bc79/urn:publicid:ap.org:d6697388b6364fe885538c9205009cf3/_w850.jpg"));
        activities.add(new Activity(4, "ISL finale", 200.5, "London oympic pool", "https://japantoday-asset.scdn3.secure.raxcdn.com/img/store/b3/a7/44f82be596a68366d52059e37116b974bc79/urn:publicid:ap.org:d6697388b6364fe885538c9205009cf3/_w850.jpg"));
        activities.add(new Activity(5, "ISL Post finale bières", 0, "London oympic pool", "https://japantoday-asset.scdn3.secure.raxcdn.com/img/store/b3/a7/44f82be596a68366d52059e37116b974bc79/urn:publicid:ap.org:d6697388b6364fe885538c9205009cf3/_w850.jpg"));
        Pack pack = new Pack(1, "Voyage Zambie", "super voyage en zambie pour visiter la savane et se faire dévorer par des lions affamés" +
                "super voyage en zambie pour visiter la savane et se faire dévorer par des lions affamés"+
                "super voyage en zambie pour visiter la savane et se faire dévorer par des lions affamés"+
                "super voyage en zambie pour visiter la savane et se faire dévorer par des lions affamés"+
                        "super voyage en zambie pour visiter la savane et se faire dévorer par des lions affamés"+
                "super voyage en zambie pour visiter la savane et se faire dévorer par des lions affamés", "https://img.ev.mu/images/portfolio/pays/245/600x400/846346.jpg");
        pack.setActivities(activities);
        return pack;
    }

    public ArrayList<Accommodation> getAccommodationsWithRooms(int packId){
        ArrayList<Accommodation>accommodations = new ArrayList<>();

        ArrayList<BedRoom>bedRooms1 = new ArrayList<>();
        bedRooms1.add(new BedRoom(1, 5, 120.85));
        bedRooms1.add(new BedRoom(2, 2, 120.85));
        bedRooms1.add(new BedRoom(3, 2, 120.85));
        bedRooms1.add(new BedRoom(4, 3, 120.85));
        bedRooms1.add(new BedRoom(5, 4, 120.85));

        ArrayList<BedRoom>bedRooms2 = new ArrayList<>();
        bedRooms2.add(new BedRoom(6, 10, 120.85));
        bedRooms2.add(new BedRoom(7, 4, 120.85));
        bedRooms2.add(new BedRoom(8, 5, 120.85));
        bedRooms2.add(new BedRoom(9, 3, 120.85));
        bedRooms2.add(new BedRoom(10, 2, 120.85));

        ArrayList<BedRoom>bedRooms3 = new ArrayList<>();
        bedRooms3.add(new BedRoom(11, 6, 120.85));
        bedRooms3.add(new BedRoom(12, 5, 120.85));
        bedRooms3.add(new BedRoom(13, 4, 120.85));
        bedRooms3.add(new BedRoom(14, 3, 120.85));
        bedRooms3.add(new BedRoom(15, 2, 120.85));

        Locality locality1 = new Locality("Ville du jambon");
        Locality locality2 = new Locality("Ville du fromage");
        Locality locality3 = new Locality("Ville du saucisson");


        Accommodation accommodation1 = new Accommodation("hotel jambon", locality1, bedRooms1);
        Accommodation accommodation2 = new Accommodation("hotel fromage", locality2, bedRooms2);
        Accommodation accommodation3 = new Accommodation("hotel saucisson", locality3, bedRooms3);

        accommodations.add(accommodation1);
        accommodations.add(accommodation2);
        accommodations.add(accommodation3);
        return accommodations;
    }

    public void RegisterNewBooking(ArrayList<Integer>bedroomsId, ArrayList<Integer>payingActivitiesId, ArrayList<Integer>planeSeatsId){
        // todo enregistrer commande
        // todo prévenir l'utilisateur si la commande se passe mal
    }

    public void loadMyBookings(PackDetailVM packVM){
        // todo charger mes réservations
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PackService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PackService pack = retrofit.create(PackService.class);
        Call<List<PackBindingModel>> call = pack.getPacks(0,2);
        call.enqueue(new Callback<List<PackBindingModel>>() {
            @Override
            public void onResponse(Call<List<PackBindingModel>> call, Response<List<PackBindingModel>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                List<PackBindingModel> packList = response.body();
                ArrayList<Pack> packs = new ArrayList<>();
                Pack pack;
                packs.clear();
                for(PackBindingModel packBindingModel : packList){
                    pack = new Pack(packBindingModel.getId(), packBindingModel.getName(), null, packBindingModel.getPictureOrVideo().get(0).getContent());
                    packs.add(pack);
                }
                packVM.setMyBookedPacks(packs);
            }

            @Override
            public void onFailure(Call<List<PackBindingModel>> call, Throwable t) {
                Log.e("Trip4Student", t.getMessage());
            }
        });
    }

    public void loadComments(PackDetailVM packVM, int packId){
        // todo charger les commentaires, et pour chaque commentaire, le user correspondant
        ArrayList<Comment>comments = new ArrayList<>();
        User user = new User("1", "Caeleb Dressel", "https://cdn.swimswam.com/wp-content/uploads/2019/06/Caeleb-Dressel-By-Jack-Spitser-CD8I8265-1080x720.jpg");
        comments.add(new Comment("Such a great trip!", user));
        comments.add(new Comment("Such a cool trip!", user));
        comments.add(new Comment("Such a nice trip!", user));
        comments.add(new Comment("Such a beautiful trip!", user));
        comments.add(new Comment("Such an amazing trip!", user));
        comments.add(new Comment("Such a crazy trip!", user));
        packVM.setSelectedBookedPackComments(comments);
    }

    public void loadActivityPreferences(PackDetailVM packVM){
        ArrayList<ActivityTag>activityTags = new ArrayList<>();
        activityTags.add(new ActivityTag(1, "sport"));
        activityTags.add(new ActivityTag(2, "nature"));
        activityTags.add(new ActivityTag(3, "culture"));
        activityTags.add(new ActivityTag(4, "relaxation"));
        activityTags.add(new ActivityTag(5, "aventure"));
        packVM.setActivityTags(activityTags);
    }
}
