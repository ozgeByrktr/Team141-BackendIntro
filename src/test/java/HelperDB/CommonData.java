package HelperDB;

import com.github.javafaker.Faker;
import lombok.Getter;

import java.sql.Array;
import java.sql.Date;
import java.util.List;
import java.util.Map;
@Getter
public class CommonData {
    public static Faker faker=new Faker();
    public static int[] result;
    //US 08  category_id, weight, same_day, next_day, sub_city, outside_city, position, status (should be 0)
    private int category_id;
    private int weight;
    private double same_day;
    private double next_day;
    private double sub_city;
    private double outside_city;
    private int position;
    private int status;
    public CommonData() {
    this.category_id =faker.number().numberBetween(1,6);
    this.weight = faker.number().numberBetween(1,10);
    this.same_day=faker.random().nextDouble();
    this.next_day=faker.random().nextDouble();
    this.sub_city=faker.random().nextDouble();
    this.outside_city=faker.random().nextDouble();
    this.position=faker.number().numberBetween(1,5);
    this.status=0;
    }

    private List<Date> creationDates;
    public static Map<Integer,String> sozialLinks;

}
