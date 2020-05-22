package com.example.exo2.dummy;

import com.android.volley.RequestQueue;
import com.example.exo2.beans.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class Machine {

    /**
     * An array of sample (dummy) items.
     */ private List<MachineItem> machineList;
    RequestQueue requestQueue;

    String insertUrl = "http://192.168.1.102:8090/machines/all";

    public static final List<MachineItem> MACHINES = new ArrayList<MachineItem>();
public  ArrayList<MachineItem> mylist=new ArrayList<MachineItem>();



    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, MachineItem> MACHINE_MAP = new HashMap<String, MachineItem>();

    private static final int COUNT = 4;

    private static void addItem(MachineItem machine) {
        MACHINES.add(machine);
        MACHINE_MAP.put(machine.id, machine);
    }

    private static MachineItem createMachine(int position) {

        return new MachineItem(String.valueOf(position), "Lamyaa " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class MachineItem {
        public  String id;
        public String reference;

        public MachineItem(String id, String reference, String prix, Marque marque) {
            this.id = id;
            this.reference = reference;
            this.prix = prix;
            this.marque = marque;
        }

        public  String prix;
        public Marque marque;

        public Marque getMarque() {
            return marque;
        }

        public void setMarque(Marque marque) {
            this.marque = marque;
        }

        public String getId() {
            return id;
        }

        public String getReference() {
            return reference;
        }

        public String getPrix() {
            return prix;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public void setPrix(String prix) {
            this.prix = prix;
        }

        public MachineItem() {
        }

        public MachineItem(String id, String reference, String prix) {
            this.id = id;
            this.reference = reference;
            this.prix = prix;
        }

        @Override
        public String toString() {
            return reference;
        }
    }
}
