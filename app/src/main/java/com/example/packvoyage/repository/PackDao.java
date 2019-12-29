package com.example.packvoyage.repository;

import android.content.Context;
import android.util.Log;

import com.example.packvoyage.Constant.Constants;
import com.example.packvoyage.Utils.ConnectionState;
import com.example.packvoyage.ViewModel.PackDetailVM;
import com.example.packvoyage.bindingModel.PackBindingModel;
import com.example.packvoyage.model.Accommodation;
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
import com.example.packvoyage.service.PackService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PackDao {

    public void loadPacks(PackDetailVM packVM, int pageIndex) {
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

    public ArrayList<Activity> getPayingActivities(int packId){
        Pack pack = getPackActivities(packId);
        ArrayList<Activity>payingActivities = new ArrayList<>();
        for(Activity act : pack.getActivities()){
            if(act.getPrice() != null && act.getPrice() > 0)
                payingActivities.add(act);
        }
        return payingActivities;
    }

    public Pack getPackWithGeneralFlightInfos(int packId){
        // todo
        ArrayList<Flight>flights = new ArrayList<>();
        flights.add(new Flight(1, true));
        flights.add(new Flight(2, false));
        flights.add(new Flight(3, true));
        flights.add(new Flight(4, false));
        flights.add(new Flight(5, true));
        flights.add(new Flight(6, false));
        flights.add(new Flight(7, true));
        flights.add(new Flight(8, false));
        flights.add(new Flight(9, true));
        flights.add(new Flight(10, false));
        Pack pack = new Pack(1, "voyage en afganistan", "Voyage voyage Au dessus des vieux volcans\n" +
                "Glissent des ailes sous les tapis du vent\n" +
                "Voyage, voyage\n" +
                "Éternellement\n" +
                "De nuages en marécages\n" +
                "De vent d'Espagne en pluie d'équateur\n" +
                "Voyage, voyage\n" +
                "Vole dans les hauteurs\n" +
                "Au dessus des capitales\n" +
                "Des idées fatales\n" +
                "Regardent l'océan", "https://images.unsplash.com/photo-1507234897433-06646bd0e673?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1189&q=80");
        pack.setFlights(flights);
        return pack;
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

    public void loadAccommodations(PackDetailVM packVM, int packId){
        ArrayList<Accommodation>accommodations = new ArrayList<>();
        Locality locality = new Locality("Sauce-ville");
        accommodations.add(new Accommodation("hotel samourai", locality, "https://fs17.lt/wp-content/uploads/2019/11/Ketchup-Factory-2.jpg"));
        accommodations.add(new Accommodation("hotel poivre", locality, "https://fs17.lt/wp-content/uploads/2019/11/Ketchup-Factory-2.jpg"));
        accommodations.add(new Accommodation("hotel barbecue", locality, "https://fs17.lt/wp-content/uploads/2019/11/Ketchup-Factory-2.jpg"));
        accommodations.add(new Accommodation("hotel andalouse", locality, "https://fs17.lt/wp-content/uploads/2019/11/Ketchup-Factory-2.jpg"));
        accommodations.add(new Accommodation("hotel mayonnaise", locality, "https://fs17.lt/wp-content/uploads/2019/11/Ketchup-Factory-2.jpg"));
        accommodations.add(new Accommodation("hotel brasil", locality, "https://fs17.lt/wp-content/uploads/2019/11/Ketchup-Factory-2.jpg"));
        accommodations.add(new Accommodation("hotel bicky", locality, "https://fs17.lt/wp-content/uploads/2019/11/Ketchup-Factory-2.jpg"));
        accommodations.add(new Accommodation("hotel béarnaise", locality, "https://fs17.lt/wp-content/uploads/2019/11/Ketchup-Factory-2.jpg"));
        packVM.setCurrentPackAccommodations(accommodations);
    }

    public void loadActivityPreferences(PackDetailVM packVM){
        ArrayList<ActivityTag>activityTags = new ArrayList<>();
        activityTags.add(new ActivityTag(1, "sport"));
        activityTags.add(new ActivityTag(2, "nature"));
        activityTags.add(new ActivityTag(3, "culture"));
        activityTags.add(new ActivityTag(4, "relaxation"));
        activityTags.add(new ActivityTag(4, "aventure"));
        packVM.setActivityTags(activityTags);
    }
}
