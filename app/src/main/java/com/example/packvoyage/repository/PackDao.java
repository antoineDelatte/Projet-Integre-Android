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
import com.example.packvoyage.bindingModel.TagBindingModel;
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
import com.example.packvoyage.model.Reservation;
import com.example.packvoyage.model.User;
import com.example.packvoyage.service.IActivityService;
import com.example.packvoyage.service.IFlightService;
import com.example.packvoyage.service.ILoginService;
import com.example.packvoyage.service.PackService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
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
                    if(activityBindingModel.getTagOfActivity() != null){
                        TagBindingModel tagBindingModel = activityBindingModel.getTagOfActivity().get(0).getTag();
                        activity.setTag(new ActivityTag(tagBindingModel.getId(), tagBindingModel.getName()));
                    }
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

    public void RegisterNewBooking(Reservation reservation, Context context, PackDetailVM packVM){
        if(!ConnectionState.isNetworkAvailable(context)){
            packVM.setApiCallStatus(Constants.NO_CONNECTION);
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PackService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PackService pack = retrofit.create(PackService.class);
        Call<ResponseBody> call = pack.register(reservation);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                packVM.setRegisterStatus(response.code());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Trip4Student", t.getMessage());
            }
        });
    }

    public void loadMyBookings(PackDetailVM packVM, String userId, Context context){
        if(!ConnectionState.isNetworkAvailable(context)){
            packVM.setApiCallStatus(Constants.NO_CONNECTION);
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PackService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PackService pack = retrofit.create(PackService.class);
        Call<List<PackBindingModel>> call = pack.getMyBookings(userId);
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
}
