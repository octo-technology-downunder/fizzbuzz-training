package Review;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class Runner {
    public static void main(String[] args) throws SQLException {
        AmazonS3 s3 = new AmazonS3Client();
        ObjectListing ol = s3.listObjects("xxxxx");
        List<S3ObjectSummary> objects = ol.getObjectSummaries();
        for (int i = 0; i < 10; i++) {
            arrangeData(s3, objects.get(i).getKey());
        }
        for (S3ObjectSummary object : objects) {
            if (object.getKey().contains("16-11")) {
                arrangeData(s3, object.getKey());
            }
        }
        //test(s3);
        //arrangeData(s3);
        //requestAthena();
    }

    private static void test(AmazonS3 s3) {
        S3Object object = s3.getObject("bucket", "test");
        S3ObjectInputStream stream = object.getObjectContent();
        try {
            String myString = IOUtils.toString(stream);
            System.out.print(myString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void arrangeData(AmazonS3 s3, String name) {
        S3Object object = s3.getObject("xxx", name);
        S3ObjectInputStream stream = object.getObjectContent();
        try {
            String myString = IOUtils.toString(stream).replace("\n", "").replace("@", "");
            //System.out.println(myString);
            JSONObject changeList = new JSONObject(myString);
            Object objectChelou = changeList.get("changeList");
            String changeListString = objectChelou.toString();
            changeListString = changeListString.replace("},{", "}\n{");
            changeListString = changeListString.substring(1, changeListString.length() - 1);
            s3.putObject("bucket", name.replace("backup", "test1"), changeListString);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void requestAthena() throws SQLException {
        Properties info = new Properties();
        info.put("user", "ddddddddddd");
        info.put("password", "xxxx");
        info.put("s3_staging_dir", "xx");

        Connection connection = DriverManager.getConnection("jdbc:awsathena://athena.us-east-1.amazonaws.com:443/", info);
        Statement statement = connection.createStatement();
        //String request = "CREATE EXTERNAL TABLE tableName ( Col1 String ) LOCATION ‘s3://bucket/tableLocation);";
        ResultSet queryResults = statement.executeQuery("SELECT attributes.dateTimeMap.eobt FROM xxxx where \"type\" = 'ActivityAttributeChange'");

        while (queryResults.next()) {
            //Retrieve table column.
            String object = queryResults.getString("oebt");

            //Display values.
            System.out.println("test" + object);
        }
    }
}
