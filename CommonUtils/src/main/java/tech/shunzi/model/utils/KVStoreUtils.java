package tech.shunzi.model.utils;

import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * OpenSource KV Store MINIO
 */
public class KVStoreUtils {

    /**
     * Setting parameters
     */
    private final static String URL = "https://play.minio.io:9000";
    private final static String ACCESS_KEY = "Q3AM3UQ867SPQQA43P2F";
    private final static String SECRET_KEY = "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG";

    private final static String SELF_URL = "http://114.116.234.136:9000";
    private final static String SELF_ACCESS_KEY = "admin";
    private final static String SELF_SECRET_KEY = "admin123456";


    /**
     * Test Data
     */
    // lower case S3 standard
    private final static String OBJECT_BUCKET = "test";
    private final static String OBJECT_KEY = "key";
    private final static String FILE_NAME = "C:\\Users\\Elvis Zhang\\Pictures\\Beach_Man_Coast_Landscape_HDR_Photography Wallpaper_2560x1600[10wallpaper.com].jpg";
    private final static String DIR_NAME = "C:\\Users\\12245\\Pictures\\Test";
    private final static String DIR_KEY = "dirkey";


    public static MinioClient getClient() throws InvalidPortException, InvalidEndpointException {
        return new MinioClient(SELF_URL, SELF_ACCESS_KEY, SELF_SECRET_KEY);
    }


    public static void main(String[] args) throws Exception  {

//        MinioClient minioClient = ();
//        List<Bucket> bucketList = minioClient.listBuckets();
//        System.out.println(bucketList);

//        PutTask task = new PutTask();
//        for (int i = 0 ; i < 10; i++) {
//            Thread thread = new Thread(task);
//            thread.start();
//        }
    }

    static class PutTask implements Runnable {

        @Override
        public void run() {
            try {
                MinioClient minioClient = getClient();
                for (int i = 0; i < 10; i++) {
                    minioClient.putObject(OBJECT_BUCKET, OBJECT_KEY, FILE_NAME);
                }
            } catch (InvalidPortException | InvalidEndpointException | InvalidBucketNameException | NoSuchAlgorithmException | InsufficientDataException | IOException | InvalidKeyException | NoResponseException | XmlPullParserException | ErrorResponseException | InternalException | InvalidArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    public void test() {
        try {
            // 使用Minio服务的URL，端口，Access key和Secret key创建一个MinioClient对象
            MinioClient minioClient = getClient();
            // 检查存储桶是否已经存在
            boolean isExist = minioClient.bucketExists(OBJECT_BUCKET);
            if (isExist) {
                System.out.println("Bucket already exists.");
            } else {
                // Create Bucket
                minioClient.makeBucket(OBJECT_BUCKET);
            }
            // 使用putObject上传一个文件到存储桶中。会覆盖同名对象
            String key = new File(FILE_NAME).getName();
            minioClient.putObject(OBJECT_BUCKET, OBJECT_KEY, FILE_NAME);
            System.out.println(String.format("%s is successfully uploaded as %s to '%s' bucket.", FILE_NAME, key, OBJECT_BUCKET));
//
//            // Upload a directory
//            minioClient.putObject(OBJECT_BUCKET, DIR_KEY, DIR_NAME);

            // Get
            InputStream inputStream = minioClient.getObject(OBJECT_BUCKET, OBJECT_KEY);
            System.out.println("Is inputStream of uploaded object null ? " + (null == inputStream));

            // Get Metedata
            ObjectStat objectStat = minioClient.statObject(OBJECT_BUCKET, OBJECT_KEY);
            System.out.println("Metadata of object : " + objectStat.toString());

            // List
            System.out.println("List objects:");
            Iterable<Result<Item>> objects = minioClient.listObjects(OBJECT_BUCKET);
            for (Result<Item> itemResult : objects) {
                Item item = itemResult.get();
                System.out.println(item.objectName());
            }


        } catch (MinioException | NoSuchAlgorithmException | IOException | InvalidKeyException | XmlPullParserException e) {
            System.out.println("Error occurred: " + e);
        }
    }
}
