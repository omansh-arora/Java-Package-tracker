package cmpt213.assignment1.packagedeliveriestracker;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Main class which runs function and saves/loads package list
 */
public class Main {

    private static ArrayList<PkgInfo> packages;

    /**
     * Saves package list after menu is finished
     */
    private static void saveData() {

        Gson myGson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
                new TypeAdapter<LocalDateTime>() {
                    @Override
                    public void write(JsonWriter jsonWriter,
                                      LocalDateTime localDateTime) throws IOException {
                        jsonWriter.value(localDateTime.toString());
                    }
                    @Override
                    public LocalDateTime read(JsonReader jsonReader) throws IOException {
                        return LocalDateTime.parse(jsonReader.nextString());
                    }
                }).setPrettyPrinting().create();

        String jsonArr = myGson.toJson(packages);
        try {
            FileWriter file = new FileWriter("src/list.json", false);
            file.write(jsonArr);
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Loads data from json file/creates new json file if none exists
     */
    private static void loadData(){
        packages = new ArrayList<>();

        File pkgFile = new File("src/list.json");

        //If no data file exists then it creates a new one
        if(!pkgFile.exists()){
            pkgFile.getParentFile().mkdirs();
            try {
                pkgFile.createNewFile();
                FileWriter file = new FileWriter("src/list.json", false);
                file.write("[{}]");
                file.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(pkgFile));
            JsonArray jsonArr = fileElement.getAsJsonArray();

            //Checks if json file is empty array
            if (!Objects.equals(jsonArr.toString(), "[{}]")){

                //Goes through each object in Json file and adds them to the array
                for (JsonElement packageElement : jsonArr) {

                    Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
                            new TypeAdapter<LocalDateTime>() {
                                @Override
                                public void write(JsonWriter jsonWriter,
                                                  LocalDateTime localDateTime) throws IOException {
                                    jsonWriter.value(localDateTime.toString());
                                }

                                @Override
                                public LocalDateTime read(JsonReader jsonReader) throws IOException {
                                    return LocalDateTime.parse(jsonReader.nextString());
                                }
                            }).create();
                    PkgInfo pkg = gson.fromJson(packageElement, PkgInfo.class);
                    packages.add(pkg);

                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    public static void main(String[] args) {

        loadData();
        MainMenu mm = new MainMenu(packages);
        mm.displayMenu();
        saveData();

    }
}