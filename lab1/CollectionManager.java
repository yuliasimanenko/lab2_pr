import org.json.simple.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

public class CollectionManager {
    private Map<Integer, Umbrella> collection =
            Collections.synchronizedMap(new LinkedHashMap<>());
    private Date initDate;

    private final String fileName;

    public CollectionManager(String fileName) {
        initDate = Calendar.getInstance().getTime();
        this.fileName = fileName;

        //...чтение из файла
        try{
            importCommand(fileName);
        }catch(Exception e){
            System.out.println("file isn't imported");
        }
    }

    public Object[] getSortedUmbrellas() {
        Object[] arr =  collection.values().toArray();
        Arrays.sort(arr);
        return arr;
    }

    /**
     * The method is used to obtain basic information
     *
     */
    public void info() {
        System.out.println(collection.getClass().toString());
        System.out.println(initDate);
        System.out.println(collection.size());


    }

    /**
     * The method that removes all items that exceed a specified value from the collection
     * @param elementJson - String for compare<br>
     *                    Special format<ul>
     *                    <li>"id" (example "id":"12")</li>
     *                    <li>"materialName"(example  "materialName":"wood")</li>
     *                    <li>"manufacturer"(example   "manufacturer":"China")</li>
     *                    <li>"Color"(example "color":{"r":"255","b":"0","g":"0"})</li></ul>
     */
    public void remove_greater(String elementJson) {
        try {
            Umbrella umbrella = this.toUmbrella(elementJson);
            Integer idInt = umbrella.getId();
//Stream API
            collection.forEach((key, value) -> {
                if (value.compareTo(umbrella) > 0) {
                    collection.replace(idInt,umbrella);
                }
            });
            this.saveToFile("collection");

        }
        catch (Exception e) {
            System.out.println("Ошибка - некорректная строка.");
        }

    }
    public void consolOut (){
        for (Umbrella umbrella : collection.values()) {
        //делать что-то с umbrella
            String material = umbrella.getMaterialName();
            String manufac = umbrella.getManufacturer();
            String id = Integer.toString(umbrella.getId());
            Color color = umbrella.getColor();
            String r = Integer.toString(color.getR());
            String b = Integer.toString(color.getB());
            String g = Integer.toString(color.getG());
            System.out.println(id+ " Производства:"+manufac+"  Материал: "+ material+"  Расцветка: "+ "R-"+r+" B-"+b+" G-"+g);
        }
    }

    public Umbrella toUmbrella(JSONObject element) {
        String materialName = (String) element.get("materialName");
        String manufacturer = (String) element.get("manufacturer");
        JSONObject dateJ = (JSONObject) element.get("date");
        String year_string = (String) dateJ.get("Year");
        String month_string = (String) dateJ.get("MONTH");
        String day_string = (String) dateJ.get("Day");
        int year = Integer.valueOf(year_string);
        int month = Integer.valueOf(month_string);
        int day = Integer.valueOf(day_string);
        GregorianCalendar date = new GregorianCalendar(year,month,day);
        JSONObject color = (JSONObject) element.get("color");
        String idStr = (String) element.get("id");
        int idInt = Integer.valueOf(idStr);
        String rstring = (String) color.get("r");
        String gstring = (String) color.get("g");
        String bstring = (String) color.get("b");
        int rint = Integer.valueOf(rstring);
        int gint = Integer.valueOf(gstring);
        int bint = Integer.valueOf(bstring);
        Color colorObject = new Color(rint, gint, bint);
        Umbrella umbrella = new Umbrella(idInt, colorObject, materialName, manufacturer,date );

        return umbrella;
    }

    public Umbrella toUmbrella (String elementJSon) throws ParseException {
            JSONParser parser = new JSONParser();
            JSONObject element = (JSONObject) parser.parse(elementJSon);
            return toUmbrella(element);
    }

    /**
     *A method that adds a smaller item to a collection that has already been saved to the collection
     * @param elementJson - String for compare<br>
     *                    Special format<ul>
     *                    <li>"id" (example "id":"12")</li>
     *                    <li>"materialName"(example  "materialName":"wood")</li>
     *                    <li>"manufacturer"(example   "manufacturer":"China")</li>
     *                    <li>"Color"(example "color":{"r":"255","b":"0","g":"0"})</li></ul>
     */
    public void add_if_min(String elementJson) {
       // System.out.println(elementJson +"  ДОБАВЛЕН");

        try {
            Umbrella umbrella = this.toUmbrella(elementJson);
            Integer idInt = umbrella.getId();
            boolean[] isMin = { true };

            collection.forEach((key, value) -> {
                 if (value.compareTo(umbrella) < 0) {
                     isMin[0] = false;
                 }
            });

            if (isMin[0]){
                collection.put(idInt, umbrella);
                this.saveToFile("collection.txt");
            }
        }
        catch (Exception e) {
            System.out.println("Ошибка - некорректная строка.");
        }

    }

    /**
     * Adds all items from the specified file to the collection
     * @param path -  path to the file
     * @throws Exception An exception that occurs when you specify the path incorrectly
     */
    public void importCommand(String path) throws Exception {

        File file = new File(path);
        try {
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                String json = "";
                while (scanner.hasNextLine())
                    json += scanner.nextLine();
                scanner.close();

                JSONParser parser = new JSONParser();

                JSONArray array = (JSONArray) parser.parse(json);
                array.forEach((object) -> {
                    JSONObject jsonObj = (JSONObject) object;
                    Umbrella umbrella = toUmbrella(jsonObj);
                    collection.put(umbrella.getId(), umbrella);
                    System.out.println("file imported");
                });
            } else {
                System.out.println("Файла нет");
            }
            this.saveToFile("collection");
        }catch (Exception e){System.out.println("Неправильный формат файла");}
    }

    public void saveToFile(String path) {
        JSONArray array = new JSONArray();
        collection.forEach((key, value)->{
            Umbrella umbrella = value;
            JSONObject obj = new JSONObject();
            obj.put("materialName", umbrella.getMaterialName());
            obj.put("manufacturer",umbrella.getManufacturer());
            obj.put("id", Integer.toString(umbrella.getId()));
            JSONObject dateObj = new JSONObject();
            //dateObj.put()
            obj.put("date",umbrella.getDate());


            JSONObject colorObj = new JSONObject();
            colorObj.put("r", Integer.toString(umbrella.getColor().getR()));
            colorObj.put("g", Integer.toString(umbrella.getColor().getG()));
            colorObj.put("b", Integer.toString(umbrella.getColor().getB()));

            obj.put("color", colorObj);

            array.add(obj);
        });

        String jsonStr = array.toJSONString();

        File fileForWrite = new File(path);
        try{
            FileOutputStream output = new FileOutputStream(fileForWrite);
            PrintStream writer = new PrintStream(output);
            writer.print(jsonStr);
            writer.close();}catch(Exception e){
            System.out.println("Файл не найден");
        }
    }

}