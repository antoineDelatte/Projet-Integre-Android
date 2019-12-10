package com.example.packvoyage.repository;

import android.util.Log;

import com.example.packvoyage.bindingModel.ImageOrVideoBindingModel;
import com.example.packvoyage.bindingModel.PackBindingModel;
import com.example.packvoyage.model.Activity;
import com.example.packvoyage.model.Flight;
import com.example.packvoyage.model.Pack;
import com.example.packvoyage.model.PlaneSeat;
import com.example.packvoyage.service.PackService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PackDao {

    private ArrayList<Pack> packs;

    private void loadPacks() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PackService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PackService pack = retrofit.create(PackService.class);
        Call<List<PackBindingModel>> call = pack.getPacks();
        call.enqueue(new Callback<List<PackBindingModel>>() {
            @Override
            public void onResponse(Call<List<PackBindingModel>> call, Response<List<PackBindingModel>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                List<PackBindingModel> packList = response.body();
                Pack pack;
                for(PackBindingModel packBindingModel : packList){
                    pack = new Pack(packBindingModel.getId(), packBindingModel.getName(), null, packBindingModel.getImageOrVideoBindingModels().get(0).getContent());
                    PackDao.this.packs.add(pack);
                }

            }

            @Override
            public void onFailure(Call<List<PackBindingModel>> call, Throwable t) {
                Log.e("Trip4Student", t.getMessage());
            }
        });
    }

    public ArrayList<Pack> getPacks() {
        /*this.loadPacks();*/
        //Pour les tests
        if(packs != null){
            packs.add(new Pack(1, "Voyage Combodge", null, "https://www.routesdumonde.com/wp-content/uploads/thumb/thumb-circuit-cambodge.jpg"));
            packs.add(new Pack(2, "Voyage Belgique", null, "https://media.routard.com/image/73/7/belgique-gand.1487737.c1000x300.jpg"));
            packs.add(new Pack(3, "Voyage Zambie", null, "https://img.ev.mu/images/portfolio/pays/245/600x400/846346.jpg"));
            packs.add(new Pack(4, "Voyage Bois de boulogne", null, "https://ak.jogurucdn.com/media/image/p25/place-2016-01-4-12-Boisdeboulogne2065e49fc359db8a638314b88f9f216d.jpg"));
            packs.add(new Pack(5, "Voyage Danemark", null, "https://live.staticflickr.com/1831/42367565350_b3577e9f9b_b.jpg"));
        }

        return packs;
    }

    public Pack getPackActivities(int packId){
        //appel methode correspondante
        // todo
        ArrayList<Activity>activities = new ArrayList<>();
        activities.add(new Activity(1, "acti1", 200.5, "espagne madrid hotel playitas bambitas", "https://www.routesdumonde.com/wp-content/uploads/thumb/thumb-circuit-cambodge.jpg"));
        activities.add(new Activity(2, "acti2", 200.5, "espagne madrid hotel playitas bambitas", "https://www.routesdumonde.com/wp-content/uploads/thumb/thumb-circuit-cambodge.jpg"));
        activities.add(new Activity(3, "acti3", 200.5, "espagne madrid hotel playitas bambitas", "https://www.routesdumonde.com/wp-content/uploads/thumb/thumb-circuit-cambodge.jpg"));
        activities.add(new Activity(4, "acti4", 200.5, "espagne madrid hotel playitas bambitas", "https://www.routesdumonde.com/wp-content/uploads/thumb/thumb-circuit-cambodge.jpg"));
        activities.add(new Activity(5, "acti5", 200.5, "espagne madrid hotel playitas bambitas", "https://www.routesdumonde.com/wp-content/uploads/thumb/thumb-circuit-cambodge.jpg"));
        Pack pack = new Pack(1, "Voyage Zambie", "super voyage en zambie pour visiter la savane et se faire dévorer par des lions affamés" +
                "super voyage en zambie pour visiter la savane et se faire dévorer par des lions affamés"+
                "super voyage en zambie pour visiter la savane et se faire dévorer par des lions affamés"+
                "super voyage en zambie pour visiter la savane et se faire dévorer par des lions affamés"+
                        "super voyage en zambie pour visiter la savane et se faire dévorer par des lions affamés"+
                "super voyage en zambie pour visiter la savane et se faire dévorer par des lions affamés", "https://img.ev.mu/images/portfolio/pays/245/600x400/846346.jpg");
        pack.setActivities(activities);
        return pack;
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
        Pack pack = new Pack(1, "voyage en afganistan", "vous voulez exploser? ceci est l'occasion rêvée pour aller s'éclater!", "https://images.unsplash.com/photo-1507234897433-06646bd0e673?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1189&q=80");
        pack.setFlights(flights);
        return pack;
    }
    public String getPackDescription(int packId){
        return "vous voulez exploser? ceci est l'occasion rêvée pour vous éclater!";
    }
    public ArrayList<Flight>getFlightsWithSeats(int packId){
        ArrayList<Flight>flights = new ArrayList<>();

        ArrayList<PlaneSeat>planeSeatsF1 = new ArrayList<>();
        planeSeatsF1.add(new PlaneSeat(1, "A3", 120.5));
        planeSeatsF1.add(new PlaneSeat(2, "A4", 120.5));
        planeSeatsF1.add(new PlaneSeat(3, "B3", 120.5));
        planeSeatsF1.add(new PlaneSeat(4, "C3", 120.5));
        planeSeatsF1.add(new PlaneSeat(5, "D3", 120.5));
        Flight flight1 = new Flight(1, true);
        flight1.setPlaneSeats(planeSeatsF1);


        ArrayList<PlaneSeat>planeSeatsF2 = new ArrayList<>();
        planeSeatsF2.add(new PlaneSeat(6, "E3", 120.5));
        planeSeatsF2.add(new PlaneSeat(7, "F3", 120.5));
        planeSeatsF2.add(new PlaneSeat(8, "G3", 120.5));
        planeSeatsF2.add(new PlaneSeat(9, "H3", 120.5));
        planeSeatsF2.add(new PlaneSeat(10, "I3", 120.5));
        Flight flight2 = new Flight(2, true);
        flight2.setPlaneSeats(planeSeatsF2);


        ArrayList<PlaneSeat>planeSeatsF3 = new ArrayList<>();
        planeSeatsF3.add(new PlaneSeat(11, "J3", 120.5));
        planeSeatsF3.add(new PlaneSeat(12, "K3", 120.5));
        planeSeatsF3.add(new PlaneSeat(13, "L3", 120.5));
        planeSeatsF3.add(new PlaneSeat(14, "M3", 120.5));
        planeSeatsF3.add(new PlaneSeat(15, "N3", 120.5));
        Flight flight3 = new Flight(2, false);
        flight3.setPlaneSeats(planeSeatsF2);

        flights.add(flight1);
        flights.add(flight2);
        flights.add(flight3);

        return flights;
    }
}
