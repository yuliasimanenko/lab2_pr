package lab1;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.sql.*;

public class ORMClass<T> {

    private Class objClass;
    private Queue<String> statements = new ArrayDeque<>();

    public ORMClass(Class classObj) {
        this.objClass = classObj;
    }

    public String create() {
        String tableName = objClass.getSimpleName();
        List<Field> annotatedFields = new ArrayList<>();

        Field[] fields = objClass.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.isAnnotationPresent(ORMField.class)) {
                annotatedFields.add(f);
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append(tableName).append(" (\n");
        sb.append("   Id int PRIMARY KEY, \n");

        for (int i = 0; i < annotatedFields.size(); i++) {
            Field f = annotatedFields.get(i);
            String fieldName = f.getName();
            String fieldType;



            switch (f.getType().getSimpleName()) {
                case "String":
                    fieldType = "text";
                    break;
                case "Integer":
                    fieldType = "int";
                    break;
                case "Color":
                    fieldType = "text";
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported type: " + f.getType().getSimpleName());
            }

            sb.append("   ").append(fieldName).append(" ").append(fieldType);
            if (i < annotatedFields.size() - 1)
                sb.append(", \n");
        }
        sb.append("\n);");

        String sql = sb.toString();
        statements.add(sql);
        return sql;

    }
    public String add(Umbrella u){
        StringBuilder strORM = new StringBuilder();
        strORM.append("INSERT INTO ").append(objClass.getSimpleName());
        strORM.append(" ").append("VALUES").append(" ( ");
        strORM.append((int)(Math.random()*200)).append(", ");
        strORM.append("\"" + u.getColor().toString() + "\"").append(", ");
        strORM.append("\""+u.getManufacturer()+"\"");

        strORM.append(" );");
        return strORM.toString();
        //statements.add(strORM.toString());

    }
    public String remove(Umbrella u){
        StringBuilder strORM = new StringBuilder();
        strORM.append("DELETE FROM ").append(objClass.getSimpleName());
        strORM.append(" ").append("WHERE").append(" ");
        strORM.append(" color like ");

        strORM.append("\"" + u.getColor().toString() + "\"").append(" and ");
        strORM.append("manufacturer like ");
        strORM.append("\""+u.getManufacturer()+"\"");

        strORM.append(" ;");
        return strORM.toString();
        //statements.add(strORM.toString());

    }
    public void replace(Umbrella u0,Umbrella u1){

        remove(u0);
        add(u1);

    }
    public String get(String s){
        StringBuilder strORM = new StringBuilder();
        strORM.append("SELECT * FROM ").append(objClass.getSimpleName());
        strORM.append(" WHERE ");
        strORM.append(s);


        strORM.append(" ;");
        //statements.add(strORM.toString());
        return strORM.toString();
    }

    public void printSQL() {
        while (statements.size() > 0) {
            System.out.println(statements.poll());
        }
    }

}
