package sample;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class Beolvas{

    private static Logger logger = LoggerFactory.getLogger(Beolvas.class);

    private List<Hallgato> hallg;

    public Beolvas(){
        FileReader reader = null;
        try {
            reader = new FileReader("C:\\Users\\Angéla\\Desktop\\8. félév\\PTech\\naplo\\src\\main\\resources\\adatok.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, (JsonDeserializer<Instant>) (json, typeOfT, context) -> Instant.parse(json.getAsJsonPrimitive().getAsString()))
                .create();

        Type type = new TypeToken<List<Hallgato>>(){}.getType();

        hallg = gson.fromJson(reader, type);

        //logger.info("Betöltve {} hallgato", hallg.size());
    }
    public Beolvas(List<Hallgato> reader){
        hallg = reader;

        //logger.info("Betöltve {} hallgato", hallg.size());
    }

    public List<Hallgato> getAll(){
        return hallg;
    }

    public List<String> hallgatok_neve(){
        return hallg
                .stream()
                .map(Hallgato::getNev)
                .sorted()
                .collect(Collectors.toList());
    }

    public Hallgato hallgato_kivalaszt(String hnev){
        if(hallg
                .stream()
                .anyMatch(s->s.getNev().equals(hnev)))
            return hallg
                    .stream()
                    .filter(s->s.getNev().equals(hnev))
                    .findFirst().get();
        else return new Hallgato();
    }

    public List<Integer> adott_hallgato_djegye(String hnev){
        if(hallg
                .stream()
                .anyMatch(s->s.getNev().equals(hnev)))
        return hallg
                .stream()
                .filter(s->s.getNev().equals(hnev))
                .findFirst().get().getD();
        else return new ArrayList<>();

    }

    public List<Integer> adott_hallgato_tzjegye(String hnev){
        if(hallg
                .stream()
                .anyMatch(s->s.getNev().equals(hnev)))
            return hallg
                    .stream()
                    .filter(s->s.getNev().equals(hnev))
                    .findFirst().get().getTz();
        else return new ArrayList<>();

    }

    public int adott_hallgato_kjegye(String hnev){
        if(hallg
                .stream()
                .anyMatch(s->s.getNev().equals(hnev)))
            return hallg
                    .stream()
                    .filter(s->s.getNev().equals(hnev))
                    .findFirst().get().getK();
        else return 0;

    }

    public static double atlag_szamol(List<Integer> normaljegy, List<Integer> nagyjegy, int kisjegy){
        double osszeg = 0;
        int db = 0;

        //A DOLGOZAT JEGYEK: 1X
        if(normaljegy.size()!=0) {
            for (int i = 0; i < normaljegy.size(); i++) {
                if(normaljegy.get(i)>5 || normaljegy.get(i)<1){
                    return -1;
                }
                osszeg += normaljegy.get(i);
                db++;
            }
        }

        //A TZ JEGYEK: 2X
        if(nagyjegy.size()!=0) {
            for (int i = 0; i < nagyjegy.size(); i++) {
                if(nagyjegy.get(i)>5 || nagyjegy.get(i)<1){
                    return -1;
                }
                osszeg += 2 * nagyjegy.get(i);
                db += 2;
            }
        }

        //A KIS JEGYEK: 5 DB KISÖTÖS = EGY NORMÁL ÖTÖS
        if(kisjegy%5 == 0){
            db += kisjegy/5;
            osszeg += (kisjegy/5) * 5;
        }
        if(osszeg==0)return 0;
        return osszeg/db;
    }

    public static void hallgato_felvetele(Hallgato ujhallgato){
        Beolvas adatok = new Beolvas();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        FileWriter writer;
        try {
            writer = new FileWriter("C:\\Users\\Angéla\\Desktop\\8. félév\\PTech\\naplo\\src\\main\\resources\\adatok.json", false);
            writer.write("[\n");
            for (int i = 0; i < adatok.getAll().size(); i++) {
                Hallgato ujH = adatok.getAll().get(i);
                gson.toJson(ujH, Hallgato.class, writer);
                writer.write(",\n");
            }

            gson.toJson(ujhallgato, Hallgato.class, writer);
            writer.write("\n]");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void hallgato_torlese(String hnev){
        Beolvas b = new Beolvas();
        List<Hallgato> megmarad = b.getAll().stream().filter(s->!(s.getNev().equals(hnev))).collect(Collectors.toList());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer;
        try {
            writer = new FileWriter("C:\\Users\\Angéla\\Desktop\\8. félév\\PTech\\naplo\\src\\main\\resources\\adatok.json", false);
            writer.write("[\n");

            for (int i = 0; i < megmarad.size(); i++) {
                gson.toJson(megmarad.get(i), Hallgato.class, writer);
                if(i!=megmarad.size()-1)
                    writer.write(",\n");
            }

            writer.write("\n]");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
