import com.mysql.fabric.jdbc.FabricMySQLDriver;
import lab1.Color;
import lab1.ORMClass;
import lab1.Umbrella;

import java.sql.*;
import java.time.LocalDateTime;


public class DataBaseManager {

    private static final String URL = "jdbc:mysql://localhost:3306/base";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";



    public static void testORM(){

        Umbrella u = new Umbrella(new Color(5, 5, 5), "Sudan", LocalDateTime.now());
        Umbrella u2 = new Umbrella(new Color(5, 5, 5), "Denmark", LocalDateTime.now().minusYears(2).minusMonths(3));

        ORMClass<Umbrella> orm = new ORMClass<Umbrella>(Umbrella.class);
//        orm.create();
//        orm.add(u);
//        orm.remove(u);
//        orm.replace(u, u2);
//        orm.get("manufacturer like \"China\"");
        orm.printSQL();
    }
    public static void main(String args[]){//dataBaseMethod
        Connection connection;


        Umbrella u = new Umbrella(new Color(5, 5, 5), "Sudan", LocalDateTime.now());
        Umbrella u2 = new Umbrella(new Color(5, 5, 5), "Denmark", LocalDateTime.now().minusYears(2).minusMonths(3));
        Umbrella u3 = new Umbrella(new Color(65, 15, 125), "China", LocalDateTime.now().minusNanos(33456778));
        ORMClass<Umbrella> orm = new ORMClass<Umbrella>(Umbrella.class);

        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            System.out.println("ok");
        } catch (SQLException e) {
            System.out.println("Соединение с базой  не установлено");
        }


        try {
            connection= DriverManager.getConnection(URL,USERNAME,PASSWORD);
            Statement statement = connection.createStatement();
            statement.execute(orm.add(u3));//Insert

//            statement.executeUpdate();//Update addBa
            ResultSet resultSet=statement.executeQuery(orm.get("manufacturer like \"China\""));//Select
            while (resultSet.next()){
                int i =resultSet.getInt(1);
                System.out.println(i);

            }
//            statement.addBatch();//несколько команд
//            statement.executeBatch();//запуск предыдущего
//            statement.cancel();


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
