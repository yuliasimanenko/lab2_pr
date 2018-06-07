package lab1;

import org.json.simple.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

        }
    }
    Lock l = new ReentrantLock();
     public Object[] getSortedUmbrellas() {
         l.lock();
        Object[] arr =  collection.values().toArray();
        Arrays.sort(arr);
        l.unlock();
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
     *                    <li>"lab1.Color"(example "color":{"r":"255","b":"0","g":"0"})</li></ul>
     */
    public void remove_greater(String elementJson) {
        try {
            Umbrella umbrella = this.toUmbrella(elementJson);
            Integer idInt = umbrella.getDateTime().getYear();
//Stream API
            collection.forEach((key, value) -> {
                if (value.compareTo(umbrella) > 0) {
                    collection.replace(idInt,umbrella);
                    this.saveToFile("collection.txt");
                }
            });

        }
        catch (Exception e) {
            System.out.println("Ошибка - некорректная строка.");
        }

    }
    public Integer  getIdandUmbrella(String element) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject ele = (JSONObject) parser.parse(element);
        Umbrella umbrella = this.toUmbrella(ele);
        return  umbrella.getDateTime().getYear();

    }
    public Umbrella  getUmbrella(String element) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject ele = (JSONObject) parser.parse(element);
        Umbrella umbrella = this.toUmbrella(ele);
        return  umbrella;

    }
//    public void consolOut (){
//        for (Umbrella umbrella : collection.values()) {
//            //делать что-то с umbrella
//            String manufac = umbrella.getManufacturer();
//            String id = Integer.toString(umbrella.getId());
//            Color color = umbrella.getColor();
//            String r = Integer.toString(color.getR());
//            String b = Integer.toString(color.getB());
//            String g = Integer.toString(color.getG());
//            System.out.println(id+ " Производства:"+manufac+"  Расцветка: "+ "R-"+r+" B-"+b+" G-"+g);
//        }
//    }


    public Umbrella toUmbrella(JSONObject element) {

        String manufacturer = (String) element.get("manufacturer");

        JSONObject color = (JSONObject) element.get("color");


        String dateStr = (String)element.get("date");
        LocalDateTime time = LocalDateTime.parse(dateStr);



        String rstring = (String) color.get("r");
        String gstring = (String) color.get("g");
        String bstring = (String) color.get("b");
        int rint = Integer.valueOf(rstring);
        int gint = Integer.valueOf(gstring);
        int bint = Integer.valueOf(bstring);
        Color colorObject = new Color(rint, gint, bint);

        Umbrella umbrella = new Umbrella( colorObject, manufacturer,time);

        return umbrella;
    }




    synchronized public Umbrella toUmbrella (String elementJSon) throws ParseException {
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
     *                    <li>"lab1.Color"(example "color":{"r":"255","b":"0","g":"0"})</li></ul>
     */
    public void add_if_min(String elementJson) {
        try {
            Umbrella umbrella = this.toUmbrella(elementJson);
            boolean[] isMin = { true };

            collection.forEach((key, value) -> {
                if (value.compareTo(umbrella) < 0) {
                    isMin[0] = false;
                }
            });

            if (isMin[0]){
                addUmbrella(umbrella.getDateTime().getYear(),umbrella);
            }
        }
        catch (Exception e) {
            System.out.println("Ошибка - некорректная строка.");
        }

    }
    public void deleteUmbrella(Integer year){ //delete but not show for the first time
        collection.forEach((key, value) -> {
            System.out.println("if");
            if (value.getDateTime().getYear()==year) {
                collection.remove(value.getDateTime().getYear());
                System.out.println("delete");
            }
            System.out.println("exit from deleteUmbr");
        });
        this.saveToFile("collection.txt");
    }
    public void change(Integer r1, Integer r2){
        collection.forEach((key, value) -> {
//            if (value.getColor().getB()==b1&&value.getColor().getG()==g1&&value.getColor().getR()==r1&&
//                    value.getManufacturer()==country1&&value.getGr().get(Calendar.YEAR)==year1) {
//                System.out.println("line is correct");
//                GregorianCalendar gregorianCalendar = new GregorianCalendar(year2,value.getGr().get(Calendar.MONTH),value.getGr().get(Calendar.DAY_OF_MONTH));
//                Color color = new Color(r2,g2,b2);
//                System.out.println("new Color");
//                Umbrella umbrella = new Umbrella(value.getId(),color,value.getMaterialName(),country2,gregorianCalendar);
//                System.out.println("new Umbrella");
//                collection.replace(value.getId(),value,umbrella);
//                System.out.println("replace");
//            }
            if(value.getDateTime().getYear()==r1){
                Umbrella.setYear(r1,value);
                //Umbrella umbrella = new Umbrella(value.getColor(),value.getManufacturer(),dr);
                //collection.replace(value.getId(),value,umbrella);
            }
        });
        this.saveToFile("collection.txt");
    }

    public void addUmbrella(Integer idInt,Umbrella umbrella){
        collection.put(idInt, umbrella);
        this.saveToFile("collection.txt");
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
                    collection.put(collection.size(), umbrella);
                });
            } else {
                System.out.println("Файла нет");
            }
            //this.saveToFile("collection");
        }catch (IOException e){System.out.println("Неправильный формат файла");
        e.printStackTrace();}
    }

    public void saveToFile(String path) {
        JSONArray array = new JSONArray();
        collection.forEach((key, value)->{
            Umbrella umbrella = value;
            JSONObject obj = new JSONObject();

            obj.put("manufacturer",umbrella.getManufacturer());
            obj.put("date",umbrella.getDateTime().toString());
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
            writer.close();}
            catch(Exception e){
            System.out.println("Файл не найден");
        }
    }

}
