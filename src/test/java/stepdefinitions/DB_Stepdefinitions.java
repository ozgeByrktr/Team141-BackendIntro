package stepdefinitions;

import HelperDB.CommonData;
import HelperDB.DeliveryCategories;
import Manage.Manage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static HelperDB.CommonData.result;
import static HelperDB.DeliveryCategories.generateCategory;
import static HelperDB.JDBC_Structure_Methods.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DB_Stepdefinitions extends Manage {
    CommonData data=new CommonData();
    public static Map<Integer,String> sozialLinks;
    @Given("Database connection established")
    public void database_connection_established() {
        createConnection();
    }
    @Given("Query prepared sozial links table")
    public void query_prepared_sozial_links_table() throws SQLException {
    query=getSozialLinks();
    resultSet=getStatement().executeQuery(query);
    }
    @Given("the results of the social links table are compared")
    public void the_results_of_the_social_links_table_are_compared() throws SQLException {
        sozialLinks=new HashMap<>();
        while (resultSet.next()){
            sozialLinks.put(resultSet.getInt("id"),resultSet.getString("name"));

        }
        if(!sozialLinks.isEmpty()){
            for (int i = 0; i <sozialLinks.size() ; i++) {
                //buraya expected liste eklenebilecek
                System.err.println(sozialLinks.get(i));
            }
        }else{
            assertFalse("ResultsetEMpty", resultSet.next());
        }
    }
    @Given("Database closed")
    public void database_closed() {
        closeConnection();
    }

    /** US08  **/

    @When("I insert the new delivery charge into the database")
    public void i_insert_the_new_delivery_charge_into_the_database() throws SQLException {
    query=getDelivery_charges();
    preparedStatement=getPraperedStatement(query);
    /**category_id, weight, same_day, next_day, sub_city, outside_city, position, status (should be 0)*/
    preparedStatement.setInt(1,data.getCategory_id());
    preparedStatement.setInt(2,data.getWeight());
    preparedStatement.setDouble(3,data.getSame_day());
    preparedStatement.setDouble(4,data.getNext_day());
    preparedStatement.setDouble(5,data.getSub_city());
    preparedStatement.setDouble(6,data.getOutside_city());
        preparedStatement.setInt(7,data.getPosition());
        preparedStatement.setInt(8,data.getStatus());
    }
    @Then("Verify that {int} added to the table")
    public void Verify_that_added_to_the_table(int row) {
        int rowCount=0;
        try {
            rowCount= preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        assertEquals(row,rowCount);
    }

    /**   US10     **/
    @Given("Prepare query to insert {int} data entry into the deliverycategories table")
    public void prepare_query_to_insert_data_entry_into_the_deliverycategories_table(int count) throws SQLException {
        query = getDelivery_categories();
        preparedStatement = getPraperedStatement(query);
        List<DeliveryCategories> categories = generateCategory(count);
        int flag = 0;
        for (DeliveryCategories category : categories) {
            preparedStatement.setString(1, categories.get(flag).getTitle());
            preparedStatement.setInt(2, categories.get(flag).getStatus());
            preparedStatement.setInt(3, categories.get(flag).getPosition());
            preparedStatement.setTimestamp(4, categories.get(flag).getCreatedAt());
            preparedStatement.setTimestamp(5, categories.get(flag).getCreatedAt());

            preparedStatement.addBatch();// her bir kategori için bir batch eklenir.
            flag++;
            if (flag == categories.size()) {
                result = preparedStatement.executeBatch();
            }
        }
    }
    @Given("Verify bulk {int} data added to the table")
    public void verify_bulk_data_added_to_the_table(int rowCount) {
        System.err.println(result.length + "RECORD IS Successfully Added");
        assertEquals(rowCount,result.length);
    }

}
