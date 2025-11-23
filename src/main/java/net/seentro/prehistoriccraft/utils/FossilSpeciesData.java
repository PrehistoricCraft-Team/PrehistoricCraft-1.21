package net.seentro.prehistoriccraft.utils;

public class FossilSpeciesData {
    public String size;
    public int hp;

    public String diet;
    public String likedFood;

    public String cleaning;
    public String sociality;
    public int maxPack;
    public int offprints;
    public boolean isAnimal;

    public Chances chances;

    public static class Chances {
        public Probability tissue;
        public Probability amber;
    }

    public static class Probability {
        public int maxProb;
        public int minProb;
    }
}
